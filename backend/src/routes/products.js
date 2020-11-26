const router = require('express').Router();

const all_products = require('./data/products.json'); // all the products available
let cart = []; // user cart

router.get('/', async (req, res) => {
  res.json(all_products);
});

router.get('/cart/:profileid', async (req, res) => {
  res.json(cart);
});

router.post('/cart/add/:productid/:profileid', async (req, res) => {
  const selected_product = all_products[req.params.productid]; // user selected product
  cart.push(selected_product); // add product to cart
  console.log(cart);
  res.json(cart); // send the cart back
  // console.log(req.params.productid, req.params.profileid);
});

module.exports = router;
