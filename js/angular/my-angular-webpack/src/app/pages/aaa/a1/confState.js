import controller from "./controller.js";
import html from "!html-loader?minimize=true!./index.main.html";

function confState($stateProvider) {
    console.log("--------main.aaa.a1 : confState")
    $stateProvider.state("main.aaa.a1", {
        url: "/a1",
        sticky: true,
        deepStateRedirect: true,
        views: {
            "a1@main.aaa": {
                template: html,
                controller: controller
            }
        }
    });
}
confState.$inject = ['$stateProvider'];

export default confState ;
