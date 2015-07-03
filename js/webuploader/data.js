var router = require("express").Router();

router.get("/zll/order", function (req, res, next) {
    res.json({
        "code": "SUCCESS",
        "order": {
            "item": [{
                name: "蚕丝被",
                price: 700
            }, {
                name: "四件套",
                price: 300
            }],
            "price": 1000
        }
    });
});

router.get("/zll/invoice", function (req, res, next) {
    res.json({
        "code": "SUCCESS",
        "invoiceList": [{
            "id": "I1",
            "type": "个人",
            "title": "张三"
        }, {
            "id": "I2",
            "type": "公司",
            "title": "钱皇网络"
        }]
    });
});

router.get("/zll/addr", function (req, res, next) {
    res.json({
        "code": "SUCCESS",
        "addrList": [{
            "id": "A1",
            "addr": "河南"
        }, {
            "id": "A2",
            "type": "浙江"
        }]
    });
});

router.get("/zll/loadAlways", function (req, res, next) {
    res.json({
        "code": "SUCCESS",
        "userList": [{
            "id": "L1",
            "addr": "张三"
        }, {
            "id": "L2",
            "type": "李四"
        }]
    });
});

router.post("/zll/upload", function (req, res, next) {

    // https://github.com/expressjs/multer

    var data = new Buffer('');
    req.on('data', function (chunk) {
        data = Buffer.concat([data, chunk]);
    });
    req.on('end', function () {
        req.rawBody = data;

        var fs = require('fs');
        // https://nodejs.org/docs/latest/api/fs.html#fs_fs_writefile_filename_data_options_callback
        fs.writeFile("/tmp/img", req.rawBody, function(err) {
            if(err) {
                console.log("The file was saved! ERROR");
            }else{
                console.log("The file was saved!");
            }

        });
    });

    res.json({
        "code": "SUCCESS"
    });
});

module.exports = router;