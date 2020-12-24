const router = require('express').Router();
const ProfileModel = require('../../database/models/profile.model');
const profile_dataset = require('../data/profile.json');
const { sendEmail, retrieveTokenAndDecode } = require('../helper/index');

let profile = profile_dataset;

router.get('/', async (req, res) => {
  try {
    const user = await retrieveTokenAndDecode(req.headers.authorization);
    ProfileModel.findOne({
      phoneNumber: user.phone_number,
    }).then((person) => {
      const signedInUser = {
        ...profile,
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
  const foundUser = await ProfileModel.findOne({
    email: req.body.email_address,
  });

  if (foundUser && foundUser.verificationCode === req.body.verificationCode) {
    const signedInUser = {
      ...profile,
      phone_number: foundUser.phoneNumber,
      email_address: foundUser.email,
    };

    const token = `Bearer ${jwt.sign(signedInUser, process.env.JWT_SECRET)}`;
    res.json({ authToken: token });
  } else res.json({ msg: 'wrong verification token' });
});

router.post('/getverificationcode', async (req, res) => {
  const phoneNumber = req.body.phone_number; // user phone number
  const emailAddress = req.body.email_address; // user email address

  // check if user already exist in database
  ProfileModel.findOne({
    email: emailAddress,
  })
    .then(async (profile) => {
      if (profile) {
        sendEmail(profile.email, profile.verificationCode);
        res.status(200);
      } else {
        // create and save user if not existing with the verification code
        const randomVerificationCode = Math.floor(1000 + Math.random() * 8999); // generates random 4 digit number
        const emailResponseID = await sendEmail(
          emailAddress,
          randomVerificationCode
        ); // send email to client
        const profileModel = new ProfileModel({
          email: emailAddress,
          phoneNumber: phoneNumber,
          verificationCode: randomVerificationCode,
        });
        profileModel.save();
      }
    })
    .catch((error) => console.log(error));
});

module.exports = router;
