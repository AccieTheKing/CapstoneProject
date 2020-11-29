const router = require('express').Router();
const profile_dataset = require('./data/profile.json');

router.get('/', async (req, res) => {
  res.json(profile_dataset);
});

module.exports = router;
