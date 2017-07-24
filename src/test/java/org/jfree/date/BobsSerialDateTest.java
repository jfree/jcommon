package org.jfree.date;

import junit.framework.TestCase;
import static org.jfree.date.SerialDate.*;

import java.util.*;

/**
 * This test is copied from the Robert C. Martin's Clean Code book. The commented lines are tests which currently do not
 * pass.
 *
 * I am merging this test into the SerialDateTest class, check the version control system for more details.
 */

public class BobsSerialDateTest extends TestCase{

	private static SpreadsheetDate d(int day, int month, int year) {return new
			SpreadsheetDate(day, month, year);}

	public void testEndOfCurrentMonth() throws Exception {
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

	public void testWeekInMonthToString() throws Exception {
		assertEquals("First",weekInMonthToString(FIRST_WEEK_IN_MONTH));
		assertEquals("Second",weekInMonthToString(SECOND_WEEK_IN_MONTH));
		assertEquals("Third",weekInMonthToString(THIRD_WEEK_IN_MONTH));
		assertEquals("Fourth",weekInMonthToString(FOURTH_WEEK_IN_MONTH));
		assertEquals("Last",weekInMonthToString(LAST_WEEK_IN_MONTH));

//todo try {
// weekInMonthToString(-1);
// fail("Invalid week code should throw exception");
// } catch (IllegalArgumentException e) {
// }
	}

	public void testRelativeToString() throws Exception {
		assertEquals("Preceding",relativeToString(PRECEDING));
		assertEquals("Nearest",relativeToString(NEAREST));
		assertEquals("Following",relativeToString(FOLLOWING));

//todo try {
// relativeToString(-1000);
// fail("Invalid relative code should throw exception");
// } catch (IllegalArgumentException e) {
// }
	}

	public void testCreateInstanceFromDDMMYYY() throws Exception {
		SerialDate date = createInstance(1, JANUARY, 1900);
		assertEquals(1,date.getDayOfMonth());
		assertEquals(JANUARY,date.getMonth());
		assertEquals(1900,date.getYYYY());
		assertEquals(2,date.toSerial());
	}

	public void testCreateInstanceFromSerial() throws Exception {
		assertEquals(d(1, JANUARY, 1900),createInstance(2));
		assertEquals(d(1, JANUARY, 1901), createInstance(367));
	}

	public void testCreateInstanceFromJavaDate() throws Exception {
		assertEquals(d(1, JANUARY, 1900),
				createInstance(new GregorianCalendar(1900,0,1).getTime()));
		assertEquals(d(1, JANUARY, 2006),
				createInstance(new GregorianCalendar(2006,0,1).getTime()));
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(BobsSerialDateTest.class);
	}
}
