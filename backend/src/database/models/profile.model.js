const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const ProfileSchema = new Schema({
  email: String,
  phoneNumber: String,
  verificationCode: Number,
});

const Profile = mongoose.model('Profile', ProfileSchema);

module.exports = Profile;
