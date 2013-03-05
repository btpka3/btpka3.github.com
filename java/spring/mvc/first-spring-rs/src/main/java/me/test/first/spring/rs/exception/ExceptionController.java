package me.test.first.spring.rs.exception;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Controller
@RequestMapping("/error/**")
public class ExceptionController {

    @RequestMapping(value = "/error/4XX")
    public ResponseEntity<Errors> handleBusinessException(
            @ModelAttribute(SimpleMappingExceptionResolver.DEFAULT_EXCEPTION_ATTRIBUTE) BusinessException e) {
        //Errors repBody = new Errors();
        HttpHeaders repHeaders = new HttpHeaders();
        HttpStatus repStatus = HttpStatus.BAD_REQUEST;
        if (e.getHttpStatus() != null) {
            repStatus = e.getHttpStatus();
        }

        List<String> messages = e.getMessages();
//		repBody.getMessage().addAll(messages);
//		return new ResponseEntity<Errors>(repBody, repHeaders, repStatus);
        return null;
    }

    @RequestMapping(value = "/error/5XX")
    public ResponseEntity<Errors> handleSystemException(
            @ModelAttribute(SimpleMappingExceptionResolver.DEFAULT_EXCEPTION_ATTRIBUTE) Exception e) {
        Errors repBody = null;
        HttpHeaders repHeaders = new HttpHeaders();
        HttpStatus repStatus = HttpStatus.NOT_FOUND;
//
//        List<String> msgList = repBody.get.getMessage();
//
//        if (e instanceof SystemException) {
//            SystemException sysExp = (SystemException) e;
//            msgList.addAll(sysExp.getMessages());
//            if (sysExp.getHttpStatus() != null) {
//                repStatus = sysExp.getHttpStatus();
//            }
//        } else {
//            msgList.add(e.getMessage());
//        }
        return new ResponseEntity<Errors>(repBody, repHeaders, repStatus);
    }
}
