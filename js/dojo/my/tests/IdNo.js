define( [ "my/IdNo", "doh" ], function(IdNo, doh) {

  doh.register("tests.IdNo", [

  /**
   * 验证构造函数。
   */
  {
    name: "xxTest",
    setUp: function(){},
    tearDown: function(){},
    runTest:function(){
      var idNo = new IdNo();
      doh.is("", idNo.toString());
    }
  },
  /**
   *
   */
  {
    name: "yyTest",
    setUp: function(){},
    tearDown: function(){},
    runTest:function(){
      var idNo = new IdNo("11010120120101909X");
      doh.is(2, idNo.idNoType);
      doh.is("110101", idNo.admRegNo);
      doh.is("20120101", idNo.birthday);
      doh.is("909", idNo.seq);
      doh.is("X", idNo.checkCode);
      doh.is("11010120120101909X", idNo.toString());
      doh.t(idNo.isValid());
    }
  }

  ]);

});
