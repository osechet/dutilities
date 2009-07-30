/*
 * ShadowBorder.java
 *
 * Copyright (C) 2009 Olivier Sechet
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.druppi.swing.borders;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.RenderingHints;

import javax.swing.border.AbstractBorder;


/**
 * This class is (mostly) inspired by Christophe Le Besnerais's work on his blog:
 * http://swing-fx.blogspot.com/2008/08/sexy-desktop-application.html. <p>
 *
 * @author Christophe Le Besnerais
 * @author Olivier Sechet
 */
public class ShadowBorder extends AbstractBorder {

    /** The serial version id.*/
    private static final long serialVersionUID = 6841846627047213451L;

    /** A general transparent color. */
    public static final Color TRANSPARENT_COLOR = new Color(0, 0, 0, 0);

    /** The default shadow color. */
    public static final Color DEFAULT_SHADOW_COLOR = new Color(0f, 0f, 0f, 0.5f);

    /** The border's insets. */
    private final Insets insets;

    /**
     * The size of the shadow margin. The shadow margin is the distance between the
     * border of the component and the beginning of the margin.
     */
    private int shadowsMargin = 0;

    /** The background colors. */
    private Paint colorRight, colorLeft, colorTop, colorBottom;

    /** The color of the shadow. */
    private Color shadowColor;

    /** <code>true</code> if the internal lines have to be drawn. */
    private boolean linesVisible = true;

    /**
     * Creates a new ShadowBorder with a no insets.
     */
    public ShadowBorder() {
        this(new Insets(0, 0, 0, 0));
    }

    /**
     * Creates a new ShadowBorder with the specified insets.
     *
     * @param insets the border's insets.
     */
    public ShadowBorder(Insets insets) {
        this(insets, TRANSPARENT_COLOR, TRANSPARENT_COLOR, TRANSPARENT_COLOR,
                TRANSPARENT_COLOR);
    }

    /**
     * Creates a new ShadowBorder with the specified insets and the specified background
     * colors.
     *
     * @param insets the border's insets.
     * @param colorRight the color of the right background.
     * @param colorLeft the color of the left background.
     * @param colorTop the color of the upper background.
     * @param colorBottom the color of the lower background.
     */
    public ShadowBorder(Insets insets, Paint colorRight, Paint colorLeft, Paint colorTop,
            Paint colorBottom) {
        this(insets, colorRight, colorLeft, colorTop, colorBottom, DEFAULT_SHADOW_COLOR);
    }

    /**
     * Creates a new ShadowBorder with the specified insets, the specified background
     * colors and the specified internal line color.
     *
     * @param insets the border's insets.
     * @param colorRight the color of the right background.
     * @param colorLeft the color of the left background.
     * @param colorTop the color of the upper background.
     * @param colorBottom the color of the lower background.
     * @param shadowColor the color of the shadow.
     */
    public ShadowBorder(Insets insets, Paint colorRight, Paint colorLeft, Paint colorTop,
            Paint colorBottom, Color shadowColor) {
        this.insets = insets;
        this.colorRight = colorRight;
        this.colorLeft = colorLeft;
        this.colorTop = colorTop;
        this.colorBottom = colorBottom;
        this.shadowColor = shadowColor;
    }

    /**
     * Returns <code>true</code> if the internal line must be drawn.
     *
     * @return <code>true</code> if the internal line must be drawn.
     */
    public boolean isLinesVisible() {
        return linesVisible;
    }

    /**
     * Sets the visibility of the internal line.
     *
     * @param linesVisible <code>true</code> if the internal line must be drawn.
     */
    public void setLinesVisible(boolean linesVisible) {
        this.linesVisible = linesVisible;
    }

    /**
     * Returns the size of the shadow margin.
     *
     * @return the size of the shadow margin.
     */
    public int getShadowsMargin() {
        return shadowsMargin;
    }

    /**
     * Sets the size of the shadow margin.
     *
     * @param shadowsMargin the new size.
     */
    public void setShadowsMargin(int shadowsMargin) {
        this.shadowsMargin = shadowsMargin;
    }

    /**
     * Returns the color of the lower background.
     *
     * @return the color of the lower background.
     */
    public Paint getColorBottom() {
        return colorBottom;
    }

    /**
     * Sets the color of the lower background.
     *
     * @param color the new color.
     */
    public void setColorBottom(Paint color) {
        this.colorBottom = color;
    }

    /**
     * Returns the color of the left background.
     *
     * @return the color of the left background.
     */
    public Paint getColorLeft() {
        return colorLeft;
    }

    /**
     * Sets the color of the left background.
     *
     * @param color the new color.
     */
    public void setColorLeft(Paint color) {
        this.colorLeft = color;
    }

    /**
     * Returns the color of the right background.
     *
     * @return the color of the right background.
     */
    public Paint getColorRight() {
        return colorRight;
    }

    /**
     * Sets the color of the right background.
     *
     * @param color the new color.
     */
    public void setColorRight(Paint color) {
        this.colorRight = color;
    }

    /**
     * Returns the color of the upper background.
     *
     * @return the color of the upper background.
     */
    public Paint getColorTop() {
        return colorTop;
    }

    /**
     * Sets the color of the top background.
     *
     * @param color the new color.
     */
    public void setColorTop(Paint color) {
        this.colorTop = color;
    }

    /**
     * Returns the color of the shadow.
     *
     * @return the color of the shadow.
     */
    public Color getShadowColor() {
        return shadowColor;
    }

    /**
     * Sets the color of the shadow.
     *
     * @param color the new color.
     */
    public void setShadowColor(Color color) {
        this.shadowColor = color;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Insets getBorderInsets(Component c) {
        return insets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // paint background :
        if (insets.right > 0) {
            g2.setPaint(colorRight);
            g2.fillRect(width - insets.right, 0, insets.right, height);
        }
        if (insets.left > 0) {
            g2.setPaint(colorLeft);
            g2.fillRect(0, 0, insets.left, height);
        }
        if (insets.top > 0) {
            g2.setPaint(colorTop);
            g2.fillRect(0, -1, width, insets.top + 2);
        }
        if (insets.bottom > 0) {
            g2.setPaint(colorBottom);
            g2.fillRect(0, height - insets.bottom, width, insets.bottom);
        }

        // paint shadows :
        if (insets.right > 0) {
            g2.setPaint(new GradientPaint(width - insets.right, 0, shadowColor, width, 0,
                    TRANSPARENT_COLOR));
            g2.fillArc(width - 2 * insets.right, insets.top + shadowsMargin,
                    insets.right * 2, height - insets.bottom - insets.top - 2
                            * shadowsMargin, -90, 180);
            if (isLinesVisible()) {
                g2.setColor(shadowColor);
                g2.drawLine(width - insets.right, insets.top, width - insets.right,
                        height - insets.bottom);
            }
        }
        if (insets.left > 0) {
            g2.setPaint(new GradientPaint(insets.left, 0, shadowColor, 0, 0,
                    TRANSPARENT_COLOR));
            g2.fillArc(0, insets.top + shadowsMargin, insets.left * 2, height
                    - insets.bottom - insets.top - 2 * shadowsMargin, 90, 180);
            if (isLinesVisible()) {
                g2.setColor(shadowColor);
                g2.drawLine(insets.left - 1, insets.top, insets.left - 1, height
                        - insets.bottom);
            }
        }
        if (insets.top > 0) {
            g2.setPaint(new GradientPaint(0, insets.top, shadowColor, 0, 0,
                    TRANSPARENT_COLOR));
            g2.fillArc(insets.left + shadowsMargin, 1, width - insets.left - insets.right
                    - 2 * shadowsMargin, insets.top * 2, 0, 180);
            if (isLinesVisible()) {
                g2.setColor(shadowColor);
                g2.drawLine(insets.left, insets.top, width - insets.right, insets.top);
            }
        }
        if (insets.bottom > 0) {
            g2.setPaint(new GradientPaint(0, height - insets.bottom, shadowColor, 0,
                    height, TRANSPARENT_COLOR));
            g2.fillArc(insets.left + shadowsMargin, height - 2 * insets.bottom, width
                    - insets.left - insets.right - 2 * shadowsMargin, insets.bottom * 2,
                    0, -180);
            if (isLinesVisible()) {
                g2.setColor(shadowColor);
                g2.drawLine(insets.left, height - insets.bottom, width - insets.right,
                        height - insets.bottom);
            }
        }
    }
}
