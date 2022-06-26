package GUI.userPage.UserLoginSignUP;

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
import lib.uiComponents.MLLabel;
import lib.uiComponents.PasswordFieldWithDescribtion;
import lib.uiComponents.TextFieldWithDescribtion;
import lib.uiComponents.rigitFreeSpace;
import lib.uiComponents.technicalUIComponents.CustomTextComponent;

/**
 * The pages that holds the UI to create a new User
 */
public class UserSignUpPage extends JPanel {

    UIController uiController;
    Controller controller;

    String[] describtions = new String[] { "UserName:", "E-mail:", "First Name:", "LastName:", "Date-Of-Birth: " };
    String[] tags = new String[] { "UserName", "E-mail", "First Name", "LastName", "Date-Of-Birth" };
    ArrayList<CustomTextComponent> textComponents = new ArrayList<CustomTextComponent>();
    MLComboBoxWithDescribtion languageBox;
    MLLabel msg;

    Dimension fieldQuestionSize = new Dimension(200, 30);
    Dimension fieldQuestionSizeDistance = new Dimension(200, 1);
    Dimension fieldBlockSizes = new Dimension(400, 30);
    Dimension preferredPanelSize = new Dimension(600, 300);

    /**
     * constructor for the sign up page
     * @param uiController
     */
    public UserSignUpPage(UIController uiController) {
        this.uiController = uiController;
        this.controller = uiController.getController();

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setPreferredSize(preferredPanelSize);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        //c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;

        add(buildSimpleUserInputQuestions(), c);
        c.gridy++;
        add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), fieldQuestionSizeDistance), c);
        c.gridy++;
        add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), fieldQuestionSizeDistance), c);
        c.gridy++;
        add(buildOtherUserInputQuestions(), c);
        c.gridy++;
        add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), fieldQuestionSizeDistance), c);
        c.gridy++;
        add(createErrorLabel(), c);
        c.gridy++;
        add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), fieldQuestionSizeDistance), c);
        c.gridy++;
        add(buildSignUpButton(), c);
    }

    /**
     * builds the sign up Questions
     * @return
     */
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

    /**
     * builds the lower questions of the second block
     * @return
     */
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

    /**
     * the sign up button itself
     * @return
     */
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
        button.addActionListener(e -> initUserProcess());
        panel.add(button);
        return panel;
    }

    /** 
     * gets all the settings from the button and puts them into a HashMap and then creates a new user based on this datamap
     */
    private void initUserProcess() {

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
        // if null that means, that the user creation was successfull.
        String portentialErrorMessage = controller.signUpAttempt(dataMap);
        if (portentialErrorMessage == null) {
            for (CustomTextComponent compontent : textComponents) {
                compontent.resetValue();
            }
        } else {
            setErrorMessage(portentialErrorMessage);
        }

    }

    /**
     * creates the Errorlabel on which to display all the messages for invalid inputs
     * @return
     */
    private JPanel createErrorLabel() {
        JPanel panel = new JPanel();
        msg = new MLLabel(uiController, "");
        msg.setForeground(uiController.getDefaultErrorColor());
        msg.setFont(uiController.getDefaultFont().deriveFont(Font.PLAIN, 15));
        panel.setPreferredSize(fieldBlockSizes);
        panel.add(msg);
        return panel;
    }

    /**
     * sets the error message
     * @param error
     */
    private void setErrorMessage(String error) {
        msg.setText(error);
        revalidate();
        repaint();
    }

}
