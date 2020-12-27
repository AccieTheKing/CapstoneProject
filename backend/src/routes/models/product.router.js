const router = require('express').Router();
const ProfileModel = require('../../database/models/profile.model');
const stripe = require('stripe')(process.env.STRIPE_SECRET_KEY);
const { retrieveTokenAndDecode } = require('../helper/index');
const all_products_list = require('../data/products.json'); // all the products available

const PRODUCT_ASSIGNMENT_METHODS = {
  ADD: 'ADD',
  INCREMENT: 'INCREMENT',
  DECREMENT: 'DECREMENT',
  REMOVE: 'REMOVE',
};

// user cart
let userCart = {
  phoneNumber: null,
  cart: {
    products: [],
    price: 0.0,
  },
};

router.get('/', async (req, res) => {
  try {
    retrieveTokenAndDecode(req.headers.authorization)
      .then((user) => {
        res.json(all_products_list);
      })
      .catch((error) => res.json({ msg: 'Invalid token' }));
  } catch (error) {
    console.log(
      `Something went wrong with fetching list of products: ${error}`
    );
  }
});

router.get('/:productID', async (req, res) => {
  try {
    retrieveTokenAndDecode(req.headers.authorization).then((user) => {
      const selectedUserProduct = all_products_list[req.params.productID];
      res.json(selectedUserProduct);
    });
  } catch (error) {}
});

router.get('/cart/:phoneNumber', async (req, res) => {
  try {
    retrieveTokenAndDecode(req.headers.authorization).then((user) => {
      res.json(userCart.cart);
    });
  } catch (error) {}
});

router.post('/cart/add/:productid/:phoneNumber', async (req, res) => {
  try {
    retrieveTokenAndDecode(req.headers.authorization).then((user) => {
      const selected_product = all_products_list[req.params.productid]; // user selected product
      updateProductWhenFound(
        selected_product,
        PRODUCT_ASSIGNMENT_METHODS.ADD
      ).then((productCart) => {
        let totalPrice = 0.0;
        productCart.cart.products.map(async (value) => {
          totalPrice += value.price;
        });
        userCart.cart.price = totalPrice;

        res.json({
          products: productCart.cart.products,
          price: totalPrice,
        });
      });
    });
  } catch (error) {}
});

/**
 * Increase and decrease the amount of items inside the cart
 */
router.post('/cart/increase/:productid/:phoneNumber', async (req, res) => {
  try {
    retrieveTokenAndDecode(req.headers.authorization).then((user) => {
      const selected_product = all_products_list[req.params.productid]; // user selected product

      updateProductWhenFound(
        selected_product,
        PRODUCT_ASSIGNMENT_METHODS.INCREMENT
      ).then((productCart) => {
        let totalPrice = 0.0;
        productCart.cart.products.map(async (value) => {
          totalPrice += value.price;
        });
        userCart.cart.price = totalPrice;

        res.json({
          products: productCart.cart.products,
          price: totalPrice,
        });
      });
    });
  } catch (error) {}
});

router.post('/cart/decrease/:productid/:phoneNumber', async (req, res) => {
  try {
    retrieveTokenAndDecode(req.headers.authorization).then((user) => {
      const selected_product = all_products_list[req.params.productid]; // user selected product

      updateProductWhenFound(
        selected_product,
        PRODUCT_ASSIGNMENT_METHODS.DECREMENT
      ).then(async (productCart) => {
        let totalPrice = 0.0;
        productCart.cart.products.map(async (value) => {
          totalPrice += value.price;
        });
        userCart.cart.price = totalPrice;
        res.json({
          products: productCart.cart.products,
          price: totalPrice,
        });
      });
    });
  } catch (error) {}
});

router.post('/cart/create-payment-intent', async (req, res) => {
  try {
    const user = await retrieveTokenAndDecode(req.headers.authorization);
    const { items } = req.body;
    const totalPrice = userCart.cart.price * 100;
    const paymentIntent = await stripe.paymentIntents.create({
      amount: totalPrice.toFixed(0),
      currency: 'eur',
      payment_method_types: ['card'],
      receipt_email: user.email,
    });

    res.send({ clientSecret: paymentIntent.client_secret });
  } catch (error) {
    console.log('payment error', error);
    res.json({ msg: 'whoops' });
  }
});

/**
 * This method will check if the product is already inside the cart of the user
 * and changes its price and amount into a single product
 *
 * @param {*} product the selected product by the user
 */
const updateProductWhenFound = async (product, method) => {
  const { exists, productIndex } = await checkIfProductExists(product);

  if (exists) {
    const product = userCart.cart.products[productIndex]; // found product in the cart
    const original_product = all_products_list[product.id]; // original product, without any changes

    switch (method) {
      case PRODUCT_ASSIGNMENT_METHODS.ADD:
        await updateProduct(
          PRODUCT_ASSIGNMENT_METHODS.ADD,
          product,
          original_product
        );
        break;
      case PRODUCT_ASSIGNMENT_METHODS.INCREMENT:
        await updateProduct(
          PRODUCT_ASSIGNMENT_METHODS.INCREMENT,
          product,
          original_product
        );
        break;
      case PRODUCT_ASSIGNMENT_METHODS.DECREMENT:
        await updateProduct(
          PRODUCT_ASSIGNMENT_METHODS.DECREMENT,
          product,
          original_product
        );
        break;
    }
  } else userCart.cart.products.push(product); // add product into the cart
  return userCart;
};

/**
 * This method will check if the product exists in the user cart
 */
async function checkIfProductExists(searchForProduct) {
  // if no product found in cart, it returns -1
  const foundProductIndex = userCart.cart.products.findIndex(
    (searchingProduct) => searchingProduct.id === searchForProduct.id
  );
  return { exists: foundProductIndex > -1, productIndex: foundProductIndex };
}

/**
 * This method will calculate the new price
 *
 * @param {*} updatedProduct // product with new amount
 * @param {*} originalPrice // the price of a single amount of this product
 */
async function updateProductPrice(updatedProduct, originalPrice) {
  // update product price with the new amount
  return updatedProduct.amount * originalPrice;
}

async function removeProduct(product) {
  const { productIndex } = await checkIfProductExists(product);
  userCart.cart.products = userCart.cart.products.slice(0, productIndex);
}

/**
 * This method will update the amount of this product and returns the new product
 *
 * @param {*} product
 * @param {*} original_product
 */
async function updateProduct(action, product, original_product) {
  // modify the amount of the product
  const productWithNewAmount = {
    ...product,
    amount:
      action == PRODUCT_ASSIGNMENT_METHODS.ADD ||
      action == PRODUCT_ASSIGNMENT_METHODS.INCREMENT
        ? product.amount + 1
        : action == PRODUCT_ASSIGNMENT_METHODS.DECREMENT
        ? product.amount - 1
        : 0,
  };

  if (productWithNewAmount.amount > 0) {
    const updatedPrice = await updateProductPrice(
      productWithNewAmount,
      original_product.price
    );
    // update product price with the new amount
    const updatedProduct = {
      ...productWithNewAmount,
      price: updatedPrice,
    };

    const { productIndex } = await checkIfProductExists(product);
    userCart.cart.products[productIndex] = updatedProduct;
  } else removeProduct(product);
}

module.exports = router;
