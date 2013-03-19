import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

/** Simple example illustrating the use of JPanels, especially
 *  the ability to add Borders.
 *  1998-99 Marty Hall, http://www.apl.jhu.edu/~hall/java/
 */

public class JPanels extends JFrame {
  public static void main(String[] args) {
    new JPanels();
  }

  public JPanels() {
    super("Using JPanels with Borders");
    WindowUtilities.setNativeLookAndFeel();
    addWindowListener(new ExitListener());
    Container content = getContentPane();
    content.setBackground(Color.lightGray);
    JPanel controlArea = new JPanel(new GridLayout(3, 1));
    String[] colors = { "Red", "Green", "Blue",
                        "Black", "White", "Gray" };
    controlArea.add(new SixChoicePanel("Color", colors));
    String[] thicknesses = { "1", "2", "3", "4", "5", "6" };
    controlArea.add(new SixChoicePanel("Line Thickness",
                                       thicknesses));
    String[] fontSizes = { "10", "12", "14", "18", "24", "36" };
    controlArea.add(new SixChoicePanel("Font Size",
                                       fontSizes));
    content.add(controlArea, BorderLayout.EAST);
    JPanel drawingArea = new JPanel();
    // Preferred height is irrelevant, since using WEST region
    drawingArea.setPreferredSize(new Dimension(400, 0));
    drawingArea.setBorder
             (BorderFactory.createLineBorder (Color.blue, 2));
    drawingArea.setBackground(Color.white);
    content.add(drawingArea, BorderLayout.WEST);
    pack();
    setVisible(true);
  }
}

