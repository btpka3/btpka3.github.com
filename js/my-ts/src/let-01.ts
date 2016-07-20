let arr:string[] = ["aaa", "bbb", "ccc"];
for (let i:number = 0; i < arr.length; i++) {
    setTimeout(()=> {
        console.log(i + " : " + arr[i]);
    }, 1000 * i)
}