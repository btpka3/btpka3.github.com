package me.test.kaptcha;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;

import com.google.code.kaptcha.impl.DefaultKaptcha;

public class Main {

    public static void main(String[] args) throws IOException {

        Properties props = new Properties();
        props.put("kaptcha.border", "yes");
        props.put("kaptcha.border.color", "255,119,0");
        props.put("kaptcha.image.width", "110");
        props.put("kaptcha.image.height", "40");
        props.put("kaptcha.textproducer.font.size", "30");
        props.put("kaptcha.textproducer.font.color", "255,119,0");
        props.put("kaptcha.textproducer.char.string", "abcd2345678gfynnpwx");
        props.put("kaptcha.textproducer.char.length", "4");
        props.put("kaptcha.session.key", "code");
        props.put("kaptcha.noise.impl", "com.google.code.kaptcha.impl.DefaultNoise");
        props.put("kaptcha.noise.color", "white");
        props.put("kaptcha.background.clear.from", "white");
        props.put("kaptcha.background.clear.to", "white");
        props.put("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        props.put("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.WaterRipple");

        ConfigEx configEx = new ConfigEx(props);

        List<Color> noiseColors = new ArrayList();

        noiseColors.add(new Color(255, 109, 77, 150));
        noiseColors.add(new Color(8, 255, 10, 100));
        noiseColors.add(new Color(0xCC, 0x33, 0xFF, 100));
        noiseColors.add(new Color(0x66, 0x33, 0xFF, 100));
        noiseColors.add(new Color(0xFF, 0xFF, 0xFF, 100));

        configEx.setNoiseColors(noiseColors.toArray(new Color[0]));

        DefaultKaptcha captchaProducer = new DefaultKaptcha();
        captchaProducer.setConfig(configEx);

        for (int i = 0; i < 10; i++) {
            String capText = captchaProducer.createText();
            BufferedImage bi = captchaProducer.createImage(capText);
            ImageIO.write(bi, "jpg", new File("/tmp/kapatcha_" + capText + ".jpg"));
        }
    }

}
