const { retrieveTokenAndDecode } = require('../routes/helper');

const checkToken = async (req, res, next) => {
  try {
    const user = await retrieveTokenAndDecode(req.headers.authorization);
    if (user) {
      next();
    } else {
      res.json({ msg: 'No or invalid JWT-token found' }).status(401);
    }
  } catch (error) {
    console.log(`token error: ${error}`);
  }
};

module.exports = { checkToken };
