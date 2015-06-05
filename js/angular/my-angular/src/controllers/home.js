app.config([
  '$stateProvider',
  '$urlRouterProvider',

  function ($stateProvider,
            $urlRouterProvider) {

    $stateProvider.state("home", {
      url: "/",
      template: 'welcome'
    });
  }]);


// 防止 侧边栏 的二级菜单消失。
// 因为侧边栏的二级菜单使用的是bootstap的tooltip，全局点击会使其隐藏的。
(function () {
  $ = jQuery;
  $(function () {
    $(".sidebar .dropdown-toggle").on("click.bs.dropdown", function () {
      var dropdown = $(this).parent(".dropdown");
      dropdown.data("doHidden", dropdown.hasClass('open'));
    });

    $(".sidebar .dropdown").on("hide.bs.dropdown", function (e) {
      if ($(e.target).data("doHidden")) {
        $(e.target).data("doHidden", false);
        return;
      } else {
        e.stopPropagation();
        e.preventDefault();
      }
    });
  });

})();