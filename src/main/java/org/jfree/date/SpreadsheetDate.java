/* ========================================================================
 * JCommon : a free general purpose class library for the Java(tm) platform
 * ========================================================================
 *
 * (C) Copyright 2000-2006, by Object Refinery Limited and Contributors.
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
 * --------------------
 * SpreadsheetDate.java
 * --------------------
 * (C) Copyright 2000-2006, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: SpreadsheetDate.java,v 1.10 2006/08/29 13:59:30 mungady Exp $
 *
 * Changes
 * -------
 * 11-Oct-2001 : Version 1 (DG);
 * 05-Nov-2001 : Added getDescription() and setDescription() methods (DG);
 * 12-Nov-2001 : Changed name from ExcelDate.java to SpreadsheetDate.java (DG);
 *               Fixed a bug in calculating day, month and year from serial
 *               number (DG);
 * 24-Jan-2002 : Fixed a bug in calculating the serial number from the day,
 *               month and year.  Thanks to Trevor Hills for the report (DG);
 * 29-May-2002 : Added equals(Object) method (SourceForge ID 558850) (DG);
 * 03-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 13-Mar-2003 : Implemented Serializable (DG);
 * 04-Sep-2003 : Completed isInRange() methods (DG);
 * 05-Sep-2003 : Implemented Comparable (DG);
 * 21-Oct-2003 : Added hashCode() method (DG);
 * 29-Aug-2006 : Removed redundant description attribute (DG);
 *
 */

package org.jfree.date;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.jfree.date.Day.SATURDAY;
import static org.jfree.date.Month.FEBRUARY;

/**
 * Represents a date using an integer, in a similar fashion to the
 * implementation in Microsoft Excel.  The range of dates supported is
 * 1-Jan-1900 to 31-Dec-9999.
 * <p>
 * Be aware that there is a deliberate bug in Excel that recognises the year
 * 1900 as a leap year when in fact it is not a leap year. You can find more
 * information on the Microsoft website in article Q181370:
 * <p>
 * http://support.microsoft.com/support/kb/articles/Q181/3/70.asp
 * <p>
 * Excel uses the convention that 1-Jan-1900 = 1.  This class uses the
 * convention 1-Jan-1900 = 2.
 * The result is that the day number in this class will be different to the
 * Excel figure for January and February 1900...but then Excel adds in an extra
 * day (29-Feb-1900 which does not actually exist!) and from that point forward
 * the day numbers will match.
 *
 * @author David Gilbert
 */
public class SpreadsheetDate extends DayDate {
    public static final int EARLIEST_DATE_ORDINAL = 2;      // 1/1/1900
    public static final int LATEST_DATE_ORDINAL = 2958465;  // 12/31/9999
    public static final int MINIMUM_YEAR_SUPPORTED = 1900;
    public static final int MAXIMUM_YEAR_SUPPORTED = 9999;
    public static final int[] AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH =
            {0, 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};
    public static final int[]
            LEAP_YEAR_AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH =
            {0, 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366};

    private int ordinalDay;
    private int day;
    private Month month;
    private int year;

    public SpreadsheetDate(int day, Month month, int year) {
        if (year < MINIMUM_YEAR_SUPPORTED || year > MAXIMUM_YEAR_SUPPORTED)
            throw new IllegalArgumentException(
                    "The 'year' argument must be in range " +
                            MINIMUM_YEAR_SUPPORTED + " to " + MAXIMUM_YEAR_SUPPORTED + ".");
        if (day < 1 || day > DateUtil.lastDayOfMonth(month, year))
            throw new IllegalArgumentException("Invalid 'day' argument.");

        this.year = year;
        this.month = month;
        this.day = day;
        ordinalDay = calcOrdinal(day, month, year);
    }

    public SpreadsheetDate(int day, int month, int year) {
        this(day, Month.fromInt(month), year);
    }

    public SpreadsheetDate(int serial) {
        if (serial < EARLIEST_DATE_ORDINAL || serial > LATEST_DATE_ORDINAL)
            throw new IllegalArgumentException(
                    "SpreadsheetDate: Serial must be in range 2 to 2958465.");

        ordinalDay = serial;
        calcDayMonthYear();
    }

    public int getOrdinalDay() {
        return this.ordinalDay;
    }

    public int getYear() {
        return this.year;
    }

    public Month getMonth() {
        return this.month;
    }

    public int getDayOfMonth() {
        return this.day;
    }

    @Override
    public Day getDayOfWeekForOrdinalZero() {
        return SATURDAY;
    }

    public boolean equals(Object object) {
        if (!(object instanceof DayDate))
            return false;

        DayDate date = (DayDate) object;
        return date.getOrdinalDay() == getOrdinalDay();
    }

    public int hashCode() {
        return getOrdinalDay();
    }

    public int compareTo(Object other) {
        return daysSince((DayDate) other);
    }

    private int calcOrdinal(int day, Month month, int year) {
        int leapDaysForYear = DateUtil.leapYearCount(year - 1);
        int daysUpToYear = (year - MINIMUM_YEAR_SUPPORTED) * 365 + leapDaysForYear;
        int daysUpToMonth = AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH[month.toInt()];
        if (DateUtil.isLeapYear(year) && month.toInt() > FEBRUARY.toInt())
            daysUpToMonth++;
        int daysInMonth = day - 1;
        return daysUpToYear + daysUpToMonth + daysInMonth + EARLIEST_DATE_ORDINAL;
    }

    private void calcDayMonthYear() {
        int days = ordinalDay - EARLIEST_DATE_ORDINAL;
        int overestimatedYear = MINIMUM_YEAR_SUPPORTED + days / 365;
        int nonleapdays = days - DateUtil.leapYearCount(overestimatedYear);
        int underestimatedYear = MINIMUM_YEAR_SUPPORTED + nonleapdays / 365;

        year = huntForYearContaining(ordinalDay, underestimatedYear);
        int firstOrdinalOfYear = firstOrdinalOfYear(year);
        month = huntForMonthContaining(ordinalDay, firstOrdinalOfYear);
        day = ordinalDay - firstOrdinalOfYear - daysBeforeThisMonth(month.toInt());
    }

    private Month huntForMonthContaining(int anOrdinal, int firstOrdinalOfYear) {
        int daysIntoThisYear = anOrdinal - firstOrdinalOfYear;
        int aMonth = 1;
        while (daysBeforeThisMonth(aMonth) < daysIntoThisYear)
            aMonth++;

        return Month.fromInt(aMonth - 1);
    }

    private int daysBeforeThisMonth(int aMonth) {
        if (DateUtil.isLeapYear(year))
            return LEAP_YEAR_AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH[aMonth] - 1;
        else
            return AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH[aMonth] - 1;
    }

    private int huntForYearContaining(int anOrdinalDay, int startingYear) {
        int aYear = startingYear;
        while (firstOrdinalOfYear(aYear) <= anOrdinalDay)
            aYear++;

        return aYear - 1;
    }

    private int firstOrdinalOfYear(int year) {
        return calcOrdinal(1, Month.JANUARY, year);
    }

    public static DayDate createInstance(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return new SpreadsheetDate(calendar.get(Calendar.DATE),
                Month.fromInt(calendar.get(Calendar.MONTH) + 1),
                calendar.get(Calendar.YEAR));
    }
}
