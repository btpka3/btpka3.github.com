package me.test.csrf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/csrfToken")
public class CsrfJsonController {

    private CsrfTokenManager csrfTokenManager;

    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String getCsrfToken() {
        String tokenName = csrfTokenManager.getTokenName();
        String tokenValue = csrfTokenManager.generateToken();
        String respJson = "{ \"tokenName\" : \"" + tokenName + "\", \"tokenValue\" : \"" + tokenValue + "\"}";
        return respJson;
    }

}
