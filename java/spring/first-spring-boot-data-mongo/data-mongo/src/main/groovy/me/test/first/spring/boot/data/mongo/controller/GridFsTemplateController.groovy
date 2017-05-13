package me.test.first.spring.boot.data.mongo.controller

import com.mongodb.gridfs.GridFSDBFile
import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.gridfs.GridFsResource
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import static org.springframework.data.mongodb.core.query.Query.query
import static org.springframework.data.mongodb.gridfs.GridFsCriteria.*

/**
 * 试用 GridFsTemplate
 */
@Controller
@RequestMapping("/gridFsTemplate")
class GridFsTemplateController {

    @Autowired
    MongoTemplate mongoTemplate

    @Autowired
    GridFsTemplate gridFsTemplate


    @RequestMapping("/del")
    @ResponseBody
    String del() {

        gridFsTemplate.delete(query(
                whereFilename().is("test01.txt")
        ))

        return "del @ " + new Date()
    }


    @RequestMapping("/add")
    @ResponseBody
    String add() {

        // metadata 也可以是一个普通的 POJO
        Document metadata = new Document([
                "type": "txt",
                "tag" : "btpka3"
        ]);

        org.springframework.core.io.Resource rsc = new ByteArrayResource("abc123".getBytes("UTF-8"))

        gridFsTemplate.store(
                rsc.getInputStream(),
                "test01.txt",
                "plain/text",
                metadata)

        return "add " + new Date()
    }


    @RequestMapping("/find01")
    @ResponseBody
    String find01() {

        // 直接使用最熟知的 Criteria 相关方法
        Criteria w = Criteria.where()
                .and("filename").is("test01.txt")
                .and("contentType").is("plain/text")
                .and("metadata.type").is("txt")
                .and("metadata.tag").is("btpka3")

        List<GridFSDBFile> files = gridFsTemplate.find(query(w))
        if (files) {
            println "find01 : test01.txt : " + files[0].inputStream.text
        }
        return files;
    }

    @RequestMapping("/find02")
    @ResponseBody
    String find02() {

        // 使用 GridFsCriteria 上的特有方法
        List<GridFSDBFile> files = gridFsTemplate.find(
                query(
                        Criteria.where().andOperator(
                                whereFilename().is("test01.txt"),
                                whereContentType().is("plain/text"),
                                whereMetaData("type").is("txt"),
                                whereMetaData("tag").is("btpka3")
                        )
                ))
        if (files) {
            println "find02 : test01.txt : " + files[0].inputStream.text
        }

        return files
    }


    @RequestMapping("/getResources01")
    @ResponseBody
    String getResources01() {


        GridFsResource[] recs = gridFsTemplate.getResources("*.txt")

        if (recs) {
            println "getResources01 : test01.txt : " + recs[0].inputStream.text
        }

        return recs
    }

}
