function greeter(person) {
    return "Hello, " + person;
}
var user = "Jane User";
console.log(greeter("world"));
var bower = require('bower');
var logger = bower.commands.info("jquery#2.1.4");
logger
    .on('end', function (data) {
    console.log("end : ", data);
})
    .on('error', function (err) {
    console.log("error : ", err);
});
