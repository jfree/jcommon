package org.jfree.date;

import junit.framework.TestCase;

import java.util.GregorianCalendar;

import static org.jfree.date.DateUtil.*;
import static org.jfree.date.Day.*;
import static org.jfree.date.Month.*;
import static org.jfree.date.SpreadsheetDate.createInstance;
import static org.jfree.date.WeekInMonth.*;

public class BobsDayDateTest extends TestCase {

    public void testIsValidWeekdayCode() throws Exception {
        for (int day = 1; day <= 7; day++)
            assertEquals(day, Day.fromInt(day).toInt());
    }

    public void testStringToWeekdayCode() throws Exception {

        //assertEquals(-1, Day.parse("Hello").toInt());
        assertEquals(MONDAY, Day.parse("Monday"));
        assertEquals(MONDAY, Day.parse("Mon"));
        //todo    assertEquals(MONDAY,stringToWeekdayCode("monday"));
        //    assertEquals(MONDAY,stringToWeekdayCode("MONDAY"));
        //    assertEquals(MONDAY, stringToWeekdayCode("mon"));

        assertEquals(TUESDAY, Day.parse("Tuesday"));
        assertEquals(TUESDAY, Day.parse("Tue"));
        //    assertEquals(TUESDAY,stringToWeekdayCode("tuesday"));
        //    assertEquals(TUESDAY,stringToWeekdayCode("TUESDAY"));
        //    assertEquals(TUESDAY, stringToWeekdayCode("tue"));
        //    assertEquals(TUESDAY, stringToWeekdayCode("tues"));

        assertEquals(WEDNESDAY, Day.parse("Wednesday"));
        assertEquals(WEDNESDAY, Day.parse("Wed"));
        //    assertEquals(WEDNESDAY,stringToWeekdayCode("wednesday"));
        //    assertEquals(WEDNESDAY,stringToWeekdayCode("WEDNESDAY"));
        //    assertEquals(WEDNESDAY, stringToWeekdayCode("wed"));

        assertEquals(THURSDAY, Day.parse("Thursday"));
        assertEquals(THURSDAY, Day.parse("Thu"));
        //    assertEquals(THURSDAY,stringToWeekdayCode("thursday"));
        //    assertEquals(THURSDAY,stringToWeekdayCode("THURSDAY"));
        //    assertEquals(THURSDAY, stringToWeekdayCode("thu"));
        //    assertEquals(THURSDAY, stringToWeekdayCode("thurs"));

        assertEquals(FRIDAY, Day.parse("Friday"));
        assertEquals(FRIDAY, Day.parse("Fri"));
        //    assertEquals(FRIDAY,stringToWeekdayCode("friday"));
        //    assertEquals(FRIDAY,stringToWeekdayCode("FRIDAY"));
        //    assertEquals(FRIDAY, stringToWeekdayCode("fri"));

        assertEquals(SATURDAY, Day.parse("Saturday"));
        assertEquals(SATURDAY, Day.parse("Sat"));
        //    assertEquals(SATURDAY,stringToWeekdayCode("saturday"));
        //    assertEquals(SATURDAY,stringToWeekdayCode("SATURDAY"));
        //    assertEquals(SATURDAY, stringToWeekdayCode("sat"));

        assertEquals(SUNDAY, Day.parse("Sunday"));
        assertEquals(SUNDAY, Day.parse("Sun"));
        //    assertEquals(SUNDAY,stringToWeekdayCode("sunday"));
        //    assertEquals(SUNDAY,stringToWeekdayCode("SUNDAY"));
        //    assertEquals(SUNDAY, stringToWeekdayCode("sun"));
    }

    public void testWeekdayCodeToString() throws Exception {
        assertEquals("Sunday", SUNDAY.toString());
        assertEquals("Monday", MONDAY.toString());
        assertEquals("Tuesday", TUESDAY.toString());
        assertEquals("Wednesday", WEDNESDAY.toString());
        assertEquals("Thursday", THURSDAY.toString());
        assertEquals("Friday", FRIDAY.toString());
        assertEquals("Saturday", SATURDAY.toString());
    }

    public void testIsValidMonthCode() throws Exception {
        for (int i = 1; i <= 12; i++)
            assertEquals(i, Month.fromInt(i).toInt());
//        assertFalse(isValidMonthCode(0));
//        assertFalse(isValidMonthCode(13));
    }

    public void testMonthToQuarter() throws Exception {
        assertEquals(1, JANUARY.quarter());
        assertEquals(1, FEBRUARY.quarter());
        assertEquals(1, MARCH.quarter());
        assertEquals(2, APRIL.quarter());
        assertEquals(2, MAY.quarter());
        assertEquals(2, JUNE.quarter());
        assertEquals(3, JULY.quarter());
        assertEquals(3, AUGUST.quarter());
        assertEquals(3, SEPTEMBER.quarter());
        assertEquals(4, OCTOBER.quarter());
        assertEquals(4, NOVEMBER.quarter());
        assertEquals(4, DECEMBER.quarter());
    }

    public void testMonthCodeToString() throws Exception {
        assertEquals("January", JANUARY.toString());
        assertEquals("February", FEBRUARY.toString());
        assertEquals("March", MARCH.toString());
        assertEquals("April", APRIL.toString());
        assertEquals("May", MAY.toString());
        assertEquals("June", JUNE.toString());
        assertEquals("July", JULY.toString());
        assertEquals("August", AUGUST.toString());
        assertEquals("September", SEPTEMBER.toString());
        assertEquals("October", OCTOBER.toString());
        assertEquals("November", NOVEMBER.toString());
        assertEquals("December", DECEMBER.toString());

        assertEquals("Jan", JANUARY.toShortString());
        assertEquals("Feb", FEBRUARY.toShortString());
        assertEquals("Mar", MARCH.toShortString());
        assertEquals("Apr", APRIL.toShortString());
        assertEquals("May", MAY.toShortString());
        assertEquals("Jun", JUNE.toShortString());
        assertEquals("Jul", JULY.toShortString());
        assertEquals("Aug", AUGUST.toShortString());
        assertEquals("Sep", SEPTEMBER.toShortString());
        assertEquals("Oct", OCTOBER.toShortString());
        assertEquals("Nov", NOVEMBER.toShortString());
        assertEquals("Dec", DECEMBER.toShortString());
    }

    public void testStringToMonthCode() throws Exception {
        assertEquals(JANUARY, Month.parse("1"));
        assertEquals(FEBRUARY, Month.parse("2"));
        assertEquals(MARCH, Month.parse("3"));
        assertEquals(APRIL, Month.parse("4"));
        assertEquals(MAY, Month.parse("5"));
        assertEquals(JUNE, Month.parse("6"));
        assertEquals(JULY, Month.parse("7"));
        assertEquals(AUGUST, Month.parse("8"));
        assertEquals(SEPTEMBER, Month.parse("9"));
        assertEquals(OCTOBER, Month.parse("10"));
        assertEquals(NOVEMBER, Month.parse("11"));
        assertEquals(DECEMBER, Month.parse("12"));

        //todo    assertEquals(-1, stringToMonthCode("0"));
        //     assertEquals(-1, stringToMonthCode("13"));

        //assertEquals(-1, Month.parse("Hello").toInt());

        for (int m = 1; m <= 12; m++) {
            assertEquals(m, Month.parse(Month.fromInt(m).toString()).toInt());
            assertEquals(m, Month.parse(Month.fromInt(m).toShortString()).toInt());
        }

        //    assertEquals(1,stringToMonthCode("jan"));
        //    assertEquals(2,stringToMonthCode("feb"));
        //    assertEquals(3,stringToMonthCode("mar"));
        //    assertEquals(4,stringToMonthCode("apr"));
        //    assertEquals(5,stringToMonthCode("may"));
        //    assertEquals(6,stringToMonthCode("jun"));
        //    assertEquals(7,stringToMonthCode("jul"));
        //    assertEquals(8,stringToMonthCode("aug"));
        //    assertEquals(9,stringToMonthCode("sep"));
        //    assertEquals(10,stringToMonthCode("oct"));
        //    assertEquals(11,stringToMonthCode("nov"));
        //    assertEquals(12,stringToMonthCode("dec"));

        //    assertEquals(1,stringToMonthCode("JAN"));
        //    assertEquals(2,stringToMonthCode("FEB"));
        //    assertEquals(3,stringToMonthCode("MAR"));
        //    assertEquals(4,stringToMonthCode("APR"));
        //    assertEquals(5,stringToMonthCode("MAY"));
        //    assertEquals(6,stringToMonthCode("JUN"));
        //    assertEquals(7,stringToMonthCode("JUL"));
        //    assertEquals(8,stringToMonthCode("AUG"));
        //    assertEquals(9,stringToMonthCode("SEP"));
        //    assertEquals(10,stringToMonthCode("OCT"));
        //    assertEquals(11,stringToMonthCode("NOV"));
        //    assertEquals(12,stringToMonthCode("DEC"));

        //    assertEquals(1,stringToMonthCode("january"));
        //    assertEquals(2,stringToMonthCode("february"));
        //    assertEquals(3,stringToMonthCode("march"));
        //    assertEquals(4,stringToMonthCode("april"));
        //    assertEquals(5,stringToMonthCode("may"));
        //    assertEquals(6,stringToMonthCode("june"));
        //    assertEquals(7,stringToMonthCode("july"));
        //    assertEquals(8,stringToMonthCode("august"));
        //    assertEquals(9,stringToMonthCode("september"));
        //    assertEquals(10,stringToMonthCode("october"));
        //    assertEquals(11,stringToMonthCode("november"));
        //    assertEquals(12,stringToMonthCode("december"));

        //    assertEquals(1,stringToMonthCode("JANUARY"));
        //    assertEquals(2,stringToMonthCode("FEBRUARY"));
        //    assertEquals(3,stringToMonthCode("MAR"));
        //    assertEquals(4,stringToMonthCode("APRIL"));
        //    assertEquals(5,stringToMonthCode("MAY"));
        //    assertEquals(6,stringToMonthCode("JUNE"));
        //    assertEquals(7,stringToMonthCode("JULY"));
        //    assertEquals(8,stringToMonthCode("AUGUST"));
        //    assertEquals(9,stringToMonthCode("SEPTEMBER"));
        //    assertEquals(10,stringToMonthCode("OCTOBER"));
        //    assertEquals(11,stringToMonthCode("NOVEMBER"));
        //    assertEquals(12,stringToMonthCode("DECEMBER"));
    }

    public void testIsLeapYear() throws Exception {
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

    public void testLeapYearCount() throws Exception {
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
        assertEquals(25, leapYearCount(2001));
        assertEquals(49, leapYearCount(2101));
        assertEquals(73, leapYearCount(2201));
        assertEquals(97, leapYearCount(2301));
        assertEquals(122, leapYearCount(2401));
    }

    public void testLastDayOfMonth() throws Exception {
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

    public void testAddDays() throws Exception {
        DayDate newYears = d(1, JANUARY, 1900);
        assertEquals(d(2, JANUARY, 1900), newYears.plusDays(1));
        assertEquals(d(1, FEBRUARY, 1900), newYears.plusDays(31));
        assertEquals(d(1, JANUARY, 1901), newYears.plusDays(365));
        assertEquals(d(31, DECEMBER, 1904), newYears.plusDays(5 * 365));
    }

    private static SpreadsheetDate d(int day, Month month, int year) {
        return new SpreadsheetDate(day, month, year);
    }

    public void testAddMonths() throws Exception {
        assertEquals(d(1, FEBRUARY, 1900), d(1, JANUARY, 1900).plusMonths(1));
        assertEquals(d(28, FEBRUARY, 1900), d(31, JANUARY, 1900).plusMonths(1));
        assertEquals(d(28, FEBRUARY, 1900), d(30, JANUARY, 1900).plusMonths(1));
        assertEquals(d(28, FEBRUARY, 1900), d(29, JANUARY, 1900).plusMonths(1));
        assertEquals(d(28, FEBRUARY, 1900), d(28, JANUARY, 1900).plusMonths(1));
        assertEquals(d(27, FEBRUARY, 1900), d(27, JANUARY, 1900).plusMonths(1));

        assertEquals(d(30, JUNE, 1900), d(31, JANUARY, 1900).plusMonths(5));
        assertEquals(d(30, JUNE, 1901), d(31, JANUARY, 1900).plusMonths(17));

        assertEquals(d(29, FEBRUARY, 1904), d(31, JANUARY, 1900).plusMonths(49));

    }

    public void testPlusYears() throws Exception {
        assertEquals(d(1, JANUARY, 1901), d(1, JANUARY, 1900).plusYears(1));
        assertEquals(d(28, FEBRUARY, 1905), d(29, FEBRUARY, 1904).plusYears(1));
        assertEquals(d(28, FEBRUARY, 1905), d(28, FEBRUARY, 1904).plusYears(1));
        assertEquals(d(28, FEBRUARY, 1904), d(28, FEBRUARY, 1903).plusYears(1));
    }

    public void testGetPreviousDayOfWeek() throws Exception {
        assertEquals(d(24, FEBRUARY, 2006), d(1, MARCH, 2006).getPreviousDayOfWeek(FRIDAY));
        assertEquals(d(22, FEBRUARY, 2006), d(1, MARCH, 2006).getPreviousDayOfWeek(WEDNESDAY));
        assertEquals(d(29, FEBRUARY, 2004), d(3, MARCH, 2004).getPreviousDayOfWeek(SUNDAY));
        assertEquals(d(29, DECEMBER, 2004), d(5, JANUARY, 2005).getPreviousDayOfWeek(WEDNESDAY));

        try {
            d(1, JANUARY, 2006).getPreviousDayOfWeek(Day.fromInt(-1));
            fail("Invalid day of week code should throw exception");
        } catch (IllegalArgumentException e) {
        }
    }

    public void testGetFollowingDayOfWeek() throws Exception {
        //    assertEquals(d(1, JANUARY, 2005),getFollowingDayOfWeek(SATURDAY, d(25, DECEMBER, 2004)));
        assertEquals(d(1, JANUARY, 2005), d(26, DECEMBER, 2004).getFollowingDayOfWeek(SATURDAY));
        assertEquals(d(3, MARCH, 2004), d(28, FEBRUARY, 2004).getFollowingDayOfWeek(WEDNESDAY));

        try {
            d(1, JANUARY, 2006).getFollowingDayOfWeek(Day.fromInt(-1));
            fail("Invalid day of week code should throw exception");
        } catch (IllegalArgumentException e) {
        }
    }

    public void testGetNearestDayOfWeek() throws Exception {
        assertEquals(d(16, APRIL, 2006), d(16, APRIL, 2006).getNearestDayOfWeek(SUNDAY));
        assertEquals(d(16, APRIL, 2006), d(17, APRIL, 2006).getNearestDayOfWeek(SUNDAY));
        assertEquals(d(16, APRIL, 2006), d(18, APRIL, 2006).getNearestDayOfWeek(SUNDAY));
        assertEquals(d(16, APRIL, 2006), d(19, APRIL, 2006).getNearestDayOfWeek(SUNDAY));
        assertEquals(d(23, APRIL, 2006), d(20, APRIL, 2006).getNearestDayOfWeek(SUNDAY));
        assertEquals(d(23, APRIL, 2006), d(21, APRIL, 2006).getNearestDayOfWeek(SUNDAY));
        assertEquals(d(23, APRIL, 2006), d(22, APRIL, 2006).getNearestDayOfWeek(SUNDAY));

        assertEquals(d(17, APRIL, 2006), d(16, APRIL, 2006).getNearestDayOfWeek(MONDAY));
        assertEquals(d(17, APRIL, 2006), d(17, APRIL, 2006).getNearestDayOfWeek(MONDAY));
        assertEquals(d(17, APRIL, 2006), d(18, APRIL, 2006).getNearestDayOfWeek(MONDAY));
        assertEquals(d(17, APRIL, 2006), d(19, APRIL, 2006).getNearestDayOfWeek(MONDAY));
        assertEquals(d(17, APRIL, 2006), d(20, APRIL, 2006).getNearestDayOfWeek(MONDAY));
        assertEquals(d(24, APRIL, 2006), d(21, APRIL, 2006).getNearestDayOfWeek(MONDAY));
        assertEquals(d(24, APRIL, 2006), d(22, APRIL, 2006).getNearestDayOfWeek(MONDAY));

        assertEquals(d(18, APRIL, 2006), d(16, APRIL, 2006).getNearestDayOfWeek(TUESDAY));
        assertEquals(d(18, APRIL, 2006), d(17, APRIL, 2006).getNearestDayOfWeek(TUESDAY));
        assertEquals(d(18, APRIL, 2006), d(18, APRIL, 2006).getNearestDayOfWeek(TUESDAY));
        assertEquals(d(18, APRIL, 2006), d(19, APRIL, 2006).getNearestDayOfWeek(TUESDAY));
        assertEquals(d(18, APRIL, 2006), d(20, APRIL, 2006).getNearestDayOfWeek(TUESDAY));
        assertEquals(d(18, APRIL, 2006), d(21, APRIL, 2006).getNearestDayOfWeek(TUESDAY));
        assertEquals(d(25, APRIL, 2006), d(22, APRIL, 2006).getNearestDayOfWeek(TUESDAY));

        assertEquals(d(19, APRIL, 2006), d(16, APRIL, 2006).getNearestDayOfWeek(WEDNESDAY));
        assertEquals(d(19, APRIL, 2006), d(17, APRIL, 2006).getNearestDayOfWeek(WEDNESDAY));
        assertEquals(d(19, APRIL, 2006), d(18, APRIL, 2006).getNearestDayOfWeek(WEDNESDAY));
        assertEquals(d(19, APRIL, 2006), d(19, APRIL, 2006).getNearestDayOfWeek(WEDNESDAY));
        assertEquals(d(19, APRIL, 2006), d(20, APRIL, 2006).getNearestDayOfWeek(WEDNESDAY));
        assertEquals(d(19, APRIL, 2006), d(21, APRIL, 2006).getNearestDayOfWeek(WEDNESDAY));
        assertEquals(d(19, APRIL, 2006), d(22, APRIL, 2006).getNearestDayOfWeek(WEDNESDAY));

        assertEquals(d(13, APRIL, 2006), d(16, APRIL, 2006).getNearestDayOfWeek(THURSDAY));
        assertEquals(d(20, APRIL, 2006), d(17, APRIL, 2006).getNearestDayOfWeek(THURSDAY));
        assertEquals(d(20, APRIL, 2006), d(18, APRIL, 2006).getNearestDayOfWeek(THURSDAY));
        assertEquals(d(20, APRIL, 2006), d(19, APRIL, 2006).getNearestDayOfWeek(THURSDAY));
        assertEquals(d(20, APRIL, 2006), d(20, APRIL, 2006).getNearestDayOfWeek(THURSDAY));
        assertEquals(d(20, APRIL, 2006), d(21, APRIL, 2006).getNearestDayOfWeek(THURSDAY));
        assertEquals(d(20, APRIL, 2006), d(22, APRIL, 2006).getNearestDayOfWeek(THURSDAY));

        assertEquals(d(14, APRIL, 2006), d(16, APRIL, 2006).getNearestDayOfWeek(FRIDAY));
        assertEquals(d(14, APRIL, 2006), d(17, APRIL, 2006).getNearestDayOfWeek(FRIDAY));
        assertEquals(d(21, APRIL, 2006), d(18, APRIL, 2006).getNearestDayOfWeek(FRIDAY));
        assertEquals(d(21, APRIL, 2006), d(19, APRIL, 2006).getNearestDayOfWeek(FRIDAY));
        assertEquals(d(21, APRIL, 2006), d(20, APRIL, 2006).getNearestDayOfWeek(FRIDAY));
        assertEquals(d(21, APRIL, 2006), d(21, APRIL, 2006).getNearestDayOfWeek(FRIDAY));
        assertEquals(d(21, APRIL, 2006), d(22, APRIL, 2006).getNearestDayOfWeek(FRIDAY));

        assertEquals(d(15, APRIL, 2006), d(16, APRIL, 2006).getNearestDayOfWeek(SATURDAY));
        assertEquals(d(15, APRIL, 2006), d(17, APRIL, 2006).getNearestDayOfWeek(SATURDAY));
        assertEquals(d(15, APRIL, 2006), d(18, APRIL, 2006).getNearestDayOfWeek(SATURDAY));
        assertEquals(d(22, APRIL, 2006), d(19, APRIL, 2006).getNearestDayOfWeek(SATURDAY));
        assertEquals(d(22, APRIL, 2006), d(20, APRIL, 2006).getNearestDayOfWeek(SATURDAY));
        assertEquals(d(22, APRIL, 2006), d(21, APRIL, 2006).getNearestDayOfWeek(SATURDAY));
        assertEquals(d(22, APRIL, 2006), d(22, APRIL, 2006).getNearestDayOfWeek(SATURDAY));

        try {
            d(1, JANUARY, 2006).getNearestDayOfWeek(Day.fromInt(-1));
            fail("Invalid day of week code should throw exception");
        } catch (IllegalArgumentException e) {
        }
    }

    public void testEndOfCurrentMonth() throws Exception {
        DayDate d = DayDateFactory.makeDate(2);
        assertEquals(d(31, JANUARY, 2006), d(1, JANUARY, 2006).getEndOfMonth());
        assertEquals(d(28, FEBRUARY, 2006), d(1, FEBRUARY, 2006).getEndOfMonth());
        assertEquals(d(31, MARCH, 2006), d(1, MARCH, 2006).getEndOfMonth());
        assertEquals(d(30, APRIL, 2006), d(1, APRIL, 2006).getEndOfMonth());
        assertEquals(d(31, MAY, 2006), d(1, MAY, 2006).getEndOfMonth());
        assertEquals(d(30, JUNE, 2006), d(1, JUNE, 2006).getEndOfMonth());
        assertEquals(d(31, JULY, 2006), d(1, JULY, 2006).getEndOfMonth());
        assertEquals(d(31, AUGUST, 2006), d(1, AUGUST, 2006).getEndOfMonth());
        assertEquals(d(30, SEPTEMBER, 2006), d(1, SEPTEMBER, 2006).getEndOfMonth());
        assertEquals(d(31, OCTOBER, 2006), d(1, OCTOBER, 2006).getEndOfMonth());
        assertEquals(d(30, NOVEMBER, 2006), d(1, NOVEMBER, 2006).getEndOfMonth());
        assertEquals(d(31, DECEMBER, 2006), d(1, DECEMBER, 2006).getEndOfMonth());
        assertEquals(d(29, FEBRUARY, 2008), d(1, FEBRUARY, 2008).getEndOfMonth());
    }

    public void testWeekInMonthToString() throws Exception {
        assertEquals("FIRST", FIRST.toString());
        assertEquals("SECOND", SECOND.toString());
        assertEquals("THIRD", THIRD.toString());
        assertEquals("FOURTH", FOURTH.toString());
        assertEquals("LAST", LAST.toString());
    }


    public void testCreateInstanceFromDDMMYYY() throws Exception {
        DayDate date = createInstance(DayDateFactory.makeDate(1, JANUARY, 1900).toDate());
        assertEquals(1, date.getDayOfMonth());
        assertEquals(JANUARY, date.getMonth());
        assertEquals(1900, date.getYear());
        assertEquals(2, date.getOrdinalDay());
    }

    public void testCreateInstanceFromSerial() throws Exception {
        assertEquals(d(1, JANUARY, 1900), createInstance(DayDateFactory.makeDate(2).toDate()));
        assertEquals(d(1, JANUARY, 1901), createInstance(DayDateFactory.makeDate(367).toDate()));
    }

    public void testCreateInstanceFromJavaDate() throws Exception {
        assertEquals(d(1, JANUARY, 1900), createInstance(new GregorianCalendar(1900, 0, 1).getTime()));
        assertEquals(d(1, JANUARY, 2006), createInstance(new GregorianCalendar(2006, 0, 1).getTime()));
    }

//  public static void main(String[] args) {
//    junit.textui.TestRunner.run(BobsSerialDateTest.class);
//  }
}