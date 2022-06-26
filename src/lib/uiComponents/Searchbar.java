package lib.uiComponents;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxUI;

import Controller.Controller;
import GUI.UIController;
import GUI.topMenuBar.CapableComboBoxEditor;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class Searchbar extends JPanel {
    int height = 30;

    public Searchbar(Controller c, UIController uiController) {
        WrittenSearchbar WSB = new WrittenSearchbar(c, uiController);
        add(WSB);
        MLButton searchButton = new MLButton(uiController, "Go");
        searchButton.addActionListener(WSB.getActionListener());
        searchButton.setMinimumSize(new Dimension(40, height));
        add(searchButton);
        setBackground(uiController.getDefaultUIColor());
    }

    class WrittenSearchbar extends JComboBox<String> {

        Controller controller;
        UIController uiController;

        CapableComboBoxEditor cBoxEditor;
        MLTextField textField;

        String defaultText;
        ComboBoxModel<String> defaultModel;

        JButton searchButton;

        boolean isInFocus = false;
        boolean inEdit = false;

        WrittenSearchbar(Controller c, UIController uiController) {
            super();
            this.controller = c;
            this.uiController = uiController;
            createSearchbar();
            createSearchButton();
            addEvents();
        }

        void createSearchButton() {
            searchButton = new JButton();
            searchButton.setBorder(BorderFactory.createEmptyBorder());
        }

        private void addEvents() {
            Component[] comps = getComponents();
            for (int i = 0; i < comps.length; i++) {
                comps[i].addFocusListener(getFocusListener(defaultModel));
                // comps[i].addKeyListener(getKeyListener());
            }
            cBoxEditor.addDocumentListener(getDocumentListener());
            //addActionListener(getActionListener());
        }

        public void createSearchbar() {

            Font font = uiController.getDefaultFont();
            font = font.deriveFont(Font.PLAIN, 15);

            defaultText = controller.lc.s("search database");

            textField = new MLTextField(uiController, "search database");
            cBoxEditor = new CapableComboBoxEditor(textField.getText());
            controller.lc.addLanguageChangeListener(e -> {
                if (this.getCurrentQuery().equals("")) {
                    defaultText = controller.lc.s("search database");
                    setModel(new DefaultComboBoxModel<String>(new String[] { defaultText }));
                    setPopupVisible(false);
                } else {
                    defaultText = controller.lc.s("search database");
                }
            });
            setEditor(cBoxEditor);

            setEditable(true);
            defaultModel = new DefaultComboBoxModel<String>(new String[] { defaultText });

            setModel(defaultModel);
            setSelectedIndex(0);

            setPreferredSize(new Dimension(300, 30));
            setFont(font);

            // https://stackoverflow.com/questions/22597108/disable-jcombobox-arrow-button

            this.setUI(new BasicComboBoxUI() {

                @Override
                protected JButton createArrowButton() {
                    return new JButton() {

                        @Override
                        public int getWidth() {
                            return 0;
                        }
                    };
                }
            });
            this.remove(this.getComponent(0));
            setBackground(uiController.getDefaultUIColor());
        }

        public void setContents(String[] suggestions) {
            inEdit = true;
            DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) this.getModel();
            String first = getCurrentQuery();
            model.removeAllElements();
            model.addElement(first);
            model.addAll(Arrays.asList(suggestions));
            System.out.println(model.getSize());
            System.out.println(isPopupVisible());
            // if (model.getSize() > 0)
            // setPopupVisible(true);
            // setEditorText(first);
            inEdit = false;
        }

        public String getCurrentQuery() {

            String currentS = cBoxEditor.getText();
            if (currentS.equals(defaultText))
                return "";
            return currentS;
        }

        public void setEditorText(String text) {
            cBoxEditor.setText(text);

        }

        public FocusListener getFocusListener(ComboBoxModel<String> defaultModel) {
            return new FocusAdapter() {

                @Override
                public void focusGained(FocusEvent e) {
                    System.out.println("Fokus");
                    isInFocus = true;
                    if (textField.getText().equals(defaultText)) {
                        setEditorText("");
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    isInFocus = false;
                    System.out.println(getCurrentQuery());
                    if (getCurrentQuery().equals("")) {
                        setEditorText(defaultText);
                        setModel(defaultModel);
                        setPopupVisible(false);
                    }
                }

            };
        }

        ActionListener getActionListener() {
            return new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("actionListener");
                    controller.openSearchQuerey((String) getSelectedItem());
                    uiController.setWindowContent(UIController.MAINSTORE_PAGE);
                    setPopupVisible(false);

                }

            };
        }

        DocumentListener getDocumentListener() {
            return new DocumentListener() {

                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (!inEdit)
                        refreshSuggestions();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (!inEdit)
                        refreshSuggestions();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    System.out.println("key");
                }

            };
        }

        KeyListener getKeyListener() {
            return new KeyListener() {

                @Override
                public void keyTyped(KeyEvent e) {
                    refreshSuggestions();
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void keyReleased(KeyEvent e) {
                    // TODO Auto-generated method stub
                }

            };
        }

        public synchronized void refreshSuggestions() {
            String[] newSuggestions = controller.searchDatabase(getCurrentQuery(), maximumRowCount - 1);
            Runnable doHighlight = new Runnable() {
                @Override
                public void run() {
                    setContents(newSuggestions);
                    setPopupVisible(true);
                }
            };
            SwingUtilities.invokeLater(doHighlight);

        }
    }
}
