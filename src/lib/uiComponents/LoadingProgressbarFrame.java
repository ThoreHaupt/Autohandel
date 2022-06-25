package lib.uiComponents;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.util.Graphics2DProxy;

import java.awt.*;

public class LoadingProgressbarFrame extends JFrame {

    int progress = 0;
    Container ContentPane;

    JProgressBar bar;

    JLabel label1;
    JLabel label2;

    public LoadingProgressbarFrame() {
        FlatDarkLaf.setup();
        initPane();
        setBasics("loadingDatabase");
    }

    public void initPane() {
        ContentPane = this.getContentPane();

        ContentPane.setLayout(new BorderLayout());
        ContentPane.add(buildProgressbar());
    }

    public JPanel buildProgressbar() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = .5;

        label1 = new JLabel("loading database...");
        panel.add(label1, c);

        c.gridy++;
        c.weighty = .1;
        bar = new JProgressBar();
        bar.setMaximum(1000);
        panel.add(bar, c);

        c.gridy++;
        c.weighty = .4;
        label2 = new JLabel("");
        panel.add(label2, c);

        return panel;
    }

    public void increment(double d) {
        progress += d * 1000;
        bar.setValue(progress);
    }

    public void setText(String text) {
        String s = text.substring(0, Math.min(text.length(), 40));
        label2.setText("currently downloading: " + s);
    }

    private void setBasics(String windowTitle) {
        this.setTitle(windowTitle);
        this.setSize(400, 150);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    public void close() {
        dispose();
    }
}
