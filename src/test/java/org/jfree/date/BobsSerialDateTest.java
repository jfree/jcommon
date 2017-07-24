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
