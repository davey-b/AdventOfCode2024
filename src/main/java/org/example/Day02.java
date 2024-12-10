package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;

public class Day02 {

    public static int part1(String input) throws IOException {
        int safeCount = 0;
        List<List<Integer>> intLines = getLines(input);
        for (List<Integer> line : intLines) {
            if (isSafe(line)) {
                safeCount += 1;
            }
        }
        return safeCount;
    }

    public static int part2(String input) throws IOException {
        int safeCount = 0;
        List<List<Integer>> intLines = getLines(input);
        for (List<Integer> line : intLines) {
            if (isSafe(line)) {
                safeCount += 1;
            } else if (isSafeWithDampener(line)) {
                safeCount += 1;
            }
        }
        return safeCount;
    }

    public static List<List<Integer>> getLines(String input) {
        List<List<Integer>> intLines = new ArrayList<>();
        List<String> lines = List.of(input.split("\r\n"));
        for (String line : lines) {
            List<Integer> intLine = List.of(line.split(" ")).stream()
                    .map(Integer::parseInt).collect(Collectors.toList());
            intLines.add(intLine);
        }
        return intLines;
    }

    public static boolean isSafe(List<Integer> line) {
        if(isAscending(line)) {
            return isDifferenceSafe(line);
        } else if (isDescending(line)) {
            return isDifferenceSafe(line);
        }
        return false;
    }

    public static boolean isAscending(List<Integer> line) {
        ListIterator<Integer> iterator = line.listIterator();
        Integer current = iterator.next();
        Integer next;
        while (iterator.hasNext()) {
            next = iterator.next();
            if (current >= next) {
                return false;
            }
            current = next;
        }
        return true;
    }

    public static boolean isDescending(List<Integer> line) {
        ListIterator<Integer> iterator = line.listIterator();
        Integer current = iterator.next();
        Integer next;
        while (iterator.hasNext()) {
            next = iterator.next();
            if (current <= next) {
                return false;
            }
            current = next;
        }
        return true;
    }

    public static boolean isDifferenceSafe(List<Integer> line) {
        BiFunction<Integer, Integer, Boolean> descendingDiff = (curr, next) -> curr - next > 3;
        BiFunction<Integer, Integer, Boolean> ascendingDiff = (curr, next) -> next - curr > 3;
        ListIterator<Integer> iterator = line.listIterator();
        Integer current = iterator.next();
        Integer next;
        while (iterator.hasNext()) {
            next = iterator.next();
            if (isDescending(line)) {
                if (descendingDiff.apply(current, next)) {
                    return false;
                }
            } else if (isAscending(line)) {
                if (ascendingDiff.apply(current, next)) {
                    return false;
                };
            }
            current = next;
        }
        return true;
    }

    public static boolean isSafeWithDampener(List<Integer> line) {
        return IntStream.range(0, line.size())
                .anyMatch(i -> {
                    List<Integer> newList = new ArrayList<>(line);
                    newList.remove(i);
                    return isSafe(newList);
                });
    }
}
