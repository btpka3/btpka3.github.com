import controller from "./controller.js";
import html from "!html-loader?minimize=true!./index.root.html";

function confState($stateProvider) {
    $stateProvider.state("main", {
        abstract: true,
        url: "",
        views: {
            "@": {
                template: html,
                //templateUrl: "/pages/index.root.html",
                controller: controller
            }
        }
    });
}
confState.$inject = ['$stateProvider'];

export default confState ;
