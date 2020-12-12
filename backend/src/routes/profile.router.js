const router = require('express').Router();
const profile_dataset = require('./data/profile.json');

let profile = profile_dataset;
let currentCustomer = null;

router.get('/:phone_number', async (req, res) => {
  const profile_data = active_customers.find(
    (el) => el.phone_number === req.params.phone_number
  );
  res.json(profile_data);
});

router.post('/save', async (req, res) => {
  const phoneNumber = req.body.phone_number; // phone number
  const emailAddress = req.body.email_address; // email address

  // update user profile
  const addCustomer = {
    ...profile,
    phone_number: phoneNumber,
    email_address: emailAddress,
  };

  currentCustomer = addCustomer; // push into array for later use

  res.json(addCustomer); // send user
});

module.exports = router;
