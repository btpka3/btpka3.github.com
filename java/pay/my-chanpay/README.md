

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



 # 生成公钥、私钥
 
 ```bash
 # 生成私钥文件 : rsa_private_key.pem
 openssl genrsa -out rsa_private_key.pem 1024
 
 # 显示PKCS8格式私钥内容，并保存到配置文件中
 openssl pkcs8 -topk8 -nocrypt -inform PEM -in rsa_private_key.pem -outform PEM outform
 
 # 生成独立公钥文件: rsa_public_key.pem 并保存内容到配置文件中
 openssl rsa -in rsa_private_key.pem -pubout -out rsa_public_key.pem
 ```


