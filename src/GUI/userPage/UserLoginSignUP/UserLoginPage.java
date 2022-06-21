package GUI.userPage.UserLoginSignUP;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import Controller.Controller;

import java.awt.*;

import GUI.UIController;
import lib.uiComponents.MLButton;
import lib.uiComponents.MLLabel;
import lib.uiComponents.PasswordFieldWithDescribtion;
import lib.uiComponents.TextFieldWithDescribtion;
import lib.uiComponents.rigitFreeSpace;

public class UserLoginPage extends JPanel {
    UIController uiController;
    Controller controller;

    MLLabel errorMessage;

    Dimension centerSize = new Dimension(300, 30);
    Dimension fieldSizes = new Dimension(200, 30);
    Dimension LoginPageActiveFieldSize = new Dimension(600, 300);

    public UserLoginPage(UIController uiController) {
        this.uiController = uiController;
        this.controller = uiController.getController();
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;

        this.add(currentlyLogedinLabel(), c);
        c.gridy++;
        this.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(200, 4)), c);
        c.gridy++;
        this.add(buildLoginField(), c);
        c.gridy++;
        this.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(200, 4)), c);
        c.gridy++;
        this.add(createErrorLabel(), c);
        c.gridy++;
        this.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(200, 4)), c);
        c.gridy++;
        this.add(buildSignUpButton(), c);
        c.gridy++;
        setPreferredSize(LoginPageActiveFieldSize);
    }

    public JPanel buildLoginField() {
        JPanel panel = new JPanel();

        panel.setLayout(new GridBagLayout());

        GridBagConstraints userNameConstraints = new GridBagConstraints();
        userNameConstraints.gridx = 0;
        userNameConstraints.gridy = 0;
        userNameConstraints.gridwidth = 1;

        // UserNameField
        TextFieldWithDescribtion userNameField = new TextFieldWithDescribtion(uiController, "UserName: ", "username",
                fieldSizes);
        userNameField.setForeground(new Color(1.0f, 1.0f, 1.0f, 0.5f));
        userNameField.setPreferredSize(centerSize);
        panel.add(userNameField, userNameConstraints);

        // some more ridgit Free space
        // r1 = rigit 1
        GridBagConstraints r1 = new GridBagConstraints();
        r1.gridx = 0;
        r1.gridy = 1;
        r1.gridwidth = 1;
        panel.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(100, 1)), r1);

        //PasswordField
        GridBagConstraints userPasswordConstraints = new GridBagConstraints();
        PasswordFieldWithDescribtion passwordFieldWithDescribtion = new PasswordFieldWithDescribtion(uiController,
                "Password: ", "password", fieldSizes);
        userPasswordConstraints.gridx = 0;
        userPasswordConstraints.gridy = 2;
        userPasswordConstraints.gridwidth = 2;
        passwordFieldWithDescribtion.setPreferredSize(centerSize);

        panel.add(passwordFieldWithDescribtion, userPasswordConstraints);

        // ridgit freespace a little bit 
        // r1 = rigit 1
        GridBagConstraints r2 = new GridBagConstraints();
        r2.gridx = 0;
        r2.gridy = 3;
        r2.gridwidth = 1;
        panel.add(new rigitFreeSpace(uiController.getDefaultBackgroundcolor(), new Dimension(200, 10)), r2);

        // Button to Login
        MLButton loginAttemptButton = new MLButton(uiController, "login");
        loginAttemptButton.setBackground(uiController.getDefaultAccentColor());

        //when loginAttemptButton is Pressed, try to login with those credentials
        loginAttemptButton.addActionListener(
                e -> controller.attemptLogin(userNameField.getText(), passwordFieldWithDescribtion.getText()));

        GridBagConstraints loginAttemptButtonConstraints = new GridBagConstraints();
        loginAttemptButtonConstraints.gridx = 0;
        loginAttemptButtonConstraints.gridy = 5;
        loginAttemptButtonConstraints.weightx = 0.5;
        loginAttemptButtonConstraints.gridwidth = 2;

        panel.add(loginAttemptButton, loginAttemptButtonConstraints);

        return panel;
    }

    private JPanel buildSignUpButton() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        MLButton signUpButton = new MLButton(uiController, "SignUp!");
        signUpButton.setBorder(BorderFactory.createEmptyBorder());
        signUpButton.setBackground(uiController.getDefaultBackgroundcolor());
        signUpButton.setForeground(uiController.getDefaultAccentColor());
        // when pressed, go to SignUpPage
        signUpButton.addActionListener(e -> uiController.setWindowContent(UIController.SIGNUP_PAGE));

        panel.add(signUpButton, BorderLayout.NORTH);

        return panel;
    }

    public MLLabel currentlyLogedinLabel() {
        MLLabel label = new MLLabel(uiController, "You are currently logged in as \"Guest\"");

        return label;
    }

    private JPanel createErrorLabel() {
        JPanel panel = new JPanel();
        errorMessage = new MLLabel(uiController, "");
        errorMessage.setForeground(uiController.getDefaultErrorColor());
        errorMessage.setFont(uiController.getDefaultFont().deriveFont(Font.PLAIN, 15));
        //panel.setPreferredSize(centerSize);
        panel.add(errorMessage);
        return panel;
    }

    public void setErrorMessage(String error) {
        errorMessage.setText(error);
        revalidate();
        repaint();
    }

}
