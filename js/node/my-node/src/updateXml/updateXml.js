
console.log("\n-------------------------- xml2js : XML -> JSON");
var parseString = require('xml2js').parseString;
var xml = "<root a='aa'>Hello <div>111</div> xml2js!  <!-- lalala --></root><!-- papapa -->"
parseString(xml, function (err, result) {
    console.dir(result);
});


console.log("\n-------------------------- xml2js : JSON -> XML");
var fs = require('fs'),
    xml2js = require('xml2js');

var obj = {name: "Super", Surname: "Man", age: 23};

var builder = new xml2js.Builder();
var xml1 = builder.buildObject(obj);
console.log(xml1);

// // node-expat install is blocking on node-gyp
//
// console.log("\n-------------------------- node-expat : parse XML");
//
// var expat = require('node-expat');
// var parser = new expat.Parser('UTF-8');
//
// parser.on('startElement', function (name, attrs) {
//     console.log(name, attrs);
// });
//
//
// parser.write('<html><head><title>Hello World</title></head><body><p>Foobar</p></body></html>')

console.log("\n-------------------------- fs : read XML");
var data=fs.readFileSync("a.xml","utf-8");
console.log(data);


console.log("\n-------------------------- xmldom : XML");
var DOMParser = require('xmldom').DOMParser;

var doc = new DOMParser().parseFromString(data);
doc.documentElement.setAttribute('id','i111');
doc.documentElement.setAttribute('x','xxx');
doc.documentElement.appendChild(doc.createElement("span"));

var XMLSerializer = require('xmldom').XMLSerializer;
var s = new XMLSerializer();
console.info(s.serializeToString(doc));
