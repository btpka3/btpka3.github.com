import controller from "./controller.js";
import html from "!html-loader?minimize=true!./index.main.html";

function confState($stateProvider) {
    console.log("--------main.aaa.a2 : confState")
    $stateProvider.state("main.aaa.a2", {
        url: "/a2",
        sticky: true,
        deepStateRedirect: true,
        views: {
            "a2@main.aaa": {
                template: html,
                controller: controller
            }
        }
    });
}
confState.$inject = ['$stateProvider'];

export default confState ;
