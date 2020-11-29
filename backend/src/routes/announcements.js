const router = require('express').Router();

const announcement_dataset = require('./data/announcement.json');

router.get('/', async (req, res) => {
  res.json(announcement_dataset);
});

module.exports = router;
