$(function() {
    var casUrl = "https://cas.localhost.me:8443/first-cas-server";

    $("#ajaxLogin").click(function() {
        $("#ajaxLoginDiv").toggle(800);
    });

    $("#ajaxLoginSubmit").click(function() {
        $.post(casUrl + "/v1/tickets", $("#ajaxLoginForm"), function(data) {
            console.info(data);
        });
    });
});