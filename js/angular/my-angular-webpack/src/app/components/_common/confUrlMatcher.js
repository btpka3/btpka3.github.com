function confUrlMatcher($urlMatcherFactoryProvider) {
    $urlMatcherFactoryProvider.strictMode(false);
}
confUrlMatcher.$inject = ['$urlMatcherFactoryProvider'];

export default confUrlMatcher;
