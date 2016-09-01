/**
 * Module : xxx
 */
(function () {
    angular.module('demoApp', ['ksSwiper'])
        .controller('TestCtrl',SwiperController);



    // ----------------------------------------------------------------------------
    // 《cannot swipe to the newly created slides (after model changed) #41》
    // https://github.com/ksachdeva/angular-swiper/issues/41
    SwiperController.$inject = ['$scope', '$timeout'];
    function SwiperController($scope, $timeout) {
        $scope.swiper = {};

        $scope.imgs = [
            {
                name: "red",
                img: "https://placehold.it/600x600/f69988",
            },
            {
                name: "pink",
                img: "https://placehold.it/600x600/f06292",
            }
        ];
        $scope.toAdd = [
            {
                name: "purple",
                img: "https://placehold.it/600x600/ab47bc"
            },
            {
                name: "deep purple",
                img: "https://placehold.it/600x600/7e57e2"
            },
            {
                name: "blue",
                img: "https://placehold.it/600x600/4d73ff"
            },
            {
                name: "cyan",
                img: "https://placehold.it/600x600/00bcd4"
            },
            {
                name: "teal",
                img: "https://placehold.it/600x600/009688"
            },
            {
                name: "green",
                img: "https://placehold.it/600x600/42bd41"
            },
            {
                name: "lime",
                img: "https://placehold.it/600x600/cddc39"
            },
            {
                name: "yellow",
                img: "https://placehold.it/600x600/ffeb3b"
            }
        ];

        $scope.remove = function () {
            if ($scope.imgs.length === 0) {
                return;
            }
            var i = $scope.imgs.pop();
            $scope.toAdd.push(i);


            $timeout(function () {
                $scope.swiper.update();
                //$scope.swiper.onResize();
            });


        };

        $scope.change = function () {
            if ($scope.toAdd.length === 0) {
                return;
            }
            $scope.imgs.push($scope.toAdd.shift());

            $timeout(function () {
                $scope.swiper.update();
                //$scope.swiper.onResize();
            });


        };


        // 不要使用该方法: 每次 $scope.imgs 都是一个新的对象,会造成 loop 行为异常。
        $scope.change2 = function () {
            if ($scope.toAdd.length === 0) {
                return;
            }
            var imgs = [];
            if ($scope.imgs) {
                Array.prototype.push.apply(imgs, $scope.imgs);
            }
            imgs.push($scope.toAdd.shift());
            $scope.imgs = imgs;


            $timeout(function () {
                $scope.swiper.update();
                //$scope.swiper.onResize();
            });


        };
    }
})();