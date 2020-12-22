const router = require('express').Router();

router.get('/', (req, res) => {
  res.json({
    title: 'Capstone Project',
    credits: 'Acdaling Edusei',
    background:
      'https://images.pexels.com/photos/3201478/pexels-photo-3201478.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260',
  });
});

module.exports = router;
