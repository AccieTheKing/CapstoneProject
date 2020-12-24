const router = require('express').Router();
const ProfileModel = require('../../database/models/profile.model');
const profile_dataset = require('../data/profile.json');
const sendEmail = require('../helper/index');

let profile = profile_dataset;
let currentCustomer = {};

initProfileRouter();

router.get('/:phone_number', async (req, res) => {
  res.json(currentCustomer);
});

router.post('/save', async (req, res) => {
  const phoneNumber = req.body.phone_number; // user phone number
  const emailAddress = req.body.email_address; // user email address

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
