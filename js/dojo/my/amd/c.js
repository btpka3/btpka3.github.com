define(function(require, exports, module) {
  function say(someone) {
    return "hello " + someone + " !";
  }

  // 可以通过exports暴露接口、数据
  exports.c = "CCC";
  exports.say = say;
});
