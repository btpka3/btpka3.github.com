const fs = require('fs');
var cssCodepoints = require('css-codepoints');
 
var css = cssCodepoints({
  fontFamily: 'MySuperFont',
  prefix: 'icon-',
  formats: {
    svg: 'iconfont20160317.svg',
    ttf: 'iconfont20160317.ttf',
    woff: 'iconfont20160317.woff',
    eot: 'iconfont20160317.eot'
  }
});
 
fs.writeFileSync('generated.css', css);

