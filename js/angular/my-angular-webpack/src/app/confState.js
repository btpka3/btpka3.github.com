function confState($stateProvider) {
    $stateProvider.state("c", {
        abstract: true,
        url: "",
        views: {
            "@": {
                template: "c111"
            }
        }
    });
    $stateProvider.state("d", {
        url: "/",
        views: {
            "@": {
                template: "d111"
            }
        }
    });
    $stateProvider.state("a", {

        url: "/a",
        views: {
            "@": {
                template: "a111"
            }
        }
    });
    $stateProvider.state("b", {

        url: "/b",
        views: {
            "@": {
                template: "b111"
            }
        }
    });
}
confState.$inject = ['$stateProvider'];

export default confState ;
