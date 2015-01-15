package org.scribe.oauth;

import org.scribe.builder.api.StateApi20;
import org.scribe.model.OAuthConfig;
import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;

public class TencentOAuth20ServiceImpl extends StateOAuth20ServiceImpl {

    public TencentOAuth20ServiceImpl(StateApi20 api, OAuthConfig config) {
        super(api, config, 0, 0, null, 0);
    }

    public TencentOAuth20ServiceImpl(StateApi20 api, OAuthConfig config, int connectTimeout, int readTimeout,
            String proxyHost, int proxyPort) {
        super(api, config, connectTimeout, readTimeout, proxyHost, proxyPort);
    }

    @Override
    public Token getRequestToken() {
        OAuthRequest request = new OAuthRequest(Verb.GET, api.getAccessTokenEndpoint());
        request.addQuerystringParameter("response_type", "code");
        request.addQuerystringParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
        request.addQuerystringParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
        request.addQuerystringParameter("state", "random_CSRF_Token");
        request.addQuerystringParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
        if (config.hasScope()) {
            request.addQuerystringParameter(OAuthConstants.SCOPE, config.getScope());
        }
        Response response = request.send();
        return null;
    }

    @Override
    public Token getAccessToken(Token requestToken, Verifier verifier)
    {
        OAuthRequest request = new OAuthRequest(api.getAccessTokenVerb(), api.getAccessTokenEndpoint());
        request.addQuerystringParameter("grant_type", "authorization_code");
        request.addQuerystringParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
        request.addQuerystringParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
        request.addQuerystringParameter(OAuthConstants.CODE, verifier.getValue());
        request.addQuerystringParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
        if (config.hasScope())
            request.addQuerystringParameter(OAuthConstants.SCOPE, config.getScope());
        Response response = request.send();
        return api.getAccessTokenExtractor().extract(response.getBody());
    }
}
