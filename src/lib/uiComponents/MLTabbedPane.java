package lib.uiComponents;

import java.util.ArrayList;

import javax.swing.JTabbedPane;

import GUI.UIController;
import LocalizationLogic.LocalizationController;
import java.awt.*;

public class MLTabbedPane extends JTabbedPane {

    UIController uiController;
    LocalizationController lc;
    ArrayList<String> tabnames = new ArrayList<>();

    public MLTabbedPane(UIController uiController) {
        this.uiController = uiController;
        lc = uiController.getController().getLocalizationController();
        updateLanguageTabTitles();
        lc.addLanguageChangeListener(e -> updateLanguageTabTitles());
    }

    public void updateLanguageTabTitles() {
        for (int i = 0; i < getTabCount(); i++) {
            setTitleAt(i, lc.s(tabnames.get(i)));
        }
    }

    @Override
    public void addTab(String title, Component component) {
        super.addTab(title, component);
        tabnames.add(title);
    }
}
