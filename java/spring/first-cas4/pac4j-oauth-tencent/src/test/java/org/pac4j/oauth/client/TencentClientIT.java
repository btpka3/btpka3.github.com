package org.pac4j.oauth.client;

import org.pac4j.core.client.Client;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.oauth.profile.tencent.TencentProfile;

import com.esotericsoftware.kryo.Kryo;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInlineFrame;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

public class TencentClientIT extends OAuthClientIT {

    private static final String APP_ID = "000000000";
    private static final String APP_KEY = "00000000000000000000000000000000";
    private static final String CALLBACK_URL = "http://test.me/";

    private static final String USER_QQ = "000000000";
    private static final String USER_PASSWORD = "0000000";
    private static final String USER_OPENID_IN_CUR_APP = "00000000000000000000000000000000";

    @Override
    protected boolean isJavascriptEnabled() {
        return true;
    }

    @Override
    public void testClone() {
        final TencentClient oldClient = new TencentClient();
        oldClient.setScope("xxx");
        final TencentClient client = (TencentClient) internalTestClone(oldClient);
        assertEquals(oldClient.getScope(), client.getScope());
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
    protected void registerForKryo(final Kryo kryo) {
        kryo.register(TencentProfile.class);
    }

    @Override
    protected String getCallbackUrl(WebClient webClient, HtmlPage authorizationPage) throws Exception {
        // TODO Auto-generated method stub

        //        System.out.println(authorizationPage.asXml());

        HtmlInlineFrame frame = (HtmlInlineFrame) authorizationPage.getElementById("ptlogin_iframe");
        HtmlPage loginPage = (HtmlPage) frame.getEnclosedPage();

        HtmlAnchor passwordLogin = (HtmlAnchor) loginPage.getElementById("switcher_plogin");
        passwordLogin.click();

        loginPage.getElementById("loginform");
        HtmlForm loginForm = loginPage.getFormByName("loginform");
        loginForm.getInputByName("u").setValueAttribute(USER_QQ);
        loginForm.getInputByName("p").setValueAttribute(USER_PASSWORD);
        HtmlSubmitInput button = (HtmlSubmitInput) loginPage.getElementById("login_button");
        HtmlPage page2 = button.click();
        return page2.getUrl().toString();
    }

    @Override
    protected void verifyProfile(UserProfile userProfile) {
        assertEquals(APP_ID, userProfile.getAttribute(TencentAttributesDefinition.CLIENT_ID));
        assertEquals(USER_OPENID_IN_CUR_APP, userProfile.getAttribute(TencentAttributesDefinition.OPEM_ID));
    }

}