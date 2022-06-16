package GUI.userPage;

import javax.swing.JPanel;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;

import GUI.UIController;
import LocalizationLogic.Language;
import lib.uiComponents.MLComboBox;
import lib.uiComponents.MLComboBoxWithDescribtion;
import lib.uiComponents.PasswordFieldWithDescribtion;
import lib.uiComponents.TextFieldWithDescribtion;
import lib.uiComponents.rigitFreeSpace;

public class UserSignUpPage extends JPanel {

    UIController uiController;

    String[] describtions = new String[] { "UserName:", "E-mail:", "First Name:", "LastName:", "Date-Of-Birth: " };
    String[] tags = new String[] { "UserName", "E-mail", "First Name", "LastName", "Date-Of-Birth" };
    Dimension fieldQuestionSize = new Dimension(200, 30);
    Dimension fieldQuestionSizeDistance = new Dimension(200, 1);
    Dimension fieldBlockSizes = new Dimension(400, 30);
    Dimension preferredPanelSize = new Dimension(600, 500);

    public UserSignUpPage(UIController uiController) {
        this.uiController = uiController;

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setPreferredSize(preferredPanelSize);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;

        add(buildSimpleUserInputQuestions(), c);
        c.gridy++;
        add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), fieldQuestionSizeDistance), c);
        c.gridy++;
        add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), fieldQuestionSizeDistance), c);
        c.gridy++;
        add(buildOtherUserInputQuestions(), c);
    }

    private JPanel buildSimpleUserInputQuestions() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(preferredPanelSize);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        for (int i = 0; i < describtions.length; i++) {

            TextFieldWithDescribtion tf = new TextFieldWithDescribtion(uiController, describtions[i], tags[i],
                    fieldQuestionSize);
            tf.setPreferredSize(fieldBlockSizes);
            panel.add(tf, c);
            c.gridy++;
            panel.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), fieldQuestionSizeDistance), c);
            c.gridy++;
        }
        return panel;

    }

    private JPanel buildOtherUserInputQuestions() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        //panel.setPreferredSize(preferredPanelSize);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;

        String[] arr = (String[]) Arrays.asList(Language.values()).stream().map(e -> (String) e.toString())
                .collect(Collectors.toList()).toArray(new String[Language.values().length]);

        MLComboBoxWithDescribtion languageBox = new MLComboBoxWithDescribtion(uiController, "Preferred Language: ",
                arr, fieldQuestionSize);
        languageBox.setPreferredSize(fieldBlockSizes);
        panel.add(languageBox, c);
        c.gridy++;
        //ridgit Area for some little space
        panel.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), fieldQuestionSizeDistance), c);
        c.gridy++;
        //ridgit Area for some little space
        panel.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), fieldQuestionSizeDistance), c);
        c.gridy++;
        //Enter Password, twice
        PasswordFieldWithDescribtion newPasswordField1 = new PasswordFieldWithDescribtion(uiController, "password: ",
                "password", fieldQuestionSize);
        newPasswordField1.setPreferredSize(fieldBlockSizes);
        panel.add(newPasswordField1, c);
        c.gridy++;
        //ridgit Area for some little space
        panel.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), fieldQuestionSizeDistance), c);
        c.gridy++;
        PasswordFieldWithDescribtion newPasswordField2 = new PasswordFieldWithDescribtion(uiController,
                "repeat password: ",
                "password", fieldQuestionSize);
        newPasswordField2.setPreferredSize(fieldBlockSizes);
        panel.add(newPasswordField2, c);
        return panel;
    }

}
