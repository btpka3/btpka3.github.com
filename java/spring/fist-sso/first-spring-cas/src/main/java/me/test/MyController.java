package me.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Controller
public class MyController {

    private Logger logger = LoggerFactory.getLogger(MyController.class);

    @Value("#{appCfg['testProxy.url.appointment']}")
    private String appointmentUrl;

    @Value("#{appCfg['testProxy.url.staff']}")
    private String staffUrl;

    @RequestMapping("/testProxy")
    // 这些权限控制的注解也可以应用到接口上
    public String testProxy(Map<String, Object> modelMap, Principal principal) throws UnsupportedEncodingException {

        RestTemplate rest = new RestTemplate();
        rest.setErrorHandler(new ResponseErrorHandler() {

            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }

            public void handleError(ClientHttpResponse response) throws IOException {
            }
        });

        CasAuthenticationToken token = null;
        if (principal instanceof CasAuthenticationToken) {
            token = (CasAuthenticationToken) principal;
        }
        // FIXME : Why SecurityContextHolder.getContext().getPrincipal() != httpServletRequest.getUserPrincipal();
        // Actually is User != CasAuthenticationToken

        // 调用 appointment 服务
        String appointmentServiceUrl = appointmentUrl;
        if (token != null) {
            String proxyTicket = token.getAssertion().getPrincipal().getProxyTicketFor(appointmentUrl);
            appointmentServiceUrl = appointmentUrl + "?ticket=" + URLEncoder.encode(proxyTicket, "UTF-8");
        }
        ResponseEntity<String> respEntity = rest.getForEntity(appointmentServiceUrl, String.class);
        modelMap.put("appointmentServiceUrl", appointmentServiceUrl);
        modelMap.put("appointmentServiceRespCode", respEntity.getStatusCode().value());
        modelMap.put("appointmentServiceResp", respEntity.getBody());

        // 使用同一个 ProxyToken 应当会失败
        if (token != null) {
            respEntity = rest.getForEntity(appointmentServiceUrl, String.class);
            HttpStatus status = respEntity.getStatusCode();
            if (HttpStatus.OK.equals(respEntity.getStatusCode())) {
                // 测试发现，实际还是会成功的，若间隔一段时间之后，手工copy该URL，并在浏览器中使用，会403
                // 貌似ProxyToken 不是说使用一次之后就失效，而是有个很短的时间内均有效。
                // 原因：使用了Ehcache的缘故
                logger.error("Second call service with same proxy ticket should faild, but success with response code "
                        + status + ", content =\n" + respEntity.getBody());
            } else {
                logger.info("Second call service with same proxy ticket should failded with response code " + status
                        + ", content =\n" + respEntity.getBody());
            }
        }

        // 调用 staff 服务
        String staffServiceUrl = staffUrl;
        if (token != null) {
            String proxyTicket = token.getAssertion().getPrincipal().getProxyTicketFor(staffUrl);
            staffServiceUrl = staffUrl + "?ticket=" + URLEncoder.encode(proxyTicket, "UTF-8");
        }
        respEntity = rest.getForEntity(staffServiceUrl, String.class);
        modelMap.put("staffServiceUrl", staffServiceUrl);
        modelMap.put("staffServiceRespCode", respEntity.getStatusCode().value());
        modelMap.put("staffServiceResp", respEntity.getBody());

        return "testProxy";
    }
}
