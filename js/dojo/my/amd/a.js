// 该变量为模块内的全局变量，其他模块不可见
var isGlobalVar = "Hi, there~";
function getGreetings(someone) {
  return "Hi, " + someone + "~";
}

// 可以通过define返回该模块要暴露出的接口、数据
define( {
  a : "AAA",
  say: getGreetings
});
