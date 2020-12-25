const router = require('express').Router();
const ProfileModel = require('../../database/models/profile.model');
const profileMockData = require('../data/profile.json');
const {
  sendEmail,
  retrieveTokenAndDecode,
  encodeObjectAndRetrievToken,
} = require('../helper/index');

/**
 * This endpoint is protected and only works with a valud JWT-token. It retrieves the
 * stored data of the user.
 */
router.get('/', async (req, res) => {
  try {
    const user = await retrieveTokenAndDecode(req.headers.authorization);
    const foundUser = await ProfileModel.findOne({
      phoneNumber: user.phone_number,
    });

    const signedInUser = {
      ...profileMockData,
      phone_number: foundUser.phoneNumber,
      email_address: foundUser.email,
    };

    res.json(signedInUser).status(200);
  } catch (error) {
    res.json({ msg: 'No or invalid token found' }).status(401);
    console.log(`Something went wrong with getting the profile: ${error}`);
  }
});

/**
 * This endpoint will check if the user exists inside the database and check the given code
 * of the client sent to this endpoint to verify the identity of the client.
 *
 * When the verification code is correct a JSON Web Token will be created and send back as response
 * for futher calls to the protected routes to the backend
 */
router.post('/sendverificationcode', async (req, res) => {
  try {
    const foundUser = await ProfileModel.findOne({
      email: req.body.email_address,
    });

    if (foundUser && foundUser.verificationCode === req.body.verificationCode) {
      const signedInUser = {
        ...profileMockData,
        phone_number: foundUser.phoneNumber,
        email_address: foundUser.email,
      };

      const token = await encodeObjectAndRetrievToken(signedInUser);
      res.json({ authToken: token });
    } else res.json({ msg: 'wrong verification token' });
  } catch (error) {
    res.json({ msg: 'something went wrong with reading code' }); // eventualy send a message to the client
    console.log(`something went wrong ${error}`);
  }
});

/**
 * This endpoint will read the phone number and email address from the body of the request.
 * If the user already exists inside the backend, send a mail to the provided email with the already stored
 * verification code. This code is needed to proceed to the next screen inside the app
 *
 * If the user doesn't exist in the backend a random 4 digit code will be generated and send to the provided email
 * address.
 */
router.post('/getverificationcode', async (req, res) => {
  const phoneNumber = req.body.phone_number;
  const emailAddress = req.body.email_address;

  try {
    const foundProfile = await ProfileModel.findOne({ email: emailAddress });
    sendEmail(foundProfile.email, foundProfile.verificationCode);

    res.status(200);
  } catch (error) {
    const randomVerificationCode = Math.floor(1000 + Math.random() * 8999); // generates random 4 digit number
    const emailResponseID = await sendEmail(
      emailAddress,
      randomVerificationCode
    );

    const profileModel = new ProfileModel({
      email: emailAddress,
      phoneNumber: phoneNumber,
      verificationCode: randomVerificationCode,
    });
    profileModel.save();
    console.log(
      `something went wrong with retrieving verification code: ${error}`
    );
  }
});

module.exports = router;
