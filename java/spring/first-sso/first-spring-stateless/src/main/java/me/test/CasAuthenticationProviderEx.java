package me.test;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

public class CasAuthenticationProviderEx extends CasAuthenticationProvider {

    private AnonymousAuthenticationToken anonymousAuthenticationToken;

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        if (anonymousAuthenticationToken == null) {
            anonymousAuthenticationToken = new AnonymousAuthenticationToken(
                    super.getKey(), "anonymousUser",
                    AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
        }
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }

        if (authentication instanceof UsernamePasswordAuthenticationToken
            && (!CasAuthenticationFilter.CAS_STATEFUL_IDENTIFIER.equals(authentication.getPrincipal().toString())
            && !CasAuthenticationFilter.CAS_STATELESS_IDENTIFIER.equals(authentication.getPrincipal().toString()))) {
            // UsernamePasswordAuthenticationToken not CAS related
            return null;
        }

        // If an existing CasAuthenticationToken, just check we created it
        if (authentication instanceof CasAuthenticationToken) {
            if (this.getKey().hashCode() == ((CasAuthenticationToken) authentication).getKeyHash()) {
                return authentication;
            } else {
                throw new BadCredentialsException(messages.getMessage("CasAuthenticationProvider.incorrectKey",
                        "The presented CasAuthenticationToken does not contain the expected key"));
            }
        }

        // Ensure credentials are presented
        if ((authentication.getCredentials() == null) || "".equals(authentication.getCredentials())) {
            if(serviceProperties instanceof ServicePropertiesEx && ((ServicePropertiesEx)serviceProperties).isGateway()){
                // TODO This token should only using in next http request, ant then removed
                return anonymousAuthenticationToken;
            }else{
            throw new BadCredentialsException(messages.getMessage("CasAuthenticationProvider.noServiceTicket",
                    "Failed to provide a CAS service ticket to validate"));
            }
        }

        return super.authenticate(authentication);
    }

    public AnonymousAuthenticationToken getAnonymousAuthenticationToken() {
        return anonymousAuthenticationToken;
    }

    public void setAnonymousAuthenticationToken(AnonymousAuthenticationToken anonymousAuthenticationToken) {
        this.anonymousAuthenticationToken = anonymousAuthenticationToken;
    }

    private ServiceProperties serviceProperties;
    public void setServiceProperties(final ServiceProperties serviceProperties) {
        super.setServiceProperties(serviceProperties);
        this.serviceProperties = serviceProperties;
    }
}
