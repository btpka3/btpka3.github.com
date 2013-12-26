
package me.test;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.servlet.http.HttpServletResponse;

import nl.captcha.Captcha;
import nl.captcha.backgrounds.FlatColorBackgroundProducer;
import nl.captcha.gimpy.DropShadowGimpyRenderer;
import nl.captcha.noise.CurvedLineNoiseProducer;
import nl.captcha.text.producer.DefaultTextProducer;
import nl.captcha.text.renderer.DefaultWordRenderer;

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

@Controller
@RequestMapping(value = "/captcha")
public class SimpleCaptchaController {

    private static final String CACHE_NAME = "captcha";
    private static final char[] DEFAULT_CHARS = new char[] {
            'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'k', 'm', 'n',
            'p', 'r',
            'w', 'x', 'y',

            '2', '3', '4', '5', '6', '7', '8',

            'A', 'B', 'D', 'E', 'F', 'G',
            'H', 'K', 'L', 'M', 'N',
            'P', 'R', 'T',
            'W', 'X', 'Y'
    };

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

        Captcha captcha = generateCaptcha();
        String captchaHash = Integer.toString(captcha.toString().hashCode());
        cacheManager.getCache(CACHE_NAME).put(captchaHash, captcha);

        UriComponents uriComponents =
                uriBuilder.path("/captcha/{hash}").buildAndExpand(captchaHash);
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

        ValueWrapper valueWrapper = cacheManager.getCache(CACHE_NAME).get(hash);
        if (valueWrapper == null || valueWrapper.get() == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        Captcha captcha = (Captcha) valueWrapper.get();

        String captchaHash = Integer.toString(captcha.toString().hashCode());
        String fileName = captchaHash + ".jpg'";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        headers.setPragma("no-cache");
        headers.setCacheControl("no-cache");
        headers.setExpires(0);
        headers.setContentType(MediaType.IMAGE_JPEG);

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ImageOutputStream imgOut = new MemoryCacheImageOutputStream(byteOut);
        ImageIO.write(captcha.getImage(), "jpg", imgOut);

        return new ResponseEntity<Resource>(new ByteArrayResource(byteOut.toByteArray()),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/{hash}", method = { RequestMethod.POST })
    @ResponseBody
    public ResponseEntity<? extends Object> submitAnswer(
            @PathVariable(value = "hash") String hash,
            @RequestParam(value = "answer") String answer) {

        ValueWrapper valueWrapper = cacheManager.getCache(CACHE_NAME).get(hash);
        if (valueWrapper == null || valueWrapper.get() == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        Captcha captcha = (Captcha) valueWrapper.get();
        if (captcha.getAnswer().equalsIgnoreCase(answer)) {
            return new ResponseEntity<String>(Boolean.TRUE.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<String>(Boolean.FALSE.toString(), HttpStatus.OK);
    }

    private Captcha generateCaptcha() {

//        List<java.awt.Color> textColors = Arrays.asList(
//                Color.RED, Color.BLUE);
        List<java.awt.Color> textColors = Arrays.asList(
                new Color(0x7FFF00),
                new Color(0xFF1493),
                new Color(0xD2691E),
                new Color(0x00BFFF),
                new Color(0x8B008B));
        List<java.awt.Font> textFonts = Arrays.asList(
                new Font("SimSun", Font.PLAIN, 40));

        Captcha captcha = new Captcha.Builder(200, 50)

                // 设定字符
                // .addText(new DefaultTextProducer(4, DEFAULT_CHARS))
                // .addText(new MyChineseTextProducer(6))
                .addText(new DefaultTextProducer(6, DEFAULT_CHARS), new MyWordRenderer(textColors, textFonts))
                // .addText(new DefaultTextProducer(6, DEFAULT_CHARS), new ColoredEdgesWordRenderer(textColors,
                // textFonts, 1f))

                // .addText(new MyChineseTextProducer(1), new DefaultWordRenderer(textColors, textFonts))
                // .addText(new NumbersAnswerProducer(2), new DefaultWordRenderer(textColors, textFonts))
                // .addText(new ColoredEdgesWordRenderer(textColors, textFonts))

                // .addBackground(new GradiatedBackgroundProducer())
                .addBackground(new FlatColorBackgroundProducer(Color.WHITE))
                .gimp()
                .gimp(new DropShadowGimpyRenderer())
                // .gimp(new RippleGimpyRenderer())
                // .addBorder()
                .addNoise(new CurvedLineNoiseProducer(new Color(Color.GRAY.getRed(), Color.GRAY.getGreen(), Color.GRAY.getBlue(), 180), 7.0f))
                .build();

        return captcha;
    }

    public static void main(String[] args) throws IOException {

        SimpleCaptchaController controller = new SimpleCaptchaController();

        Captcha captcha = controller.generateCaptcha();

        ImageIO.write(captcha.getImage(), "jpg", new File("/" + captcha.getAnswer() + ".jpg"));
    }
}
