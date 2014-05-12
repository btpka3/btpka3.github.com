
package me.test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.engine.GenericCaptchaEngine;
import com.octo.captcha.engine.image.DefaultImageCaptchaEngine;
import com.octo.captcha.engine.image.ImageCaptchaEngine;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.engine.image.fisheye.SimpleFishEyeEngine;
import com.octo.captcha.engine.image.gimpy.BaffleListGimpyEngine;
import com.octo.captcha.engine.image.gimpy.BasicGimpyEngine;
import com.octo.captcha.engine.image.gimpy.BasicListGimpyEngine;
import com.octo.captcha.engine.image.gimpy.DefaultGimpyEngine;
import com.octo.captcha.engine.image.gimpy.DeformedBaffleListGimpyEngine;
import com.octo.captcha.engine.image.gimpy.DoubleRandomListGimpyEngine;
import com.octo.captcha.engine.image.gimpy.MultipleGimpyEngine;
import com.octo.captcha.engine.image.gimpy.NonLinearTextGimpyEngine;
import com.octo.captcha.engine.image.gimpy.SimpleListImageCaptchaEngine;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

//@Controller
//@RequestMapping(value = "/captcha")
public class JCaptchaController {

    private static final String CACHE_NAME = "jcaptcha";

    //@javax.annotation.Resource
    private ImageCaptchaService imageCaptchaService;

    @javax.annotation.Resource
    private CacheManager cacheManager;

    /**
     * 获取
     *
     *
     * @param hash
     *            要显示指定的验证码图片
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/", method = { RequestMethod.POST })
    @ResponseBody
    public ResponseEntity<Void> createNewCaptcha(
            HttpServletResponse response, UriComponentsBuilder uriBuilder) throws IOException {

        UUID imgHash = UUID.randomUUID();
        BufferedImage captchaImg = imageCaptchaService.getImageChallengeForID(imgHash.toString());
        cacheManager.getCache(CACHE_NAME).put(imgHash, captchaImg);

        UriComponents uriComponents =
                uriBuilder.path("/captcha/{hash}").buildAndExpand(imgHash.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    /**
     * 获取指定的验证码图片。
     *
     * @param hash
     *            要显示的验证码图片的HashCode
     * @return 验证码图片数据
     * @throws IOException
     */
    @RequestMapping(value = "/{hash}", method = { RequestMethod.GET })
    @ResponseBody
    public ResponseEntity<? extends Object> getCaptcha(
            @PathVariable(value = "hash") String hash) throws IOException {

        UUID imgHash = UUID.fromString(hash);
        ValueWrapper valueWrapper = cacheManager.getCache(CACHE_NAME).get(imgHash);
        if (valueWrapper == null || valueWrapper.get() == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        BufferedImage captchaImg = (BufferedImage) valueWrapper.get();

        String fileName = hash + ".jpg'";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        headers.setPragma("no-cache");
        headers.setCacheControl("no-cache");
        headers.setExpires(0);
        headers.setContentType(MediaType.IMAGE_JPEG);

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ImageOutputStream imgOut = new MemoryCacheImageOutputStream(byteOut);
        ImageIO.write(captchaImg, "jpg", imgOut);

        return new ResponseEntity<Resource>(new ByteArrayResource(byteOut.toByteArray()),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/{hash}", method = { RequestMethod.POST })
    @ResponseBody
    public ResponseEntity<? extends Object> submitAnswer(
            @PathVariable(value = "hash") String hash,
            @RequestParam(value = "answer") String answer) {

        UUID imgHash = UUID.fromString(hash);
        ValueWrapper valueWrapper = cacheManager.getCache(CACHE_NAME).get(imgHash);
        if (valueWrapper == null || valueWrapper.get() == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        if (imageCaptchaService.validateResponseForID(imgHash.toString(), answer)) {
            return new ResponseEntity<String>(Boolean.TRUE.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<String>(Boolean.FALSE.toString(), HttpStatus.OK);
    }

    public static void main(String[] args) throws IOException {

//        CaptchaEngine[] engines = new CaptchaEngine[] {
//                new BaffleListGimpyEngine(),
//                new BasicGimpyEngine(),
//                new BasicListGimpyEngine(),
//                new DefaultGimpyEngine(),
//                new DefaultImageCaptchaEngine(),
//                new DeformedBaffleListGimpyEngine(),
//                new DoubleRandomListGimpyEngine(),
//                new GenericCaptchaEngine(),
////                new ImageCaptchaEngine(),
////                new ListImageCaptchaEngine(),
//                new NonLinearTextGimpyEngine(),
//                new SimpleFishEyeEngine(),
//                new SimpleListImageCaptchaEngine()
//        };
//        ImageCaptchaService imageCaptchaService = new DefaultManageableImageCaptchaService();
//        String id = "1";
//        ImageIO.write(imageCaptchaService.getImageChallengeForID(id), "jpg", new File("/" + id + ".jpg"));
    }
}
