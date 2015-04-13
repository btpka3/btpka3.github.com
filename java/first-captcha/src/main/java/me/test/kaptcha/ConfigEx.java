package me.test.kaptcha;

import com.google.code.kaptcha.*;
import com.google.code.kaptcha.impl.DefaultBackground;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.impl.DefaultNoise;
import com.google.code.kaptcha.impl.WaterRipple;
import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.text.WordRenderer;
import com.google.code.kaptcha.text.impl.DefaultTextCreator;
import com.google.code.kaptcha.text.impl.DefaultWordRenderer;
import com.google.code.kaptcha.util.Config;
import com.google.code.kaptcha.util.ConfigHelper;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;

/**
 * 扩展kaptcha的config类，使其nosise color能够透明、随机。
 * User: zll
 * Date: 5/27/14
 * Time: 10:59 AM
 */
public class ConfigEx extends Config {


    private List<Color> noiseColors;
    private Random rand = new Random(System.currentTimeMillis());

    public ConfigEx(Properties properties) {
        super(properties);
        this.helper = new ConfigHelperEx();
    }

    @Override
    public Color getNoiseColor() {
        if (noiseColors == null || noiseColors.isEmpty()) {
            return super.getNoiseColor();
        }
        return noiseColors.get(rand.nextInt(noiseColors.size()));
    }

    public void setNoiseColors(Color[] noiseColors) {
        this.noiseColors = Arrays.asList(noiseColors);
    }

    /** */
    private ConfigHelper helper;


    /** */
    public Producer getProducerImpl()
    {
        String paramName = Constants.KAPTCHA_PRODUCER_IMPL;
        String paramValue = super.getProperties().getProperty(paramName);
        Producer producer = (Producer) this.helper.getClassInstance(paramName, paramValue, new DefaultKaptcha(), this);
        return producer;
    }

    /** */
    public TextProducer getTextProducerImpl()
    {
        String paramName = Constants.KAPTCHA_TEXTPRODUCER_IMPL;
        String paramValue = super.getProperties().getProperty(paramName);
        TextProducer textProducer = (TextProducer) this.helper.getClassInstance(paramName, paramValue,
                new DefaultTextCreator(), this);
        return textProducer;
    }






    /** */
    public NoiseProducer getNoiseImpl()
    {
        String paramName = Constants.KAPTCHA_NOISE_IMPL;
        String paramValue = super.getProperties().getProperty(paramName);
        NoiseProducer noiseProducer = (NoiseProducer) this.helper.getClassInstance(paramName, paramValue,
                new DefaultNoise(), this);
        return noiseProducer;
    }

    /** */
    public GimpyEngine getObscurificatorImpl()
    {
        String paramName = Constants.KAPTCHA_OBSCURIFICATOR_IMPL;
        String paramValue = super.getProperties().getProperty(paramName);
        GimpyEngine gimpyEngine = (GimpyEngine) this.helper.getClassInstance(paramName, paramValue, new WaterRipple(), this);
        return gimpyEngine;
    }

    /** */
    public WordRenderer getWordRendererImpl()
    {
        String paramName = Constants.KAPTCHA_WORDRENDERER_IMPL;
        String paramValue = super.getProperties().getProperty(paramName);
        WordRenderer wordRenderer = (WordRenderer) this.helper.getClassInstance(paramName, paramValue,
                new DefaultWordRenderer(), this);
        return wordRenderer;
    }

    /** */
    public BackgroundProducer getBackgroundImpl()
    {
        String paramName = Constants.KAPTCHA_BACKGROUND_IMPL;
        String paramValue = super.getProperties().getProperty(paramName);
        BackgroundProducer backgroundProducer = (BackgroundProducer) this.helper.getClassInstance(paramName, paramValue,
                new DefaultBackground(), this);
        return backgroundProducer;
    }

}
