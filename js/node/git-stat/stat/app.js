(function () {
    "use strict";
    angular
        .module('app', ['ngMaterial'])

        .controller('MyCtrl', ['$scope', '$http', function ($scope, $http) {

            var myChart = echarts.init(document.getElementById('main'));

            var vm = this;
            var option = vm.option = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                legend: {
                    data: []
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'category',
                        data: []
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: []
            };
            var committers = option.xAxis[0].data;

            var gitCommitCountArr = [];
            var gitAddLinesArr = [];
            var gitDeletedLinesArr = [];
            var gitAllLinesArr = [];
            var gitUnRebasedCommitArr = [];
            var gitUnMergedCommitArr = [];

            vm.stats = [{
                label: "Git提交次数",
                serials: gitCommitCountArr
            }, {
                label: "Git增加行数",
                serials: gitAddLinesArr
            }, {
                label: "Git删除行数",
                serials: gitDeletedLinesArr
            }, {
                label: "Git最终增加行数",
                serials: gitAllLinesArr
            }, {
                label: "Git未rebase提交次数",
                serials: gitUnRebasedCommitArr
            }, {
                label: "Git未合并提交次数",
                serials: gitUnMergedCommitArr
            }];
            vm.curStat = vm.stats[0];
            vm.updateChart = function () {
                console.log("1111111111111");
                vm.option.series = vm.curStat.serials;
                myChart.setOption(option);
            };

            $http({
                method: "GET",
                url: "data.json"
            }).then(function (resp) {
                var data = resp.data;

                findAllCommiters(data, committers);

                function findAllCommiters(data, arr) {
                    Object.keys(data).forEach(function (projName) {
                        option.legend.data.push(projName);
                        var repoResult = data[projName].repoResult;
                        Object.keys(repoResult).forEach(function (commiter) {
                            if (!arr.includes(commiter)) {
                                arr.push(commiter);
                            }
                        });
                    });
                }


                Object.keys(data).forEach(function (projName) {

                    var gitCommitCount = {
                        name: projName,
                        type: 'bar',
                        stack: 'gitCommitCount',
                        data: []
                    };
                    var gitAddLines = {
                        name: projName,
                        type: 'bar',
                        stack: 'gitAddLines',
                        data: []
                    };
                    var gitDeletedLines = {
                        name: projName,
                        type: 'bar',
                        stack: 'gitDeletedLines',
                        data: []
                    };
                    var gitAllLines = {
                        name: projName,
                        type: 'bar',
                        stack: 'gitAllLines',
                        data: []
                    };
                    var gitUnRebasedCommit = {
                        name: projName,
                        type: 'bar',
                        stack: 'gitUnRebasedCommit',
                        data: []
                    };
                    var gitUnMergedCommit = {
                        name: projName,
                        type: 'bar',
                        stack: 'gitUnMergedCommit',
                        data: []
                    };


                    var projInfo = data[projName];
                    option.legend.data.push(projName);

                    committers.forEach(function (committer) {
                        var curStat = projInfo.repoResult[committer];
                        if (curStat) {
                            gitCommitCount.data.push(curStat.commits);
                            gitAddLines.data.push(curStat.addedLines);
                            gitDeletedLines.data.push(curStat.deletedLines);
                            gitAllLines.data.push(curStat.addedLines + curStat.deletedLines);
                            gitUnRebasedCommit.data.push(curStat.unRebasedCount);
                            gitUnMergedCommit.data.push(curStat.unMergedCount);
                        } else {
                            gitCommitCount.data.push(0);
                            gitAddLines.data.push(0);
                            gitDeletedLines.data.push(0);
                            gitAllLines.data.push(0);
                            gitUnRebasedCommit.data.push(0);
                            gitUnMergedCommit.data.push(0);
                        }
                    });

                    gitCommitCountArr.push(gitCommitCount);
                    gitAddLinesArr.push(gitAddLines);
                    gitDeletedLinesArr.push(gitDeletedLines);
                    gitAllLinesArr.push(gitAllLines);
                    gitUnRebasedCommitArr.push(gitUnRebasedCommit);
                    gitUnMergedCommitArr.push(gitUnMergedCommit);

                    //option.series.push(gitCommitCount);
                    //option.series.push(gitAddLines);
                    //option.series.push(gitDeletedLines);
                    //option.series.push(gitAllLines);
                    //option.series.push(gitUnRebasedCommit);
                    //option.series.push(gitUnMergedCommit);


                });
                console.log("222", vm.option.curStat);
                vm.updateChart();
            });
        }]);
})();
/*

 $(function () {
 "use strict";
 // 基于准备好的dom，初始化echarts实例
 var myChart = echarts.init(document.getElementById('main'));

 var gitCommitCountArr = [];
 var gitAddLinesArr = [];
 var gitDeletedLinesArr = [];
 var gitAllLinesArr = [];
 var gitUnRebasedCommitArr = [];
 var gitUnMergedCommitArr = [];

 $.getJSON("data.json", function (data, status, xhr) {
 // 使用刚指定的配置项和数据显示图表。

 var option = {
 tooltip: {
 trigger: 'axis',
 axisPointer: {            // 坐标轴指示器，坐标轴触发有效
 type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
 }
 },
 legend: {
 data: []
 },
 grid: {
 left: '3%',
 right: '4%',
 bottom: '3%',
 containLabel: true
 },
 xAxis: [
 {
 type: 'category',
 data: []
 }
 ],
 yAxis: [
 {
 type: 'value'
 }
 ],
 series: []
 };

 var committers = option.xAxis[0].data;
 findAllCommiters(data, committers);
 console.log(committers);


 Object.keys(data).forEach(function (projName) {

 var gitCommitCount = {
 name: projName,
 type: 'bar',
 stack: 'gitCommitCount',
 data: []
 };
 var gitAddLines = {
 name: projName,
 type: 'bar',
 stack: 'gitAddLines',
 data: []
 };
 var gitDeletedLines = {
 name: projName,
 type: 'bar',
 stack: 'gitDeletedLines',
 data: []
 };
 var gitAllLines = {
 name: projName,
 type: 'bar',
 stack: 'gitAllLines',
 data: []
 };
 var gitUnRebasedCommit = {
 name: projName,
 type: 'bar',
 stack: 'gitUnRebasedCommit',
 data: []
 };
 var gitUnMergedCommit = {
 name: projName,
 type: 'bar',
 stack: 'gitUnMergedCommit',
 data: []
 };


 var projInfo = data[projName];
 option.legend.data.push(projName);

 committers.forEach(function (committer) {
 var curStat = projInfo.repoResult[committer];
 if (curStat) {
 gitCommitCount.data.push(curStat.commits);
 gitAddLines.data.push(curStat.addedLines);
 gitDeletedLines.data.push(curStat.deletedLines);
 gitAllLines.data.push(curStat.addedLines + curStat.deletedLines);
 gitUnRebasedCommit.data.push(curStat.unRebasedCount);
 gitUnMergedCommit.data.push(curStat.unMergedCount);
 } else {
 gitCommitCount.data.push(0);
 gitAddLines.data.push(0);
 gitDeletedLines.data.push(0);
 gitAllLines.data.push(0);
 gitUnRebasedCommit.data.push(0);
 gitUnMergedCommit.data.push(0);
 }
 });

 option.series.push(gitCommitCount);
 //option.series.push(gitAddLines);
 //option.series.push(gitDeletedLines);
 //option.series.push(gitAllLines);
 //option.series.push(gitUnRebasedCommit);
 //option.series.push(gitUnMergedCommit);
 });

 function findAllCommiters(data, arr) {
 Object.keys(data).forEach(function (projName) {
 option.legend.data.push(projName);
 var repoResult = data[projName].repoResult;
 Object.keys(repoResult).forEach(function (commiter) {
 if (!arr.includes(commiter)) {
 arr.push(commiter);
 }
 });
 });
 }

 });

 });
 */