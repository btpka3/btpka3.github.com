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
 { userId:"u3", userName:"zhang3", age:23, totalAmount: 600, orderIds : [o302,o303] },
 { userId:"u4", userName:"li4",    age:24, totalAmount: 300, orderIds : [o402,o403] },
 { userId:"u5", userName:"wang5",  age:25, totalAmount: 700, orderIds : [o503] }
 ]
 */
var mapUser = function () {
    var value = {
        userName: this.name,
        age: this.age
    };
    emit(this._id, value);
};

// values ： map 函数的 相同 key 的 value 的集合 + 输出目标表中的已有记录
// 具体情况是： u3 - valuse.lenght == 2, 其他为 1
// reduce 方法会在map执行结果长度>1 时执行一次。Bug? 很不合理的一个设计
// reduce 方法在保存到数据库前，如果数据库中有同key记录，还会再执行一次（此时，values.lenght == 2）
var reduceUser = function (key, values) {
    // 该打印语句需要到 mongod 所在服务器上查看其日志文件 
    // # tailf /data0/mongod/log/mongodb.log
    printjson("--====================");
    printjson(values);

    var result = {};

    // FIXME：应当倒序遍历 values
    values.forEach(function (value) {

        // 注意：数据库输出表中如果已有记录，则其是 values 中最后一个元素。
        // 所以，如果处理不当，如本例所示，u3的age 应该是 23, 实际是 22
        for (var field in value) {
            if (value.hasOwnProperty(field)) {
                result[field] = value[field];
            }
        }
    });
    return result;
};


db.user.mapReduce(mapUser, reduceUser, {verbose: true, out: {reduce: "tmpUserOrder"}});
db.tmpUserOrder.find();


var mapOrder = function () {
    var value = {
        orderId: this._id,
        amount: this.amount
    };
    emit(this.userId, value);
};
var reduceOrder = function (key, values) {
    printjson("********************");
    printjson(values);

    var mapFileds = ['orderId', 'amount'];
    var resultFields = ['totalAmount', 'orderIds'];

    // 新建 或 从数据库中获取原始值
    var result = {
        totalAmount: 0,
        orderIds: []
    };

    // 倒序遍历 values
    for (var i = values.length - 1; i >= 0; i--) {
        var value = values[i];

        // 计算总额
        if (value.totalAmount) {
            result.totalAmount += value.totalAmount;
        }

        // 记录 order ID
        if (value.orderId) {
            result.orderIds.push(value.orderId);
        }

        // 复制数据库中已有的值
        for (var field in value) {
            if (value.hasOwnProperty(field) && mapFileds.lastIndexOf(field) < 0 && resultFields.lastIndexOf(field) < 0) {
                result[field] = value[field];
            }
        }
    }
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


















