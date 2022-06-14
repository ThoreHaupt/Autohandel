package lib.uiComponents;

import javax.swing.*;

import Controller.Controller;
import GUI.UIController;
import LocalizationLogic.LocalizationController;

import java.awt.*;

public class MultiLanguageButton extends JButton {
    UIController uiController;
    LocalizationController lc;
    String text;

    public MultiLanguageButton(UIController uiController, String text) {
        super();
        this.uiController = uiController;
        Controller controller = uiController.getController();
        this.lc = controller.getLocalizationController();

        this.setText(text);
        lc.addLanguageChangeListener(e -> updateText());
    }

    @Override
    public void setText(String text) {
        if (lc == null) {
            super.setText(text);
            return;
        }
        this.text = text;
        super.setText(lc.s(text));
    }

    public void updateText() {
        System.out.println("apparently there is some language Change");
        this.setText(text);
        revalidate();
    }

}
