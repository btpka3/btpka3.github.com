import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

// http://docs.oracle.com/javase/tutorial/uiswing/components/index.html
// http://www.oracle.com/technetwork/java/painting-140037.html
public class LogPolarGrid extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final int n = 12;

    public static JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu, submenu;
        JMenuItem menuItem;

        // Build the first menu.
        menu = new JMenu("A Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);

        // dd
        menuItem = new JMenuItem("A text-only menu item", KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
                ActionEvent.ALT_MASK));
        menu.add(menuItem);

        menu.addSeparator();
        submenu = new JMenu("A submenu");
        submenu.setMnemonic(KeyEvent.VK_S);
        menuItem = new JMenuItem("An item in the submenu");
        submenu.add(menuItem);
        menuItem = new JMenuItem("Another item");
        submenu.add(menuItem);
        menu.add(submenu);

        return menuBar;
    }

    public static double[] logSpace(double lowBoundery, double highBoundery,
            int numOfSlices) {
        if (highBoundery == Math.PI) {
            highBoundery = Math.log(Math.PI);
        }

        double[] logSpace = new double[numOfSlices];
        double distance = highBoundery - lowBoundery;
        numOfSlices = numOfSlices - 1;

        for (short i = 0; i < numOfSlices; ++i) {
            logSpace[i] = Math
                    .pow(10, lowBoundery + i * distance / numOfSlices);
        }

        logSpace[numOfSlices] = Math.pow(10, highBoundery);
        return logSpace;
    }

    public static JPanel createDrawingPanel() {
        final int panelWidth = 600;

        JPanel drawingPanel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics graphics) {

                // original point
                int ox = panelWidth / 2;
                int oy = ox;
                int r = panelWidth / 3;

                Graphics2D g = (Graphics2D) graphics;

                // fill background color
                g.setColor(Color.WHITE);
                g.fill(new Rectangle(0, 0, this.getWidth(), this.getHeight()));

                g.setColor(Color.BLUE);

                // draw radial
                for (int i = 0; i < n; i++) {
                    double radian = 2 * Math.PI / n * i;
                    if (radian >= 2 * Math.PI) {
                        break;
                    }
                    int x2 = ox + (int) (r * Math.cos(radian));
                    int y2 = oy + (int) (r * Math.sin(radian));

                    g.drawLine(ox, oy, x2, y2);
                }

                for (int i = 0; i < 3; i++) {
                    Arc2D.Double d = new Arc2D.Double(ox - r / 3 * (3 - i), oy
                            - r / 3 * (3 - i), 2 * r / 3 * (3 - i), 2 * r / 3
                            * (3 - i), 0, 360, Arc2D.CHORD);
                    g.draw(d);
                }
            }
        };
        drawingPanel.setPreferredSize(new Dimension(panelWidth, panelWidth));
        drawingPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        drawingPanel.setDoubleBuffered(true);
        return drawingPanel;
    }

    public static void main(String[] args) throws Exception {
        double[] d = logSpace(Math.log(1 / 8.0), Math.log(2), 5);
        for (double i : d) {
            System.out.println(i);
        }
    }

    public static void main1(String[] args) throws Exception {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        JFrame frame = new JFrame("My Swing Sample");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());

        JMenuBar menuBar = createMenuBar();
        frame.setJMenuBar(menuBar);

        JPanel drawingPanel = createDrawingPanel();
        Container contentPane = frame.getContentPane();
        contentPane.add(drawingPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
