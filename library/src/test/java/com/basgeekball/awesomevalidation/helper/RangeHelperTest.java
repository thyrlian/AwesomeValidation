package com.basgeekball.awesomevalidation.helper;

import androidx.core.util.Pair;

import junit.framework.TestCase;

import java.util.ArrayList;

public class RangeHelperTest extends TestCase {

    private ArrayList<Pair<Integer, Integer>> expected;
    private ArrayList<Pair<Integer, Integer>> ranges;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        expected = new ArrayList<>();
        ranges = new ArrayList<>();
    }

    public void testInverse1() {
        expected.add(Pair.create(0, 3));
        expected.add(Pair.create(6, 9));
        ranges.add(Pair.create(4, 5));
        ArrayList<Pair<Integer, Integer>> actual = RangeHelper.inverse(ranges, 10);
        assertTrue(compareRanges(expected, actual));
    }

    public void testInverse2() {
        expected.add(Pair.create(0, 0));
        expected.add(Pair.create(9, 9));
        ranges.add(Pair.create(1, 8));
        ArrayList<Pair<Integer, Integer>> actual = RangeHelper.inverse(ranges, 10);
        assertTrue(compareRanges(expected, actual));
    }

    public void testInverse3() {
        expected.add(Pair.create(0, 1));
        expected.add(Pair.create(4, 5));
        expected.add(Pair.create(8, 9));
        ranges.add(Pair.create(2, 3));
        ranges.add(Pair.create(6, 7));
        ArrayList<Pair<Integer, Integer>> actual = RangeHelper.inverse(ranges, 10);
        assertTrue(compareRanges(expected, actual));
    }

    public void testInverse4() {
        expected.add(Pair.create(0, 1));
        expected.add(Pair.create(6, 6));
        ranges.add(Pair.create(2, 5));
        ranges.add(Pair.create(7, 9));
        ArrayList<Pair<Integer, Integer>> actual = RangeHelper.inverse(ranges, 10);
        assertTrue(compareRanges(expected, actual));
    }

    public void testInverse5() {
        ranges.add(Pair.create(0, 9));
        ArrayList<Pair<Integer, Integer>> actual = RangeHelper.inverse(ranges, 10);
        assertTrue(compareRanges(expected, actual));
    }

    public void testInverse6() {
        expected.add(Pair.create(0, 9));
        ArrayList<Pair<Integer, Integer>> actual = RangeHelper.inverse(ranges, 10);
        assertTrue(compareRanges(expected, actual));
    }

    public void testInverse7() {
        expected.add(Pair.create(0, 3));
        ranges.add(Pair.create(4, 9));
        ArrayList<Pair<Integer, Integer>> actual = RangeHelper.inverse(ranges, 10);
        assertTrue(compareRanges(expected, actual));
    }

    public void testInverse8() {
        expected.add(Pair.create(7, 9));
        ranges.add(Pair.create(0, 6));
        ArrayList<Pair<Integer, Integer>> actual = RangeHelper.inverse(ranges, 10);
        assertTrue(compareRanges(expected, actual));
    }

    private boolean compareRanges(ArrayList<Pair<Integer, Integer>> ranges1, ArrayList<Pair<Integer, Integer>> ranges2) {
        if (ranges1.size() != ranges2.size()) return false;
        for (int i = 0; i < ranges1.size(); i++) {
            if (!ranges1.get(i).equals(ranges2.get(i))) return false;
        }
        return true;
    }

}