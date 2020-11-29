const router = require('express').Router();

router.get('/', async (req, res) => {
  res.json([
    {
      title: 'Launch of the app',
      banner_image: 'https://images.acdaling.nl/me.jpg',
      text: 'This is the first announcement, enjoy the app!',
    },
    {
      title: 'Software development',
      banner_image:
        'https://www.sbcnl.nl/wp-content/uploads/2020/09/Software-Development-SBCNL.jpeg',
      text: 'This app is going to be greath!',
    },
    {
      title: 'Project Mobile Development',
      banner_image:
        'https://lvivity.com/wp-content/uploads/2019/02/mobile-app-brief.jpg',
      text: 'I have to think about writing some more content for this app',
    },
    {
      title: 'Atos project',
      banner_image:
        'https://media.glassdoor.com/sqll/10686/atos-squarelogo-1579011562667.png',
      text: 'We are building a greath app with the team',
    },
    {
      title: 'The last announcement',
      banner_image:
        'https://www.simplilearn.com/ice9/free_resources_article_thumb/COVER-IMAGE_Digital-Selling-Foundation-Program.jpg',
      text: 'What will my grade be for this app?',
    },
  ]);
});

module.exports = router;