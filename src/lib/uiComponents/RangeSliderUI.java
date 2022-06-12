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
        upperThumbRect.setBounds(thumbRect);
        super.calculateThumbSize();
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
            int valueUpperPosition = xPositionForValue(slider.getValue());

            upperThumbRect.x = valueUpperPosition - (upperThumbRect.width / 2);
            upperThumbRect.y = trackRect.y;
        } else {
            int valueUpperPosition = yPositionForValue(slider.getValue());

            upperThumbRect.x = trackRect.x;
            upperThumbRect.y = valueUpperPosition - (upperThumbRect.height / 2);
        }
    }

    // FOr whatever erason the method is private, so I copied the code
    public int getTickSpacing() {
        int majorTickSpacing = slider.getMajorTickSpacing();
        int minorTickSpacing = slider.getMinorTickSpacing();

        int result;

        if (minorTickSpacing > 0) {
            result = minorTickSpacing;
        } else if (majorTickSpacing > 0) {
            result = majorTickSpacing;
        } else {
            result = 0;
        }
        return result;
    }

}
