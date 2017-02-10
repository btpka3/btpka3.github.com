import controller from "./controller.js";
import html from "!html-loader?minimize=true!./index.main.html";

function confState($stateProvider) {
    console.log("--------main.ccc : confState");
    $stateProvider.state("main.ccc", {
        url: "/ccc",
        sticky: true,
        deepStateRedirect: true,
        views: {
            "ccc@main": {
                template: html,
                controller: controller
            }
        }
    });
}
confState.$inject = ['$stateProvider'];

export default confState ;
