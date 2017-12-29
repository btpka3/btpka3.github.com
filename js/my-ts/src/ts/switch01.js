// 测试 javascript : switch + string
function print(e) {
    switch (e) {
        case "A" :
            console.log("----- A : " + e);
            break;
        case "B" :
            console.log("----- B : " + e);
            break;
        case "C" :
            console.log("----- C : " + e);
            break;
        default:
            console.log("----- error")
    }
}

print("B");