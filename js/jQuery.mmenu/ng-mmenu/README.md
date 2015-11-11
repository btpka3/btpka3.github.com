


This is a simple angular directive for jQuery.mmenu.

## Demo

See `test/ng-mmenu-test.html`.

the demo used angualr ui-router plugin. it defined three state:

1. state 'a' : contains a left menu.
1. sub state 'a.a1' : insert new content into state 'a' and bring a new right menu
1. state 'b' : contains no menu.


## Usage


```html
<nav mmenu="confObj">
  <ul>
    <li></li>
    <li></li>
    <li></li>
  </ul>
</nav>
```

the `confObj` can has following attribute:

|attribute|type     |description|
|---------|---------|---|
|opts     |object   |which is passed to `$("xxx").mmenu(opts, conf)`|
|conf     |object   |which is passed to `$("xxx").mmenu(opts, conf)`|
|init     |function |when directive is initialized, this callback function will be invoked, and pass a object parameter `mmenuDirApi`|


the `mmenuDirApi` can has following attribute:

|attribute|type     |description|
|---------|---------|---|
|element  |object   |represent the dom node. When jQuery is present, this is a jQuery object, otherwise it is a [`angular.element`](https://docs.angularjs.org/api/ng/function/angular.element)|
|remove   |function |A function whout argument which should be invoked when page is left. (e.g. If using angular ui-router plugin , exit a state)|



If all html content is in a single element( e.g. `div`). this element need add a class `mmenu-page`

Do not use anchor to open the menu. Use the off-canvas plugin's api instead.

