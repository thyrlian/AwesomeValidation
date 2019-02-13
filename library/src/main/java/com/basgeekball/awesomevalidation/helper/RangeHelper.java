package com.basgeekball.awesomevalidation.helper;

import androidx.core.util.Pair;

import java.util.ArrayList;

public class RangeHelper {

    private RangeHelper() {
        throw new UnsupportedOperationException();
    }

    public static ArrayList<Pair<Integer, Integer>> inverse(ArrayList<Pair<Integer, Integer>> ranges, int lengthOfText) {
        ArrayList<Pair<Integer, Integer>> inverseRanges = new ArrayList<>();
        if (ranges.size() == 0) {
            if (lengthOfText == 0) {
                return inverseRanges;
            } else {
                inverseRanges.add(Pair.create(0, lengthOfText - 1));
                return inverseRanges;
            }
        }
        for (int i = 0; i <= ranges.size(); i++) {
            if (i == 0) {
                if (ranges.get(i).first > 0) {
                    inverseRanges.add(Pair.create(0, ranges.get(i).first - 1));
                }
            } else if (i < ranges.size()) {
                inverseRanges.add(Pair.create(ranges.get(i - 1).second + 1, ranges.get(i).first - 1));
            } else {
                if (ranges.get(i - 1).second < lengthOfText - 1) {
                    inverseRanges.add(Pair.create(ranges.get(i - 1).second + 1, lengthOfText - 1));
                }
            }
        }
        return inverseRanges;
    }

}