package org.pac4j.oauth.client;

import org.pac4j.core.profile.converter.Converters;
import org.pac4j.oauth.profile.OAuthAttributesDefinition;

public class TencentAttributesDefinition extends OAuthAttributesDefinition {

    public static final String CLIENT_ID = "client_id";
    public static final String OPEN_ID = "openid";

    public static final TencentAttributesDefinition instance = new TencentAttributesDefinition();

    private TencentAttributesDefinition() {
        addAttribute(CLIENT_ID, Converters.stringConverter);
        addAttribute(OPEN_ID, Converters.stringConverter);
    }
}
