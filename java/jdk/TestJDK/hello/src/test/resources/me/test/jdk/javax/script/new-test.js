var o = {
    "success": true,
    "data": {
        "exponent": "010001",
        "modulus": "00ba3d972efea794358c211077993f867f0bb61cac53a1e5ab37518459da74c8f93ef648bcd42ebad48de5ad7ede2df77f65111c1f29277a27f17a6494b5e271e5e5c35e4c8c347d34df86a32579483fc26bdebe149c47cc3901ecbe0abc395fc2918b7f5ef8876c647f516bc7568e352f0217224a358bdbd489106e9258597577"
    }
};
// console.log("---------------");
var kp = window.RSAUtils.getKeyPair(o.data.exponent, '', o.data.modulus);

var t = "621dbdeb41dc4d2bce32133b63e5bf8313476066e24d239e71483be454fd5ccf038caa844a9a3eaf7fb012fc36896e519d463aef966492c5cf3fb6127524d24551f0634857547182defa08c84067f6c58e686422f9ba3aac7e1bbfecdfde0c335e0fbed226303d2bf3e6c5e6cbbde6481d49dcc7a50272f23ab5b2c74d40a225";
// console.log("------target  : " + t);

var c = window.RSAUtils.encryptedString(kp, "933125");
// console.log("------current : " + c);