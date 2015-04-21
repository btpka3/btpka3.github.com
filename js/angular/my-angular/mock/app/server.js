var express = require('express');
var app = express();

var server = app.listen(3000, function () {
  console.log('Listening on port %d', server.address().port);
});

////////////////////////////////////////////// views
app.set('views', __dirname);
app.set('view engine', 'jade');

////////////////////////////////////////////// route

app.use('/', express.static('target/dist'));

app.use(require("./user/router"));


app.use(function(err, req, res, next) {
  console.error(err.stack);
  res.status(500).send('Something broke!');
});

app.use(function(req, res, next) {
  res.status(404).send('Sorry cant find that!');
});