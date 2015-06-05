app.factory('userService', [
  '$resource',

  function ($resource) {
    return $resource('user/:userId', {}, {
      query: {method: 'GET', params: {userId: ''}, isArray: true}
    });
  }
]);