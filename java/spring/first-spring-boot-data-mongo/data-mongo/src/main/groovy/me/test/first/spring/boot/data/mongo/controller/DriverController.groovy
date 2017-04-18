package me.test.first.spring.boot.data.mongo.controller

import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.gridfs.GridFSBucket
import com.mongodb.client.gridfs.GridFSBuckets
import com.mongodb.client.gridfs.model.GridFSFile
import com.mongodb.client.gridfs.model.GridFSUploadOptions
import com.mongodb.client.model.Filters
import groovy.transform.CompileStatic
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import static com.mongodb.client.model.Filters.eq

/**
 * 测试如何使用 MongoDb Java driver
 *
 * 参考：
 * http://mongodb.github.io/mongo-java-driver/3.4/driver/getting-started/quick-start/
 */
@Controller
@RequestMapping("/driver")
@CompileStatic
class DriverController {


    @Autowired
    MongoClient mongoClient


    @RequestMapping("/list")
    @ResponseBody
    Object list() {
        MongoDatabase database = mongoClient.getDatabase("test");

        MongoCollection<Document> addr = database.getCollection("addr");
        List<Document> addrList = addr.find(Filters.in("name", 'addr-1', 'addr-2')).toList()



        MongoCollection<Document> user = database.getCollection("user");


        List<String> addrIdList = addrList*.get("_id") as List<String>
        println "---" + user.count()

        List<Document> docs = user.find(Filters.in('addr.$id', addrIdList)).toList()


        return docs
    }

    @RequestMapping("/gridFs_C")
    @ResponseBody
    Object gridFs_C() {
        MongoDatabase database = mongoClient.getDatabase("test");

        GridFSBucket gridFSBucket = GridFSBuckets.create(database, "fs");

        InputStream streamToUploadFrom = new ByteArrayInputStream("/tmp/mongodb-tutorial.pdf".getBytes("UTF-8"));

        GridFSUploadOptions options = new GridFSUploadOptions()
                .chunkSizeBytes(358400)
                .metadata(new Document([
                "type"       : "presentation",
                "contentType": "image/png"
        ] as Map<String, Object>));

        ObjectId fileId = gridFSBucket.uploadFromStream("mongodb-tutorial", streamToUploadFrom, options);

        return fileId
    }

    @RequestMapping("/gridFs_R")
    @ResponseBody
    Object gridFs_R() {


        MongoDatabase database = mongoClient.getDatabase("test");
        GridFSBucket gridFSBucket = GridFSBuckets.create(database, "fs");



        GridFSFile gridFSFile = gridFSBucket.find(eq("metadata.contentType", "image/png"))
                .first();

        ByteArrayOutputStream byteArrOs = new ByteArrayOutputStream()
        gridFSBucket.downloadToStream(gridFSFile.id, byteArrOs)


        return [
                file    : gridFSFile.filename,
                metadata: gridFSFile.metadata,
                content : new String(byteArrOs.toByteArray(), "UTF-8")
        ]
    }

}




