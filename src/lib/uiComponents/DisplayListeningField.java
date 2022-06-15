package lib.uiComponents;

import java.util.EventListener;

import javax.swing.JTextField;

public class DisplayListeningField<L extends EventListener> extends JTextField {
    L listener;

    public DisplayListeningField(L listener) {
        this.listener = listener;
        this.setEditable(false);

    }

    public L getListener() {
        return listener;
    }

}
