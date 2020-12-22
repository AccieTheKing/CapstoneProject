require('dotenv').config();
const cors = require('cors');
const express = require('express');
const database = require('./src/database');
const app = express();

const port = process.env.SERVER_PORT || 5000;

app.use(express.json());
app.use(cors());

const appRouter = require('./src/routes');

app.use('/announcement', appRouter.announcementRouter);
app.use('/product', appRouter.productRouter);
app.use('/profile', appRouter.profileRouter);
app.use('/splashscreen', appRouter.splashRouter);

database.once('open', () =>
  console.log('connection with the database successful')
);

app.listen(port, () => console.log(`App listens to port ${port}`));
