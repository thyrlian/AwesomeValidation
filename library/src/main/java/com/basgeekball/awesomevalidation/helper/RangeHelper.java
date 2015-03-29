package com.basgeekball.awesomevalidation.helper;

import java.util.ArrayList;

public class RangeHelper {

    private RangeHelper() {}

    public static ArrayList<int[]> inverse(ArrayList<int[]> ranges, int lengthOfText) {
        ArrayList<int[]> inverseRanges = new ArrayList<int[]>();
        if (ranges.size() == 0) {
            if (lengthOfText == 0) {
                return inverseRanges;
            } else {
                inverseRanges.add(new int[]{0, lengthOfText - 1});
                return inverseRanges;
            }
        }
        for (int i = 0; i <= ranges.size(); i++) {
            if (i == 0) {
                if (ranges.get(i)[0] > 0) {
                    inverseRanges.add(new int[]{0, ranges.get(i)[0] - 1});
                }
            } else if (i < ranges.size()) {
                inverseRanges.add(new int[]{ranges.get(i - 1)[1] + 1, ranges.get(i)[0] - 1});
            } else {
                if (ranges.get(i - 1)[1] < lengthOfText - 1) {
                    inverseRanges.add(new int[]{ranges.get(i - 1)[1] + 1, lengthOfText - 1});
                }
            }
        }
        return inverseRanges;
    }

}