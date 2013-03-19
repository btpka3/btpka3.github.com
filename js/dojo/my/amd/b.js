define( [ "dojo/dom" ], function(dom) {
  // 可以通过返回值暴露接口、数据
  return {
    b : dom.byId('b').innerHTML
  };
});
