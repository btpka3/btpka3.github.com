import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class DrawingPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * The width that the picture of the letter must be
     */
    public static final int FIXED_WIDTH = 40;
    /**
     * The hight that the picture of the letter must be
     */
    public static final int FIXED_HIGHT = 40;
    /**
     * Represents the image which is used to draw on
     */
    private BufferedImage image;

    public DrawingPanel() {
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        // if (image == null) {
        // createDrawingArea();
        // } else {
        // recreateDrawingArea(image, getWidth(), getHeight());
        // }
        if (image == null) {
            image = new BufferedImage(getWidth(), getHeight(),
                    BufferedImage.TYPE_INT_RGB);

            Graphics2D g2 = image.createGraphics();
            g2.setPaint(getBackground());
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
        g1.drawImage(image, 0, 0, this);

        Graphics2D graphics2 = (Graphics2D) g1;
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(100, 100, 240, 160, 10, 10);
        graphics2.draw(roundedRectangle);
    }

    /**
     * This method is used to draw on the panel
     *
     * @param start
     *            point of the drawing line
     * @param end
     *            point of the drawing line
     */
    public void draw(Point start, Point end) {
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(Color.BLACK);
        g2.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_MITER));
        g2.draw(new Line2D.Double(start, end));
        g2.dispose();

        repaint();
    }

    /**
     * This method clears everything that is drown on the panel
     */
    public void clearDrawingArea() {
        Graphics g = image.getGraphics();

        g.setColor(getBackground());
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.dispose();

        repaint();
    }

    /**
     * This method finds out the coordinate of the drawen letter and creates the
     * approporite image from those coordinates
     */
    public void getDrawnLetter() {

        prepareImage();

        int upperCoordinate = getUpperCoordinate();
        int bottomCoordinate = getBottomCoordinate(upperCoordinate);
        int leftCoordinate = getLeftCoordinate(upperCoordinate,
                bottomCoordinate);
        int rightCoordinate = getRightCoordinate(upperCoordinate,
                bottomCoordinate, leftCoordinate);

        BufferedImage subImage = image.getSubimage(leftCoordinate,
                upperCoordinate, rightCoordinate - leftCoordinate,
                bottomCoordinate - upperCoordinate);
        // BufferedImage subImage = reSizingImage(image.getSubimage(
        // leftCoordinate, upperCoordinate, rightCoordinate
        // - leftCoordinate, bottomCoordinate - upperCoordinate));
        // subImage = Cleaner.blackAndLightGrayCleaning(subImage);
        try {
            ImageIO.write(subImage, "PNG", new File("letter.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveDrawnLetter(String letter) {

        prepareImage();

        int upperCoordinate = getUpperCoordinate();
        int bottomCoordinate = getBottomCoordinate(upperCoordinate);
        int leftCoordinate = getLeftCoordinate(upperCoordinate,
                bottomCoordinate);
        int rightCoordinate = getRightCoordinate(upperCoordinate,
                bottomCoordinate, leftCoordinate);

        BufferedImage subImage = reSizingImage(image.getSubimage(
                leftCoordinate, upperCoordinate, rightCoordinate
                        - leftCoordinate, bottomCoordinate - upperCoordinate));
        // subImage = Cleaner.blackAndLightGrayCleaning(subImage);
        // int fileNumber = SaveImage.numberOfFiles(letter) + 1;
        try {
            ImageIO.write(subImage, "PNG", new File("1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method creats the image, that is used to draw on
     */
    private void createDrawingArea() {

        image = new BufferedImage(getWidth(), getHeight(),
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g = image.createGraphics();
        g.setPaint(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.dispose();
    }

    /**
     * This method creates new image with new demensions based on the old image
     * and provided dimensions
     *
     * @param bufferedImage
     *            old image
     * @param width
     *            new width of the image
     * @param height
     *            new height of the image
     */
    private void recreateDrawingArea(BufferedImage bufferedImage, int width,
            int height) {

        BufferedImage newImage = new BufferedImage(width, height,
                bufferedImage.getType());
        Graphics2D g = newImage.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.drawImage(bufferedImage, 0, 0, width, height, 0, 0, width, height,
                null);
        g.dispose();

        this.image = newImage;
    }

    /**
     * This method resizes image to the appropriate size
     *
     * @param bufferedImage
     *            represents an image that needs to be resized
     *
     * @return the resized image
     */
    private BufferedImage reSizingImage(BufferedImage bufferedImage) {

        BufferedImage newImage = new BufferedImage(FIXED_WIDTH, FIXED_HIGHT,
                bufferedImage.getType());
        Graphics2D g = newImage.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, FIXED_WIDTH, FIXED_HIGHT);
        g.drawImage(bufferedImage, 0, 0, FIXED_WIDTH, FIXED_HIGHT, 0, 0,
                bufferedImage.getWidth(), bufferedImage.getHeight(), null);
        g.dispose();

//        return ImageSampler.downSampleImage(new Dimension(FIXED_WIDTH,
//                FIXED_HIGHT), newImage);
        return newImage;
    }

    /**
     * This method finds out what is the upper coordinate of the drawn letter,
     * by gooing through two loops, the first starts from the 0 coordinate until
     * the image height and the second starts from the 0 coordinate until the
     * image widht, searching for the first pixel that has a color diffrent then
     * white
     *
     * @return the intager value of the upper coordinate
     *
     * @throws RuntimeException
     *             if on drawing can be founded
     */
    private int getUpperCoordinate() {
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if (image.getRGB(j, i) != -1) {
                    return i - 1;
                }
            }
        }
        throw new RuntimeException(
                "No drawing could be founded, try writing again");
    }

    /**
     * This method finds out what is the bottom coordinate of the drawn letter,
     * by gooing through two loops, the first starts from the upper coordinate
     * until the image height and the second starts from the 0 coordinate until
     * the image widht, searching for the first vertical line of pixels thas has
     * all the white pixels
     *
     * @param upperCoordinate
     *            the upper coordinate of the drawn letter
     *
     * @return the intager value of the bottom coordinate
     *
     * @throws RuntimeException
     *             if on drawing can be founded
     */
    private int getBottomCoordinate(int upperCoordinate) {

        int counter;
        for (int i = upperCoordinate + 1; i < image.getHeight(); i++) {
            counter = 0;
            for (int j = 0; j < image.getWidth(); j++) {
                if (image.getRGB(j, i) == -1) {
                    counter++;
                }
            }
            if (counter == image.getWidth()) {
                return i;
            }
        }
        throw new RuntimeException(
                "No drawing could be founded, try writing again");
    }

    /**
     * This method finds out what is the left coordinate of the drawn letter, by
     * gooing through two loops, the first starts from the 0 coordinate until
     * the image widht and the second starts from the upper coordinate until the
     * image widht, searching for the first pixel that has a color diffrent then
     * white
     *
     * @param upperCoordinate
     *            the upper coordinate of the drawn letter
     * @param bottomCoordinate
     *            the bottom coordinate of the drawn letter
     *
     * @return the intager value of the left coordinate
     *
     * @throws RuntimeException
     *             if on drawing can be founded
     */
    private int getLeftCoordinate(int upperCoordinate, int bottomCoordinate) {

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = upperCoordinate + 1; j < bottomCoordinate; j++) {
                if (image.getRGB(i, j) != -1) {
                    return i - 1;
                }
            }
        }
        throw new RuntimeException(
                "No drawing could be founded, try writing again");
    }

    /**
     * This method finds out what is the bottom coordinate of the drawn letter,
     * by gooing through two loops, the first starts from the left coordinate
     * until the image widht and the second starts from the upper coordinate
     * until the bottom coordinate, searching for the first horizontal line of
     * pixels thas has all the white pixels
     *
     * @param upperCoordinate
     *            the upper coordinate of the drawn letter
     * @param bottomCoordinate
     *            the bottom coordinate of the drawn letter
     * @param leftCoordinate
     *            the left coordinate of the drawn letter
     *
     * @return the intager value of the right coordinate
     *
     * @throws RuntimeException
     *             if on drawing can be founded
     */
    private int getRightCoordinate(int upperCoordinate, int bottomCoordinate,
            int leftCoordinate) {

        int counter;
        for (int i = leftCoordinate + 1; i < image.getWidth(); i++) {
            counter = 0;
            for (int j = upperCoordinate + 1; j < bottomCoordinate; j++) {
                if (image.getRGB(i, j) == -1) {
                    counter++;
                }
            }

            if (counter == bottomCoordinate - upperCoordinate - 1) {
                return i;
            }
        }
        throw new RuntimeException(
                "No drawing could be founded, try writing again");
    }

    /**
     * Prepares the image for trimming by setting the white color around the
     * eges in order so that coordinate of the drawen letter stay inside that
     * white space
     */
    private void prepareImage() {
        int height = image.getHeight();
        int width = image.getWidth();

        for (int i = 0; i < height; i++) {
            image.setRGB(0, i, -1);
            image.setRGB(width - 1, i, -1);
        }

        for (int i = 0; i < width; i++) {
            image.setRGB(i, 0, -1);
            image.setRGB(i, height - 1, -1);
        }
    }
}
