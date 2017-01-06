

```
npm init
npm install --save-dev babel-cli
npm install --save-dev babel-preset-latest
npm install --save-dev babel-polyfill
npm run build
```

## await 如何处理 promise 的 reject？

[demo](https://babeljs.io/repl/#?experimental=false&evaluate=true&loose=false&spec=false&code=async%20function%20bad()%20%7B%0D%0A%20%20try%20%7B%0D%0A%20%20%20%20await%20Promise.reject(%27bad%27)%3B%0D%0A%20%20%7D%20catch(err)%20%7B%0D%0A%20%20%20%20alert(err)%3B%0D%0A%20%20%20%20console.log(err)%3B%0D%0A%20%20%7D%0D%0A%7D%0D%0A%0D%0Abad()%3B%0D%0A)

```js
sync function bad() {
  try {
    await Promise.reject('bad');
  } catch(err) {
    console.log(err);
  }
}

bad();
```