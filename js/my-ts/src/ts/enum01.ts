enum E {
    A = "AAA",
    B = "BBB",
    C = "CCC"
};


type myPrintFunc = (e: E) => void;

// error TS2384: Overload signatures must all be ambient or non-ambient.
// 先声明类型
// 再定义实现
let myPrint: myPrintFunc = (e) => {
    switch (e) {
        case E.A :
            console.log("----- A : " + e);
            break;
        case E.B :
            console.log("----- B : " + e);
            break;
        case E.C :
            console.log("----- C : " + e);
            break;
        default:
            console.log("----- error")
    }
};

myPrint(E.A);
myPrint(E.B);
myPrint(E.C);

