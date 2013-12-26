
package me.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class FontTest {

    /**
     * TODO 方法的作用是？
     *
     * @author zhangliangliang
     * @date 2013-12-25下午5:07:05
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
        attributes.put(TextAttribute.KERNING, TextAttribute.KERNING_ON);
        attributes.put(TextAttribute.TRACKING, TextAttribute.TRACKING_TIGHT);
        Font font = new Font("Arial", Font.BOLD, 40);
        font = font.deriveFont(attributes);

        BufferedImage img = new BufferedImage(500, 200, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();

        // set background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());

        g.setFont(font);
        g.setColor(Color.BLACK);

        FontMetrics metrics = g.getFontMetrics(font);

        g.drawString("HELLO testing", 0, metrics.getHeight());

        ImageIO.write(img, "jpg", new File("/test.jpg"));
    }

}
