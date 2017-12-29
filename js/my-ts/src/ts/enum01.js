var E;
(function (E) {
    E["A"] = "AAA";
    E["B"] = "BBB";
    E["C"] = "CCC";
})(E || (E = {}));
;
var myPrint = function (e) {
    switch (e) {
        case E.A:
            console.log("----- A : " + e);
            break;
        case E.B:
            console.log("----- B : " + e);
            break;
        case E.C:
            console.log("----- C : " + e);
            break;
        default:
            console.log("----- error");
    }
};
myPrint(E.A);
myPrint(E.B);
myPrint(E.C);
