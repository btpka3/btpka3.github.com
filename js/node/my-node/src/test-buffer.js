const {Buffer} = require('node:buffer');

function test1() {
    const buf = Buffer.from('hello world', 'utf8');


    console.log(buf.toString('hex'));
    console.log(buf.toString('base64'));
    console.log(buf.toString());
}

function test2() {


    const buf = Buffer.from('hello', 'utf16le');

    console.log("JSON.stringify(buf) = ", JSON.stringify(buf));
    const uint16array = new Uint16Array(
        buf.buffer,
        buf.byteOffset,
        buf.length / Uint16Array.BYTES_PER_ELEMENT);

    console.log(uint16array);

    const buf2 = Buffer.from(uint16array);
    console.log(buf2.toString());
}

test1();
test2();