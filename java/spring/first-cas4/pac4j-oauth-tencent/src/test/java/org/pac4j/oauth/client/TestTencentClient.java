package org.pac4j.oauth.client;

import org.pac4j.core.client.Client;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.oauth.profile.tencent.TencentProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.kryo.Kryo;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInlineFrame;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

public class TestTencentClient extends TestOAuthClient {

    private static final String APP_ID = "00000000";
    private static final String APP_KEY = "00000000000000000000000000000000";
    private static final String CALLBACK_URL = "http://test.me/";

    private static final String USER_QQ = "000000";
    private static final String USER_PASSWORD = "000000";
    private static final String USER_OPENID_IN_CUR_APP = "0000000000000000000000000000000000";

    protected Logger log = LoggerFactory.getLogger(TestTencentClient.class);

    @Override
    protected boolean isJavascriptEnabled() {
        return true;
    }

    @Override
    protected void registerForKryo(final Kryo kryo) {
        kryo.register(TencentProfile.class);
    }

    @Override
    public void testClone() {
        final TencentClient oldClient = new TencentClient();
        oldClient.setScope("xxx");
        final TencentClient client = (TencentClient) internalTestClone(oldClient);
        assertEquals(oldClient.getScope(), client.getScope());
    }

    @Override
    protected String getCallbackUrl(HtmlPage authorizationPage) throws Exception {

        HtmlInlineFrame frame = (HtmlInlineFrame) authorizationPage.getElementById("ptlogin_iframe");
        //        log.info("------------------------###\n{}------------------------===\n", authorizationPage.asXml());
        HtmlPage loginPage = (HtmlPage) frame.getEnclosedPage();

        log.info("------------------------###\n{}------------------------===\n", loginPage.getUrl().toString());

        HtmlAnchor passwordLogin = (HtmlAnchor) loginPage.getElementById("switcher_plogin");
        passwordLogin.click();

        HtmlForm loginForm = loginPage.getFormByName("loginform");
        loginForm.getInputByName("u").setValueAttribute(USER_QQ);
        loginForm.getInputByName("p").setValueAttribute(USER_PASSWORD);
        HtmlSubmitInput button = (HtmlSubmitInput) loginPage.getElementById("login_button");
        HtmlPage page2 = button.click();
        page2 = button.click();
        log.info(
                "------------------------###\n callbackUrl : {}------------------------===\n{}------------------------@@@@\n",
                page2.getUrl().toString(), page2.asXml());
        return page2.getUrl().toString();
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected Client getClient() {
        final TencentClient tencentClient = new TencentClient();
        tencentClient.setKey(APP_ID);
        tencentClient.setSecret(APP_KEY);
        tencentClient.setCallbackUrl(CALLBACK_URL);
        return tencentClient;
    }

    @Override
    protected void verifyProfile(UserProfile userProfile) {
        assertEquals(APP_ID, userProfile.getAttribute(TencentAttributesDefinition.CLIENT_ID));
        assertEquals(USER_OPENID_IN_CUR_APP, userProfile.getAttribute(TencentAttributesDefinition.OPEM_ID));
    }

}
