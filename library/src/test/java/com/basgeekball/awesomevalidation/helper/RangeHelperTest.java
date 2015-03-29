package com.basgeekball.awesomevalidation.helper;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;

public class RangeHelperTest extends TestCase {

    ArrayList<int[]> expected;
    ArrayList<int[]> ranges;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        expected = new ArrayList<int[]>();
        ranges = new ArrayList<int[]>();
    }

    public void testInverse1() {
        expected.add(new int[]{0, 3});
        expected.add(new int[]{6, 9});
        ranges.add(new int[]{4, 5});
        ArrayList<int[]> actual = RangeHelper.inverse(ranges, 10);
        assertTrue(compareRanges(expected, actual));
    }

    public void testInverse2() {
        expected.add(new int[]{0, 0});
        expected.add(new int[]{9, 9});
        ranges.add(new int[]{1, 8});
        ArrayList<int[]> actual = RangeHelper.inverse(ranges, 10);
        assertTrue(compareRanges(expected, actual));
    }

    public void testInverse3() {
        expected.add(new int[]{0, 1});
        expected.add(new int[]{4, 5});
        expected.add(new int[]{8, 9});
        ranges.add(new int[]{2, 3});
        ranges.add(new int[]{6, 7});
        ArrayList<int[]> actual = RangeHelper.inverse(ranges, 10);
        assertTrue(compareRanges(expected, actual));
    }

    public void testInverse4() {
        expected.add(new int[]{0, 1});
        expected.add(new int[]{6, 6});
        ranges.add(new int[]{2, 5});
        ranges.add(new int[]{7, 9});
        ArrayList<int[]> actual = RangeHelper.inverse(ranges, 10);
        assertTrue(compareRanges(expected, actual));
    }

    public void testInverse5() {
        ranges.add(new int[]{0, 9});
        ArrayList<int[]> actual = RangeHelper.inverse(ranges, 10);
        assertTrue(compareRanges(expected, actual));
    }

    public void testInverse6() {
        expected.add(new int[]{0, 9});
        ArrayList<int[]> actual = RangeHelper.inverse(ranges, 10);
        assertTrue(compareRanges(expected, actual));
    }

    public void testInverse7() {
        expected.add(new int[]{0, 3});
        ranges.add(new int[]{4, 9});
        ArrayList<int[]> actual = RangeHelper.inverse(ranges, 10);
        assertTrue(compareRanges(expected, actual));
    }

    public void testInverse8() {
        expected.add(new int[]{7, 9});
        ranges.add(new int[]{0, 6});
        ArrayList<int[]> actual = RangeHelper.inverse(ranges, 10);
        assertTrue(compareRanges(expected, actual));
    }

    private boolean compareRanges(ArrayList<int[]> ranges1, ArrayList<int[]> ranges2) {
        for (int[] range1 : ranges1) {
            boolean equality = false;
            for (int[] range2 : ranges2) {
                if (Arrays.equals(range1, range2)) {
                    equality = true;
                }
            }
            if (!equality) {
                return false;
            }
        }
        return true;
    }

}