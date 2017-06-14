'use strict';

module.exports = app => {
  //app.get('/', 'home.index');
  app.get('/', app.controller.home.index);
  app.get('/aa', app.controller.home.aa);
  app.get('/img1', app.controller.home.img1);
  app.get('/params', app.controller.home.params);
  app.get('/img', app.controller.img.index);
};
