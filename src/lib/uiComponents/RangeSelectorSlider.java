package lib.uiComponents;

import javax.swing.JSlider;

public class RangeSelectorSlider extends JSlider {

    public RangeSelectorSlider() {
        this.setUI(new RangeSliderUI());
    }
}
