const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const ProfileSchema = new Schema({
  email: String,
  phoneNumber: String,
  verificationCode: Number,
});

const Profile = mongoose.model('Profile', ProfileSchema);

const findByEmail = async (email) => {
  const foundProfile = await Profile.findOne({ email });
  if (foundProfile) return foundProfile;
  return false;
};

const findByPhoneNumber = async (phoneNumber) => {
  const foundProfile = await Profile.findOne({ phoneNumber });
  if (foundProfile) return foundProfile;
  return false;
};

const storeProfile = async (email, phoneNumber, verificationCode) => {
  try {
    const profileModel = new ProfileModel({
      email,
      phoneNumber,
      verificationCode,
    });
    profileModel.save();
  } catch (error) {
    console.log(`something went wrong with saving the profileModel: ${error}`);
  }
};

module.exports = {
  storeProfile,
  findByEmail,
  findByPhoneNumber,
};
