function confMdTheming($mdThemingProvider) {
    $mdThemingProvider
        .theme('default')
        .primaryPalette('grey', {
            'default': '800'
        });
}
confMdTheming.$inject = ['$mdThemingProvider'];

export default confMdTheming;
