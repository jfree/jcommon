/* ========================================================================
 * JCommon : a free general purpose class library for the Java(tm) platform
 * ========================================================================
 *
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
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
 * SerialDateUtilities.java
 * ------------------------
 * (C) Copyright 2001-2003, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: SerialDateUtilities.java,v 1.6 2005/11/16 15:58:40 taqua Exp $
 *
 * Changes (from 26-Oct-2001)
 * --------------------------
 * 26-Oct-2001 : Changed package to com.jrefinery.date.*;
 * 08-Dec-2001 : Dropped isLeapYear() method (DG);
 * 04-Mar-2002 : Renamed SerialDates.java --> SerialDateUtilities.java (DG);
 * 25-Jun-2002 : Fixed a bug in the dayCountActual() method (DG);
 * 03-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 *
 */

package org.jfree.date;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import static java.util.Calendar.*;
import static org.jfree.date.DateUtil.isLeapYear;

/**
 * A utility class that provides a number of useful methods (some static).
 * Many of these are used in the implementation of the day-count convention
 * classes.  I recognise some limitations in this implementation:
 * <p>
 * [1] some of the methods assume that the default Calendar is a
 * GregorianCalendar (used mostly to determine leap years) - so the code
 * won&rsquo;t work if some other Calendar is the default.  I'm not sure how
 * to handle this properly?
 * <p>
 * [2] a whole bunch of static methods isn't very object-oriented - but I couldn't think of a good
 * way to extend the Date and Calendar classes to add the functions I required,
 * so static methods are doing the job for now.
 *
 * @author David Gilbert
 */
public class SerialDateUtilities {

    /** The default date format symbols. */
    private DateFormatSymbols dateFormatSymbols;

    /** Strings representing the weekdays. */
    private String[] weekdays;

    /** Strings representing the months. */
    private String[] months;

    /**
     * Creates a new utility class for the default locale.
     */
    public SerialDateUtilities() {
        this.dateFormatSymbols = new DateFormatSymbols();
        this.weekdays = this.dateFormatSymbols.getWeekdays();
        this.months = this.dateFormatSymbols.getMonths();
    }

    /**
     * Returns an array of strings representing the days-of-the-week.
     *
     * @return an array of strings representing the days-of-the-week.
     */
    public String[] getWeekdays() {
        return this.weekdays;
    }

    /**
     * Returns an array of strings representing the months.
     *
     * @return an array of strings representing the months.
     */
    public String[] getMonths() {
        return this.months;
    }

    /**
     * Converts the specified string to a weekday, using the default locale.
     *
     * @param s  a string representing the day-of-the-week.
     *
     * @return an integer representing the day-of-the-week.
     */
    public int stringToWeekday(final String s) {

        if (s.equals(this.weekdays[Calendar.SATURDAY])) {
            return SATURDAY;
        }
        else if (s.equals(this.weekdays[Calendar.SUNDAY])) {
            return SUNDAY;
        }
        else if (s.equals(this.weekdays[Calendar.MONDAY])) {
            return MONDAY;
        }
        else if (s.equals(this.weekdays[TUESDAY])) {
            return TUESDAY;
        }
        else if (s.equals(this.weekdays[WEDNESDAY])) {
            return WEDNESDAY;
        }
        else if (s.equals(this.weekdays[THURSDAY])) {
            return THURSDAY;
        }
        else {
            return FRIDAY;
        }

    }

    /**
     * Returns the actual number of days between two dates.
     *
     * @param start  the start date.
     * @param end  the end date.
     *
     * @return the number of days between the start date and the end date.
     */
    public static int dayCountActual(final DayDate start, final DayDate end) {
        return end.daysSince(start);
    }

    /**
     * Returns the number of days between the specified start and end dates,
     * assuming that there are thirty days in every month (that is,
     * corresponding to the 30/360 day-count convention).
     * <P>
     * The method handles cases where the start date is before the end date (by
     * switching the dates and returning a negative result).
     *
     * @param start  the start date.
     * @param end  the end date.
     *
     * @return the number of days between the two dates, assuming the 30/360 day-count convention.
     */
    public static int dayCount30(final DayDate start, final DayDate end) {
        final int d1;
        final Month m1;
        final int y1;
        final int d2;
        final Month m2;
        final int y2;
        if (start.isBefore(end)) {  // check the order of the dates
            d1 = start.getDayOfMonth();
            m1 = start.getMonth();
            y1 = start.getYear();
            d2 = end.getDayOfMonth();
            m2 = end.getMonth();
            y2 = end.getYear();
            return 360 * (y2 - y1) + 30 * (m2.toInt() - m1.toInt()) + (d2 - d1);
        }
        else {
            return -dayCount30(end, start);
        }
    }

    /**
     * Returns the number of days between the specified start and end dates,
     * assuming that there are thirty days in every month, and applying the
     * ISDA adjustments (that is, corresponding to the 30/360 (ISDA) day-count
     * convention).
     * <P>
     * The method handles cases where the start date is before the end date (by
     * switching the dates around and returning a negative result).
     *
     * @param start  the start date.
     * @param end  the end date.
     *
     * @return The number of days between the two dates, assuming the 30/360
     *      (ISDA) day-count convention.
     */
    public static int dayCount30ISDA(final DayDate start, final DayDate end) {
        int d1;
        final Month m1;
        final int y1;
        int d2;
        final Month m2;
        final int y2;
        if (start.isBefore(end)) {
            d1 = start.getDayOfMonth();
            m1 = start.getMonth();
            y1 = start.getYear();
            if (d1 == 31) {  // first ISDA adjustment
                d1 = 30;
            }
            d2 = end.getDayOfMonth();
            m2 = end.getMonth();
            y2 = end.getYear();
            if ((d2 == 31) && (d1 == 30)) {  // second ISDA adjustment
                d2 = 30;
            }
            return 360 * (y2 - y1) + 30 * (m2.toInt() - m1.toInt()) + (d2 - d1);
        }
        else if (start.isAfter(end)) {
            return -dayCount30ISDA(end, start);
        }
        else {
            return 0;
        }
    }

    /**
     * Returns the number of days between the specified start and end dates,
     * assuming that there are thirty days in every month, and applying the PSA
     * adjustments (that is, corresponding to the 30/360 (PSA) day-count convention).
     * The method handles cases where the start date is before the end date (by
     * switching the dates around and returning a negative result).
     *
     * @param start  the start date.
     * @param end  the end date.
     *
     * @return The number of days between the two dates, assuming the 30/360
     *      (PSA) day-count convention.
     */
    public static int dayCount30PSA(final DayDate start, final DayDate end) {
        int d1;
        final int m1;
        final int y1;
        int d2;
        final int m2;
        final int y2;

        if (start.isOnOrBefore(end)) { // check the order of the dates
            d1 = start.getDayOfMonth();
            m1 = start.getMonth().toInt();
            y1 = start.getYear();

            if (SerialDateUtilities.isLastDayOfFebruary(start)) {
                d1 = 30;
            }
            if ((d1 == 31) || SerialDateUtilities.isLastDayOfFebruary(start)) {
                // first PSA adjustment
                d1 = 30;
            }
            d2 = end.getDayOfMonth();
            m2 = end.getMonth().toInt();
            y2 = end.getYear();
            if ((d2 == 31) && (d1 == 30)) {  // second PSA adjustment
                d2 = 30;
            }
            return 360 * (y2 - y1) + 30 * (m2 - m1) + (d2 - d1);
        }
        else {
            return -dayCount30PSA(end, start);
        }
    }

    /**
     * Returns the number of days between the specified start and end dates,
     * assuming that there are thirty days in every month, and applying the
     * European adjustment (that is, corresponding to the 30E/360 day-count
     * convention).
     * <P>
     * The method handles cases where the start date is before the end date (by
     * switching the dates around and returning a negative result).
     *
     * @param start  the start date.
     * @param end  the end date.
     *
     * @return the number of days between the two dates, assuming the 30E/360
     *      day-count convention.
     */
    public static int dayCount30E(final DayDate start, final DayDate end) {
        int d1;
        final int m1;
        final int y1;
        int d2;
        final int m2;
        final int y2;
        if (start.isBefore(end)) {
            d1 = start.getDayOfMonth();
            m1 = start.getMonth().toInt();
            y1 = start.getYear();
            if (d1 == 31) {  // first European adjustment
                d1 = 30;
            }
            d2 = end.getDayOfMonth();
            m2 = end.getMonth().toInt();
            y2 = end.getYear();
            if (d2 == 31) {  // first European adjustment
                d2 = 30;
            }
            return 360 * (y2 - y1) + 30 * (m2 - m1) + (d2 - d1);
        }
        else if (start.isAfter(end)) {
            return -dayCount30E(end, start);
        }
        else {
            return 0;
        }
    }

    /**
     * Returns true if the specified date is the last day in February (that is, the
     * 28th in non-leap years, and the 29th in leap years).
     *
     * @param d  the date to be tested.
     *
     * @return a boolean that indicates whether or not the specified date is
     *      the last day of February.
     */
    public static boolean isLastDayOfFebruary(final DayDate d) {

        final int dom;
        if (d.getMonth().toInt() == FEBRUARY) {
            dom = d.getDayOfMonth();
            if (isLeapYear(d.getYear())) {
                return (dom == 29);
            }
            else {
                return (dom == 28);
            }
        }
        else { // not even February
            return false;
        }

    }

    /**
     * Returns the number of times that February 29 falls within the specified
     * date range.  The result needs to correspond to the ACT/365 (Japanese)
     * day-count convention. The difficult cases are where the start or the
     * end date is Feb 29 (include or not?).  Need to find out how JGBs do this
     * (since this is where the ACT/365 (Japanese) convention comes from ...
     *
     * @param start  the start date.
     * @param end  the end date.
     *
     * @return the number of times that February 29 occurs within the date
     *      range.
     */
    public static int countFeb29s(final DayDate start, final DayDate end) {
        int count = 0;
        DayDate feb29;
        final int y1;
        final int y2;
        int year;

        // check the order of the dates
        if (start.isBefore(end)) {

            y1 = start.getYear();
            y2 = end.getYear();
            for (year = y1; year == y2; year++) {
                if (isLeapYear(year)) {
                    feb29 = DayDateFactory.makeDate(29, MonthConstants.FEBRUARY, year);
                    if (feb29.isInRange(start, end, DateInterval.CLOSED_RIGHT)) {
                        count++;
                    }
                }
            }
            return count;
        }
        else {
            return countFeb29s(end, start);
        }
    }

}
