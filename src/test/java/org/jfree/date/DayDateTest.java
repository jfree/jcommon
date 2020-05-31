/* ========================================================================
 * JCommon : a free general purpose class library for the Java(tm) platform
 * ========================================================================
 *
 * (C) Copyright 2000-2014, by Object Refinery Limited and Contributors.
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
 * -------------------
 * SerialDateTest.java
 * -------------------
 * (C) Copyright 2001-2014, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: SerialDateTest.java,v 1.7 2007/11/02 17:50:35 taqua Exp $
 *
 * Changes
 * -------
 * 15-Nov-2001 : Version 1 (DG);
 * 25-Jun-2002 : Removed unnecessary import (DG);
 * 24-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 13-Mar-2003 : Added serialization test (DG);
 * 05-Jan-2005 : Added test for bug report 1096282 (DG);
 *
 */

package org.jfree.date;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import static org.jfree.date.DateUtil.isLeapYear;
import static org.jfree.date.DateUtil.leapYearCount;
import static org.jfree.date.Day.*;
import static org.jfree.date.Month.DECEMBER;

/**
 * Some JUnit tests for the {@link DayDate} class.
 */
public class DayDateTest extends TestCase {

    /**
     * Date representing November 9.
     */
    private DayDate nov9Y2001;

    /**
     * Creates a new test case.
     *
     * @param name the name.
     */
    public DayDateTest(final String name) {
        super(name);
    }

    /**
     * Returns a test suite for the JUnit test runner.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(DayDateTest.class);
    }

    /**
     * Problem set up.
     */
    protected void setUp() {
        this.nov9Y2001 = DayDateFactory.makeDate(9, MonthConstants.NOVEMBER, 2001);
    }

    /**
     * 9 Nov 2001 plus two months should be 9 Jan 2002.
     */
    public void testAddMonthsTo9Nov2001() {
        final DayDate jan9Y2002 = this.nov9Y2001.plusMonths(2);
        final DayDate answer = DayDateFactory.makeDate(9, 1, 2002);
        assertEquals(answer, jan9Y2002);
    }

    /**
     * A test case for a reported bug, now fixed.
     */
    public void testAddMonthsTo5Oct2003() {
        final DayDate d1 = DayDateFactory.makeDate(5, MonthConstants.OCTOBER, 2003);
        final DayDate d2 = d1.plusMonths(2);
        assertEquals(d2, DayDateFactory.makeDate(5, MonthConstants.DECEMBER, 2003));
    }

    /**
     * A test case for a reported bug, now fixed.
     */
    public void testAddMonthsTo1Jan2003() {
        final DayDate d1 = DayDateFactory.makeDate(1, MonthConstants.JANUARY, 2003);
        final DayDate d2 = d1.plusMonths(0);
        assertEquals(d2, d1);
    }

    /**
     * Monday preceding Friday 9 November 2001 should be 5 November.
     */
    public void testMondayPrecedingFriday9Nov2001() {
        DayDate mondayBefore = this.nov9Y2001.getPreviousDayOfWeek(MONDAY);
        assertEquals(5, mondayBefore.getDayOfMonth());
    }

    /**
     * Monday following Friday 9 November 2001 should be 12 November.
     */
    public void testMondayFollowingFriday9Nov2001() {
        DayDate mondayAfter = this.nov9Y2001.getFollowingDayOfWeek(MONDAY);
        assertEquals(12, mondayAfter.getDayOfMonth());
    }

    /**
     * Monday nearest Friday 9 November 2001 should be 12 November.
     */
    public void testMondayNearestFriday9Nov2001() {
        DayDate mondayNearest = this.nov9Y2001.getNearestDayOfWeek(MONDAY);
        assertEquals(12, mondayNearest.getDayOfMonth());
    }

    /**
     * The Monday nearest to 22nd January 1970 falls on the 19th.
     */
    public void testMondayNearest22Jan1970() {
        DayDate jan22Y1970 = DayDateFactory.makeDate(22, MonthConstants.JANUARY, 1970);
        DayDate mondayNearest = jan22Y1970.getNearestDayOfWeek(MONDAY);
        assertEquals(19, mondayNearest.getDayOfMonth());
    }

    /**
     * Problem that the conversion of days to strings returns the right result.  Actually, this
     * result depends on the Locale so this test needs to be modified.
     */
    public void testWeekdayCodeToString() {

        final String test = SATURDAY.toString();
        assertEquals("Saturday", test);

    }

    /**
     * Test the conversion of a string to a weekday.  Note that this test will fail if the
     * default locale doesn't use English weekday names...devise a better test!
     */
    public void testStringToWeekday() {

        Day weekday = Day.parse("Wednesday");
        assertEquals(WEDNESDAY, weekday);

        weekday = Day.parse(" Wednesday ");
        assertEquals(WEDNESDAY, weekday);

        weekday = Day.parse("Wed");
        assertEquals(WEDNESDAY, weekday);

    }

    /**
     * Test the conversion of a string to a month.  Note that this test will fail if the default
     * locale doesn't use English month names...devise a better test!
     */
    public void testStringToMonthCode() {

        int m = Month.parse("January").toInt();
        assertEquals(MonthConstants.JANUARY, m);

        m = Month.parse(" January ").toInt();
        assertEquals(MonthConstants.JANUARY, m);

        m = Month.parse("Jan").toInt();
        assertEquals(MonthConstants.JANUARY, m);

    }

    /**
     * Tests the conversion of a month code to a string.
     */
    public void testMonthCodeToStringCode() {

        final String test = DECEMBER.toString();
        assertEquals("December", test);

    }

    /**
     * 1900 is not a leap year.
     */
    public void testIsNotLeapYear1900() {
        assertFalse(isLeapYear(1900));
    }

    /**
     * 2000 is a leap year.
     */
    public void testIsLeapYear2000() {
        assertTrue(isLeapYear(2000));
    }

    /**
     * The number of leap years from 1900 up-to-and-including 1899 is 0.
     */
    public void testLeapYearCount1899() {
        assertEquals(leapYearCount(1899), 0);
    }

    /**
     * The number of leap years from 1900 up-to-and-including 1903 is 0.
     */
    public void testLeapYearCount1903() {
        assertEquals(leapYearCount(1903), 0);
    }

    /**
     * The number of leap years from 1900 up-to-and-including 1904 is 1.
     */
    public void testLeapYearCount1904() {
        assertEquals(leapYearCount(1904), 1);
    }

    /**
     * The number of leap years from 1900 up-to-and-including 1999 is 24.
     */
    public void testLeapYearCount1999() {
        assertEquals(leapYearCount(1999), 24);
    }

    /**
     * The number of leap years from 1900 up-to-and-including 2000 is 25.
     */
    public void testLeapYearCount2000() {
        assertEquals(leapYearCount(2000), 25);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        DayDate d1 = DayDateFactory.makeDate(15, 4, 2000);
        DayDate d2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(d1);
            out.close();

            ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()));
            d2 = (DayDate) in.readObject();
            in.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        assertEquals(d1, d2);

    }

    /**
     * A test for bug report 1096282 (now fixed).
     */
    public void test1096282() {
        DayDate d = DayDateFactory.makeDate(29, 2, 2004);
        d = d.plusYears(1);
        DayDate expected = DayDateFactory.makeDate(28, 2, 2005);
        assertTrue(d.isOn(expected));
    }

    /**
     * Miscellaneous tests for the addMonths() method.
     */
    public void testAddMonths() {
        DayDate d1 = DayDateFactory.makeDate(31, 5, 2004);

        DayDate d2 = d1.plusMonths(1);
        assertEquals(30, d2.getDayOfMonth());
        assertEquals(6, d2.getMonth().toInt());
        assertEquals(2004, d2.getYear());

        DayDate d3 = d1.plusMonths(2);
        assertEquals(31, d3.getDayOfMonth());
        assertEquals(7, d3.getMonth().toInt());
        assertEquals(2004, d3.getYear());

        DayDate d4 = d2.plusMonths(1);
        assertEquals(30, d4.getDayOfMonth());
        assertEquals(7, d4.getMonth().toInt());
        assertEquals(2004, d4.getYear());
    }
}
