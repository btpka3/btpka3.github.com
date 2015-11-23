# TypeScript 手册

## 基本类型

### boolean
等价于JavaScript的 boolean 类型，只有 `true` 或者 `false` 两种值。

```js
var isDone: boolean = false;
```

### number
同JavaScript中的number一样，所有数值型在 TypeScript 中均为浮点数。

```js
var height: number = 6;
```

### string
字符串的值在声明时，需要用双引号 `"` 或者 单引号 `'` 括起来。

```js
var name: string = "bob";
name = 'smith';
```

### Array
数组可以是两种方式书写。第一种是使用方括号 `[]` :

```js
var list:number[] = [1, 2, 3];
```

第二种是数组的泛型格式—— 即 `Array<elemType>` :

```js
var list:Array<number> = [1, 2, 3];
```

### enum
比标准的JavaScript多提供了一种枚举型—— `enum`。像其他编程语言（比如 C# ）,
枚举可以使一组数值有更有意义的名字：

```js
enum Color {Red, Green, Blue};
var c: Color = Color.Green;
```

默认，枚举元素所代表的数值是从0开始，但是可以为特定元素手动赋值。
比如，我们可以使前面例子中的枚举从1开始，而不是从0开始。

```js
enum Color {Red = 1, Green, Blue};
var c: Color = Color.Green;
```

当然，我们也可以为枚举的每一个元素都手动赋值

```js
enum Color {Red = 1, Green, Blue};
var colorName: string = Color[2];

alert(colorName);
```

### any
如果我们在编程时（而不是在运行时）无法确定一个变量的类型，可以使用 any 来描述。
这些值可能是动态内容，它可能来自于用户，也可能来自于第三方类库。
如果使用 any 类型，TypeScript将不在编译时进行类型检查，程序能否正确运行取决于运行时的类型检查。

```js
var notSure: any = 4;
notSure = "maybe a string instead";
notSure = false; // 没问题，此处绝对是一个布尔型
```
