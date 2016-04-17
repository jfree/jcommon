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
 * Contributor(s):   Robert C. Martin (through the book: Clean Code);
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
 * This class is being updated with the contents of the section: "First, Make It Work" page 268 of the book Clean Code.
 *
 * This class is now updated with the contents of the Listing B-4 BobsSerialDateTest from the book Clean Code.
 * ISBN: 978-0-13-235088-4
 *
 * There are minor differences though, because this is a production environment. I wanted to keep the conventions of the
 * package as much as possible. There are test cases which I kept, there were some wording issues I corrected and also
 * there were stuff I just simply preferred in my way.
 *
 * The class is of course not called BobsSerialDateTest. It should match nicely with the code you can find in the book.
 * Additionally, I split the additions into many commits so it can be read well too. I hope.
 *
 */

package org.jfree.date;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.GregorianCalendar;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import static org.jfree.date.SerialDate.*;

/**
 * Some JUnit tests for the {@link SerialDate} class.
 */
public class SerialDateTest extends TestCase {
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

    public void testIsValidWeekdayCode() {
        for (int day = 1; day <= 7; day++) {
            assertTrue(isValidWeekdayCode(day));
        }
        assertFalse(isValidWeekdayCode(0));
        assertFalse(isValidWeekdayCode(8));
    }

    /**
     * This test will fail if the default locale doesn't use English weekday names. Devise a better test!
     */
    public void testStringToWeekdayCode() {
        assertEquals(-1, stringToWeekdayCode("Hello"));

        assertEquals(MONDAY, stringToWeekdayCode("Monday"));
        assertEquals(MONDAY, stringToWeekdayCode(" Monday "));
        assertEquals(MONDAY, stringToWeekdayCode("Mon"));
        assertEquals(MONDAY, stringToWeekdayCode("monday"));
        assertEquals(MONDAY, stringToWeekdayCode("MONDAY"));
        assertEquals(MONDAY, stringToWeekdayCode("mon"));

        assertEquals(TUESDAY, stringToWeekdayCode("Tuesday"));
        assertEquals(TUESDAY, stringToWeekdayCode(" Tuesday "));
        assertEquals(TUESDAY, stringToWeekdayCode("Tue"));
        assertEquals(TUESDAY, stringToWeekdayCode("tuesday"));
        assertEquals(TUESDAY, stringToWeekdayCode("TUESDAY"));
        assertEquals(TUESDAY, stringToWeekdayCode("tue"));

        assertEquals(WEDNESDAY, stringToWeekdayCode("Wednesday"));
        assertEquals(WEDNESDAY, stringToWeekdayCode(" Wednesday "));
        assertEquals(WEDNESDAY, stringToWeekdayCode("Wed"));
        assertEquals(WEDNESDAY, stringToWeekdayCode("wednesday"));
        assertEquals(WEDNESDAY, stringToWeekdayCode("WEDNESDAY"));
        assertEquals(WEDNESDAY, stringToWeekdayCode("wed"));

        assertEquals(THURSDAY, stringToWeekdayCode("Thursday"));
        assertEquals(THURSDAY, stringToWeekdayCode(" Thursday "));
        assertEquals(THURSDAY, stringToWeekdayCode("Thu"));
        assertEquals(THURSDAY, stringToWeekdayCode("thursday"));
        assertEquals(THURSDAY, stringToWeekdayCode("THURSDAY"));
        assertEquals(THURSDAY, stringToWeekdayCode("thu"));

        assertEquals(FRIDAY, stringToWeekdayCode("Friday"));
        assertEquals(FRIDAY, stringToWeekdayCode(" Friday "));
        assertEquals(FRIDAY, stringToWeekdayCode("Fri"));
        assertEquals(FRIDAY, stringToWeekdayCode("friday"));
        assertEquals(FRIDAY, stringToWeekdayCode("FRIDAY"));
        assertEquals(FRIDAY, stringToWeekdayCode("fri"));

        assertEquals(SATURDAY, stringToWeekdayCode("Saturday"));
        assertEquals(SATURDAY, stringToWeekdayCode(" Saturday "));
        assertEquals(SATURDAY, stringToWeekdayCode("Sat"));
        assertEquals(SATURDAY, stringToWeekdayCode("saturday"));
        assertEquals(SATURDAY, stringToWeekdayCode("SATURDAY"));
        assertEquals(SATURDAY, stringToWeekdayCode("sat"));

        assertEquals(SUNDAY, stringToWeekdayCode("Sunday"));
        assertEquals(SUNDAY, stringToWeekdayCode(" Sunday "));
        assertEquals(SUNDAY, stringToWeekdayCode("Sun"));
        assertEquals(SUNDAY, stringToWeekdayCode("sunday"));
        assertEquals(SUNDAY, stringToWeekdayCode("SUNDAY"));
        assertEquals(SUNDAY, stringToWeekdayCode("sun"));
    }

    /**
     * Depends on the Locale so this test needs to be modified.
     */
    public void testWeekdayCodeToString() {
        assertEquals("Sunday", weekdayCodeToString(SUNDAY));
        assertEquals("Monday", weekdayCodeToString(MONDAY));
        assertEquals("Tuesday", weekdayCodeToString(TUESDAY));
        assertEquals("Wednesday", weekdayCodeToString(WEDNESDAY));
        assertEquals("Thursday", weekdayCodeToString(THURSDAY));
        assertEquals("Friday", weekdayCodeToString(FRIDAY));
        assertEquals("Saturday", weekdayCodeToString(SATURDAY));
    }

    public void testIsValidMonthCode() {
        for (int i = 1; i <= 12; i++) {
            assertTrue(isValidMonthCode(i));
        }
        assertFalse(isValidMonthCode(0));
        assertFalse(isValidMonthCode(13));
    }

    public void testMonthCodeToQuarter() {
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
        }
        catch (IllegalArgumentException e) {
        }
        try {
            monthCodeToQuarter(0);
            fail("Invalid Month Code should throw exception");
        }
        catch (IllegalArgumentException e) {
        }
        try {
            monthCodeToQuarter(13);
            fail("Invalid Month Code should throw exception");
        }
        catch (IllegalArgumentException e) {
        }
    }

    /**
     * This test will fail if the default locale doesn't use English month names. Devise a better test!
     */
    public void testMonthCodeToString() {
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
        }
        catch (IllegalArgumentException e) {
        }
        try {
            monthCodeToString(0);
            fail("Invalid month code should throw exception");
        }
        catch (IllegalArgumentException e) {
        }
        try {
            monthCodeToString(13);
            fail("Invalid month code should throw exception");
        }
        catch (IllegalArgumentException e) {
        }
    }

    public void testStringToMonthCode() {
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

    public void testIsValidWeekInMonthCode() {
        for (int w = 0; w <= 4; w++) {
            assertTrue(isValidWeekInMonthCode(w));
        }
        assertFalse(isValidWeekInMonthCode(5));
    }

    public void testIsLeapYear() {
        assertFalse(isLeapYear(1900));
        assertFalse(isLeapYear(1901));
        assertFalse(isLeapYear(1902));
        assertFalse(isLeapYear(1903));
        assertTrue(isLeapYear(1904));
        assertTrue(isLeapYear(1908));
        assertFalse(isLeapYear(1955));
        assertTrue(isLeapYear(1964));
        assertTrue(isLeapYear(1980));
        assertTrue(isLeapYear(2000));
        assertFalse(isLeapYear(2001));
        assertFalse(isLeapYear(2100));
    }

    public void testLeapYearCount() {
        assertEquals(0, leapYearCount(1899));
        assertEquals(0, leapYearCount(1900));
        assertEquals(0, leapYearCount(1901));
        assertEquals(0, leapYearCount(1902));
        assertEquals(0, leapYearCount(1903));
        assertEquals(1, leapYearCount(1904));
        assertEquals(1, leapYearCount(1905));
        assertEquals(1, leapYearCount(1906));
        assertEquals(1, leapYearCount(1907));
        assertEquals(2, leapYearCount(1908));
        assertEquals(24, leapYearCount(1999));
        assertEquals(25, leapYearCount(2000));
        assertEquals(25, leapYearCount(2001));
        assertEquals(49, leapYearCount(2101));
        assertEquals(73, leapYearCount(2201));
        assertEquals(97, leapYearCount(2301));
        assertEquals(122, leapYearCount(2401));
    }

    public void testLastDayOfMonth() {
        assertEquals(31, lastDayOfMonth(JANUARY, 1901));
        assertEquals(28, lastDayOfMonth(FEBRUARY, 1901));
        assertEquals(31, lastDayOfMonth(MARCH, 1901));
        assertEquals(30, lastDayOfMonth(APRIL, 1901));
        assertEquals(31, lastDayOfMonth(MAY, 1901));
        assertEquals(30, lastDayOfMonth(JUNE, 1901));
        assertEquals(31, lastDayOfMonth(JULY, 1901));
        assertEquals(31, lastDayOfMonth(AUGUST, 1901));
        assertEquals(30, lastDayOfMonth(SEPTEMBER, 1901));
        assertEquals(31, lastDayOfMonth(OCTOBER, 1901));
        assertEquals(30, lastDayOfMonth(NOVEMBER, 1901));
        assertEquals(31, lastDayOfMonth(DECEMBER, 1901));
        assertEquals(29, lastDayOfMonth(FEBRUARY, 1904));
    }

    public void testAddDays() {
        SerialDate newYears = d(1, JANUARY, 1900);
        assertEquals(d(2, JANUARY, 1900), addDays(1, newYears));
        assertEquals(d(1, FEBRUARY, 1900), addDays(31, newYears));
        assertEquals(d(1, JANUARY, 1901), addDays(365, newYears));
        assertEquals(d(31, DECEMBER, 1904), addDays(5 * 365, newYears));
    }

    private static SpreadsheetDate d(int day, int month, int year) {
        return new SpreadsheetDate(day, month, year);
    }

    public void testAddMonths() {
        assertEquals(d(1, JANUARY, 2003), addMonths(0, d(1, JANUARY, 2003)));

        assertEquals(d(1, FEBRUARY, 1900), addMonths(1, d(1, JANUARY, 1900)));
        assertEquals(d(28, FEBRUARY, 1900), addMonths(1, d(31, JANUARY, 1900)));
        assertEquals(d(28, FEBRUARY, 1900), addMonths(1, d(30, JANUARY, 1900)));
        assertEquals(d(28, FEBRUARY, 1900), addMonths(1, d(29, JANUARY, 1900)));
        assertEquals(d(28, FEBRUARY, 1900), addMonths(1, d(28, JANUARY, 1900)));
        assertEquals(d(27, FEBRUARY, 1900), addMonths(1, d(27, JANUARY, 1900)));

        assertEquals(d(9, JANUARY, 2002), addMonths(2, d(9, NOVEMBER, 2001)));
        assertEquals(d(5, DECEMBER, 2003), addMonths(2, d(5, OCTOBER, 2003)));

        assertEquals(d(30, JUNE, 1900), addMonths(5, d(31, JANUARY, 1900)));
        assertEquals(d(30, JUNE, 1901), addMonths(17, d(31, JANUARY, 1900)));

        assertEquals(d(29, FEBRUARY, 1904), addMonths(49, d(31, JANUARY, 1900)));

        assertEquals(d(30, JUNE, 2004), addMonths(1, d(31, MAY, 2004)));
        assertEquals(d(31, JULY, 2004), addMonths(2, d(31, MAY, 2004)));
        assertEquals(d(30, JULY, 2004), addOneMonthTwice(d(31, MAY, 2004)));
    }

    private SerialDate addOneMonthTwice(SerialDate d) {
        return addMonths(1, addMonths(1, d));
    }

    public void testAddYears() {
        assertEquals(d(1, JANUARY, 1901), addYears(1, d(1, JANUARY, 1900)));
        assertEquals(d(28, FEBRUARY, 1905), addYears(1, d(29, FEBRUARY, 1904)));
        assertEquals(d(28, FEBRUARY, 1905), addYears(1, d(28, FEBRUARY, 1904)));
        assertEquals(d(28, FEBRUARY, 1904), addYears(1, d(28, FEBRUARY, 1903)));

        // TODO Verify this isOn method, if it is really necessary here or not.
        // A test for bug report 1096282 (now fixed).
        assertTrue(addYears(1, d(29, FEBRUARY, 2004)).isOn(d(28, FEBRUARY, 2005)));
    }

    public void testGetPreviousDayOfWeek() {
        assertEquals(d(5, NOVEMBER, 2001), getPreviousDayOfWeek(MONDAY, d(9, NOVEMBER, 2001)));

        assertEquals(d(24, FEBRUARY, 2006), getPreviousDayOfWeek(FRIDAY, d(1, MARCH, 2006)));
        assertEquals(d(22, FEBRUARY, 2006), getPreviousDayOfWeek(WEDNESDAY, d(1, MARCH, 2006)));
        assertEquals(d(29, FEBRUARY, 2004), getPreviousDayOfWeek(SUNDAY, d(3, MARCH, 2004)));
        assertEquals(d(29, DECEMBER, 2004), getPreviousDayOfWeek(WEDNESDAY, d(5, JANUARY, 2005)));

        try {
            getPreviousDayOfWeek(-1, d(1, JANUARY, 2006));
            fail("Invalid day of week code should throw exception");
        }
        catch (IllegalArgumentException e) {
        }
    }

    public void testGetFollowingDayOfWeek() {
        assertEquals(d(12, NOVEMBER, 2001), getFollowingDayOfWeek(MONDAY, d(9, NOVEMBER, 2001)));

        //assertEquals(d(1, JANUARY, 2005),getFollowingDayOfWeek(SATURDAY, d(25, DECEMBER, 2004)));
        assertEquals(d(1, JANUARY, 2005), getFollowingDayOfWeek(SATURDAY, d(26, DECEMBER, 2004)));
        assertEquals(d(3, MARCH, 2004), getFollowingDayOfWeek(WEDNESDAY, d(28, FEBRUARY, 2004)));

        try {
            getFollowingDayOfWeek(-1, d(1, JANUARY, 2006));
            fail("Invalid day of week code should throw exception");
        }
        catch (IllegalArgumentException e) {
        }
    }

    public void testGetNearestDayOfWeek() {
        assertEquals(d(12, NOVEMBER, 2001), getNearestDayOfWeek(MONDAY, d(9, NOVEMBER, 2001)));
        assertEquals(d(19, JANUARY, 1970), getNearestDayOfWeek(MONDAY, d(22, JANUARY, 1970)));

        assertEquals(d(16, APRIL, 2006), getNearestDayOfWeek(SUNDAY, d(16, APRIL, 2006)));
        assertEquals(d(16, APRIL, 2006), getNearestDayOfWeek(SUNDAY, d(17, APRIL, 2006)));
        assertEquals(d(16, APRIL, 2006), getNearestDayOfWeek(SUNDAY, d(18, APRIL, 2006)));
        assertEquals(d(16, APRIL, 2006), getNearestDayOfWeek(SUNDAY, d(19, APRIL, 2006)));
        assertEquals(d(23, APRIL, 2006), getNearestDayOfWeek(SUNDAY, d(20, APRIL, 2006)));
        assertEquals(d(23, APRIL, 2006), getNearestDayOfWeek(SUNDAY, d(21, APRIL, 2006)));
        assertEquals(d(23, APRIL, 2006), getNearestDayOfWeek(SUNDAY, d(22, APRIL, 2006)));

        //TODO assertEquals(d(17, APRIL, 2006), getNearestDayOfWeek(MONDAY, d(16, APRIL, 2006)));
        assertEquals(d(17, APRIL, 2006), getNearestDayOfWeek(MONDAY, d(17, APRIL, 2006)));
        assertEquals(d(17, APRIL, 2006), getNearestDayOfWeek(MONDAY, d(18, APRIL, 2006)));
        assertEquals(d(17, APRIL, 2006), getNearestDayOfWeek(MONDAY, d(19, APRIL, 2006)));
        assertEquals(d(17, APRIL, 2006), getNearestDayOfWeek(MONDAY, d(20, APRIL, 2006)));
        assertEquals(d(24, APRIL, 2006), getNearestDayOfWeek(MONDAY, d(21, APRIL, 2006)));
        assertEquals(d(24, APRIL, 2006), getNearestDayOfWeek(MONDAY, d(22, APRIL, 2006)));

        //assertEquals(d(18, APRIL, 2006), getNearestDayOfWeek(TUESDAY, d(16, APRIL, 2006)));
        //assertEquals(d(18, APRIL, 2006), getNearestDayOfWeek(TUESDAY, d(17, APRIL, 2006)));
        assertEquals(d(18, APRIL, 2006), getNearestDayOfWeek(TUESDAY, d(18, APRIL, 2006)));
        assertEquals(d(18, APRIL, 2006), getNearestDayOfWeek(TUESDAY, d(19, APRIL, 2006)));
        assertEquals(d(18, APRIL, 2006), getNearestDayOfWeek(TUESDAY, d(20, APRIL, 2006)));
        assertEquals(d(18, APRIL, 2006), getNearestDayOfWeek(TUESDAY, d(21, APRIL, 2006)));
        assertEquals(d(25, APRIL, 2006), getNearestDayOfWeek(TUESDAY, d(22, APRIL, 2006)));

        //assertEquals(d(19, APRIL, 2006), getNearestDayOfWeek(WEDNESDAY, d(16, APRIL, 2006)));
        //assertEquals(d(19, APRIL, 2006), getNearestDayOfWeek(WEDNESDAY, d(17, APRIL, 2006)));
        //assertEquals(d(19, APRIL, 2006), getNearestDayOfWeek(WEDNESDAY, d(18, APRIL, 2006)));
        assertEquals(d(19, APRIL, 2006), getNearestDayOfWeek(WEDNESDAY, d(19, APRIL, 2006)));
        assertEquals(d(19, APRIL, 2006), getNearestDayOfWeek(WEDNESDAY, d(20, APRIL, 2006)));
        assertEquals(d(19, APRIL, 2006), getNearestDayOfWeek(WEDNESDAY, d(21, APRIL, 2006)));
        assertEquals(d(19, APRIL, 2006), getNearestDayOfWeek(WEDNESDAY, d(22, APRIL, 2006)));

        //assertEquals(d(13, APRIL, 2006), getNearestDayOfWeek(THURSDAY, d(16, APRIL, 2006)));
        //assertEquals(d(20, APRIL, 2006), getNearestDayOfWeek(THURSDAY, d(17, APRIL, 2006)));
        //assertEquals(d(20, APRIL, 2006), getNearestDayOfWeek(THURSDAY, d(18, APRIL, 2006)));
        //assertEquals(d(20, APRIL, 2006), getNearestDayOfWeek(THURSDAY, d(19, APRIL, 2006)));
        assertEquals(d(20, APRIL, 2006), getNearestDayOfWeek(THURSDAY, d(20, APRIL, 2006)));
        assertEquals(d(20, APRIL, 2006), getNearestDayOfWeek(THURSDAY, d(21, APRIL, 2006)));
        assertEquals(d(20, APRIL, 2006), getNearestDayOfWeek(THURSDAY, d(22, APRIL, 2006)));

        //assertEquals(d(14, APRIL, 2006), getNearestDayOfWeek(FRIDAY, d(16, APRIL, 2006)));
        //assertEquals(d(14, APRIL, 2006), getNearestDayOfWeek(FRIDAY, d(17, APRIL, 2006)));
        //assertEquals(d(21, APRIL, 2006), getNearestDayOfWeek(FRIDAY, d(18, APRIL, 2006)));
        //assertEquals(d(21, APRIL, 2006), getNearestDayOfWeek(FRIDAY, d(19, APRIL, 2006)));
        //assertEquals(d(21, APRIL, 2006), getNearestDayOfWeek(FRIDAY, d(20, APRIL, 2006)));
        assertEquals(d(21, APRIL, 2006), getNearestDayOfWeek(FRIDAY, d(21, APRIL, 2006)));
        assertEquals(d(21, APRIL, 2006), getNearestDayOfWeek(FRIDAY, d(22, APRIL, 2006)));

        //assertEquals(d(15, APRIL, 2006), getNearestDayOfWeek(SATURDAY, d(16, APRIL, 2006)));
        //assertEquals(d(15, APRIL, 2006), getNearestDayOfWeek(SATURDAY, d(17, APRIL, 2006)));
        //assertEquals(d(15, APRIL, 2006), getNearestDayOfWeek(SATURDAY, d(18, APRIL, 2006)));
        //assertEquals(d(22, APRIL, 2006), getNearestDayOfWeek(SATURDAY, d(19, APRIL, 2006)));
        //assertEquals(d(22, APRIL, 2006), getNearestDayOfWeek(SATURDAY, d(20, APRIL, 2006)));
        //assertEquals(d(22, APRIL, 2006), getNearestDayOfWeek(SATURDAY, d(21, APRIL, 2006)));
        assertEquals(d(22, APRIL, 2006), getNearestDayOfWeek(SATURDAY, d(22, APRIL, 2006)));

        try {
            getNearestDayOfWeek(-1, d(1, JANUARY, 2006));
            fail("Invalid day of week code should throw exception");
        }
        catch (IllegalArgumentException e) {
        }
    }

    public void testGetEndOfCurrentMonth() {
        SerialDate d = SerialDate.createInstance(2);
        assertEquals(d(31, JANUARY, 2006), d.getEndOfCurrentMonth(d(1, JANUARY, 2006)));
        assertEquals(d(28, FEBRUARY, 2006), d.getEndOfCurrentMonth(d(1, FEBRUARY, 2006)));
        assertEquals(d(31, MARCH, 2006), d.getEndOfCurrentMonth(d(1, MARCH, 2006)));
        assertEquals(d(30, APRIL, 2006), d.getEndOfCurrentMonth(d(1, APRIL, 2006)));
        assertEquals(d(31, MAY, 2006), d.getEndOfCurrentMonth(d(1, MAY, 2006)));
        assertEquals(d(30, JUNE, 2006), d.getEndOfCurrentMonth(d(1, JUNE, 2006)));
        assertEquals(d(31, JULY, 2006), d.getEndOfCurrentMonth(d(1, JULY, 2006)));
        assertEquals(d(31, AUGUST, 2006), d.getEndOfCurrentMonth(d(1, AUGUST, 2006)));
        assertEquals(d(30, SEPTEMBER, 2006), d.getEndOfCurrentMonth(d(1, SEPTEMBER, 2006)));
        assertEquals(d(31, OCTOBER, 2006), d.getEndOfCurrentMonth(d(1, OCTOBER, 2006)));
        assertEquals(d(30, NOVEMBER, 2006), d.getEndOfCurrentMonth(d(1, NOVEMBER, 2006)));
        assertEquals(d(31, DECEMBER, 2006), d.getEndOfCurrentMonth(d(1, DECEMBER, 2006)));
        assertEquals(d(29, FEBRUARY, 2008), d.getEndOfCurrentMonth(d(1, FEBRUARY, 2008)));
    }

    public void testWeekInMonthToString() {
        assertEquals("First",weekInMonthToString(FIRST_WEEK_IN_MONTH));
        assertEquals("Second",weekInMonthToString(SECOND_WEEK_IN_MONTH));
        assertEquals("Third",weekInMonthToString(THIRD_WEEK_IN_MONTH));
        assertEquals("Fourth",weekInMonthToString(FOURTH_WEEK_IN_MONTH));
        assertEquals("Last",weekInMonthToString(LAST_WEEK_IN_MONTH));

        //TODO try {
        //weekInMonthToString(-1);
        //fail("Invalid week code should throw exception");
        //}
        //catch (IllegalArgumentException e) {
        //}
    }

    public void testRelativeToString() {
        assertEquals("Preceding",relativeToString(PRECEDING));
        assertEquals("Nearest",relativeToString(NEAREST));
        assertEquals("Following",relativeToString(FOLLOWING));

        //TODO try {
        //relativeToString(-1000);
        //fail("Invalid relative code should throw exception");
        //}
        //catch (IllegalArgumentException e) {
        //}
    }

    public void testCreateInstanceFromDDMMYYYY() {
        SerialDate date = createInstance(1, JANUARY, 1900);
        assertEquals(1,date.getDayOfMonth());
        assertEquals(JANUARY,date.getMonth());
        assertEquals(1900,date.getYYYY());
        assertEquals(2,date.toSerial());
    }

    public void testCreateInstanceFromSerial() {
        assertEquals(d(1, JANUARY, 1900), createInstance(2));
        assertEquals(d(1, JANUARY, 1901), createInstance(367));
    }

    public void testCreateInstanceFromJavaDate() {
        assertEquals(d(1, JANUARY, 1900),
                createInstance(new GregorianCalendar(1900,0,1).getTime()));
        assertEquals(d(1, JANUARY, 2006),
                createInstance(new GregorianCalendar(2006,0,1).getTime()));
    }
}
