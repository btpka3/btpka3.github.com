// 两秒后，将参数+1 并返回
async function aaa(num) {
    console.log(new Date() + "----------- aaa.1: " + num);
    return new Promise((resolve, reject) => {
        setTimeout(function () {
            console.log(new Date() + "----------- aaa.2: " + num);
            resolve(1 + num);
        }, 2000);
    });
}

export async function bbb(num) {
    console.log(new Date() + "----------- bbb.1 : " + num);
    num = await aaa(num);

    console.log(new Date() + "----------- bbb.1 : " + num);
    num = await aaa(num);
    console.log(new Date() + "----------- bbb.1 : " + num);
    return num;
};

