package org.example;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class Day01 {
    public static Integer part1(String input) throws IOException {
        List<String> lines = List.of(input.split("\r\n"));
        DistanceLists distanceLists = getDistanceLists(lines);
        return getTotal(distanceLists);
    }

    public static Integer part2(String input) throws IOException {
        DistanceLists distanceLists = getDistanceLists(List.of(input.split("\r\n")));
        HashMap<Integer, Integer> frequencies = getFrequencies(distanceLists);
        return(getSimilarityScore(frequencies));
    }

    public static HashMap<Integer, Integer> getFrequencies(DistanceLists distanceLists) {
        HashMap<Integer, Integer> frequencies = new HashMap<Integer, Integer>();
        for(int target : distanceLists.getFirstList()) {
            Integer count = (int) distanceLists.getSecondList().stream().filter(e -> e == target).count();
            int value = frequencies.containsKey(target) ? frequencies.get(target) + count : count;
            frequencies.put(target, value);
        }
        return frequencies;
    }

    public static Integer getSimilarityScore(HashMap<Integer, Integer> frequencies) {
        Integer total = 0;
        for (Map.Entry<Integer, Integer> entry : frequencies.entrySet()) {
            total += entry.getKey() * entry.getValue();
        }
        return total;
    }

    public static DistanceLists getDistanceLists(List<String> lines) {
        List<Integer> firstList = new ArrayList<>();
        List<Integer> secondList = new ArrayList<>();

        for (String line : lines) {
            List<String> thing = List.of(line.split("\\s+"));
            firstList.add(parseInt(thing.get(0)));
            secondList.add(parseInt(thing.get(1)));
        }

        Collections.sort(firstList);
        Collections.sort(secondList);

        return new DistanceLists(firstList, secondList);
    }

    private static int getTotal(@NotNull DistanceLists distanceLists) {
        int total = 0;
        for(int i = 0; i < distanceLists.getFirstList().size() ; i++) {
            int diff = distanceLists.getFirstList().get(i) - distanceLists.getSecondList().get(i);
            diff = diff < 0 ? diff * -1 : diff;
            total += diff;
        }
        return total;
    }
}
