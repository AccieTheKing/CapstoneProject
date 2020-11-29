const router = require('express').Router();

const all_products_list = require('./data/products.json'); // all the products available
let cart = []; // user cart

router.get('/', async (req, res) => {
  res.json(all_products_list);
});

router.get('/cart/:profileid', async (req, res) => {
  res.json(cart);
});

router.post('/cart/add/:productid/:profileid', async (req, res) => {
  const selected_product = all_products_list[req.params.productid]; // user selected product
  updateProductWhenFound(selected_product).then(() => {
    res.json(cart);
  });
});

/**
 * This method will check if the product is already inside the cart of the user
 * and changes its price and amount into a single product
 *
 * @param {*} product the selected product by the user
 */
const updateProductWhenFound = async (product) => {
  // find product index
  const foundProductIndex = cart.findIndex(
    (searchingProduct) => searchingProduct.id === product.id
  );

  // if no product found in cart, it returns -1
  if (foundProductIndex > -1) {
    const product = cart[foundProductIndex];
    const original_product = all_products_list[product.id]; // original product, without any changes

    // modify the amount of the product
    const updatedProductWithNewAmount = {
      ...product,
      amount: product.amount + 1,
    };

    // update product price with the new amount
    const updatedProduct = {
      ...updatedProductWithNewAmount,
      price: updatedProductWithNewAmount.amount * original_product.price,
    };

    cart[foundProductIndex] = updatedProduct; // replace the stored product with our modified one
  } else {
    cart.push(product); // add product into the cart
  }
};

module.exports = router;
