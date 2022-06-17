package GUI.userPage;

import javax.swing.JPanel;

import Controller.Controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import GUI.UIController;
import LocalizationLogic.Language;
import Model.UserComponentes.User;
import lib.DataStructures.HashMapImplementation.THashMap;
import lib.uiComponents.MLButton;
import lib.uiComponents.MLComboBoxWithDescribtion;
import lib.uiComponents.PasswordFieldWithDescribtion;
import lib.uiComponents.TextFieldWithDescribtion;
import lib.uiComponents.rigitFreeSpace;
import lib.uiComponents.technicalUIComponents.CustomTextComponent;

public class UserSignUpPage extends JPanel {

    UIController uiController;
    Controller controller;

    String[] describtions = new String[] { "UserName:", "E-mail:", "First Name:", "LastName:", "Date-Of-Birth: " };
    String[] tags = new String[] { "UserName", "E-mail", "First Name", "LastName", "Date-Of-Birth" };
    ArrayList<CustomTextComponent> textComponents = new ArrayList<CustomTextComponent>();
    MLComboBoxWithDescribtion languageBox;

    Dimension fieldQuestionSize = new Dimension(200, 30);
    Dimension fieldQuestionSizeDistance = new Dimension(200, 1);
    Dimension fieldBlockSizes = new Dimension(400, 30);
    Dimension preferredPanelSize = new Dimension(600, 500);

    public UserSignUpPage(UIController uiController) {
        this.uiController = uiController;
        this.controller = uiController.getController();

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
        c.gridy++;
        add(buildSignUpButton(), c);
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
            textComponents.add(tf);
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

        languageBox = new MLComboBoxWithDescribtion(uiController, "Preferred Language: ",
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
        textComponents.add(newPasswordField1);
        panel.add(newPasswordField1, c);
        c.gridy++;
        //ridgit Area for some little space
        panel.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), fieldQuestionSizeDistance), c);
        c.gridy++;
        PasswordFieldWithDescribtion newPasswordField2 = new PasswordFieldWithDescribtion(uiController,
                "repeat password: ",
                "password", fieldQuestionSize);
        textComponents.add(newPasswordField2);
        newPasswordField2.setPreferredSize(fieldBlockSizes);
        panel.add(newPasswordField2, c);
        return panel;
    }

    private JPanel buildSignUpButton() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        panel.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), fieldQuestionSizeDistance), c);
        c.gridy++;
        panel.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), fieldQuestionSizeDistance), c);
        c.gridy++;
        MLButton button = new MLButton(uiController, "Done!");
        button.setBackground(uiController.getDefaultBackgroundcolor());
        button.addActionListener(e -> {
            THashMap<String, String> dataMap = new THashMap<String, String>();
            for (int i = 0; i < describtions.length + 2; i++) {
                dataMap.put(User.INPUTTAGS[i], textComponents.get(i).getText());
            }
            dataMap.put(User.PREFERED_LANGUAGE, languageBox.getSelectedIndex() + "");
            for (String string : dataMap) {
                System.out.println(string);
            }
            // call method in Controller and pass dataMap
            // that tells uiController to set Page to User Profile / Shop idk
            String portentialErrorMessage = controller.signUpAttempt(dataMap);
            if (portentialErrorMessage == null) {
                for (CustomTextComponent compontent : textComponents) {
                    compontent.resetValue();
                }
            }
        });
        panel.add(button);
        return panel;
    }

}
