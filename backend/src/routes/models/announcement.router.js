const router = require('express').Router();
const announcement_dataset = require('../data/announcement.json');

router.get('/', async (req, res) => {
  res.json(announcement_dataset);
});

router.get('/:id', async (req, res) => {
  const found_announcement = announcement_dataset[req.params.id];
  if (found_announcement) res.json(found_announcement);
});

module.exports = router;
