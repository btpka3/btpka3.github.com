package org.pac4j.oauth.client;

import org.pac4j.core.client.Client;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.oauth.client.FacebookClient;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TencentClientIT extends OAuthClientIT{


    @Override
    public void testClone() {
//        final  TencentClient oldClient = new TencentClient();
//        oldClient.setScope(SCOPE);
//        oldClient.setFields(FIELDS);
//        oldClient.setLimit(LIMIT);
//        final FacebookClient client = (FacebookClient) internalTestClone(oldClient);
//        assertEquals(oldClient.getScope(), client.getScope());
//        assertEquals(oldClient.getFields(), client.getFields());
//        assertEquals(oldClient.getLimit(), client.getLimit());
    }

    @Override
    protected Client getClient() {
        final  TencentClient tencentClient = new TencentClient();
        tencentClient.setKey("c98f506ffdeb0b40996795a006fc2d98");
        return null;
    }

    @Override
    protected String getCallbackUrl(WebClient webClient, HtmlPage authorizationPage) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void verifyProfile(UserProfile userProfile) {
        // TODO Auto-generated method stub

    }

}