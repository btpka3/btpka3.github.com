var a = {
    "tm": "1310715248309",
    "br": "IExplorer|8|Windows",
    "mp": [                     // 鼠标位置列表 (Mouse Position)
        {
            "target"    : "",   // 点击 HTML 对象的ID
            "x"         : 0,
            "y"         : 0,
            "time"      : 0     // 本次事件距离页面载入时间的毫秒数
        }
    ],
    "mc": [                     // 鼠标点击事件列表 (Mouse click)
        {
            "target"    : "",   // 点击 HTML 对象的ID
            "button"    : "",   // 鼠标的那个按键，候选值: 'left', 'middle', 'right'
            "attr"      : "",   // （可选）对象属性。格式: "k1=v1&k2=v2", 其中k,v都需要经过 url encode
            "time"      : 0,    // 本次事件距离页面载入时间的毫秒数
            "ex"        : 0,
            "ey"        : 0,
            "ew"        : 0,
            "eh"        : 0
        }
    ],
    "ks": [                     // 键盘按键事件列表 (Key Stroke)
        {
            "target"    : "",   // 点击对象的ID
            "key"       : "",   // 按键的字符串表示，比如 : "Ctrl+Alt+A", "key_code:229"
            "time"      : 0     // 本次事件距离页面载入时间的毫秒数
        }
    ],
    "ct": "",
    "ma": "",                   // MAC 地址信息
    "pr": "",                   // 代理服务器信息
    "id": "",                   // CollinaID 信息，JSON字符串。比如 "[hash,strID,flag]"
                                // flag=0 : 客户端没有ID
                                // flag=1 : 客户端有一个合法ID
                                // flag=2 : 客户端有一个ID，但是校验未通过。可能是客户端伪造的
    "lo": [""],                 // 当前页面的 location 和 referer, 格式为 "[location,referer]"
    "tk": "",                   // 服务器端设置的 token 字符串
    "si": {                     // 屏幕及窗口大小信息
        "screenLeft"    : 0,
        "screenTop"     : 0,
        "clientWidth"   : 0,
        "clientHeight"  : 0,
        "screenWidth"   : 0,
        "screenHeight"  : 0,
        "availWidth"    : 0,
        "availHeight"   : 0
    },
    "fi": [                     // 输入焦点信息
        {
            "time"      : 0,    // 本次事件距离页面载入时间的毫秒数
            "target"    : "",   // 事件 HTML 对象的ID
            "type"      : 0     // 0 - 失去焦点，1 - 获取焦点
        }
    ],
    "bh": "",                   // 浏览历史
    "fl": {                     // flash 获取到的信息
        "version"       : "",
        "os"            : ""
    },
    "fk": "",
    "fm": "",
    "fc": "",
    "um": ""
};


if(event_type='mouse_click',2,if(event_type='focus_input',0,if(event_type='key_stroke',1,if(event_type='mouse_position',1,2)))) as event_type

if(split(regexp_replace(target,'-','_'),'_')[0]='J',0,if(target is null,1,if(split(regexp_replace(target,'-','_'),'_')[0]='newPwd',2,if(split(regexp_replace(target,'-','_'),'_')[0]='nc',3,if(split(regexp_replace(target,'-','_'),'_')[0]='btn',4,if(split(regexp_replace(target,'-','_'),'_')[0]='nick',5,if(split(regexp_replace(target,'-','_'),'_')[0]='rePassword',6,if(split(regexp_replace(target,'-','_'),'_')[0]='mobile',7,if(split(regexp_replace(target,'-','_'),'_')[0]='password',8,if(split(regexp_replace(target,'-','_'),'_')[0]='firstName',9,if(split(regexp_replace(target,'-','_'),'_')[0]='lastName',10,if(split(regexp_replace(target,'-','_'),'_')[0]='container',11,if(split(regexp_replace(target,'-','_'),'_')[0]='companyName',12,if(split(regexp_replace(target,'-','_'),'_')[0]='username',13,if(split(regexp_replace(target,'-','_'),'_')[0]='ks',14,if(split(regexp_replace(target,'-','_'),'_')[0]='phoneNumber',15,if(split(regexp_replace(target,'-','_'),'_')[0]='checkbox',16,if(split(regexp_replace(target,'-','_'),'_')[0]='phoneArea',17,if(split(regexp_replace(target,'-','_'),'_')[0]='register',18,if(split(regexp_replace(target,'-','_'),'_')[0]='email',19,if(split(regexp_replace(target,'-','_'),'_')[0]='contactName',20,if(split(regexp_replace(target,'-','_'),'_')[0]='city',21,if(split(regexp_replace(target,'-','_'),'_')[0]='county',22,if(split(regexp_replace(target,'-','_'),'_')[0]='province',23,if(split(regexp_replace(target,'-','_'),'_')[0]='undefined',24,if(split(regexp_replace(target,'-','_'),'_')[0]='phone',25,if(split(regexp_replace(target,'-','_'),'_')[0]='code',26,if(split(regexp_replace(target,'-','_'),'_')[0]='qq',27,28)))))))))))))))))))))))))))) as target