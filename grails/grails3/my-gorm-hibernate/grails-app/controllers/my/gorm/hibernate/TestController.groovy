package my.gorm.hibernate

import grails.converters.JSON

class TestController {

    def index() {

        render "index " + new Date()
    }


    def insert() {

        def user1 = new User([
                name     : "zhang3",
                age      : 22,
                lastVisit: new Date(),
                gender   : UserGenderEnum.MALE
        ])
        user1.addToAddresses(new Address([
                province: "浙江省",
                city    : "杭州市",
                street  : "江干区东方电子商务园15幢1楼",
                userName: "zhang3",
                phone   : "17011112222"

        ]))
        user1.addToAddresses(new Address([
                province: "上海市",
                city    : "上海市",
                street  : "888999000",
                userName: "zhang3",
                phone   : "17022223333"
        ]))

        user1.save()

        def user2 = new User([
                name     : "li4",
                age      : 33,
                lastVisit: new Date(),
                gender   : UserGenderEnum.FEMALE
        ])
        user2.addToAddresses(new Address([
                province: "北京市",
                city    : "北京市",
                street  : "静安寺",
                userName: "li4",
                phone   : "18011112222"

        ]))
        user2.addToAddresses(new Address([
                province: "山东省",
                city    : "威海市",
                street  : "哈工大",
                userName: "li4",
                phone   : "18022223333"
        ]))

        user2.save()

        render "insert :  " + new Date()
    }

    def select() {
        def userList = User.findAll()
        log.debug "---------select : " + userList

        def usersJson = userList.findResults { User user ->
            [
                    id         : user.id,
                    version    : user.version,
                    dateCreated: user.dateCreated,
                    lastUpdated: user.lastUpdated,

                    name       : user.name,
                    age        : user.age,
                    lastVisit  : user.lastVisit,
                    gender     : user.gender.name(),  // 使用 String 类型的名称，而不是枚举型。

                    addresses  : user.addresses?.findResults { Address addr ->
                        [
                                id         : addr.id,
                                version    : addr.version,
                                dateCreated: addr.dateCreated,
                                lastUpdated: addr.lastUpdated,

                                province   : addr.province,
                                city       : addr.city,
                                street     : addr.street,
                                userName   : addr.userName,
                                phone      : addr.phone
                        ]
                    }
            ]
        }

        render usersJson as JSON
    }

}
