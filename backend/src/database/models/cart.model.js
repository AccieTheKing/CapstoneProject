const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const CartSchema = new Schema({
  userEmail: String,
  paid: Boolean,
  items: [String],
});

const Cart = mongoose.model('Cart', CartSchema);

module.exports = Cart;
