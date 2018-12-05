package me.test;

import java.util.Map;

public class Yyy {

    public static void main(String[] args) {

    }

    /**
     * 对 UA 明文 collinadecryrslt 字符串解析，返回 map 结果。
     * 该 Map 用于调用 垃圾注册ua深度模型。
     *
     * 结果 Map 包含以下 key:
     *    - happen_time : 事件发生时间列表，减号拼接。比如: "4151-4153-9188"
     *
     *    - event_type  : 事件类型的列表，减号拼接。比如: "2-0-1"
     *                      0 : fi
     *                      1 : ks/mp
     *                      2 : mc/其他
     *
     *    - button      : 鼠标按键（mc）类型的列表，减号拼接。比如: "1-3-3"
     *                      0 : button == "right"
     *                      1 : button == "left"
     *                      2 : button == "middle"
     *                      3 : button == *
     *
     *    - target      : 事件焦点(fi)列表，减号拼接。比如: "1-7-3"
     *                      0 : target 以 "J_" 或 "J-" 开头
     *                      1 : target == null
     *                      2 : target 以 "newPwd_" 或 "newPwd-" 开头
     *                      3 : target 以 "nc_" 或 "nc-" 开头
     *                      4 : target 以 "btn_" 或 "btn-" 开头
     *                      5 : target 以 "nick_" 或 "nick-" 开头
     *                      6 : target 以 "rePassword_" 或 "rePassword-" 开头
     *                      7 : target 以 "mobile_" 或 "mobile-" 开头
     *                      9 : target 以 "firstName_" 或 "firstName-" 开头
     *                      10 : target 以 "lastName_" 或 "lastName-" 开头
     *                      11 : target 以 "container_" 或 "container-" 开头
     *                      12 : target 以 "companyName_" 或 "companyName-" 开头
     *                      13 : target 以 "username_" 或 "username-" 开头
     *                      14 : target 以 "ks_" 或 "ks-" 开头
     *                      15 : target 以 "phoneNumber_" 或 "phoneNumber-" 开头
     *                      16 : target 以 "checkbox_" 或 "checkbox-" 开头
     *                      17 : target 以 "phoneArea_" 或 "phoneArea-" 开头
     *                      18 : target 以 "register_" 或 "register-" 开头
     *                      19 : target 以 "email_" 或 "email-" 开头
     *                      20 : target 以 "contactName_" 或 "contactName-" 开头
     *                      21 : target 以 "city_" 或 "city-" 开头
     *                      22 : target 以 "county_" 或 "county-" 开头
     *                      23 : target 以 "province_" 或 "province-" 开头
     *                      24 : target 以 "undefined_" 或 "undefined-" 开头
     *                      25 : target 以 "phone_" 或 "phone-" 开头
     *                      26 : target 以 "code_" 或 "code-" 开头
     *                      27 : target 以 "qq_" 或 "qq-" 开头
     *                      28 : target 的值是其他情形
     *
     *    - x           : 鼠标位置 x 坐标列表(限 mp/mc 事件)，减号拼接。比如: "88-78-64"
     *                      0 : x == null
     *                      x : x
     *    - y           : 鼠标位置 y 坐标列表(限 mp/mc 事件)，减号拼接。比如: "88-78-64"
     *                      0 : y == null
     *                      y : y
     *
     *    - type        : 焦点变化列表，减号拼接。比如 "1-2-3"
     *                      0 : type == 0
     *                      1 : type == 1
     *                      2 : type == null
     *                      3 : type == 其他
     */
    public static Map<String, String> xx(String str) {



        return null;

    }
}


/*
if(split(regexp_replace(target,'-','_'),'_')[0]='J',0,
if(target is null,1,
if(split(regexp_replace(target,'-','_'),'_')[0]='newPwd',2,
if(split(regexp_replace(target,'-','_'),'_')[0]='nc',3,
if(split(regexp_replace(target,'-','_'),'_')[0]='btn',4,
if(split(regexp_replace(target,'-','_'),'_')[0]='nick',5,
if(split(regexp_replace(target,'-','_'),'_')[0]='rePassword',6,
if(split(regexp_replace(target,'-','_'),'_')[0]='mobile',7,
if(split(regexp_replace(target,'-','_'),'_')[0]='password',8,
if(split(regexp_replace(target,'-','_'),'_')[0]='firstName',9,
if(split(regexp_replace(target,'-','_'),'_')[0]='lastName',10,
if(split(regexp_replace(target,'-','_'),'_')[0]='container',11,
if(split(regexp_replace(target,'-','_'),'_')[0]='companyName',12,
if(split(regexp_replace(target,'-','_'),'_')[0]='username',13,
if(split(regexp_replace(target,'-','_'),'_')[0]='ks',14,
if(split(regexp_replace(target,'-','_'),'_')[0]='phoneNumber',15,
if(split(regexp_replace(target,'-','_'),'_')[0]='checkbox',16,
if(split(regexp_replace(target,'-','_'),'_')[0]='phoneArea',17,
if(split(regexp_replace(target,'-','_'),'_')[0]='register',18,
if(split(regexp_replace(target,'-','_'),'_')[0]='email',19,
if(split(regexp_replace(target,'-','_'),'_')[0]='contactName',20,
if(split(regexp_replace(target,'-','_'),'_')[0]='city',21,
if(split(regexp_replace(target,'-','_'),'_')[0]='county',22,
if(split(regexp_replace(target,'-','_'),'_')[0]='province',23,
if(split(regexp_replace(target,'-','_'),'_')[0]='undefined',24,
if(split(regexp_replace(target,'-','_'),'_')[0]='phone',25,
if(split(regexp_replace(target,'-','_'),'_')[0]='code',26,
if(split(regexp_replace(target,'-','_'),'_')[0]='qq',27,28
)))))))))))))))))))))))))))) as target
 */

