package lib.uiComponents;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

import java.awt.*;

public class RangeSliderUI extends BasicSliderUI {

    protected Rectangle upperThumbRect;

    @Override
    public void installUI(JComponent c) {
        upperThumbRect = new Rectangle();
        super.installUI(c);
    }

    @Override
    public void calculateThumbSize() {
        super.calculateThumbSize();
        upperThumbRect.setBounds(thumbRect);
    }

    @Override
    public void calculateThumbLocation() {
        super.calculateThumbLocation();
        if (slider.getSnapToTicks()) {
            int sliderValue = slider.getValue();
            int snappedValue = sliderValue;
            int tickSpacing = getTickSpacing();

            if (tickSpacing != 0) {
                // If it's not on a tick, change the value
                if ((sliderValue - slider.getMinimum()) % tickSpacing != 0) {
                    float temp = (float) (sliderValue - slider.getMinimum()) / (float) tickSpacing;
                    int whichTick = Math.round(temp);

                    // This is the fix for the bug #6401380
                    if (temp - (int) temp == .5 && sliderValue < getLowestValue()) {
                        whichTick--;
                    }
                    snappedValue = slider.getMinimum() + (whichTick * tickSpacing);
                }

                if (snappedValue != sliderValue) {
                    slider.setValue(snappedValue);
                }
            }
        }

        if (slider.getOrientation() == JSlider.HORIZONTAL) {
            int valuePosition = xPositionForValue(slider.getValue());

            upperThumbRect.x = valuePosition - (upperThumbRect.width / 2);
            upperThumbRect.y = trackRect.y;
        } else {
            int valuePosition = yPositionForValue(slider.getValue());

            upperThumbRect.x = trackRect.x;
            upperThumbRect.y = valuePosition - (upperThumbRect.height / 2);
        }
    }

}
