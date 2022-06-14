package lib.technicalComponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.Shape;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;

import lib.uiComponents.RangeSlider;

//Folgt dem Tutorial ziemlich genau, ist aber verändert, um vernünftig auszusehen und so: https://ernienotes.wordpress.com/2010/12/27/creating-a-java-swing-range-slider/

public class RangeSliderUI extends BasicSliderUI {

    //the displayed area between the 2 thumbs
    private Color rangeColor = (Color) UIManager.get("Slider.trackValueColor");

    // for the frame of the thumb
    private Color frameColor = (Color) UIManager.get("Slider.trackColor");

    // for the frame of the thumb
    private Color trackColor = (Color) UIManager.get("Slider.trackColor");

    //Disabled thumb 

    //active Thumb

    private int trackWidth = 2 + (int) UIManager.get("Slider.trackWidth");

    // We dont like shadow, so this gets to be null
    private Color shadowColor = null;

    /** Location and size of thumb for upper value. */
    private Rectangle upperThumbRect;
    /** Indicator that determines whether upper thumb is selected. */
    private boolean upperThumbSelected;

    /** Indicator that determines whether lower thumb is being dragged. */
    private transient boolean lowerDragging;
    /** Indicator that determines whether upper thumb is being dragged. */
    private transient boolean upperDragging;

    @Override
    public void installUI(JComponent c) {
        upperThumbRect = new Rectangle();
        checkIfLookAndFeelsetColor();
        super.installUI(c);
    }

    private void checkIfLookAndFeelsetColor() {
        if (rangeColor == null) {
            rangeColor = Color.ORANGE;
        }
        if (frameColor == null) {
            frameColor = Color.GRAY;
        }
        if (trackColor == null) {
            trackColor = Color.LIGHT_GRAY;
        }

    }

    /**
     * Creates a listener to handle track events in the specified slider.
     */
    @Override
    protected TrackListener createTrackListener(JSlider slider) {
        return new RangeTrackListener();
    }

    /**
     * Creates a listener to handle change events in the specified slider.
     */
    @Override
    protected ChangeListener createChangeListener(JSlider slider) {
        return new ChangeHandler();
    }

    @Override
    public void calculateThumbSize() {
        super.calculateThumbSize();
        // Set upper thumb size.
        upperThumbRect.setSize(thumbRect.width, thumbRect.height);
    }

    /**
     * snaps the location of the thump to the nearest Value, if the feature is turned on
     */
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
            int valueUpperPosition = xPositionForValue(slider.getValue() + slider.getExtent());

            upperThumbRect.x = valueUpperPosition - (upperThumbRect.width / 2);
            upperThumbRect.y = trackRect.y;
        } else {
            int valueUpperPosition = yPositionForValue(slider.getValue() + slider.getExtent());

            upperThumbRect.x = trackRect.x;
            upperThumbRect.y = valueUpperPosition - (upperThumbRect.height / 2);
        }
    }

    // For whatever reason the method is private, so I copied the code
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

    /**
     * Returns the size of a thumb.
     */
    @Override
    protected Dimension getThumbSize() {
        return new Dimension(17, 17);
    }

    /**
     * Paints the slider.  The selected thumb is always painted on top of the
     * other thumb.
     */
    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);

        Rectangle clipRect = g.getClipBounds();
        if (upperThumbSelected) {
            // Paint lower thumb first, then upper thumb.
            if (clipRect.intersects(thumbRect)) {
                paintLowerThumb(g);
            }
            if (clipRect.intersects(upperThumbRect)) {
                paintUpperThumb(g);
            }

        } else {
            // Paint upper thumb first, then lower thumb.
            if (clipRect.intersects(upperThumbRect)) {
                paintUpperThumb(g);
            }
            if (clipRect.intersects(thumbRect)) {
                paintLowerThumb(g);
            }
        }
    }

    /**
     * Paints the track.
     */
    @Override
    public void paintTrack(Graphics g) {

        Rectangle trackBounds = trackRect;

        if (slider.getOrientation() == JSlider.HORIZONTAL) {
            int cy = (trackBounds.height / 2) - 2;
            int cw = trackBounds.width;

            g.translate(trackBounds.x, trackBounds.y + cy);

            g.setColor(shadowColor);
            g.drawLine(0, 0, cw - 1, 0);
            g.drawLine(0, 1, 0, 2);
            /* g.setColor(highlightColor);
            g.drawLine(0, 3, cw, 3);
            g.drawLine(cw, 0, cw, 3); */
            g.setColor(trackColor);

            g.drawLine(1, 1, cw, 1);
            for (int y = 0; y <= trackWidth; y++) {
                g.drawLine(1, y, cw, y);
            }

            g.translate(-trackBounds.x, -(trackBounds.y + cy));

        } else {
            int cx = (trackBounds.width / 2) - 2;
            int ch = trackBounds.height;

            g.translate(trackBounds.x + cx, trackBounds.y);

            g.setColor(getShadowColor());
            g.drawLine(0, 0, 0, ch - 1);
            g.drawLine(1, 0, 2, 0);
            g.setColor(getHighlightColor());
            g.drawLine(3, 0, 3, ch);
            g.drawLine(0, ch, 3, ch);
            g.setColor(Color.black);
            g.drawLine(1, 1, 1, ch - 2);

            g.translate(-(trackBounds.x + cx), -trackBounds.y);
        }

        if (slider.getOrientation() == JSlider.HORIZONTAL) {
            // Determine position of selected range by moving from the middle
            // of one thumb to the other.
            int lowerX = thumbRect.x + (thumbRect.width / 2);
            int upperX = upperThumbRect.x + (upperThumbRect.width / 2);

            // Determine track position.
            int cy = (trackBounds.height / 2) - 2;

            // Save color and shift position.
            Color oldColor = g.getColor();
            g.translate(trackBounds.x, trackBounds.y + cy);

            // Draw selected range.
            g.setColor(rangeColor);
            for (int y = 0; y <= trackWidth; y++) {
                g.drawLine(lowerX - trackBounds.x, y, upperX - trackBounds.x, y);
            }

            // Restore position and color.
            g.translate(-trackBounds.x, -(trackBounds.y + cy));
            g.setColor(oldColor);

        } else {
            // Determine position of selected range by moving from the middle
            // of one thumb to the other.
            int lowerY = thumbRect.x + (thumbRect.width / 2);
            int upperY = upperThumbRect.x + (upperThumbRect.width / 2);

            // Determine track position.
            int cx = (trackBounds.width / 2) - 2;

            // Save color and shift position.
            Color oldColor = g.getColor();
            g.translate(trackBounds.x + cx, trackBounds.y);

            // Draw selected range.
            g.setColor(rangeColor);
            for (int x = 0; x <= 3; x++) {
                g.drawLine(x, lowerY - trackBounds.y, x, upperY - trackBounds.y);
            }

            // Restore position and color.
            g.translate(-(trackBounds.x + cx), -trackBounds.y);
            g.setColor(oldColor);
        }
    }

    /**
     * Overrides superclass method to do nothing.  Thumb painting is handled
     * within the <code>paint()</code> method.
     */
    @Override
    public void paintThumb(Graphics g) {
        // Do nothing.
    }

    /**
     * Paints the thumb for the lower value using the specified graphics object.
     */
    private void paintLowerThumb(Graphics g) {
        paintThumb(g, thumbRect);
    }

    /**
     * Paints the thumb for the upper value using the specified graphics object.
     */
    private void paintUpperThumb(Graphics g) {
        paintThumb(g, upperThumbRect);
    }

    /**
     * Paints focus.
     * @param g the graphics
     */
    public void paintFocus(Graphics g) {
        g.setColor(getFocusColor());

    }

    /**
     * Returns a Thumb based on the thumbs base Rectangle.
     */
    private Graphics paintThumb(Graphics g, Rectangle thumb) {

        Rectangle knobBounds = thumb;
        int w = knobBounds.width;
        int h = knobBounds.height;

        g.translate(knobBounds.x, knobBounds.y);
        Rectangle clip = g.getClipBounds();
        g.clipRect(0, 0, w, h);

        if (slider.isEnabled()) {
            g.setColor(slider.getBackground());
        } else {
            g.setColor(slider.getBackground().darker());
        }

        int cw = w / 2;
        g.fillRect(1, 1, w - 3, h - 1 - cw);

        Polygon p = new Polygon();
        p.addPoint(1, h - cw);
        p.addPoint(cw - 1, h - 1);
        p.addPoint(w - 2, h - 1 - cw);
        g.fillPolygon(p);

        g.setColor(frameColor);
        g.drawLine(0, 0, w - 2, 0);
        g.drawLine(0, 1, 0, h - 1 - cw);
        g.drawLine(0, h - cw, cw - 1, h - 1);

        g.setColor(frameColor);
        g.drawLine(w - 1, 0, w - 1, h - 2 - cw);
        g.drawLine(w - 1, h - 1 - cw, w - 1 - cw, h - 1);

        // this is null, because shadow color is null, so we will not have a shadow, which is good
        g.setColor(shadowColor);
        g.drawLine(w - 2, 1, w - 2, h - 2 - cw);
        g.drawLine(w - 2, h - 1 - cw, w - 1 - cw, h - 2);

        g.setClip(clip);
        g.translate(-knobBounds.x, -knobBounds.y);

        return g;
    }

    /** 
     * Sets the location of the upper thumb, and repaints the slider.  This is
     * called when the upper thumb is dragged to repaint the slider.  The
     * <code>setThumbLocation()</code> method performs the same task for the
     * lower thumb.
     */
    private void setUpperThumbLocation(int x, int y) {
        Rectangle upperUnionRect = new Rectangle();
        upperUnionRect.setBounds(upperThumbRect);

        upperThumbRect.setLocation(x, y);

        SwingUtilities.computeUnion(upperThumbRect.x, upperThumbRect.y, upperThumbRect.width, upperThumbRect.height,
                upperUnionRect);
        slider.repaint(upperUnionRect.x, upperUnionRect.y, upperUnionRect.width, upperUnionRect.height);
    }

    /**
     * Moves the selected thumb in the specified direction by a block increment.
     * This method is called when the user presses the Page Up or Down keys.
     */
    public void scrollByBlock(int direction) {
        synchronized (slider) {
            int blockIncrement = (slider.getMaximum() - slider.getMinimum()) / 10;
            if (blockIncrement <= 0 && slider.getMaximum() > slider.getMinimum()) {
                blockIncrement = 1;
            }
            int delta = blockIncrement * ((direction > 0) ? POSITIVE_SCROLL : NEGATIVE_SCROLL);

            if (upperThumbSelected) {
                int oldValue = ((RangeSlider) slider).getUpperValue();
                ((RangeSlider) slider).setUpperValue(oldValue + delta);
            } else {
                int oldValue = slider.getValue();
                slider.setValue(oldValue + delta);
            }
        }
    }

    /**
     * Moves the selected thumb in the specified direction by a unit increment.
     * This method is called when the user presses one of the arrow keys.
     */
    public void scrollByUnit(int direction) {
        synchronized (slider) {
            int delta = 1 * ((direction > 0) ? POSITIVE_SCROLL : NEGATIVE_SCROLL);

            if (upperThumbSelected) {
                int oldValue = ((RangeSlider) slider).getUpperValue();
                ((RangeSlider) slider).setUpperValue(oldValue + delta);
            } else {
                int oldValue = slider.getValue();
                slider.setValue(oldValue + delta);
            }
        }
    }

    /**
     * Listener to handle model change events.  This calculates the thumb 
     * locations and repaints the slider if the value change is not caused by
     * dragging a thumb.
     */
    public class ChangeHandler implements ChangeListener {
        public void stateChanged(ChangeEvent arg0) {
            if (!lowerDragging && !upperDragging) {
                calculateThumbLocation();
                slider.repaint();
            }
        }
    }

    /**
     * Listener to handle mouse movements in the slider track.
     */
    public class RangeTrackListener extends TrackListener {

        @Override
        public void mousePressed(MouseEvent e) {
            if (!slider.isEnabled()) {
                return;
            }

            currentMouseX = e.getX();
            currentMouseY = e.getY();

            if (slider.isRequestFocusEnabled()) {
                slider.requestFocus();
            }

            // Determine which thumb is pressed.  If the upper thumb is 
            // selected (last one dragged), then check its position first;
            // otherwise check the position of the lower thumb first.
            boolean lowerPressed = false;
            boolean upperPressed = false;
            if (upperThumbSelected || slider.getMinimum() == slider.getValue()) {
                if (upperThumbRect.contains(currentMouseX, currentMouseY)) {
                    upperPressed = true;
                } else if (thumbRect.contains(currentMouseX, currentMouseY)) {
                    lowerPressed = true;
                }
            } else {
                if (thumbRect.contains(currentMouseX, currentMouseY)) {
                    lowerPressed = true;
                } else if (upperThumbRect.contains(currentMouseX, currentMouseY)) {
                    upperPressed = true;
                }
            }

            // Handle lower thumb pressed.
            if (lowerPressed) {
                switch (slider.getOrientation()) {
                    case JSlider.VERTICAL:
                        offset = currentMouseY - thumbRect.y;
                        break;
                    case JSlider.HORIZONTAL:
                        offset = currentMouseX - thumbRect.x;
                        break;
                }
                upperThumbSelected = false;
                lowerDragging = true;
                return;
            }
            lowerDragging = false;

            // Handle upper thumb pressed.
            if (upperPressed) {
                switch (slider.getOrientation()) {
                    case JSlider.VERTICAL:
                        offset = currentMouseY - upperThumbRect.y;
                        break;
                    case JSlider.HORIZONTAL:
                        offset = currentMouseX - upperThumbRect.x;
                        break;
                }
                upperThumbSelected = true;
                upperDragging = true;
                return;
            }
            upperDragging = false;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            lowerDragging = false;
            upperDragging = false;
            slider.setValueIsAdjusting(false);
            super.mouseReleased(e);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (!slider.isEnabled()) {
                return;
            }

            currentMouseX = e.getX();
            currentMouseY = e.getY();

            if (lowerDragging) {
                slider.setValueIsAdjusting(true);
                moveLowerThumb();

            } else if (upperDragging) {
                slider.setValueIsAdjusting(true);
                moveUpperThumb();
            }
        }

        @Override
        public boolean shouldScroll(int direction) {
            return false;
        }

        /**
         * Moves the location of the lower thumb, and sets its corresponding 
         * value in the slider.
         */
        private void moveLowerThumb() {
            int thumbMiddle = 0;

            switch (slider.getOrientation()) {
                case JSlider.VERTICAL:
                    int halfThumbHeight = thumbRect.height / 2;
                    int thumbTop = currentMouseY - offset;
                    int trackTop = trackRect.y;
                    int trackBottom = trackRect.y + (trackRect.height - 1);
                    int vMax = yPositionForValue(slider.getValue() + slider.getExtent());

                    // Apply bounds to thumb position.
                    if (drawInverted()) {
                        trackBottom = vMax;
                    } else {
                        trackTop = vMax;
                    }
                    thumbTop = Math.max(thumbTop, trackTop - halfThumbHeight);
                    thumbTop = Math.min(thumbTop, trackBottom - halfThumbHeight);

                    setThumbLocation(thumbRect.x, thumbTop);

                    // Update slider value.
                    thumbMiddle = thumbTop + halfThumbHeight;
                    slider.setValue(valueForYPosition(thumbMiddle));
                    break;

                case JSlider.HORIZONTAL:
                    int halfThumbWidth = thumbRect.width / 2;
                    int thumbLeft = currentMouseX - offset;
                    int trackLeft = trackRect.x;
                    int trackRight = trackRect.x + (trackRect.width - 1);
                    int hMax = xPositionForValue(slider.getValue() + slider.getExtent());

                    // Apply bounds to thumb position.
                    if (drawInverted()) {
                        trackLeft = hMax;
                    } else {
                        trackRight = hMax;
                    }
                    thumbLeft = Math.max(thumbLeft, trackLeft - halfThumbWidth);
                    thumbLeft = Math.min(thumbLeft, trackRight - halfThumbWidth);

                    setThumbLocation(thumbLeft, thumbRect.y);

                    // Update slider value.
                    thumbMiddle = thumbLeft + halfThumbWidth;
                    slider.setValue(valueForXPosition(thumbMiddle));
                    break;

                default:
                    return;
            }
        }

        /**
         * Moves the location of the upper thumb, and sets its corresponding 
         * value in the slider.
         */
        private void moveUpperThumb() {
            int thumbMiddle = 0;

            switch (slider.getOrientation()) {
                case JSlider.VERTICAL:
                    int halfThumbHeight = thumbRect.height / 2;
                    int thumbTop = currentMouseY - offset;
                    int trackTop = trackRect.y;
                    int trackBottom = trackRect.y + (trackRect.height - 1);
                    int vMin = yPositionForValue(slider.getValue());

                    // Apply bounds to thumb position.
                    if (drawInverted()) {
                        trackTop = vMin;
                    } else {
                        trackBottom = vMin;
                    }
                    thumbTop = Math.max(thumbTop, trackTop - halfThumbHeight);
                    thumbTop = Math.min(thumbTop, trackBottom - halfThumbHeight);

                    setUpperThumbLocation(thumbRect.x, thumbTop);

                    // Update slider extent.
                    thumbMiddle = thumbTop + halfThumbHeight;
                    slider.setExtent(valueForYPosition(thumbMiddle) - slider.getValue());
                    break;

                case JSlider.HORIZONTAL:
                    int halfThumbWidth = thumbRect.width / 2;
                    int thumbLeft = currentMouseX - offset;
                    int trackLeft = trackRect.x;
                    int trackRight = trackRect.x + (trackRect.width - 1);
                    int hMin = xPositionForValue(slider.getValue());

                    // Apply bounds to thumb position.
                    if (drawInverted()) {
                        trackRight = hMin;
                    } else {
                        trackLeft = hMin;
                    }
                    thumbLeft = Math.max(thumbLeft, trackLeft - halfThumbWidth);
                    thumbLeft = Math.min(thumbLeft, trackRight - halfThumbWidth);

                    setUpperThumbLocation(thumbLeft, thumbRect.y);

                    // Update slider extent.
                    thumbMiddle = thumbLeft + halfThumbWidth;
                    slider.setExtent(valueForXPosition(thumbMiddle) - slider.getValue());
                    break;

                default:
                    return;
            }
        }
    }

}
