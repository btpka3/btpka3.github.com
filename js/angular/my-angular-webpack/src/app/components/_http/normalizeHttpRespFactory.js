var $log, $q;

// 返回非200相应，且响应内容为空。
let defaultEmptyRespErrMsg = {
    400: "参数请求格式不正确",
    401: "需要先登录呦~",
    403: "您未取得相应的权限！",
    404: "OH no, 页面未找到~",
    500: "哎呀，服务器出错了~",
    502: "服务器网关出错了",
    504: "服务器网关响应超时~"
};

function genDefaultErrJson(response) {

    let defaultErrMsg = defaultEmptyRespErrMsg[response.status];
    let defautlRespJson = {
        code: 'ERROR',
        msg: defaultErrMsg ? defaultErrMsg : "系统错误，请稍后重试",
        rawMsg: null
    };
    return defautlRespJson;
}


function process200RawMsg(jsonData) {
    if (!jsonData.raw) {
        return
    }
    jsonData.rawMsg = jsonData.msg;
    jsonData.msg = null;
    delete jsonData.raw
}

function processErrRawMsg(jsonData) {
    if (!jsonData.raw) {
        return
    }
    jsonData.rawMsg = jsonData.msg;

    let defaultErrMsg = defaultEmptyRespErrMsg[response.status];

    jsonData.msg = defaultErrMsg ? defaultErrMsg : "系统错误，请稍后重试"

    delete jsonData.raw
}

function process200Json(response) {
    var respData = response.data;
    if (respData && respData.code && respData.code === 'SUCCESS') {
        return response;
    }
}


function processErrJson(response) {
    var respData = response.data;
    if (respData && respData.code && respData.code === 'SUCCESS') {
        return response;
    }
}


/**
 * 处理 200 等正常情况
 */
function handleResp(response) {

    // 是否跳过
    if (response.config.skipNomorelize) {
        return response;
    }

    var respData = response.data;

    // 没有数据，则构造默认值
    if (!respData) {
        response.data = {code: "SUCCESS"};
        return response
    }

    // 非JSON数据
    var contentType = response.headers('Content-Type');
    if (!contentType || contentType.indexOf('application/json') !== 0) {
        return response;
    }

    // JSON数据
    if (!respData.code) {
        respData.code = "SUCCESS";
    }

    if (respData.code === "SUCCESS") {
        process200RawMsg(respData)
        return response;
    }

    // 错误码不是 SUCCESS
    return $q.reject(response);
}

/**
 * 处理 401 404 500 等错误。
 *
 */
function handleRespErr(response) {

    // 是否跳过
    if (response.config.skipNomorelize) {
        return $q.reject(response);
    }

    var respData = response.data;

    // 没有数据，则构造默认值
    if (!respData) {
        response.data = genDefaultErrJson(response);
        return $q.reject(response);
    }

    // 有数据，但不是json，不处理
    var contentType = response.headers('Content-Type');
    if (!contentType || contentType.indexOf('application/json') !== 0) {
        return $q.reject(response);
    }

    // 有 json 数据
    if (!respData.code) {
        respData.code = "ERROR";
    }
    processErrRawMsg(respData);

    return $q.reject(response);
}

/**
 * 该 Angular factory 将http响应进行正规化处理——非JSON数据不处理，空数据和JSON数据则统一格式为：
 *
 * {
 *      code    : "",        // 错误码，成功或失败, 示例值 : "SUCCESS", "ERROR"
 *      msg     : "",        // 可以消息提醒给终端用户的错误消息。该内容可能是 JS 端生成的。
 *      rawMsg  : "",        // 错误消息，内容可能包含错误堆栈信息，适合开发人员调试使用，不适合展示给终端用户。
 *      data    : ...,       // 业务数据，属性key可以不是 "data"，也可以是一级属性。
 *      ...     : ...        // 其他业务数据
 * }
 *
 * 可以在请求时，配置选项 skipNomorelize=false 来跳过处理。
 *
 *
 * 1. 对于200响应:
 *    1.1 如果没有响应内容，则构造一个默认的成功JSON结果 :
 *         {code:"SUCCESS"}
 *    1.2 如果有响应内容，但内容不是 JSON，则不做任何处理。
 *    1.3 如果有 JSON 响应, 且 code == "SUCCESS", 并处理 raw msg
 *    1.4 如果有 JSON 响应, 且 code != "SUCCESS", 则将 code 变更为 "ERROR"， 并处理 raw msg.
 *
 * 2. 对于非200响应：
 *    2.1 如果没有响应内容，则构造一个默认的成功JSON结果 : {code:"ERROR", msg:"系统错误，请稍后重试"}
 *    2.2 如果有响应内容，但内容不是 JSON，则不做任何处理。
 *    2.3 如果有 JSON 响应, 则处理错误码，并处理 raw msg.
 *
 * 3. raw msg 处理：
 *    3.1 {msg:"xxxStackTrace", raw:true}  => {msg:"系统错误，请稍后重试", rawMsg: "xxxStackTrace"}
 *    3.2 {msg:"yyyyyy", raw:false}  => {msg:"yyyyyy"}
 *
 * @param _$log
 * @param _$q
 * @returns {{response: handleResp, responseError: handleRespErr}}
 */
function normalizeHttpRespFactory(_$log, _$q) {
    $log = _$log;
    $q = _$q;

    return {
        // request: function(config){...}
        // requestError: function(rejection){...}
        response: handleResp,
        responseError: handleRespErr
    };
}
normalizeHttpRespFactory.$inject = ['$log', '$q'];

export default normalizeHttpRespFactory;
