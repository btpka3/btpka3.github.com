package me.test.boot.converter

import me.test.boot.controller.MyMsg
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class MyMsg2MapConverter implements Converter<MyMsg, Map<String, String>> {

    @Override
    Map<String, String> convert(MyMsg source) {

        Map<String, String> map = new LinkedHashMap<>()
        map.put("name", source.getName())
        map.put("age", "" + source.getAge())
        map.put("birthday", "" + source.getBirthday())
        map.put("hobbies", "" + source.getHobbies())
        map.put("memo", "" + source.getMemo())
        println "=============== MyMsg2MapConverter : " + map
        return map
    }
}
