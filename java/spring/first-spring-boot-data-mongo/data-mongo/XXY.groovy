

// ----------------------------------------- 共通数据
/** 2.3.1 - 病人信息 */
class PatientInfo {
    /** 卡标示 */
    String hosCardFlag
    /** 卡号 */
    String hosCardNo
    // ...
}
/** 2.3.2 - 支付数据 */
// FIXME : 这部分 PDF 文档没说明清楚, 以下代码是通过 5.1.1 中的示例进行反推
class PayInfo {

    BankPayInfo bankPayInfo
    //WeChatPayInfo weChatPayInfo
    //AlipayInfo alipayInfo
    // ...
}

class BankPayInfo {
    /** 支付卡号 */
    String payBankCardNo
    /** 支付金额 */
    Integer bankPayAmount
    // ...
}
// ----------------------------------------- 共通 - 请求
/** 公共请求参数 */
class RequestHead {
    /** 业务功能码 */
    String busCode
    /** 终端号 */
    String terminalNo
    // ...
}

class Request<T> {
    RequestHead requestHead
    T data
}

// ----------------------------------------- 共通 - 响应
/** 公共响应参数 */
class ResponseHead {
    /** 成功标志 */
    String retCode
    /** 失败信息 */
    String retMsg
    // ...
}

class Response<T>{
    ResponseHead responseHead
    T data
}


// ----------------------------------------- 1001 - 签约办卡
/** 5.1.1 : 1001 - 签约办卡 - 请求 */
class Biz1001ReqData {
    /** 病人信息 */
    // FIXME: PDF 中的代码示例是把病人信息一一展开了，但应当像我这样代码一样封装起立
    PatientInfo patientInfo
    /** 支付方式 */
    String payMethod
    /** 支付数据 */
    PayInfo payInfo
}
/** 5.1.1 : 1001 - 签约办卡 - 响应 */
class Biz1001RespData{
    /** 患者医院 ID */
    String patientId
}
class Biz1001Service {
    Response<Biz1001RespData> biz1001(Request<Biz1001ReqData> request) {
        // TODO ...
    }
}