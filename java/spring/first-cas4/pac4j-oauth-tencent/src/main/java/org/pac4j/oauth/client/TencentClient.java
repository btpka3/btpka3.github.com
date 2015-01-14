package org.pac4j.oauth.client;

import org.pac4j.oauth.profile.TencentProfile;

import org.pac4j.core.client.BaseClient;
import org.pac4j.core.context.WebContext;
import org.pac4j.oauth.client.BaseOAuth20Client;
import org.pac4j.oauth.credentials.OAuthCredentials;
import org.scribe.model.Token;

public class TencentClient extends BaseOAuth20Client<TencentProfile> {

    @Override
    protected boolean requiresStateParameter() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected boolean hasBeenCancelled(WebContext context) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected String getProfileUrl(Token accessToken) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected TencentProfile extractUserProfile(String body) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected BaseClient<OAuthCredentials, TencentProfile> newClient() {
        // TODO Auto-generated method stub
        return null;
    }

}
