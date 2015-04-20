var express = require('express');
var app = express();

var server = app.listen(3000, function () {
  console.log('Listening on port %d', server.address().port);
});


app.get('/', function (req, res) {
  res.sendFile('index.html', {root: __dirname});
});


app.get('/hi', function (req, res) {
  res.send('Hello World!');
});

app.use('/img', express.static('src/assets'));

////////////////////////////////////////////// views
app.set('views', './views');
app.set('view engine', 'jade');

////////////////////////////////////////////// route

var router = express.Router();


router.use(function (req, res, next) {
  console.log('Time:', Date.now());
  next();
});

router.use('/user/:id', function (req, res, next) {
  console.log('Request URL:', req.originalUrl);
  next();
}, function (req, res, next) {
  console.log('Request Type:', req.method);
  next();
});

// a middleware sub-stack which handles GET requests to /user/:id
router.get('/user/:id', function (req, res, next) {
  // if user id is 0, skip to the next router
  if (req.params.id == 0) next('route');
  // else pass the control to the next middleware in this stack
  else next(); //
}, function (req, res, next) {
  // render a regular page
  res.render('regular', {title: 'Hey regular', message: 'Hello there! regular ' + req.params.id});
});

// handler for /user/:id which renders a special page
router.get('/user/:id', function (req, res, next) {
  console.log(req.params.id);
  res.json({title: 'Hey special', message: 'Hello there! special ' + req.params.id});
});

app.use('/user', router);