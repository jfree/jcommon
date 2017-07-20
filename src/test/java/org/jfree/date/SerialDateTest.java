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

/*
 * This class is being updated with the contents of the Listing B-4 BobsSerialDateTest from the book Clean Code.
 * ISBN: 978-0-13-235088-4
 *
 * Hopefully the commits are small enough sou you can track each test and each difference from the book.
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

import static org.jfree.date.SerialDate.*;

/**
 * Some JUnit tests for the {@link SerialDate} class.
 */
public class SerialDateTest extends TestCase {

    /** Date representing November 9. */
    private SerialDate nov9Y2001;

    /**
     * Creates a new test case.
     *
     * @param name  the name.
     */
    public SerialDateTest(final String name) {
        super(name);
    }

    /**
     * Returns a test suite for the JUnit test runner.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(SerialDateTest.class);
    }

    /**
     * Problem set up.
     */
    protected void setUp() {
        this.nov9Y2001 = SerialDate.createInstance(9, MonthConstants.NOVEMBER, 2001);
    }

    /**
     * 9 Nov 2001 plus two months should be 9 Jan 2002.
     */
    public void testAddMonthsTo9Nov2001() {
        final SerialDate jan9Y2002 = SerialDate.addMonths(2, this.nov9Y2001);
        final SerialDate answer = SerialDate.createInstance(9, 1, 2002);
        assertEquals(answer, jan9Y2002);
    }

    /**
     * A test case for a reported bug, now fixed.
     */
    public void testAddMonthsTo5Oct2003() {
        final SerialDate d1 = SerialDate.createInstance(5, MonthConstants.OCTOBER, 2003);
        final SerialDate d2 = SerialDate.addMonths(2, d1);
        assertEquals(d2, SerialDate.createInstance(5, MonthConstants.DECEMBER, 2003));
    }

    /**
     * A test case for a reported bug, now fixed.
     */
    public void testAddMonthsTo1Jan2003() {
        final SerialDate d1 = SerialDate.createInstance(1, MonthConstants.JANUARY, 2003);
        final SerialDate d2 = SerialDate.addMonths(0, d1);
        assertEquals(d2, d1);
    }

    /**
     * Monday preceding Friday 9 November 2001 should be 5 November.
     */
    public void testMondayPrecedingFriday9Nov2001() {
        SerialDate mondayBefore = SerialDate.getPreviousDayOfWeek(
            SerialDate.MONDAY, this.nov9Y2001
        );
        assertEquals(5, mondayBefore.getDayOfMonth());
    }

    /**
     * Monday following Friday 9 November 2001 should be 12 November.
     */
    public void testMondayFollowingFriday9Nov2001() {
        SerialDate mondayAfter = SerialDate.getFollowingDayOfWeek(
            SerialDate.MONDAY, this.nov9Y2001
        );
        assertEquals(12, mondayAfter.getDayOfMonth());
    }

    /**
     * Monday nearest Friday 9 November 2001 should be 12 November.
     */
    public void testMondayNearestFriday9Nov2001() {
        SerialDate mondayNearest = SerialDate.getNearestDayOfWeek(
            SerialDate.MONDAY, this.nov9Y2001
        );
        assertEquals(12, mondayNearest.getDayOfMonth());
    }

    /**
     * The Monday nearest to 22nd January 1970 falls on the 19th.
     */
    public void testMondayNearest22Jan1970() {
        SerialDate jan22Y1970 = SerialDate.createInstance(22, MonthConstants.JANUARY, 1970);
        SerialDate mondayNearest = SerialDate.getNearestDayOfWeek(SerialDate.MONDAY, jan22Y1970);
        assertEquals(19, mondayNearest.getDayOfMonth());
    }

    /**
     * Tests the conversion of a month code to a string.
     */
    public void testMonthCodeToStringCode() {

        final String test = SerialDate.monthCodeToString(MonthConstants.DECEMBER);
        assertEquals("December", test);

    }

    /**
     * 1900 is not a leap year.
     */
    public void testIsNotLeapYear1900() {
        assertTrue(!SerialDate.isLeapYear(1900));
    }

    /**
     * 2000 is a leap year.
     */
    public void testIsLeapYear2000() {
        assertTrue(SerialDate.isLeapYear(2000));
    }

    /**
     * The number of leap years from 1900 up-to-and-including 1899 is 0.
     */
    public void testLeapYearCount1899() {
        assertEquals(SerialDate.leapYearCount(1899), 0);
    }

    /**
     * The number of leap years from 1900 up-to-and-including 1903 is 0.
     */
    public void testLeapYearCount1903() {
        assertEquals(SerialDate.leapYearCount(1903), 0);
    }

    /**
     * The number of leap years from 1900 up-to-and-including 1904 is 1.
     */
    public void testLeapYearCount1904() {
        assertEquals(SerialDate.leapYearCount(1904), 1);
    }

    /**
     * The number of leap years from 1900 up-to-and-including 1999 is 24.
     */
    public void testLeapYearCount1999() {
        assertEquals(SerialDate.leapYearCount(1999), 24);
    }

    /**
     * The number of leap years from 1900 up-to-and-including 2000 is 25.
     */
    public void testLeapYearCount2000() {
        assertEquals(SerialDate.leapYearCount(2000), 25);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        SerialDate d1 = SerialDate.createInstance(15, 4, 2000);
        SerialDate d2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(d1);
            out.close();

            ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()));
            d2 = (SerialDate) in.readObject();
            in.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        assertEquals(d1, d2);

    }

    /**
     * A test for bug report 1096282 (now fixed).
     */
    public void test1096282() {
        SerialDate d = SerialDate.createInstance(29, 2, 2004);
        d = SerialDate.addYears(1, d);
        SerialDate expected = SerialDate.createInstance(28, 2, 2005);
        assertTrue(d.isOn(expected));
    }

    /**
     * Miscellaneous tests for the addMonths() method.
     */
    public void testAddMonths() {
        SerialDate d1 = SerialDate.createInstance(31, 5, 2004);

        SerialDate d2 = SerialDate.addMonths(1, d1);
        assertEquals(30, d2.getDayOfMonth());
        assertEquals(6, d2.getMonth());
        assertEquals(2004, d2.getYYYY());

        SerialDate d3 = SerialDate.addMonths(2, d1);
        assertEquals(31, d3.getDayOfMonth());
        assertEquals(7, d3.getMonth());
        assertEquals(2004, d3.getYYYY());

        SerialDate d4 = SerialDate.addMonths(1, SerialDate.addMonths(1, d1));
        assertEquals(30, d4.getDayOfMonth());
        assertEquals(7, d4.getMonth());
        assertEquals(2004, d4.getYYYY());
    }

    public void testIsValidWeekdayCode() throws Exception {
        for (int day = 1; day <= 7; day++) {
            assertTrue(isValidWeekdayCode(day));
        }
        assertFalse(isValidWeekdayCode(0));
        assertFalse(isValidWeekdayCode(8));
    }

    /**
     * This test will fail if the default locale doesn't use English weekday names. Devise a better test!
     */
    public void testStringToWeekdayCode() throws Exception {
        assertEquals(-1, stringToWeekdayCode("Hello"));

        assertEquals(MONDAY, stringToWeekdayCode("Monday"));
        assertEquals(MONDAY, stringToWeekdayCode(" Monday "));
        assertEquals(MONDAY, stringToWeekdayCode("Mon"));
        //TODO assertEquals(MONDAY,stringToWeekdayCode("monday"));
        //assertEquals(MONDAY,stringToWeekdayCode("MONDAY"));
        //assertEquals(MONDAY, stringToWeekdayCode("mon"));

        assertEquals(TUESDAY, stringToWeekdayCode("Tuesday"));
        assertEquals(TUESDAY, stringToWeekdayCode(" Tuesday "));
        assertEquals(TUESDAY, stringToWeekdayCode("Tue"));
        //assertEquals(TUESDAY,stringToWeekdayCode("tuesday"));
        //assertEquals(TUESDAY,stringToWeekdayCode("TUESDAY"));
        //assertEquals(TUESDAY, stringToWeekdayCode("tue"));
        //assertEquals(TUESDAY, stringToWeekdayCode("tues"));

        assertEquals(WEDNESDAY, stringToWeekdayCode("Wednesday"));
        assertEquals(WEDNESDAY, stringToWeekdayCode(" Wednesday "));
        assertEquals(WEDNESDAY, stringToWeekdayCode("Wed"));
        //assertEquals(WEDNESDAY,stringToWeekdayCode("wednesday"));
        //assertEquals(WEDNESDAY,stringToWeekdayCode("WEDNESDAY"));
        //assertEquals(WEDNESDAY, stringToWeekdayCode("wed"));

        assertEquals(THURSDAY, stringToWeekdayCode("Thursday"));
        assertEquals(THURSDAY, stringToWeekdayCode(" Thursday "));
        assertEquals(THURSDAY, stringToWeekdayCode("Thu"));
        //assertEquals(THURSDAY,stringToWeekdayCode("thursday"));
        //assertEquals(THURSDAY,stringToWeekdayCode("THURSDAY"));
        //assertEquals(THURSDAY, stringToWeekdayCode("thu"));
        //assertEquals(THURSDAY, stringToWeekdayCode("thurs"));

        assertEquals(FRIDAY, stringToWeekdayCode("Friday"));
        assertEquals(FRIDAY, stringToWeekdayCode(" Friday "));
        assertEquals(FRIDAY, stringToWeekdayCode("Fri"));
        //assertEquals(FRIDAY,stringToWeekdayCode("friday"));
        //assertEquals(FRIDAY,stringToWeekdayCode("FRIDAY"));
        //assertEquals(FRIDAY, stringToWeekdayCode("fri"));

        assertEquals(SATURDAY, stringToWeekdayCode("Saturday"));
        assertEquals(SATURDAY, stringToWeekdayCode(" Saturday "));
        assertEquals(SATURDAY, stringToWeekdayCode("Sat"));
        //assertEquals(SATURDAY,stringToWeekdayCode("saturday"));
        //assertEquals(SATURDAY,stringToWeekdayCode("SATURDAY"));
        //assertEquals(SATURDAY, stringToWeekdayCode("sat"));

        assertEquals(SUNDAY, stringToWeekdayCode("Sunday"));
        assertEquals(SUNDAY, stringToWeekdayCode(" Sunday "));
        assertEquals(SUNDAY, stringToWeekdayCode("Sun"));
        //assertEquals(SUNDAY,stringToWeekdayCode("sunday"));
        //assertEquals(SUNDAY,stringToWeekdayCode("SUNDAY"));
        //assertEquals(SUNDAY, stringToWeekdayCode("sun"));
    }

    /**
     * Depends on the Locale so this test needs to be modified.
     */
    public void testWeekdayCodeToString() throws Exception {
        assertEquals("Sunday", weekdayCodeToString(SUNDAY));
        assertEquals("Monday", weekdayCodeToString(MONDAY));
        assertEquals("Tuesday", weekdayCodeToString(TUESDAY));
        assertEquals("Wednesday", weekdayCodeToString(WEDNESDAY));
        assertEquals("Thursday", weekdayCodeToString(THURSDAY));
        assertEquals("Friday", weekdayCodeToString(FRIDAY));
        assertEquals("Saturday", weekdayCodeToString(SATURDAY));
    }

    public void testIsValidMonthCode() throws Exception {
        for (int i = 1; i <= 12; i++) {
            assertTrue(isValidMonthCode(i));
        }
        assertFalse(isValidMonthCode(0));
        assertFalse(isValidMonthCode(13));
    }

    public void testMonthCodeToQuarter() throws Exception {
        assertEquals(1, monthCodeToQuarter(JANUARY));
        assertEquals(1, monthCodeToQuarter(FEBRUARY));
        assertEquals(1, monthCodeToQuarter(MARCH));
        assertEquals(2, monthCodeToQuarter(APRIL));
        assertEquals(2, monthCodeToQuarter(MAY));
        assertEquals(2, monthCodeToQuarter(JUNE));
        assertEquals(3, monthCodeToQuarter(JULY));
        assertEquals(3, monthCodeToQuarter(AUGUST));
        assertEquals(3, monthCodeToQuarter(SEPTEMBER));
        assertEquals(4, monthCodeToQuarter(OCTOBER));
        assertEquals(4, monthCodeToQuarter(NOVEMBER));
        assertEquals(4, monthCodeToQuarter(DECEMBER));

        try {
            monthCodeToQuarter(-1);
            fail("Invalid Month Code should throw exception");
        } catch (IllegalArgumentException e) {
        }
        try {
            monthCodeToQuarter(0);
            fail("Invalid Month Code should throw exception");
        } catch (IllegalArgumentException e) {
        }
        try {
            monthCodeToQuarter(13);
            fail("Invalid Month Code should throw exception");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * This test will fail if the default locale doesn't use English month names. Devise a better test!
     */
    public void testMonthCodeToString() throws Exception {
        assertEquals("January", monthCodeToString(JANUARY));
        assertEquals("February", monthCodeToString(FEBRUARY));
        assertEquals("March", monthCodeToString(MARCH));
        assertEquals("April", monthCodeToString(APRIL));
        assertEquals("May", monthCodeToString(MAY));
        assertEquals("June", monthCodeToString(JUNE));
        assertEquals("July", monthCodeToString(JULY));
        assertEquals("August", monthCodeToString(AUGUST));
        assertEquals("September", monthCodeToString(SEPTEMBER));
        assertEquals("October", monthCodeToString(OCTOBER));
        assertEquals("November", monthCodeToString(NOVEMBER));
        assertEquals("December", monthCodeToString(DECEMBER));

        assertEquals("Jan", monthCodeToString(JANUARY, true));
        assertEquals("Feb", monthCodeToString(FEBRUARY, true));
        assertEquals("Mar", monthCodeToString(MARCH, true));
        assertEquals("Apr", monthCodeToString(APRIL, true));
        assertEquals("May", monthCodeToString(MAY, true));
        assertEquals("Jun", monthCodeToString(JUNE, true));
        assertEquals("Jul", monthCodeToString(JULY, true));
        assertEquals("Aug", monthCodeToString(AUGUST, true));
        assertEquals("Sep", monthCodeToString(SEPTEMBER, true));
        assertEquals("Oct", monthCodeToString(OCTOBER, true));
        assertEquals("Nov", monthCodeToString(NOVEMBER, true));
        assertEquals("Dec", monthCodeToString(DECEMBER, true));

        try {
            monthCodeToString(-1);
            fail("Invalid month code should throw exception");
        } catch (IllegalArgumentException e) {
        }
        try {
            monthCodeToString(0);
            fail("Invalid month code should throw exception");
        } catch (IllegalArgumentException e) {
        }
        try {
            monthCodeToString(13);
            fail("Invalid month code should throw exception");
        } catch (IllegalArgumentException e) {
        }
    }

    public void testStringToMonthCode() throws Exception {
        assertEquals(JANUARY,stringToMonthCode("1"));
        assertEquals(FEBRUARY,stringToMonthCode("2"));
        assertEquals(MARCH,stringToMonthCode("3"));
        assertEquals(APRIL,stringToMonthCode("4"));
        assertEquals(MAY,stringToMonthCode("5"));
        assertEquals(JUNE,stringToMonthCode("6"));
        assertEquals(JULY,stringToMonthCode("7"));
        assertEquals(AUGUST,stringToMonthCode("8"));
        assertEquals(SEPTEMBER,stringToMonthCode("9"));
        assertEquals(OCTOBER,stringToMonthCode("10"));
        assertEquals(NOVEMBER, stringToMonthCode("11"));
        assertEquals(DECEMBER,stringToMonthCode("12"));

        //TODO assertEquals(-1, stringToMonthCode("0"));
        //assertEquals(-1, stringToMonthCode("13"));

        assertEquals(-1,stringToMonthCode("Hello"));

        for (int m = 1; m <= 12; m++) {
            assertEquals(m, stringToMonthCode(monthCodeToString(m, false)));
            assertEquals(m, stringToMonthCode(monthCodeToString(m, true)));
        }

        assertEquals(JANUARY,stringToMonthCode("January"));
        assertEquals(FEBRUARY,stringToMonthCode("February"));
        assertEquals(MARCH,stringToMonthCode("March"));
        assertEquals(APRIL,stringToMonthCode("April"));
        assertEquals(MAY,stringToMonthCode("May"));
        assertEquals(JUNE,stringToMonthCode("June"));
        assertEquals(JULY,stringToMonthCode("July"));
        assertEquals(AUGUST,stringToMonthCode("August"));
        assertEquals(SEPTEMBER,stringToMonthCode("September"));
        assertEquals(OCTOBER,stringToMonthCode("October"));
        assertEquals(NOVEMBER,stringToMonthCode("November"));
        assertEquals(DECEMBER,stringToMonthCode("December"));

        assertEquals(JANUARY,stringToMonthCode(" January "));
        assertEquals(FEBRUARY,stringToMonthCode(" February "));
        assertEquals(MARCH,stringToMonthCode(" March "));
        assertEquals(APRIL,stringToMonthCode(" April "));
        assertEquals(MAY,stringToMonthCode(" May "));
        assertEquals(JUNE,stringToMonthCode(" June "));
        assertEquals(JULY,stringToMonthCode(" July "));
        assertEquals(AUGUST,stringToMonthCode(" August "));
        assertEquals(SEPTEMBER,stringToMonthCode(" September "));
        assertEquals(OCTOBER,stringToMonthCode(" October "));
        assertEquals(NOVEMBER,stringToMonthCode(" November "));
        assertEquals(DECEMBER,stringToMonthCode(" December "));

        assertEquals(JANUARY,stringToMonthCode("Jan"));
        assertEquals(FEBRUARY,stringToMonthCode("Feb"));
        assertEquals(MARCH,stringToMonthCode("Mar"));
        assertEquals(APRIL,stringToMonthCode("Apr"));
        assertEquals(MAY,stringToMonthCode("May"));
        assertEquals(JUNE,stringToMonthCode("Jun"));
        assertEquals(JULY,stringToMonthCode("Jul"));
        assertEquals(AUGUST,stringToMonthCode("Aug"));
        assertEquals(SEPTEMBER,stringToMonthCode("Sep"));
        assertEquals(OCTOBER,stringToMonthCode("Oct"));
        assertEquals(NOVEMBER, stringToMonthCode("Nov"));
        assertEquals(DECEMBER,stringToMonthCode("Dec"));

        //assertEquals(1,stringToMonthCode("jan"));
        //assertEquals(2,stringToMonthCode("feb"));
        //assertEquals(3,stringToMonthCode("mar"));
        //assertEquals(4,stringToMonthCode("apr"));
        //assertEquals(5,stringToMonthCode("may"));
        //assertEquals(6,stringToMonthCode("jun"));
        //assertEquals(7,stringToMonthCode("jul"));
        //assertEquals(8,stringToMonthCode("aug"));
        //assertEquals(9,stringToMonthCode("sep"));
        //assertEquals(10,stringToMonthCode("oct"));
        //assertEquals(11,stringToMonthCode("nov"));
        //assertEquals(12,stringToMonthCode("dec"));

        //assertEquals(1,stringToMonthCode("JAN"));
        //assertEquals(2,stringToMonthCode("FEB"));
        //assertEquals(3,stringToMonthCode("MAR"));
        //assertEquals(4,stringToMonthCode("APR"));
        //assertEquals(5,stringToMonthCode("MAY"));
        //assertEquals(6,stringToMonthCode("JUN"));
        //assertEquals(7,stringToMonthCode("JUL"));
        //assertEquals(8,stringToMonthCode("AUG"));
        //assertEquals(9,stringToMonthCode("SEP"));
        //assertEquals(10,stringToMonthCode("OCT"));
        //assertEquals(11,stringToMonthCode("NOV"));
        //assertEquals(12,stringToMonthCode("DEC"));

        //assertEquals(1,stringToMonthCode("january"));
        //assertEquals(2,stringToMonthCode("february"));
        //assertEquals(3,stringToMonthCode("march"));
        //assertEquals(4,stringToMonthCode("april"));
        //assertEquals(5,stringToMonthCode("may"));
        //assertEquals(6,stringToMonthCode("june"));
        //assertEquals(7,stringToMonthCode("july"));
        //assertEquals(8,stringToMonthCode("august"));
        //assertEquals(9,stringToMonthCode("september"));
        //assertEquals(10,stringToMonthCode("october"));
        //assertEquals(11,stringToMonthCode("november"));
        //assertEquals(12,stringToMonthCode("december"));

        //assertEquals(1,stringToMonthCode(" january "));
        //assertEquals(2,stringToMonthCode(" february "));
        //assertEquals(3,stringToMonthCode(" march "));
        //assertEquals(4,stringToMonthCode(" april "));
        //assertEquals(5,stringToMonthCode(" may "));
        //assertEquals(6,stringToMonthCode(" june "));
        //assertEquals(7,stringToMonthCode(" july "));
        //assertEquals(8,stringToMonthCode(" august "));
        //assertEquals(9,stringToMonthCode(" september "));
        //assertEquals(10,stringToMonthCode(" october "));
        //assertEquals(11,stringToMonthCode(" november "));
        //assertEquals(12,stringToMonthCode(" december "));

        //assertEquals(1,stringToMonthCode("JANUARY"));
        //assertEquals(2,stringToMonthCode("FEBRUARY"));
        //assertEquals(3,stringToMonthCode("MAR"));
        //assertEquals(4,stringToMonthCode("APRIL"));
        //assertEquals(5,stringToMonthCode("MAY"));
        //assertEquals(6,stringToMonthCode("JUNE"));
        //assertEquals(7,stringToMonthCode("JULY"));
        //assertEquals(8,stringToMonthCode("AUGUST"));
        //assertEquals(9,stringToMonthCode("SEPTEMBER"));
        //assertEquals(10,stringToMonthCode("OCTOBER"));
        //assertEquals(11,stringToMonthCode("NOVEMBER"));
        //assertEquals(12,stringToMonthCode("DECEMBER"));
    }
}
