import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.metrics.FastCompass;
import com.alibaba.metrics.MetricManager;
import com.alibaba.metrics.MetricName;
import com.alibaba.security.tenant.common.model.RiskResult;
import com.taobao.eagleeye.EagleEye;
import com.taobao.mbus.biz.service.preprocess.impl.BaseEventPreProcessHandler;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Supplier;


/**
 * 钉钉商家群的的企业回调消息。
 * <p>
 * 并注册为钉钉商家群组织的一个应用的企业回调消息的 API.
 * 该API 要以 TOP API方式暴露出去
 * 云成那边需要修正 钉钉开放平台，以便支持TOP的鉴权、验签机制。
 * <p>
 * 该 TOP API 需要返回的封装 MTEE3 HSF 的 RequestService#request，返回特定的 JSON 格式。
 * <p>
 * 注册 TOP API  时候，需要填写公网的IP。 针对 Mtee3 所在机房 的潜在公网IP会很多，咨询 土壕 后确认可以填写为 1.1.1.1
 * 如果后续填写的公网IP要变动 ， @墨菀
 *
 * @author dangqian.zll
 * @date 2019-12-05
 * @see com.alibaba.security.tenant.common.service.RequestService#request(java.lang.String, java.util.Map)
 * @see <a href="http://api.alibaba-inc.com/api/rest/view?detailId=63856">云成提供的示例API</a>
 * @see <a href="https://yuque.antfin-inc.com/docs/share/9bb49d2a-daf4-4ec4-8aab-7d7a9dfc0011#">云成-行业中台CRO对接方案</a>
 * @see <a href="https://ding-doc.dingtalk.com/doc#/serverapi2/skn8ld">钉钉开放平台-业务事件回调</a>
 * @see <a href="https://github.com/open-dingtalk/eapp-isv-quick-start/blob/master/src/main/java/com/controller/CallbackController.java">CallbackController.java</a>
 * @see <a href="https://aone.alibaba-inc.com/req/24972336">钉钉商家群：增加新字段：淘宝用户ID</a>
 */
public class CustomEventProcessor extends BaseEventPreProcessHandler {

    private static final Logger logger = LoggerFactory.getLogger("eventPreProcess");


    /**
     * 自行随机生成，但只能随机生成一次。
     * 因为该值在注册回调URL的时候，会告诉钉钉开放平台，然后钉钉开放平台用该值加密、解密。
     */
    private static final String TOKEN = "xxx";

    /**
     * 自行随机生成，但只能随机生成一次。
     * 因为该值在注册回调URL的时候，会告诉钉钉开放平台，然后钉钉开放平台用该值加密、解密。
     */
    private static final String AES_KEY = "xxx";


    // 日常
    private static final String DAILY_DOMAIN = "http://daily-oapi.dingtalk.test";
    private static final String DAILY_APP_KEY = "xxx";
    private static final String DAILY_APP_SECRET = "xxx";
    private static final String DAILY_CORP_ID = "xx";
    private static final DingTalkEncryptor DAILY_ENCRYPTOR = new DingTalkEncryptor(TOKEN, AES_KEY, DAILY_CORP_ID);

//    // 预发（testOrg的，现在已经废弃）
//    private static final String PRE_DOMAIN = "https://pre-oapi.dingtalk.com";
//    private static final String PRE_APP_KEY = "dingvkqrzqwlubsmyx8l";
//    private static final String PRE_APP_SECRET = "z4OuBo9RX3HXuvCOywWkGYZcMRyHLBQbhPPO-_JhYjuvrzSorf7d1IQZY75WwRkt";
//    private static final String PRE_CORP_ID = "dingd9c1cd4e41c5a35435c2f4657eb6378f";
//    private static final DingTalkEncryptor PRE_ENCRYPTOR = new DingTalkEncryptor(TOKEN, AES_KEY, PRE_CORP_ID);

    // 预发
    private static final String PRE_DOMAIN = "https://pre-oapi.dingtalk.com";
    private static final String PRE_APP_KEY = "xx";
    private static final String PRE_APP_SECRET = "xx";
    private static final String PRE_CORP_ID = "xx";
    private static final DingTalkEncryptor PRE_ENCRYPTOR = new DingTalkEncryptor(TOKEN, AES_KEY, PRE_CORP_ID);

    // 线上的
    private static final String PROD_DOMAIN = "https://oapi.dingtalk.com";
    private static final String PROD_APP_KEY = "x";
    private static final String PROD_APP_SECRET = new String(Base64.getDecoder().decode("xxx=="), StandardCharsets.UTF_8);
    private static final String PROD_CORP_ID = "xx";
    private static final DingTalkEncryptor PROD_ENCRYPTOR = new DingTalkEncryptor(TOKEN, AES_KEY, PROD_CORP_ID);


    public transient SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * FastCompass 子分类
     *
     * @see <a href="http://gitlab.alibaba-inc.com/eagleeye-sdk-group/ali-metrics/wikis/quick-start">FastCompass可以标记的子分类</a>
     */
    public static final String FC_CATALOG_SUCCESS = "success";
    public static final String FC_CATALOG_ERROR = "error";
    public static final String FC_CATALOG_HIT = "hit";


    public static final String SUCCESS = "success";
    public static final String EVENT_TYPE_CHECK_URL = "check_url";
    public static final String EVENT_INDUSTRY_CONTENT_MODIFY = "industry_content_modify";
    public static final String TARGET_EVENT_CODE = "taobao_taobao_meta_mtee_sns_unify_check";

    public static final RiskResult RESULT_INVALID_REQUEST = new RiskResult();


    /**
     * env=daily/pre/prod
     */
    protected static String env = "prod";

    static {
        RESULT_INVALID_REQUEST.setCode(444);
        RESULT_INVALID_REQUEST.setMsg("该事件值用于接收钉钉开放平台推送的回调消息, 以 TOP API 的方式调用。");
        // 未命中风险
        RESULT_INVALID_REQUEST.setResult(false);

        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            if (hostName.contains("pre")) {
                env = "pre";
            } else if (!hostName.contains("mtee")) {
                env = "daily";
            }
        } catch (UnknownHostException e) {
        }
    }


    /**
     * hsf 调用该接口
     *
     * @param eventCode 事件CODE
     * @param context   事件请求上下文
     * @return 业务结果
     */
    @Override
    public Object request(String eventCode, Map<String, Object> context) {
        // 调试用
        logger.warn("dingding open callback message received : traceId={}, eventCode={}, context={}",
                EagleEye.getTraceId(),
                eventCode,
                JSON.toJSONString(context)
        );

        // 钉钉开放平台标准的回调消息有 4个key: msg_signature/timeStamp/nonce/encrypt
        // 但是由于 经过 TOP 平台，TOP 占用了一些名称，比如：app_ip/app_open_domain/app_key/method/timestamp 等
        // 未避免冲突：云成将 4个key 重命名为: msg_signature/time_stamp/msg_nonce/encrypt

        long startTime = System.currentTimeMillis();

        Ctx ctx = new Ctx();
        ctx.eventCode = eventCode;

        try {
            doRequest(ctx, context);

            long endTime = System.currentTimeMillis();
            getFastCompass(ctx).record(endTime - startTime, FC_CATALOG_SUCCESS);

            // 返回正确的结果
            return getEncryptor().getEncryptedMap(
                    SUCCESS,
                    System.currentTimeMillis() / 1000,
                    generateNonce()
            );
        } catch (Exception e) {

            if (e instanceof CallbackHandleException) {
                ctx.errorCode = ((CallbackHandleException) e).getCode();
            } else if (e instanceof DingTalkEncryptException) {
                ctx.errorCode = ((DingTalkEncryptException) e).getCode();
            } else {
                ctx.errorCode = 999999;
            }

            logger.error("failed to handle dingding open platform callback message. traceId={}, eventCode={}, context={}",
                    EagleEye.getTraceId(),
                    eventCode,
                    JSON.toJSONString(context),
                    e
            );

            long endTime = System.currentTimeMillis();
            getFastCompass(ctx).record(endTime - startTime, FC_CATALOG_ERROR);

            Map<String, String> errorResp = new HashMap<>(4);
            errorResp.put("traceId", EagleEye.getTraceId());
            errorResp.put("msg", e.getMessage());
            errorResp.put("stacktrace", ExceptionUtils.getStackTrace(e));
            String respClearText = JSON.toJSONString(errorResp);

            return getEncryptor().getEncryptedMap(
                    respClearText,
                    System.currentTimeMillis() / 1000,
                    generateNonce()
            );
        }
    }

    protected void doRequest(Ctx ctx, Map<String, Object> context) {

        String msgSignature = (String) context.get("msg_signature");
        if (StringUtils.isBlank(msgSignature)) {
            throw new CallbackHandleException(990001, "`msg_signature` is required");
        }

        String timestamp = (String) context.get("msg_timestamp");
        if (StringUtils.isBlank(timestamp)) {
            throw new CallbackHandleException(990002, "`msg_timestamp` is required");
        }
        String nonce = (String) context.get("msg_nonce");
        if (StringUtils.isBlank(nonce)) {
            throw new CallbackHandleException(990003, "`msg_nonce` is required");
        }

        String encrypt = (String) context.get("msg_encrypt");
        if (StringUtils.isBlank(nonce)) {
            throw new CallbackHandleException(990004, "`msg_encrypt` is required");
        }


        // 验证签名并解密
        String msg = getEncryptor().getDecryptMsg(msgSignature, timestamp, nonce, encrypt);

        logger.warn("dingding open callback message decrypted : traceId={}, rawMsg={}",
                EagleEye.getTraceId(),
                msg
        );

        JSONObject msgJsonObj = JSON.parseObject(msg);
        String eventType = msgJsonObj.getString("EventType");
        ctx.eventType = eventType;

        if (Objects.equals(EVENT_TYPE_CHECK_URL, eventType)) {
            handleEventCheckUrl();
        }
        if (Objects.equals(EVENT_INDUSTRY_CONTENT_MODIFY, eventType)) {
            handleEventIndustryContentModify(ctx, msgJsonObj);
        }
    }


    @Override
    public RiskResult handle(String eventCode, Object data) {
        return RESULT_INVALID_REQUEST;
    }

    @Override
    public RiskResult asyncHandle(String eventCode, Object data) {
        return RESULT_INVALID_REQUEST;
    }

    protected FastCompass getFastCompass(
            Ctx ctx
    ) {
        return MetricManager.getFastCompass(
                "mtee3",
                MetricName.build(ctx.eventCode).tagged(
                        "eventCode", ctx.eventCode,
                        "eventType", ctx.eventType,
                        "errorCode", ctx.errorCode == null ? null : Integer.toString(ctx.errorCode),
                        "forwarded", Boolean.toString(ctx.forwarded)
                )
        );
    }

    protected void handleEventCheckUrl() {
        // do nothing
    }

    protected void handleEventIndustryContentModify(Ctx ctx, JSONObject msgJsonObj) {

        Map<String, Object> map = toCsiParam(msgJsonObj);
        if (map == null || map.isEmpty()) {
            return;
        }

        super.handle(TARGET_EVENT_CODE, map);
        ctx.forwarded = true;
    }

    protected void assertThat(boolean result, Supplier<String> msgSupplier) {
        if (!result) {
            throw new RuntimeException(msgSupplier.get());
        }
    }

    protected Map<String, Object> toCsiParam4Im(JSONObject msgJsonObj) {


        Map<String, Object> r = new HashMap<>(16);
        // 淘宝联防
        // http://uf-pre.alibaba.com/v2/index.html#/platform-content/settings/business/ali.china.taobao_taobaodefense/ding_groupchat
        r.put("businessName", "ali.china.taobao_taobaodefense");

        // 钉钉商家群
        r.put("firstProductName", "ding_groupchat");

        // gmtCreate
        Long createAt = msgJsonObj.getLong("createAt");
        if (createAt != null && createAt > 0) {
            String gmtCreate = FORMATTER.format(new Date(createAt));
            r.put("gmtCreate", gmtCreate);
        }

        // $.contentType
        r.put("contentType", "groupChat");

        // $.srcId

        JSONObject imContentJson = msgJsonObj.getJSONObject("imContent");
        assertThat(imContentJson != null, () -> "`$.imContent` is required");
        String chatId = imContentJson.getString("chatId");
        assertThat(StringUtils.isNotBlank(chatId), () -> "`$.imContent.chatId` is required");

        String msgId = (String) JSONPath.eval(msgJsonObj, "$.imContent.msgId");
        assertThat(StringUtils.isNotBlank(chatId), () -> "`$.imContent.msgId` is required");
        String srcId = msgId + "_" + chatId;
        r.put("srcId", srcId);


        // $.imGroup.id
        Map<String, Object> imGroupMap = new HashMap<>(4);
        imGroupMap.put("id", chatId);

        // $.imGroup.name
        String chatName = imContentJson.getString("chatName");
        if (StringUtils.isNotBlank(chatName)) {
            imGroupMap.put("name", chatName);
        }

        // $.imGroup.company.id
        String corpId = msgJsonObj.getString("corpId");
        Map<String, Object> companyMap = new HashMap<>(4);
        companyMap.put("id", corpId);
        imGroupMap.put("company", companyMap);

        r.put("imGroup", imGroupMap);

        // $.csiUser.specialId

        JSONObject userInfoJson = msgJsonObj.getJSONObject("userInfo");

        Map<String, Object> csiUserMap = new HashMap<>(4);

        String userId = userInfoJson.getString("userId");
        assertThat(StringUtils.isNotBlank(chatId), () -> "`$.userInfo.userId` is required");
        csiUserMap.put("specialId", userId);

        // $.csiUser.userNick
        String userNick = userInfoJson.getString("nick");
        if (StringUtils.isNotBlank(userNick)) {
            csiUserMap.put("userNick", userNick);
        }


        // $.csiUser.avatarImage.url
        String avatarUrl = userInfoJson.getString("avatarUrl");
        if (StringUtils.isNotBlank(avatarUrl)) {
            Map<String, Object> avatarImageMap = new HashMap<>(4);
            avatarImageMap.put("url", avatarUrl);
            csiUserMap.put("avatarImage", avatarImageMap);
        }

        r.put("csiUser", csiUserMap);


        // $.csiTexts[0].content
        String text = imContentJson.getString("text");
        if (StringUtils.isNotBlank(text)) {
            List<Map<String, Object>> csiTexts = new ArrayList<>();
            Map<String, Object> csiTextMap = new HashMap<>(4);
            csiTextMap.put("content", text);
            csiTexts.add(csiTextMap);
            r.put("csiTexts", csiTexts);
        }

        // $.csiImages*.*
        JSONArray imagesJsonArr = imContentJson.getJSONArray("images");

        if (imagesJsonArr != null && !imagesJsonArr.isEmpty()) {
            List<Map<String, Object>> csiImages = new ArrayList<>(imagesJsonArr.size());

            for (Object imageJsonObj : imagesJsonArr) {
                JSONObject imageJson = (JSONObject) imageJsonObj;
                if (imageJson == null) {
                    continue;
                }
                Map<String, Object> csiImage = new HashMap<>(4);

                String mediaUrl = imageJson.getString("mediaUrl");
                if (StringUtils.isNotBlank(mediaUrl)) {
                    csiImage.put("url", mediaUrl);
                }

                String mediaMd5 = imageJson.getString("mediaMd5");
                if (StringUtils.isNotBlank(mediaMd5)) {
                    csiImage.put("md5", mediaMd5);
                }

                String downloadCode = imageJson.getString("downloadCode");
                if (StringUtils.isNotBlank(downloadCode)) {
                    csiImage.put("id", downloadCode);
                }
                if (csiImage.isEmpty()) {
                    continue;
                }

                csiImages.add(csiImage);
            }
            r.put("csiImages", csiImages);
        }

        // $.csiVideo*.*
        JSONArray videosJsonArr = imContentJson.getJSONArray("videos");
        if (videosJsonArr != null && !videosJsonArr.isEmpty()) {
            List<Map<String, Object>> csiVideoList = new ArrayList<>(videosJsonArr.size());
            for (Object videosJsonObj : videosJsonArr) {
                JSONObject videosJson = (JSONObject) videosJsonObj;
                Map<String, Object> csiVideo = new HashMap<>(4);

                String mediaUrl = videosJson.getString("mediaUrl");
                if (StringUtils.isNotBlank(mediaUrl)) {
                    csiVideo.put("url", mediaUrl);
                }

                String mediaMd5 = videosJson.getString("mediaMd5");
                if (StringUtils.isNotBlank(mediaMd5)) {
                    csiVideo.put("md5", mediaMd5);
                }

                String downloadCode = videosJson.getString("downloadCode");
                if (StringUtils.isNotBlank(downloadCode)) {
                    csiVideo.put("id", downloadCode);
                }

                if (csiVideo.isEmpty()) {
                    continue;
                }
                csiVideoList.add(csiVideo);
            }
            r.put("csiVideo", csiVideoList);
        }

        // $.ext.taobaoUserIdList
        Map<String, Object> ext = new HashMap<>(4);
        String extJson = msgJsonObj.getString("extJson");
        if (StringUtils.isNotBlank(extJson)) {
            JSONObject extJsonObj = JSON.parseObject(extJson);
            String retailUserIds = extJsonObj.getString("retailUserIds");
            if (StringUtils.isNotBlank(retailUserIds)) {
                ext.put("taobaoUserIdList", retailUserIds);
            }
        }

        // $.ext.msgId
        ext.put("msgId", msgId);

        if (!ext.isEmpty()) {
            r.put("ext", ext);
        }

        Map<String, Object> param = new HashMap<>(4);
        param.put("r", r);
        return param;
    }

    protected Map<String, Object> toCsiParam(JSONObject msgJsonObj) {
        // 场景： IM/profile/comment， 需要根据该值设置不同的 businessName/firstProductName
        String scene = msgJsonObj.getString("scene");
        assertThat(StringUtils.isNotBlank(scene), () -> "`$.scene` is required");
        if ("im".equals(scene)) {
            return toCsiParam4Im(msgJsonObj);
        }
        return null;
    }

    protected static String getAppKey() {
        if ("prod".equals(env)) {
            return PROD_APP_KEY;
        } else if ("pre".equals(env)) {
            return PRE_APP_KEY;
        } else {
            return DAILY_APP_KEY;
        }
    }

    protected static String getAppSecret() {
        if ("prod".equals(env)) {
            return PROD_APP_SECRET;
        } else if ("pre".equals(env)) {
            return PRE_APP_SECRET;
        } else {
            return DAILY_APP_SECRET;
        }
    }

    protected static String getDomain() {
        if ("prod".equals(env)) {
            return PROD_DOMAIN;
        } else if ("pre".equals(env)) {
            return PRE_DOMAIN;
        } else {
            return DAILY_DOMAIN;
        }
    }

    protected static DingTalkEncryptor getEncryptor() {
        if ("prod".equals(env)) {
            return PROD_ENCRYPTOR;
        } else if ("pre".equals(env)) {
            return PRE_ENCRYPTOR;
        } else {
            return DAILY_ENCRYPTOR;
        }
    }


    protected String generateNonce() {
        return RandomStringUtils.randomAlphanumeric(12);
    }

    /**
     * 主要用于跨方法打印 tracing 用数据
     */
    public static class Ctx {
        /**
         * mtee3 事件 code
         */
        public String eventCode;

        /**
         * eventType
         */
        public String eventType;

        /**
         * 错误码
         */
        public Integer errorCode;

        /**
         * 是否将氢气转发到 taobao_taobao_meta_mtee_sns_unify_check 。
         */
        public boolean forwarded;

    }

    /**
     * 自定义异常。
     * 错误码从 990001 开始，防止和 DingTalkEncryptException 的冲突。
     * 999999 作为默认系统异常的错误code
     */
    public static class CallbackHandleException extends RuntimeException {

        private static Map<Integer, String> msgMap = new HashMap();

        static {
            // msgMap.put(990001, "`msg_signature` is required");
        }

        private Integer code;

        public Integer getCode() {
            return this.code;
        }

        public CallbackHandleException(Integer code) {
            super(msgMap.get(code));
            this.code = code;
        }

        public CallbackHandleException(Integer code, String message) {
            super(message);
            this.code = code;
        }

        public CallbackHandleException(Integer code, String message, Throwable cause) {
            super(message, cause);
            this.code = code;
        }
    }

    // -------------------------- com.taobao.top:lippi-oapi-encrpt 代码复制

    public static class DingTalkEncryptor {
        private static final Charset CHARSET = StandardCharsets.UTF_8;
        private byte[] aesKey;
        private String token;
        private String corpId;
        private static final Integer AES_ENCODE_KEY_LENGTH = 43;
        private static final Integer RANDOM_LENGTH = 16;

        public DingTalkEncryptor(String token, String encodingAesKey, String corpIdOrSuiteKey) throws DingTalkEncryptException {
            if (null != encodingAesKey && encodingAesKey.length() == AES_ENCODE_KEY_LENGTH) {
                this.token = token;
                this.corpId = corpIdOrSuiteKey;
                this.aesKey = Base64.getDecoder().decode(encodingAesKey + "=");
            } else {
                throw new DingTalkEncryptException(900004);
            }
        }

        public Map<String, String> getEncryptedMap(String plaintext, Long timeStamp, String nonce) throws DingTalkEncryptException {
            if (null == plaintext) {
                throw new DingTalkEncryptException(900001);
            } else if (null == timeStamp) {
                throw new DingTalkEncryptException(900002);
            } else if (null == nonce) {
                throw new DingTalkEncryptException(900003);
            } else {
                String encrypt = this.encrypt(Utils.getRandomStr(RANDOM_LENGTH), plaintext);
                String signature = this.getSignature(this.token, String.valueOf(timeStamp), nonce, encrypt);
                Map<String, String> resultMap = new HashMap();
                resultMap.put("msg_signature", signature);
                resultMap.put("encrypt", encrypt);
                resultMap.put("timeStamp", String.valueOf(timeStamp));
                resultMap.put("nonce", nonce);
                return resultMap;
            }
        }

        public String getDecryptMsg(String msgSignature, String timeStamp, String nonce, String encryptMsg) throws DingTalkEncryptException {
            String signature = this.getSignature(this.token, timeStamp, nonce, encryptMsg);
            if (!signature.equals(msgSignature)) {
                throw new DingTalkEncryptException(900006);
            } else {
                String result = this.decrypt(encryptMsg);
                return result;
            }
        }

        private String encrypt(String random, String plaintext) throws DingTalkEncryptException {
            try {
                byte[] randomBytes = random.getBytes(CHARSET);
                byte[] plainTextBytes = plaintext.getBytes(CHARSET);
                byte[] lengthByte = Utils.int2Bytes(plainTextBytes.length);
                byte[] corpidBytes = this.corpId.getBytes(CHARSET);
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                byteStream.write(randomBytes);
                byteStream.write(lengthByte);
                byteStream.write(plainTextBytes);
                byteStream.write(corpidBytes);
                byte[] padBytes = PKCS7Padding.getPaddingBytes(byteStream.size());
                byteStream.write(padBytes);
                byte[] unencrypted = byteStream.toByteArray();
                byteStream.close();
                Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
                SecretKeySpec keySpec = new SecretKeySpec(this.aesKey, "AES");
                IvParameterSpec iv = new IvParameterSpec(this.aesKey, 0, 16);
                cipher.init(1, keySpec, iv);
                byte[] encrypted = cipher.doFinal(unencrypted);
                String result = java.util.Base64.getEncoder().encodeToString(encrypted);
                return result;
            } catch (Exception var15) {
                throw new DingTalkEncryptException(900007);
            }
        }

        private String decrypt(String text) throws DingTalkEncryptException {
            byte[] originalArr;
            byte[] networkOrder;
            try {
                Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
                SecretKeySpec keySpec = new SecretKeySpec(this.aesKey, "AES");
                IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(this.aesKey, 0, 16));
                cipher.init(2, keySpec, iv);
                networkOrder = Base64.getDecoder().decode(text);
                originalArr = cipher.doFinal(networkOrder);
            } catch (Exception var9) {
                throw new DingTalkEncryptException(900008);
            }

            String plainText;
            String fromCorpid;
            try {
                byte[] bytes = PKCS7Padding.removePaddingBytes(originalArr);
                networkOrder = Arrays.copyOfRange(bytes, 16, 20);
                int plainTextLegth = Utils.bytes2int(networkOrder);
                plainText = new String(Arrays.copyOfRange(bytes, 20, 20 + plainTextLegth), CHARSET);
                fromCorpid = new String(Arrays.copyOfRange(bytes, 20 + plainTextLegth, bytes.length), CHARSET);
            } catch (Exception var8) {
                throw new DingTalkEncryptException(900009);
            }

            if (!fromCorpid.equals(this.corpId)) {
                throw new DingTalkEncryptException(900010);
            } else {
                return plainText;
            }
        }

        public String getSignature(String token, String timestamp, String nonce, String encrypt) throws DingTalkEncryptException {
            try {
                String[] array = new String[]{token, timestamp, nonce, encrypt};
                Arrays.sort(array);
                StringBuffer sb = new StringBuffer();

                for (int i = 0; i < 4; ++i) {
                    sb.append(array[i]);
                }

                String str = sb.toString();
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                md.update(str.getBytes());
                byte[] digest = md.digest();
                StringBuffer hexstr = new StringBuffer();
                String shaHex = "";

                for (int i = 0; i < digest.length; ++i) {
                    shaHex = Integer.toHexString(digest[i] & 255);
                    if (shaHex.length() < 2) {
                        hexstr.append(0);
                    }

                    hexstr.append(shaHex);
                }

                return hexstr.toString();
            } catch (Exception var13) {
                throw new DingTalkEncryptException(900006);
            }
        }

        private static void RemoveCryptographyRestrictions() throws Exception {
            Class<?> jceSecurity = getClazz("javax.crypto.JceSecurity");
            Class<?> cryptoPermissions = getClazz("javax.crypto.CryptoPermissions");
            Class<?> cryptoAllPermission = getClazz("javax.crypto.CryptoAllPermission");
            if (jceSecurity != null) {
                setFinalStaticValue(jceSecurity, "isRestricted", false);
                PermissionCollection defaultPolicy = (PermissionCollection) getFieldValue(jceSecurity, "defaultPolicy", (Object) null, PermissionCollection.class);
                if (cryptoPermissions != null) {
                    Map<?, ?> map = (Map) getFieldValue(cryptoPermissions, "perms", defaultPolicy, Map.class);
                    map.clear();
                }

                if (cryptoAllPermission != null) {
                    Permission permission = (Permission) getFieldValue(cryptoAllPermission, "INSTANCE", (Object) null, Permission.class);
                    defaultPolicy.add(permission);
                }
            }

        }

        private static Class<?> getClazz(String className) {
            Class clazz = null;

            try {
                clazz = Class.forName(className);
            } catch (Exception var3) {
            }

            return clazz;
        }

        private static void setFinalStaticValue(Class<?> srcClazz, String fieldName, Object newValue) throws Exception {
            Field field = srcClazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & -17);
            field.set((Object) null, newValue);
        }

        private static <T> T getFieldValue(Class<?> srcClazz, String fieldName, Object owner, Class<T> dstClazz) throws Exception {
            Field field = srcClazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return dstClazz.cast(field.get(owner));
        }

        static {
            try {
                Security.setProperty("crypto.policy", "limited");
                RemoveCryptographyRestrictions();
            } catch (Exception var1) {
            }

        }
    }


    public static class DingTalkEncryptException extends RuntimeException {
        public static final int SUCCESS = 0;
        public static final int ENCRYPTION_PLAINTEXT_ILLEGAL = 900001;
        public static final int ENCRYPTION_TIMESTAMP_ILLEGAL = 900002;
        public static final int ENCRYPTION_NONCE_ILLEGAL = 900003;
        public static final int AES_KEY_ILLEGAL = 900004;
        public static final int SIGNATURE_NOT_MATCH = 900005;
        public static final int COMPUTE_SIGNATURE_ERROR = 900006;
        public static final int COMPUTE_ENCRYPT_TEXT_ERROR = 900007;
        public static final int COMPUTE_DECRYPT_TEXT_ERROR = 900008;
        public static final int COMPUTE_DECRYPT_TEXT_LENGTH_ERROR = 900009;
        public static final int COMPUTE_DECRYPT_TEXT_CORPID_ERROR = 900010;
        private static Map<Integer, String> msgMap = new HashMap();
        private Integer code;

        public Integer getCode() {
            return this.code;
        }

        public DingTalkEncryptException(Integer exceptionCode) {
            super((String) msgMap.get(exceptionCode));
            this.code = exceptionCode;
        }

        static {
            msgMap.put(0, "成功");
            msgMap.put(900001, "加密明文文本非法");
            msgMap.put(900002, "加密时间戳参数非法");
            msgMap.put(900003, "加密随机字符串参数非法");
            msgMap.put(900005, "签名不匹配");
            msgMap.put(900006, "签名计算失败");
            msgMap.put(900004, "不合法的aes key");
            msgMap.put(900007, "计算加密文字错误");
            msgMap.put(900008, "计算解密文字错误");
            msgMap.put(900009, "计算解密文字长度不匹配");
            msgMap.put(900010, "计算解密文字corpid不匹配");
        }
    }

    public static class Utils {
        public Utils() {
        }

        public static String getRandomStr(int count) {
            String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            Random random = new Random();
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < count; ++i) {
                int number = random.nextInt(base.length());
                sb.append(base.charAt(number));
            }

            return sb.toString();
        }

        public static byte[] int2Bytes(int count) {
            byte[] byteArr = new byte[]{(byte) (count >> 24 & 255), (byte) (count >> 16 & 255), (byte) (count >> 8 & 255), (byte) (count & 255)};
            return byteArr;
        }

        public static int bytes2int(byte[] byteArr) {
            int count = 0;

            for (int i = 0; i < 4; ++i) {
                count <<= 8;
                count |= byteArr[i] & 255;
            }

            return count;
        }
    }


    public static class PKCS7Padding {
        private static final Charset CHARSET = StandardCharsets.UTF_8;
        private static final int BLOCK_SIZE = 32;

        public PKCS7Padding() {
        }

        public static byte[] getPaddingBytes(int count) {
            int amountToPad = 32 - count % 32;
            if (amountToPad == 0) {
                amountToPad = 32;
            }

            char padChr = chr(amountToPad);
            String tmp = new String();

            for (int index = 0; index < amountToPad; ++index) {
                tmp = tmp + padChr;
            }

            return tmp.getBytes(CHARSET);
        }

        public static byte[] removePaddingBytes(byte[] decrypted) {
            int pad = decrypted[decrypted.length - 1];
            if (pad < 1 || pad > 32) {
                pad = 0;
            }

            return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
        }

        private static char chr(int a) {
            byte target = (byte) (a & 255);
            return (char) target;
        }
    }

}


