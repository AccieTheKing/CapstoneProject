const router = require('express').Router();
const mailer = require('nodemailer');
const profile_dataset = require('../data/profile.json');

let profile = profile_dataset;
let currentCustomer = {};

initProfileRouter();

router.get('/:phone_number', async (req, res) => {
  res.json(currentCustomer);
});

router.post('/save', async (req, res) => {
  const phoneNumber = req.body.phone_number; // user phone number
  const emailAddress = req.body.email_address; // user email address

  // send mail with defined transport object
  let info = await transporter.sendMail({
    from: `"Its a test😎 👻" ${process.env.MY_EMAIL_ADDRESS}`, // sender address
    to: `${emailAddress}`, // list of receivers
    subject: 'Hello ✔', // Subject line
    text: 'Hello world?', // plain text body
  });

  // update user profile
  const addCustomer = {
    ...profile,
    phone_number: phoneNumber,
    email_address: emailAddress,
  };

  currentCustomer = addCustomer; // push into array for later use

  res.json(currentCustomer); // send user
});

async function initProfileRouter() {
  mailer.createTransport({
    service: process.env.EMAIL_SERVICES,
    auth: {
      user: process.env.MY_EMAIL_ADDRESS,
      pass: process.env.MY_EMAIL_PASSWORD,
    },
  });
}
module.exports = router;
