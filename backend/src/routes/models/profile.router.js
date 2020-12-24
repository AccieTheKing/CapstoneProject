const router = require('express').Router();
const ProfileModel = require('../../database/models/profile.model');
const profileMockData = require('../data/profile.json');
const {
  sendEmail,
  retrieveTokenAndDecode,
  encodeObjectAndRetrievToken,
} = require('../helper/index');

router.get('/', async (req, res) => {
  try {
    const user = await retrieveTokenAndDecode(req.headers.authorization);
    ProfileModel.findOne({
      phoneNumber: user.phone_number,
    }).then((person) => {
      const signedInUser = {
        ...profileMockData,
        phone_number: person.phoneNumber,
        email_address: person.email,
      };
      res.json(signedInUser).status(200);
    });
  } catch (error) {
    res.json({ msg: 'No or invalid token found' }).status(401);
    console.log(`Something went wrong with getting the profile: ${error}`);
  }
});

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
    // res.json({ msg: 'wrong verification token' });
    console.log(`something went wrong ${error}`);
  }
});

router.post('/getverificationcode', async (req, res) => {
  const phoneNumber = req.body.phone_number; // user phone number
  const emailAddress = req.body.email_address; // user email address
  try {
    // check if user already exist in database
    const foundProfile = await ProfileModel.findOne({
      email: emailAddress,
    });
    sendEmail(foundProfile.email, foundProfile.verificationCode);
    res.status(200);
  } catch (error) {
    // create and save user if not existing with the verification code
    const randomVerificationCode = Math.floor(1000 + Math.random() * 8999); // generates random 4 digit number
    // send email to client with code for verification
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
