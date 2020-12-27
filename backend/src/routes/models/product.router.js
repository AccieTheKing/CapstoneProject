const router = require('express').Router();
const stripe = require('stripe')(process.env.STRIPE_SECRET_KEY);
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
  res.json(all_products_list);
});

router.get('/:productID', async (req, res) => {
  try {
    retrieveTokenAndDecode(req.headers.authorization).then((user) => {
      const selectedUserProduct = all_products_list[req.params.productID];
      res.json(selectedUserProduct);
    });
  } catch (error) {}
});
  const selectedUserProduct = all_products_list[req.params.productID];
  res.json(selectedUserProduct);
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
  const selected_product = all_products_list[req.params.productid]; // user selected product

  // fill the user with the phone number
  userCart = {
    ...userCart,
    phoneNumber: req.params.phoneNumber,
  };

  updateProductWhenFound(selected_product, PRODUCT_ASSIGNMENT_METHODS.ADD).then(
    () => {
      res.json(userCart.cart);
    }
  );
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
  // fill the user with the phone number
  userCart = {
    ...userCart,
    phoneNumber: req.params.phoneNumber,
  };

      updateProductWhenFound(
        selected_product,
        PRODUCT_ASSIGNMENT_METHODS.INCREMENT
      ).then((productCart) => {
    res.json(userCart.cart);
  });
});
  } catch (error) {}

router.post('/cart/decrease/:productid/:phoneNumber', async (req, res) => {
  try {
    retrieveTokenAndDecode(req.headers.authorization).then((user) => {
      const selected_product = all_products_list[req.params.productid]; // user selected product
  // fill the user with the phone number
  userCart = {
    ...userCart,
    phoneNumber: req.params.phoneNumber,
  };

      updateProductWhenFound(
        selected_product,
        PRODUCT_ASSIGNMENT_METHODS.DECREMENT
      ).then(async (productCart) => {
    res.json(userCart.cart);
  });
  } catch (error) {}
});

const calculateOrderAmount = (items) => {
  // Replace this constant with a calculation of the order's amount
  // Calculate the order total on the server to prevent
  // people from directly manipulating the amount on the client
  return 1400;
};
router.post('/cart/create-payment-intent', async (req, res) => {
  const { items } = req.body;
  // Create a PaymentIntent with the order amount and currency
  const paymentIntent = await stripe.paymentIntents.create({
    amount: calculateOrderAmount(items),
    currency: 'usd',
  });
  res.json({
    clientSecret: paymentIntent.client_secret,
  });
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
      // replace the stored product with our modified one
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
  calculateCartTotalPrice();
};

async function calculateCartTotalPrice() {
  let totalPrice = 0.0;
  userCart.cart.products.forEach((product) => {
    totalPrice += product.amount;
  });

  const userCartWithUpdatedPrice = Object.assign(
    { cart: { ...userCart.cart, price: totalPrice } },
    userCart
  );
  userCart = userCartWithUpdatedPrice;
}

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
