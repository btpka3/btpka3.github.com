/*
 * 说明：该脚本是一个学习Mongo 的 MapReduce 功能的例子。尚未成功。请忽略。
 *
 *
 * 总结：
 * MapReduce在Map的结果为1条时，并不会调用 reduce 函数。
 * 因此，MapReduce不应当改变要处理的数据结构。
 * MapReduce 保存结果到表中时，字段名称只能是 "value" ?
 * MapReduce在保存到数据库中时，如果数据库中已经存在同key的记录，会再次调用 reduce 方法。
 */


/*
 ---------------------------------------- 现状
 user {      // 用户表
 _id     // 用户ID
 name    // 姓名
 gender  // 性别
 age     // 年龄
 }

 order {     // 订单表
 _id     // 订单ID
 userId  // 用户ID
 date    // 订单日期
 status  // 订单状态： 1-待付款，2-已付款，3-已收货，4-已取消
 amount  // 金额
 }

 ---------------------------------------- 需求
 统计出2015年3月～5月，有成功订单，且总金额>500元的用户，并分页显示。显示数据如下：
 {
 userId      // 用户ID
 userName    // 用户姓名
 age         // 年龄

 totalAmount // 最近3个月成功订单的总金额
 orderIds    // 数组，订单ID列表
 }
 */

/* 测试 Array.prototype.sort()

 var arr = [{age:10,name:'zhang3'}, {age:9,name:'li4'},{age:11,name:'wang5'}];
 arr.sort(function(a,b){
 if(!a && !b ){
 return 0;
 }
 if(a && !b){
 return 1;
 }
 if(!a && b){
 return -1;
 }
 return a.age - b.age;
 });


 */

var arr = [
    {
        "userName": "zhang3",
        "age": 23,
        "statUserTime": ISODate("2015-07-30T09:56:20.277Z")
    }, {
        "age": 22,
        "province": "ZheJiang"
    }
];
arr.sort(function (a, b) {
    if (!a && !b) {
        return 0;
    }
    if (a && !b) {
        return 1;
    }
    if (!a && b) {
        return -1;
    }

    if (!a.statUserTime && !b.statUserTime) {
        return 0;
    }
    if (a.statUserTime && !b.statUserTime) {
        return 1;
    }
    if (!a.statUserTime && b.statUserTime) {
        return -1;
    }
    return a.statUserTime - b.statUserTime;
});


db.user.drop();
db.order.drop();
db.tmpUserOrder.drop();
db.tmpUserOrder.insert({"_id": "u3", "value": {age: 22, "province": "ZheJiang"}});


db.user.insert({_id: 'u3', name: "zhang3", gender: "M", age: 23});
db.user.insert({_id: 'u4', name: "li4", gender: "F", age: 24});
db.user.insert({_id: 'u5', name: "wang5", gender: "F", age: 25});

db.order.insert({_id: 'o301', userId: 'u3', date: new Date(2015, 2, 13), status: 4, amount: 100});
db.order.insert({_id: 'o302', userId: 'u3', date: new Date(2015, 3, 13), status: 2, amount: 200}); // 满足条件
db.order.insert({_id: 'o303', userId: 'u3', date: new Date(2015, 4, 13), status: 3, amount: 400}); // 满足条件
db.order.insert({_id: 'o304', userId: 'u3', date: new Date(2015, 5, 13), status: 1, amount: 300});


db.order.insert({_id: 'o401', userId: 'u4', date: new Date(2015, 2, 14), status: 4, amount: 300});
db.order.insert({_id: 'o402', userId: 'u4', date: new Date(2015, 3, 14), status: 2, amount: 100}); // 满足条件
db.order.insert({_id: 'o403', userId: 'u4', date: new Date(2015, 4, 14), status: 3, amount: 200}); // 满足条件
db.order.insert({_id: 'o404', userId: 'u4', date: new Date(2015, 5, 14), status: 1, amount: 400});


db.order.insert({_id: 'o501', userId: 'u5', date: new Date(2015, 2, 15), status: 4, amount: 300});
db.order.insert({_id: 'o502', userId: 'u5', date: new Date(2015, 3, 15), status: 4, amount: 100});
db.order.insert({_id: 'o503', userId: 'u5', date: new Date(2015, 4, 15), status: 3, amount: 700}); // 满足条件
db.order.insert({_id: 'o504', userId: 'u5', date: new Date(2015, 5, 15), status: 1, amount: 400});


/*
 // 期待结果
 tmpUserOrder: [
 { userId:"u3", userName:"zhang3", age:23, statUserTime: new Date(), totalAmount: 600, orderIds : [o302,o303], statOrderTime: new Date()},
 { userId:"u4", userName:"li4",    age:24, statUserTime: new Date(), totalAmount: 300, orderIds : [o402,o403], statOrderTime: new Date()},
 { userId:"u5", userName:"wang5",  age:25, statUserTime: new Date(), totalAmount: 700, orderIds : [o503],      statOrderTime: new Date()}
 ]
 */
var mapUser = function () {
    var value = {
        userName: this.name,
        age: this.age,
        statUserTime: new Date()
    };
    emit(this._id, value);
};


// values ： map 函数的 相同 key 的 value 的集合 + 输出目标表中的已有记录
// 具体情况是： u3 - valuse.lenght == 2, 其他为 1
// reduce 方法会在map执行结果长度>1 时执行一次。
// reduce 方法在保存到数据库前，如果数据库中有同key记录，还会再执行一次（此时，values.lenght == 2）
var reduceUser = function (key, values) {
    // 该打印语句需要到 mongod 所在服务器上查看其日志文件 
    // # tailf /data0/mongod/log/mongodb.log
    printjson("======================");
    printjson(values);

    // 排序，最新的放到最后
    values = values.sort(function (a, b) {
        if (!a && !b) {
            return 0;
        }
        if (a && !b) {
            return 1;
        }
        if (!a && b) {
            return -1;
        }

        if (!a.statUserTime && !b.statUserTime) {
            return 0;
        }
        if (a.statUserTime && !b.statUserTime) {
            return 1;
        }
        if (!a.statUserTime && b.statUserTime) {
            return -1;
        }
        return a.statUserTime - b.statUserTime;
    });
    printjson(values);

    var result = {};

    for (var i = values.length - 1; i >= 0; i--) {
        var value = values[i];

        // 复制来自其他Collection的统计值
        for (var field in value) {
            if (value.hasOwnProperty(field) && !result.hasOwnProperty(field)) {
                result[field] = value[field];
            }
        }
    }
    // 这里要处理的值使用使用最新的
    result.userName = values[values.length - 1].userName;
    result.age = values[values.length - 1].age;
    result.statUserTime = values[values.length - 1].statUserTime;

    return result;
};


db.user.mapReduce(mapUser, reduceUser, {verbose: true, out: {reduce: "tmpUserOrder"}});
db.tmpUserOrder.find();


var mapOrder = function () {
    var value = {
        totalAmount: this.amount,
        orderIds: [this._id],
        statOrderTime: new Date()
    };
    emit(this.userId, value);
};
var reduceOrder = function (key, values) {
    printjson("********************");
    printjson(values);

    // 排序，最新的放到最后
    values = values.sort(function (a, b) {
        if (!a && !b) {
            return 0;
        }
        if (a && !b) {
            return 1;
        }
        if (!a && b) {
            return -1;
        }

        if (!a.statOrderTime && !b.statOrderTime) {
            return 0;
        }
        if (a.statOrderTime && !b.statOrderTime) {
            return 1;
        }
        if (!a.statOrderTime && b.statOrderTime) {
            return -1;
        }
        return a.statOrderTime - b.statOrderTime;
    });

    printjson(values);



    var result = {};

    for (var i = values.length - 1; i >= 0; i--) {
        var value = values[i];

        // 复制来自其他Collection的统计值
        for (var field in value) {
            if (value.hasOwnProperty(field) && !result.hasOwnProperty(field)) {
                result[field] = value[field];
            }
        }
    }
    // TODO statOrderTime -> statOrderVersion, statOrderVersion最大，且相同的结果合并，其他舍弃。需定义scope对象


    return result;
};
var beginDate = new Date(2015, 3, 1);
var endDate = new Date(2015, 6, 1);

db.order.mapReduce(
    mapOrder,
    reduceOrder,
    {
        verbose: true,
        query: {
            date: {$gte: beginDate, $lt: endDate},
            status: {$in: [2, 3]}
        },
        out: {reduce: "tmpUserOrder"}

    });
db.tmpUserOrder.find();


















