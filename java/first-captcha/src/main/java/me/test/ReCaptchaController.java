
package me.test;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping(value = "/recaptcha")
public class ReCaptchaController {

    private Logger logger = LoggerFactory.getLogger(ReCaptchaController.class);

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping(value = "/", method = { RequestMethod.POST })
    @ResponseBody
    public ResponseEntity<? extends Object> verify(
            HttpServletRequest req,
            @RequestParam(value = "recaptcha_challenge_field") String challengeKey,
            @RequestParam(value = "recaptcha_response_field") String answer) {

        MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
        request.set("privatekey", "6Le098kSAAAAAIvst-QpKIxqkUpITQd7aKtLSgMQ");
        request.set("remoteip", req.getRemoteAddr());
        request.set("challenge", challengeKey);
        request.set("response", answer);
        logger.debug("==============" + req.getRemoteAddr());
        logger.debug("==============req = " + request);

        String resultStr = restTemplate.postForObject("http://www.google.com/recaptcha/api/verify",
                request, String.class);
        String[] results = resultStr.split("\n");
        if ("true".equals(results[0])) {
            return new ResponseEntity<String>(Boolean.TRUE.toString(), HttpStatus.OK);
        } else {
            logger.debug(resultStr);
            return new ResponseEntity<String>(Boolean.FALSE.toString(), HttpStatus.OK);
        }
    }
}
