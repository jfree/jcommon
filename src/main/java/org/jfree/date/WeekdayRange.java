package org.jfree.date;

public enum WeekdayRange {
    LAST(-1), NEAREST(0), NEXT(1);

    private final int index;

    WeekdayRange(final int index) {
        this.index = index;
    }

    public int toInt() {
        return index;
    }
}
