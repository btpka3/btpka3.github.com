# TypeScript 手册

E文原文出自[这里](http://www.typescriptlang.org/Handbook#basic-types).
该非严谨翻译始于 2015-11-23, 结束于 ????。

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

`any` 类型主要用于与既有JavaScript类库，以方便你决定在特定的情形是否TypeScript的编译时类型检查。

`any` 类型也通常用在你只知道部分类型，而不是全部。比如，一个混类元素类型的数组。

```js
var list:any[] = [1, true, "free"];

list[1] = 100;
```

### void
在某种程度上，与 `any` 语义相反的就是 `void` 了。通常用在没有返回值的函数的声明上。

```js
function warnUser(): void {
    alert("This is my warning message");
}
```

## 接口
TypeScript一个核心的原则就是——类型检查聚焦在一个值的轮廓。
有时，这被称作 "duck typing" 或 "structural subtyping"。
在TypeScript中，接口的角色就是对这些类型进行命名。
无论在你的代码内部，还是在你的工程之外（别人调用你的代码时），
接口都是一个定义约定的一个有效方式。

### 我们的第一个接口
理解接口最快的方式就从一个简单的例子开始：

```js
function printLabel(labelledObj: {label: string}) {
  console.log(labelledObj.label);
}

var myObj = {size: 10, label: "Size 10 Object"};
printLabel(myObj);
```

类型检查器检查了 `printLabel` 方法的调用。 `printLabel` 方法需要一个参数，
该参数必须要有一个类型为string型、名称为 `label` 的属性。
注意，例子中我们的参数对象是比要求的还多包含了一些其他属性，
但编译器只检查所要求的类型、名称的属性。

我们可以再写一个例子。 这次，我们使用interface来描述这样一个要求
——对象需要包含一个类型是 string 型，名称是 `label` 的属性。

```js
interface LabelledValue {
  label: string;
}

function printLabel(labelledObj: LabelledValue) {
  console.log(labelledObj.label);
}

var myObj = {size: 10, label: "Size 10 Object"};
printLabel(myObj);
```

接口 `LabelledValue` 就是前面例子中用以描述这样的需求约束的。
注意：我们并没明确说传递给 `printLabel` 方法的 参数对象必须
实现（implements）这个接口（其他强类型开发语言里会这样要求，比如 C#，Java）。
这里接口仅仅约定了该对象的内部结构而已，它可以只有 `instanceof Object` 才为true。

需要指出的是，类型检查器并不关心所要求的属性的先后顺序。只要指定名称的属性存在且类型匹配即可。


### 可选属性

有这样的可能：一个接口中，并非所有的属性都是必须的。可能一些属性只有在特定的情形下才必须有值。
通常用于可选参数。举例说明：

```
interface SquareConfig {
  color?: string;
  width?: number;
}

function createSquare(config: SquareConfig): {color: string; area: number} {
  var newSquare = {color: "white", area: 100};
  if (config.color) {
    newSquare.color = config.color;
  }
  if (config.width) {
    newSquare.area = config.width * config.width;
  }
  return newSquare;
}

var mySquare = createSquare({color: "black"});
```

接口中的可选属性的书写方式与必选属性类似，仅仅是在冒号前面多加了一个问号。

接口可选属性的优点是——我们只使用那些预期可能会有的属性，使用没有预期的属性将会编译报错。
比如下面的例子，我们内部因拼写错误而使用了非预期的属性，将会引发错误：

```js
interface SquareConfig {
  color?: string;
  width?: number;
}

function createSquare(config: SquareConfig): {color: string; area: number} {
  var newSquare = {color: "white", area: 100};
  if (config.color) {
    newSquare.color = config.collor;  // 此处会报错（因拼写错误使用了非预期的属性）
  }
  if (config.width) {
    newSquare.area = config.width * config.width;
  }
  return newSquare;
}

var mySquare = createSquare({color: "black"});
```

### 函数类型

接口除了可以描述JavaScript对象属性，还可以描述函数类型。

要使用接口来描述一个函数的类型，我们需要给接口定义一个调用签名。
它就像一个函数声明，但仅仅声明的参数列表和返回值类型。

```
interface SearchFunc {
  (source: string, subString: string): boolean;
}
```

一旦定义好了，我们就可以用它了。
下面将展示如何声明创建一个函数类型的变量，并按照接口定义给它赋值。

```js
var mySearch: SearchFunc;
mySearch = function(source: string, subString: string) {
  var result = source.search(subString);
  if (result == -1) {
    return false;
  }
  else {
    return true;
  }
}
```

对函数类型的接口检查而言，参数的名称可以变更。比如，上面的例子也可写作：

```js
var mySearch: SearchFunc;
mySearch = function(src: string, sub: string) {
  var result = src.search(sub);
  if (result == -1) {
    return false;
  }
  else {
    return true;
  }
}
```
函数的参数每次只检查一个，只要相同位置上参数的类型一致就可以了。
注意：我们函数表达式省略了返回值类型，因为按照接口的约定，已经暗指它要返回 boolean 类型了。
如果它内部返回了其他类型（比如数值型），则将会报错。

### 数组类型

同如何通过接口描述函数类型一样，我们也可以用接口来描述数组。
数组类型有一个 `index` 类型来描述可以用来索引对象的类型，
以及一个相应返回值的类型。

```
interface StringArray {
  [index: number]: string;
}

var myArray: StringArray;
myArray = ["Bob", "Fred"];
```

有两种支持的索引类型： string 或 number。
同时支持两种索引类型是可能的，但是数值型索引的返回值的类型必须是string型索引的返回值的一个子类型。

索引签名是描述数组、字典类型（dictionary）的一个有效方式，它也限定了所有属性必须匹配给定的返回值类型。
下面的例子中，特定属性就没有匹配更共通的索引签名描述，因而会报错：

```js
interface Dictionary {
  [index: string]: string;
  length: number;    // 报错。'length'的类型不是索引返回值的子类型（string）
}
```


























