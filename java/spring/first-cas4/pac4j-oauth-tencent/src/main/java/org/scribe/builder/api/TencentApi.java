package org.scribe.builder.api;

import org.scribe.model.OAuthConfig;
import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.scribe.oauth.TencentOAuth20ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TencentApi extends StateApi20 {

    protected static final Logger logger = LoggerFactory.getLogger(TencentApi.class);

    @Override
    public String getAccessTokenEndpoint() {

        return "https://graph.qq.com/oauth2.0/token";
    }

    @Override
    public OAuthService createService(OAuthConfig config) {
        return new TencentOAuth20ServiceImpl(this, config);
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig config, String state) {

        // http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token

        final String authorizationEndpoint = "https://graph.qq.com/oauth2.0/authorize";

        OAuthRequest request = new OAuthRequest(Verb.GET, authorizationEndpoint);
        request.addQuerystringParameter("response_type", "code");
        request.addQuerystringParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
        request.addQuerystringParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
        request.addQuerystringParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
        request.addQuerystringParameter("state", state);
        if (config.hasScope()) {
            request.addQuerystringParameter(OAuthConstants.SCOPE, config.getScope());
        }
        return request.getCompleteUrl();
    }
}
