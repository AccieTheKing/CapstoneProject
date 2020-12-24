const mailer = require('nodemailer');

const transporter = mailer.createTransport({
  service: process.env.EMAIL_SERVICES,
  auth: {
    user: process.env.MY_EMAIL_ADDRESS,
    pass: process.env.MY_EMAIL_PASSWORD,
  },
});

const sendEmail = async (emailAddress, verificationCode) => {
  const fromTitle = 'Capstone backend😎👻';
  const subject = 'Capstone Verification code';
  const text = `Thanks for using this app,\n\n Your verification code is: ${verificationCode} \n Enter that code inside the app and continue  \n\n Greetings from the dev Acdaling`;

  // send mail with defined transport object
  return transporter.sendMail({
    from: `${fromTitle} ${process.env.MY_EMAIL_ADDRESS}`, // sender address
    to: `${emailAddress}`, // list of receivers
    subject, // Subject line
    text,
  });
};

const jwt = require('jsonwebtoken');
const retrieveTokenAndDecode = async (authHeader) => {
  if (authHeader) {
    const token = authHeader.split('Bearer ')[1];
    return jwt.verify(token, process.env.JWT_SECRET);
  } else return false;
};

module.exports = {
  sendEmail,
  retrieveTokenAndDecode,
};
