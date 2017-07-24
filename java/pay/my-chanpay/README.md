

## 官方示例

|type     | name             |  官方示例                                 |自己演示 demo 确认示例|
|---------|-------------------|-----------------------------------------|--------------------|
|入款类    |网银支付接口文档Demo | bak/wangyin/ChanpayGatewayQpayDemo.java |                       |
|入款类    |担保交易接口文档Demo | bak/danbao/ChanpayGatewayQpayDemo.java  |                       |
|入款类    |扫码接口文档Demo    | bak/saoma/ChanpaySCPDemo.java            |MyScanQrCodeController|
|入款类    |快捷接口文档Demo    | bak/kuaijie/ChanpayGatewayDemo.java      |                       |
|入款类    |代收付接口文档Demo  | bak/daishou/**                           |                       |
|入款类    |安卓SDK接口文档     |                                          |                       |
|入款类    |iOSSDK接口文档     |                                          |                       |
|入款类    |手机控件支付接口文档 |                                          |                       |
|出款类    |出款java版demo     | bak/chukuan/ChanpayGatewayQpayDemo.java  |                       |




# ddd


|api    | service                   | 名称                    |微信? |支付宝?   |银联?    |网银?    |代收?    | desp|
|-------|---------------------------|------------------------|-----|---------|--------|---------|---------|------|
|A      |mag_init_code_pay          |用户扫描二维码            |Y    |Y        |Y       |         |         |      |
|A      |mag_pass_code_pay          |商家扫描二维码            |Y    |Y        |Y       |         |         |      |
|A      |mag_one_code_pay           |一码付                   |     |         |        |         |         |      |
|A      |mag_pub_com_pay            |微信公众号 (大商户)       |Y    |         |        |         |         |适合商家无微信公众号  |
|A      |mag_pub_sinm_pay           |微信公众号 (一户一码)     |Y    |         |        |         |         |适合商家有自己的微信公众号      |
|A      |mag_ali_pub_sinm_pay       |支付宝服务窗 (一户一码)    |    |Y         |        |         |         |      |
|A      |mag_ali_wap_pay            |支付宝WAP支付            |     |Y        |        |         |         |      |
|A      |nmg_ensure_trade_confirm   |担保交易确认接口          |     |         |        |         |         |      |
|A      |nmg_api_query_trade        |商户订单状态查询          |     |         |        |         |         |      |
|A      |nmg_api_refund             |商户退款申请请求          |     |         |        |         |         |      |
|A      |nmg_api_everyday_trade_file|交易对账单               |     |         |        |         |         |      |
|A      |nmg_api_refund_trade_file  |退款对账单               |     |         |        |         |         |      |



* A : API 字段命名格式为驼峰式，但首字母大写。



 # 生成公钥、私钥
 
 ```bash
 # 生成私钥文件 : rsa_private_key.pem
 openssl genrsa -out rsa_private_key.pem 1024
 
 # 显示PKCS8格式私钥内容，并保存到配置文件中
 openssl pkcs8 -topk8 -nocrypt -inform PEM -in rsa_private_key.pem -outform PEM outform
 
 # 生成独立公钥文件: rsa_public_key.pem 并保存内容到配置文件中
 openssl rsa -in rsa_private_key.pem -pubout -out rsa_public_key.pem
 ```


# DB?

```text
CP_NOTIFY_XXX   从畅捷支付推送过来的通知消息
CP_REQ_XXX      向畅捷支付发送的API调用时请求的参数
CP_RESP_XXX     向畅捷支付发送的API调用时响应的参数
```

# API?

```text
# API 调用
    // 向畅捷支付发送请求，并将消息同样发送到 MQ
    // MQ 监听1: 接收消息，作为日志将请求保存到数据库里
    // 当 API 调用结果返回时，也同样将消息发送到 MQ，并返回结果给调用者。
    // MQ 监听2: 接收消息，作为日志将响应保存到数据库里

# 推送通知
/api/chanpay/notify/{mchId} 
    // API 实现: 接收消息，转发到 MQ，立即响应 "success"
    // MQ 监听1: 保存到数据库里
    // MQ 监听2: 处理响应的业务逻辑
```

