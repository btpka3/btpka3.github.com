#!/usr/bin/env node
/*
 http://mongoosejs.com/docs/index.html
 */
'use strict';

var mongoose = require('mongoose');
var assert = require('assert');


var kittySchema = mongoose.Schema({
    name: String
});

Object.assign(kittySchema.methods, {
    speak: function () {
        var greeting = this.name
            ? "Meow name is " + this.name + ", id = " + this.id
            : "I don't have a name";
        console.log(greeting);
    }
});

Object.assign(kittySchema.statics, {
    findByName: function (name, cb) {
        return this.find({name: new RegExp(name, 'i')}, cb);
    }
});
var Kitten = mongoose.model('Kitten', kittySchema);


mongoose.connect('mongodb://localhost:27017/test');


var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function (callback) {

    var silence = new Kitten({name: 'Silence'});
    silence.save(function (err, s) {
        if (err) return console.error(err);
        silence.speak();
        Kitten.findByName("ile", function (err, recs) {
            console.log(arguments);
        });
    });
});



