/* ========================================================================
 * JCommon : a free general purpose class library for the Java(tm) platform
 * ========================================================================
 *
 * (C) Copyright 2000-2016, by Object Refinery Limited and Contributors.
 * 
 * Project Info:  http://www.jfree.org/jcommon/index.html
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, 
 * USA.  
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 * 
 * ------------------------
 * PaintUtilitiesTest.java
 * ------------------------
 * (C) Copyright 2005-2016, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: PaintUtilitiesTest.java,v 1.3 2007/11/02 17:50:37 taqua Exp $
 *
 * Changes
 * -------
 * 23-Feb-2005 : Version 1 (DG);
 * 16-Jan-2016 : Backport updates to equals() method (DG);
 *
 */

package org.jfree.util;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Some tests for the {@link PaintUtilities} class.
 */
public class PaintUtilitiesTest  extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(PaintUtilitiesTest.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public PaintUtilitiesTest(String name) {
        super(name);
    }

    /**
     * Some checks for the equal(Paint, Paint) method.
     */
    public void testEqual() {
        Paint p1 = Color.red;
        Paint p2 = Color.blue;
        Paint p3 = new Color(1, 2, 3, 4);
        Paint p4 = new Color(1, 2, 3, 4);
        Paint p5 = new GradientPaint(1.0f, 2.0f, Color.red, 3.0f, 4.0f, 
                Color.yellow);
        Paint p6 = new GradientPaint(1.0f, 2.0f, Color.red, 3.0f, 4.0f, 
                Color.yellow);
        Paint p7 = new GradientPaint(1.0f, 2.0f, Color.red, 3.0f, 4.0f, 
                Color.blue);
        assertTrue(PaintUtilities.equal(null, null));
        assertFalse(PaintUtilities.equal(p1, null));
        assertFalse(PaintUtilities.equal(null, p1));
        assertFalse(PaintUtilities.equal(p1, p2));
        assertTrue(PaintUtilities.equal(p3, p3));
        assertTrue(PaintUtilities.equal(p3, p4));
        assertTrue(PaintUtilities.equal(p5, p6));
        assertFalse(PaintUtilities.equal(p5, p7));
    }
    
    public void testLinearGradientPaint() {
        Point2D start1 = new Point2D.Float(0, 0);
        Point2D end1 = new Point2D.Float(50, 50);
        float[] dist1 = {0.0f, 0.2f, 1.0f};
        Color[] colors1 = {Color.RED, Color.WHITE, Color.BLUE};
        LinearGradientPaint p1 = new LinearGradientPaint(start1, end1, dist1, 
                colors1);    

        Point2D start2 = new Point2D.Float(0, 0);
        Point2D end2 = new Point2D.Float(50, 50);
        float[] dist2 = {0.0f, 0.2f, 1.0f};
        Color[] colors2 = {Color.RED, Color.WHITE, Color.BLUE};
        LinearGradientPaint p2 = new LinearGradientPaint(start2, end2, dist2, 
                colors2);
        assertTrue(PaintUtilities.equal(p1, p2));
        assertFalse(PaintUtilities.equal(p1, Color.RED));
        assertFalse(PaintUtilities.equal(p1, null));
        assertFalse(PaintUtilities.equal(null, p1));
        
        // check ColorSpaceType
        p1 = new LinearGradientPaint(start1, end1, dist1, colors1, 
                MultipleGradientPaint.CycleMethod.NO_CYCLE, 
                MultipleGradientPaint.ColorSpaceType.LINEAR_RGB, 
                new AffineTransform());
        p2 = new LinearGradientPaint(start1, end1, dist1, colors1, 
                MultipleGradientPaint.CycleMethod.NO_CYCLE, 
                MultipleGradientPaint.ColorSpaceType.SRGB, 
                new AffineTransform());
        assertFalse(PaintUtilities.equal(p1, p2));
        p2 = new LinearGradientPaint(start1, end1, dist1, colors1, 
                MultipleGradientPaint.CycleMethod.NO_CYCLE, 
                MultipleGradientPaint.ColorSpaceType.LINEAR_RGB, 
                new AffineTransform());
        assertTrue(PaintUtilities.equal(p1, p2));

        // check transform
        p1 = new LinearGradientPaint(start1, end1, dist1, colors1, 
                MultipleGradientPaint.CycleMethod.NO_CYCLE, 
                MultipleGradientPaint.ColorSpaceType.LINEAR_RGB, 
                AffineTransform.getTranslateInstance(1.0, 2.0));
        assertFalse(PaintUtilities.equal(p1, p2));
        p2 = new LinearGradientPaint(start1, end1, dist1, colors1, 
                MultipleGradientPaint.CycleMethod.NO_CYCLE, 
                MultipleGradientPaint.ColorSpaceType.LINEAR_RGB, 
                AffineTransform.getTranslateInstance(1.0, 2.0));
        assertTrue(PaintUtilities.equal(p1, p2));
    }
    
    public void testRadialGradientPaint() {
        RadialGradientPaint p1 = new RadialGradientPaint(1.0f, 2.0f, 3.0f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        RadialGradientPaint p2 = new RadialGradientPaint(1.0f, 2.0f, 3.0f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertTrue(PaintUtilities.equal(p1, p2));
        assertFalse(PaintUtilities.equal(p1, Color.RED));
        assertFalse(PaintUtilities.equal(p1, null));
        assertFalse(PaintUtilities.equal(null, p1));

        p1 = new RadialGradientPaint(1.1f, 2.0f, 3.0f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertFalse(PaintUtilities.equal(p1, p2));
        p2 = new RadialGradientPaint(1.1f, 2.0f, 3.0f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertTrue(PaintUtilities.equal(p1, p2));

        p1 = new RadialGradientPaint(1.1f, 2.2f, 3.0f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertFalse(PaintUtilities.equal(p1, p2));
        p2 = new RadialGradientPaint(1.1f, 2.2f, 3.0f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertTrue(PaintUtilities.equal(p1, p2));

        p1 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertFalse(PaintUtilities.equal(p1, p2));
        p2 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 
                new float[] {0.0f, 0.4f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertTrue(PaintUtilities.equal(p1, p2));

        p1 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertFalse(PaintUtilities.equal(p1, p2));
        p2 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.GREEN, Color.BLUE});
        assertTrue(PaintUtilities.equal(p1, p2));
    
        p1 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE});
        assertFalse(PaintUtilities.equal(p1, p2));
        p2 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE});
        assertTrue(PaintUtilities.equal(p1, p2));
        
        p1 = new RadialGradientPaint(1.1f, 2.2f, 3.3f,
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE}, 
                MultipleGradientPaint.CycleMethod.REPEAT);
        assertFalse(PaintUtilities.equal(p1, p2));
        p2 = new RadialGradientPaint(1.1f, 2.2f, 3.3f,
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE},
                MultipleGradientPaint.CycleMethod.REPEAT);
        assertTrue(PaintUtilities.equal(p1, p2));
        
        p1 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 4.4f, 5.5f,
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE}, 
                MultipleGradientPaint.CycleMethod.REPEAT);
        assertFalse(PaintUtilities.equal(p1, p2));
        p2 = new RadialGradientPaint(1.1f, 2.2f, 3.3f, 4.4f, 5.5f,
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE},
                MultipleGradientPaint.CycleMethod.REPEAT);
        assertTrue(PaintUtilities.equal(p1, p2));

        // check ColorSpaceType
        Point2D center = new Point2D.Float(1.1f, 2.2f);
        float radius = 3.3f;
        Point2D focus = new Point2D.Float(4.4f, 5.5f);
        p1 = new RadialGradientPaint(center, radius, focus,
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE}, 
                MultipleGradientPaint.CycleMethod.REPEAT,
                MultipleGradientPaint.ColorSpaceType.SRGB, 
                new AffineTransform());
        p2 = new RadialGradientPaint(center, radius, focus,
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE}, 
                MultipleGradientPaint.CycleMethod.REPEAT,
                MultipleGradientPaint.ColorSpaceType.LINEAR_RGB,
                new AffineTransform());
        assertFalse(PaintUtilities.equal(p1, p2));
        p2 = new RadialGradientPaint(center, radius, focus,
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE}, 
                MultipleGradientPaint.CycleMethod.REPEAT,
                MultipleGradientPaint.ColorSpaceType.SRGB,
                new AffineTransform());        
        assertTrue(PaintUtilities.equal(p1, p2));

        // check transform
        p1 = new RadialGradientPaint(center, radius, focus,
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE}, 
                MultipleGradientPaint.CycleMethod.REPEAT,
                MultipleGradientPaint.ColorSpaceType.SRGB, 
                AffineTransform.getTranslateInstance(1.0, 2.0));
        assertFalse(PaintUtilities.equal(p1, p2));
        p2 = new RadialGradientPaint(center, radius, focus,
                new float[] {0.0f, 0.6f, 1.0f}, 
                new Color[] {Color.RED, Color.YELLOW, Color.BLUE}, 
                MultipleGradientPaint.CycleMethod.REPEAT,
                MultipleGradientPaint.ColorSpaceType.SRGB, 
                AffineTransform.getTranslateInstance(1.0, 2.0));
        assertTrue(PaintUtilities.equal(p1, p2));
    }

}
