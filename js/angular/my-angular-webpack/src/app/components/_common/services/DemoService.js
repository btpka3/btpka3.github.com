const [isLoggedIn, userInfo] = [Symbol(), Symbol()];

class DemoService {

    constructor($http, $q, $rootScope) {
        Object.assign(this, {$http, $q, $rootScope});
        // private variable
        this[isLoggedIn] = false;
        this[userInfo] = null;
    }

    isLoggedIn() {
        return this[isLoggedIn];
    }
}

DemoService.$inject = ['$http', '$q', '$rootScope'];

export default DemoService;
