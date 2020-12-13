require('dotenv').config();
const cors = require('cors');
const express = require('express');
const app = express();

const port = process.env.SERVER_PORT || 5000;

app.use(express.json());
app.use(cors());

const announcementsRouter = require('./src/routes/announcements');
const productsRouter = require('./src/routes/products');
const profileRouter = require('./src/routes/profile.router');
const splashScreenRouter = require('./src/routes/splash.router');

app.use('/announcement', announcementsRouter);
app.use('/product', productsRouter);
app.use('/profile', profileRouter);
app.use('/splashscreen', splashScreenRouter);

app.listen(port, () => console.log(`App listens to port ${port}`));
