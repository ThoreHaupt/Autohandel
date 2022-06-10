package lib.technicalComponents;

import javax.swing.*;
import java.awt.*;

/* ich möchte zwei verschiede JPanels übereinander malen, damit ich sozusagen 2 Layers habe, auf denen ich Knöpfe hinzufügen kann. 
Dafür schalte ich das LPanel in der zweiten Schicht auf transparent, wenn ich dann aber beide Schichten zu dem ContenPane hinzufüge.
 dann
sehe ich den Hintergrund von dem ContenPane. Das heipt, dass panel1 nicht gerendert wird. Gibt es eine Möglichkeit, dass das Panel
und seine Compoenten gerendert werden, oder ist das nicht möglich?
 */

public class WindowTest extends JFrame {

    /*
     * public static void main(String[] args) {
     * new WindowTest();
     * }
     */

    WindowTest() {
        Container c = getContentPane();

        // JPanel 1
        setLayout(new BorderLayout());
        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(255, 0, 0));
        c.add(panel1, BorderLayout.CENTER);

        // JPanel 2
        TransparentJPane panel2 = new TransparentJPane();
        panel2.add(new JLabel("Text"));
        c.add(panel2, BorderLayout.CENTER);

        c.setBackground(new Color(0, 0, 255));
        setBasics("test");
    }

    private void setBasics(String windowTitle) {
        this.setTitle(windowTitle);
        this.setSize(1000, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // ein JPanel, dass durchsichtig sein sollte, aka, der kann keine Pixel
    // schreiben.

    class TransparentJPane extends JPanel {

        /**
         * 
         */
        public TransparentJPane() {
            setOpaque(true);
        }

        @Override
        public void paintComponent(Graphics g) {
            // g.setColor(new Color(255, 0, 0, 64));
            // Insets insets = getInsets();
            // g.fillRect(insets.left, insets.top,
            // getWidth() - insets.left - insets.right,
            // getHeight() - insets.top - insets.bottom);
            // super.paintComponent(g);
        }

    }

}
