var router = require("express").Router();


router.get("/user", function (req, res, next) {
  res.json([
    {
      "id": 1,
      "name": "zhang3",
      "email": "zhang3@xxx.com",
      "phone": "123456789",
      "lastLoginTime": 1427874559716,
      "lastLoginIp": "127.0.0.1",
      "enabled": true
    },
    {
      "id": 2,
      "name": "li4",
      "email": "li4@xxx.com",
      "phone": "123456789",
      "lastLoginTime": 1427874559716,
      "lastLoginIp": "127.0.0.1",
      "enabled": true
    },
    {
      "id": 3,
      "name": "wang5",
      "email": "wang5@xxx.com",
      "phone": "123456789",
      "lastLoginTime": 1427874559716,
      "lastLoginIp": "127.0.0.1",
      "enabled": true
    },
    {
      "id": 4,
      "name": "test4",
      "email": "test4@xxx.com",
      "phone": "123456789",
      "lastLoginTime": 1427874559716,
      "lastLoginIp": "127.0.0.1",
      "enabled": true
    },
    {
      "id": 5,
      "name": "test5",
      "email": "test5@xxx.com",
      "phone": "123456789",
      "lastLoginTime": 1427874559716,
      "lastLoginIp": "127.0.0.1",
      "enabled": true
    },
    {
      "id": 6,
      "name": "test6",
      "email": "test6@xxx.com",
      "phone": "123456789",
      "lastLoginTime": 1427874559716,
      "lastLoginIp": "127.0.0.1",
      "enabled": true
    },
    {
      "id": 7,
      "name": "test7",
      "email": "test7@xxx.com",
      "phone": "123456789",
      "lastLoginTime": 1427874559716,
      "lastLoginIp": "127.0.0.1",
      "enabled": true
    },
    {
      "id": 8,
      "name": "test8",
      "email": "test8@xxx.com",
      "phone": "123456789",
      "lastLoginTime": 1427874559716,
      "lastLoginIp": "127.0.0.1",
      "enabled": false
    },
    {
      "id": 9,
      "name": "test9",
      "email": "test9@xxx.com",
      "phone": "123456789",
      "lastLoginTime": 1427874559716,
      "lastLoginIp": "127.0.0.1",
      "enabled": false
    },
    {
      "id": 10,
      "name": "test10",
      "email": "test10@xxx.com",
      "phone": "123456789",
      "lastLoginTime": 1427874559716,
      "lastLoginIp": "127.0.0.1",
      "enabled": false
    },
    {
      "id": 11,
      "name": "test11",
      "email": "test11@xxx.com",
      "phone": "123456789",
      "lastLoginTime": 1427874559716,
      "lastLoginIp": "127.0.0.1",
      "enabled": false
    }
  ]);

});


router.get("/user/:id", function (req, res, next) {

  res.json({
    "id": "" + req.params.id,
    "name": "zhang3_" + req.params.id,
    "email": "zhang3@xxx.com",
    "phone": "123456789",
    "lastLoginTime": 1427874559716,
    "lastLoginIp": "127.0.0.1",
    "enabled": true
  });

});

module.exports = router;