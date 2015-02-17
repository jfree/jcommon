/* ========================================================================
 * JCommon : a free general purpose class library for the Java(tm) platform
 * ========================================================================
 *
 * (C) Copyright 2000-2015, by Object Refinery Limited and Contributors.
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
 * RectangleAnchorTest.java
 * ------------------------
 * (C) Copyright 2004-2015, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 08-Jan-2004 : Version 1 (DG);
 * 17-Feb-2015 : Add tests (DG);
 *
 */

package org.jfree.ui;

import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests for the {@link RectangleAnchor} class.
 */
public class RectangleAnchorTest extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(RectangleAnchorTest.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public RectangleAnchorTest(String name) {
        super(name);
    }

    /**
     * Tests for the createRectangle() method.
     */
    public void testCreateRectangle() {
        Size2D s = new Size2D(3.0, 8.0);
        assertEquals(new Rectangle2D.Double(-0.5, -2.0, 3.0, 8.0), 
                RectangleAnchor.createRectangle(s, 1.0, 2.0, RectangleAnchor.CENTER));
        assertEquals(new Rectangle2D.Double(1.0, -2.0, 3.0, 8.0), 
                RectangleAnchor.createRectangle(s, 1.0, 2.0, RectangleAnchor.LEFT));
        assertEquals(new Rectangle2D.Double(-2.0, -2.0, 3.0, 8.0), 
                RectangleAnchor.createRectangle(s, 1.0, 2.0, RectangleAnchor.RIGHT));
        assertEquals(new Rectangle2D.Double(-0.5, 2.0, 3.0, 8.0), 
                RectangleAnchor.createRectangle(s, 1.0, 2.0, RectangleAnchor.TOP));
        assertEquals(new Rectangle2D.Double(1.0, 2.0, 3.0, 8.0), 
                RectangleAnchor.createRectangle(s, 1.0, 2.0, RectangleAnchor.TOP_LEFT));
        assertEquals(new Rectangle2D.Double(-2.0, 2.0, 3.0, 8.0), 
                RectangleAnchor.createRectangle(s, 1.0, 2.0, RectangleAnchor.TOP_RIGHT));
        assertEquals(new Rectangle2D.Double(-0.5, -6.0, 3.0, 8.0), 
                RectangleAnchor.createRectangle(s, 1.0, 2.0, RectangleAnchor.BOTTOM));
        assertEquals(new Rectangle2D.Double(1.0, -6.0, 3.0, 8.0), 
                RectangleAnchor.createRectangle(s, 1.0, 2.0, RectangleAnchor.BOTTOM_LEFT));
        assertEquals(new Rectangle2D.Double(-2.0, -6.0, 3.0, 8.0), 
                RectangleAnchor.createRectangle(s, 1.0, 2.0, RectangleAnchor.BOTTOM_RIGHT));
    }
    
    /**
     * Tests the equals() method.
     */
    public void testEquals() {
        assertTrue(RectangleAnchor.TOP.equals(RectangleAnchor.TOP));
    }
    
    /**
     * Serialize an instance, restore it, and check for identity.
     */
    public void testSerialization() {
        RectangleAnchor a1 = RectangleAnchor.RIGHT;
        RectangleAnchor a2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(a1);
            out.close();

            ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()));
            a2 = (RectangleAnchor) in.readObject();
            in.close();
        }
        catch (Exception e) {
            System.err.println(e.toString());
        }
        assertTrue(a1 == a2); 
    }

}
