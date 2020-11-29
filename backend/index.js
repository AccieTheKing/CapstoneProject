require('dotenv').config();
const cors = require('cors');
const express = require('express');
const app = express();

const port = process.env.SERVER_PORT || 5000;

app.use(express.json());
app.use(cors());

const announcementsRouter = require('./src/routes/announcements');
const productsRouter = require('./src/routes/products');
// const productsRouter = require('./src/routes/products');

app.use('/announcements', announcementsRouter);
app.use('/products', productsRouter);

app.listen(port, () => console.log(`App listens to port ${port}`));
