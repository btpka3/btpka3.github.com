const Koa = require('koa');
const cors = require('@koa/cors');
const json = require('koa-json');

const app = new Koa();

app.use(cors());
app.use(json());
app.use(ctx => {
  ctx.body = {a: "aaa"};
});

app.listen(3000);
