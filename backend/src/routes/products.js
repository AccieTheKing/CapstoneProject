const router = require('express').Router();

const all_products_list = require('./data/products.json'); // all the products available

// user cart
let userCart = {
  phoneNumber: null,
  cart: {
    items: [],
    totalPrice: 0,
  },
};

router.get('/', async (req, res) => {
  res.json(all_products_list);
});

router.get('/:productID', async (req, res) => {
  const selectedUserProduct = all_products_list[req.params.productID];
  res.json(selectedUserProduct);
});

router.get('/cart/:phoneNumber', async (req, res) => {
  userCart = {
    ...userCart,
    phoneNumber: req.params.phoneNumber,
  };

  res.json(userCart.cart);
});

router.post('/cart/add/:productid/:phoneNumber', async (req, res) => {
  const selected_product = all_products_list[req.params.productid]; // user selected product

  // fill the user with the phone number
  userCart = {
    ...userCart,
    phoneNumber: req.params.phoneNumber,
  };

  updateProductWhenFound(selected_product).then(() => {
    res.json(userCart.cart);
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
  const foundProductIndex = userCart.cart.findIndex(
    (searchingProduct) => searchingProduct.id === product.id
  );

  // if no product found in cart, it returns -1
  if (foundProductIndex > -1) {
    const product = userCart.cart.items[foundProductIndex]; // found product in the cart
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

    userCart.cart.items[foundProductIndex] = updatedProduct; // replace the stored product with our modified one
  } else {
    userCart.cart.items.push(product); // add product into the cart
  }
};

module.exports = router;
