package GUI.userPage;

import javax.swing.JPanel;

import GUI.UIController;
import lib.uiComponents.MLButton;
import lib.uiComponents.MLLabel;

import java.awt.*;

/**
 * gets displayed after making a purchase
 */
public class ThankYouPage extends JPanel {

    UIController uiController;
    String thankYouTextString = "Thank you for your purchase!";

    /**
     * constructs the Thank you for your Purchase page
     * @param uiController
     */
    public ThankYouPage(UIController uiController) {
        this.uiController = uiController;
        buildThankYouPage();
    }

    /**
     * combines and adds the different components for the thank you page
     */
    public void buildThankYouPage() {
        setLayout(new BorderLayout());
        add(buildThankYouText(), BorderLayout.NORTH);
        add(buildContinueShoppingPage(), BorderLayout.SOUTH);
    }

    /**
     * builds the text sections for the thank you page
     * @return
     */
    public JPanel buildThankYouText() {
        JPanel panel = new JPanel();

        MLLabel thankYouText = new MLLabel(uiController, thankYouTextString);
        thankYouText.setFont(uiController.getDefaultFont().deriveFont(Font.BOLD, 40));
        panel.add(thankYouText);

        return panel;
    }

    /**
     * builds the button that directly brings you back to the shop
     * @return
     */
    public JPanel buildContinueShoppingPage() {
        JPanel panel = new JPanel();

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.7;
        c.weighty = 0.5;
        c.anchor = GridBagConstraints.CENTER;

        MLButton continueShopping = new MLButton(uiController, "Click here to continue Shopping");
        continueShopping.setFont(uiController.getDefaultFont().deriveFont(Font.PLAIN, 20));

        continueShopping.addActionListener(e -> uiController.setWindowContent(UIController.MAINSTORE_PAGE));

        panel.add(continueShopping, c);
        return panel;
    }

}
