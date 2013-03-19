import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

public class GUI extends JFrame{



    public static void main(String[] args) throws Exception {


        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        JFrame frame = new JFrame("My Swing Sample");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel emptyLabel = new JLabel("sss");
        emptyLabel.setPreferredSize(new Dimension(175, 100));
        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

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

        Container content = frame.getContentPane();
        DrawingPanel drawingPanel = new DrawingPanel();
        DrawingListener l = new DrawingListener(drawingPanel);
        drawingPanel.addMouseListener(l);
        drawingPanel.addMouseMotionListener(l);
        drawingPanel.setPreferredSize(new Dimension(400, 0));
        drawingPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
        drawingPanel.setBackground(Color.white);

        Graphics g =  drawingPanel.getGraphics();
        System.out.println(frame.getGraphics()+"==");
        //g.draw3DRect(0, 0, 40, 30, true);
        content.add(drawingPanel, BorderLayout.WEST);

        // Display the window.
        frame.pack();
        frame.setSize(450, 260);
        frame.setVisible(true);
    }


}
