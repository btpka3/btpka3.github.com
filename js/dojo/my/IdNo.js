
/*
 * TODO 使用 Jsdoc 3 完善文档
 *        https://github.com/jsdoc3/jsdoc
 * TODO 使用 Dojo D.O.H 完善单元测试
 *        http://dojotoolkit.org/reference-guide/1.7/util/doh.html
 */
/**
 *
 */
define([
    "exports",
    "dojo/dom",
    "dojo/parser",
    "dojo/_base/declare",
    "dojo/_base/lang",
    "dojo/date/locale"
],
 function(
     exports,
     dom,
     parser,
     declare,
     lang,
     dateLocale) {

    // 内部可见的变量、方法
    var GENERATION_1 = 1; // 第一代身份证号（15位）
    var GENERATION_2 = 2; // 第二代身份证号（18位）

    // 声明类型
    declare("my.IdNo", null, {
        // 成员变量
        // 基本类型是值拷贝，实例与实例之间互不影响（字符串、数值）
        // 对象类型是值引用，实例与实例之间共享，会相互影响（数组、对象）
        S1 : "S1",
        O1 : [ "O1" ],

        // 构造函数
        /**
         * @constructor
         */
        constructor : function(args) {
          // 无论对象类型还是基本数据类型的成员变量都统一在构造函数中声明
          // 可以避免对象类型成员变量被共享。
          this.idNoType = GENERATION_2;

          this.admRegNo = "";
          this.birthday = "";
          this.seq = "";
          this.checkCode = "";

          this.S2 = "S2";
          this.O2 = [ "O2" ];

          // 使用用户的自定义配置
          if (lang.isString(args)) {
            var idNoStr = args;
            if (idNoStr.length == 15) {
              this.idNoType = GENERATION_1;

              this.admRegNo = idNoStr.substring(0, 6);
              this.birthday = idNoStr.substring(6, 12);
              this.seq = idNoStr.substring(12, 15);

            } else if (idNoStr.length == 18) {
              this.idNoType = GENERATION_2;

              this.admRegNo = idNoStr.substring(0, 6);
              this.birthday = idNoStr.substring(6, 14);
              this.seq = idNoStr.substring(14, 17);
              this.checkCode = idNoStr.substring(17, 18);
            }
          } else {
            declare.safeMixin(this, args);
          }
        },

        isValid : function() {
          if (this.idNoType != GENERATION_1 && this.idNoType != GENERATION_2) {
            return false;
          }
          if (!lang.isString(this.admRegNo) || !this.admRegNo.match("\\d{6}")) {
            return false;
          }

          if (!lang.isString(this.birthday)
              || !this.birthday.match("\\d{6}|\\d{8}")) {
            return false;
          }
          if (this.idNoType == GENERATION_1) {
            if (!this.birthday.match("\\d{6}")) {
              return false;
            }
            var date = dateLocale.parse(this.birthday, {
              datePattern : "yyMMdd",
              selector : "date",
              strict : true
            });
            if (date == null) {
              return false;
            }
          } else {
            if (!this.birthday.match("\\d{8}")) {
              return false;
            }
            var date = dateLocale.parse(this.birthday, {
              datePattern : "yyyyMMdd",
              selector : "date",
              strict : true
            });
            if (date == null) {
              return false;
            }
          }

          if (!lang.isString(this.seq) || this.seq == null
              || !this.seq.match("\\d{3}")) {
            return false;
          }
          if (this.idNoType == GENERATION_2) {
            var calcCheckCode = my.IdNo.calcCheckCode(this.toString());
            if (calcCheckCode == null || calcCheckCode != this.checkCode) {
              return false;
            }
          }
          return true;
        },
        toGeneration1Str : function() {
          if (this.idNoType == GENERATION_2 && this.isValid()) {
            var str = "";
            if (this.admRegNo) {
              str += this.admRegNo;
            }
            if (this.birthday) {
              str += this.birthday.substring(2);
            }
            if (this.seq) {
              str += this.seq;
            }
            return str;
          }
          return this.toString();
        },
        toGeneration2Str : function() {
          if (this.idNoType == GENERATION_1 && this.isValid()) {
            var str = "";
            if (this.admRegNo) {
              str += this.admRegNo;
            }
            if (this.birthday) {
              str += "19" + this.birthday;
            }
            if (this.seq) {
              str += this.seq;
            }
            str += my.IdNo.calcCheckCode(this.toString());
            return str;
          }
          return this.toString();
        },

        toString : function() {
          var str = "";
          if (this.admRegNo) {
            str += this.admRegNo;
          }
          if (this.birthday) {
            str += this.birthday;
          }
          if (this.seq) {
            str += this.seq;
          }
          if (this.checkCode) {
            str += this.checkCode;
          }
          return str;
      }
    });

    // ----------------------------------------------------- 静态属性、方法
    // 只能静态引用，不能通过实例引用
    my.IdNo.S0 = "S0";
    my.IdNo.O0 = [ "O0" ];
    my.IdNo.GENERATION_1 = 1;
    my.IdNo.GENERATION_2 = 2;

    my.IdNo.calcCheckCode = function(idNoStr) {
        if (idNoStr == null || !(lang.isString(idNoStr)
                && (idNoStr.length == 17 || idNoStr.length == 18))) {
            return "";
        }

        var A = idNoStr;

        //** 权值 */
        var W = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];

        var checkSum = 0;
        for ( var i = 0; i < 17; i++) {
            var ai = parseInt(A.charAt(i)); // 字符 -> 数值
            var wi = W[i];
            checkSum += ai * wi;
        }

        //** ∑(Ai×Wi) % 11 后对应的校验和 */
        var C = [ '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' ];

        return C[checkSum % 11];
    };

    my.IdNo.valueOf = function(args) {
      return new my.IdNo(args);
    }

    return my.IdNo;
  });
