'use strict';
var fs = require("fs");


module.exports = app => {
    class HomeController extends app.Controller {
        * index() {
            this.ctx.body = 'hi, egg';
        }

        /**
         *
         * @returns {Promise.<void>}
         */
        async aa() {
            this.ctx.body = {
                a: 'hi, aa 中国'
            };
        }

        /**
         * 返回图片
         */
        async img1() {

            var data = fs.readFileSync("app/public/1.jpg");
            // this.ctx.response.type = "image/jpeg";
            this.ctx.response.type = "jpg";
            this.ctx.body = data;

        }

        async params() {
            this.ctx.body = this.ctx.query;

        }
    }
    return HomeController;
};
