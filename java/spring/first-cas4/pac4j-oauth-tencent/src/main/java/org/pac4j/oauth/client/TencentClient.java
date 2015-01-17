package org.pac4j.oauth.client;

import org.apache.commons.lang3.StringUtils;
import org.pac4j.core.client.BaseClient;
import org.pac4j.core.context.WebContext;
import org.pac4j.oauth.credentials.OAuthCredentials;
import org.pac4j.oauth.profile.JsonHelper;
import org.pac4j.oauth.profile.tencent.TencentProfile;
import org.scribe.builder.api.StateApi20;
import org.scribe.builder.api.TencentApi;
import org.scribe.model.OAuthConfig;
import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequest;
import org.scribe.model.SignatureType;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.TencentOAuth20ServiceImpl;

import com.fasterxml.jackson.databind.JsonNode;

public class TencentClient extends BaseOAuth20Client<TencentProfile> {

    public final static String DEFAULT_SCOPE = "get_user_info";
    public final static String OPEN_ID_URL = "https://graph.qq.com/oauth2.0/me";

    protected String scope = DEFAULT_SCOPE;

    protected StateApi20 api20;

    public TencentClient() {
        super();
    }

    @Override
    protected void internalInit() {
        super.internalInit();

        this.api20 = new TencentApi();
        String theScope = StringUtils.isNotBlank(this.scope) ? this.scope : null;
        final OAuthConfig conf = new OAuthConfig(this.key, this.secret,
                this.callbackUrl, SignatureType.Header, theScope, null);
        this.service = new TencentOAuth20ServiceImpl(this.api20, conf);
    }

    @Override
    protected boolean requiresStateParameter() {
        return true;
    }

    @Override
    protected boolean hasBeenCancelled(WebContext context) {
        return false;
    }

    @Override
    protected TencentProfile extractUserProfile(String body) {
        final TencentProfile profile = new TencentProfile();
        String jsonStr = body.substring(9, body.length() - 2);
        final JsonNode json = JsonHelper.getFirstNode(jsonStr);
        for (final String attribute : TencentAttributesDefinition.instance.getAllAttributes()) {
            profile.addAttribute(attribute, JsonHelper.get(json, attribute));
        }
        profile.setId(profile.getAttribute(TencentAttributesDefinition.OPEN_ID));
        return profile;
    }

    @Override
    protected String getProfileUrl() {
        return OPEN_ID_URL;
    }

    protected String getProfileUrl(Token accessToken) {
        OAuthRequest request = new OAuthRequest(Verb.GET, OPEN_ID_URL);
        request.addQuerystringParameter(OAuthConstants.ACCESS_TOKEN, accessToken.getToken());
        return request.getCompleteUrl();
    }

    @Override
    protected BaseClient<OAuthCredentials, TencentProfile> newClient() {
        final TencentClient tencentClient = new TencentClient();
        tencentClient.setScope(this.getScope());
        return tencentClient;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
