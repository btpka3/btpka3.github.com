#!/usr/bin/env node
/*
 http://mongoosejs.com/docs/index.html
 */
'use strict';

var mongoose = require('mongoose');
var assert = require('assert');

mongoose.connect('mongodb://localhost:27017/test');


var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function (callback) {
    // yay!
});

var kittySchema = mongoose.Schema({
    name: String
});
kittySchema.methods.speak = function () {
    var greeting = this.name
        ? "Meow name is " + this.name
        : "I don't have a name";
    console.log(greeting);
};

var Kitten = mongoose.model('Kitten', kittySchema);

var silence = new Kitten({ name: 'Silence' });
silence.speak();

var insertDocuments = function (db, callback) {
    // Get the documents collection
    var collection = db.collection('documents');
    // Insert some documents
    collection.insertMany([
        {a: 1}, {a: 2}, {a: 3}
    ], function (err, result) {
        assert.equal(err, null);
        assert.equal(3, result.result.n);
        assert.equal(3, result.ops.length);
        console.log("Inserted 3 documents into the document collection");
        callback(result);
    });
};

var insertClob = function (db, callback) {
    var gridStore = new GridStore(db, "test.txt", "w", {
        "content_type": "plain/text",
        "metadata": {
            "author": "Daniel"
        },
        "chunk_size": 1024 * 4
    });
    gridStore.open(function (err, gridStore) {
        gridStore.write("hello world!" + new Date(), function (err, gridStore) {
            gridStore.close(function (err, result) {

                // Let's read the file using object Id
                GridStore.read(db, result._id, function (err, data) {
                    console.log("gridStore data = ", data.toString("UTF-8"));
                    db.close();
                });
            });
        });
    });
};


MongoClient.connect(url, function (err, db) {
    assert.equal(null, err);
    console.log("Connected correctly to server");

    //insertDocuments(db, function () {
    //    db.close();
    //});
    insertClob(db, function () {
        db.close();
    });
});


