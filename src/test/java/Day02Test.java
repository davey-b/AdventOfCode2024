import org.example.Day02;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Day02Test {

    @Test
    public void getLinesTest() throws IOException {
        String input = Files.readString(Path.of("src/main/resources/Day02/testInput.txt"));
        Day02.getLines(input);
    }

    @Test
    public void allIncreasingTest() {
        List<Integer> ascending = List.of(1, 2, 3, 4, 5);
        assertTrue(Day02.isAscending(ascending));
    }
    @Test
    public void allDereasingTest() {
        List<Integer> descending = List.of(5, 4, 3, 2, 1);
        assertTrue(Day02.isDescending(descending));
    }

    @Test
    public void isDifferenceSafeTest() {
        List<Integer> safeDesc = List.of(9, 8, 7, 6, 3);
        List<Integer> safeAsc = List.of(1, 2, 3, 4, 7);
        List<Integer> unsafeDesc = List.of(9, 8, 7, 6, 2);
        List<Integer> unsafeAsc = List.of(1, 2, 3, 4, 8);

        BiFunction<Integer, Integer, Boolean> descendingDiff = (curr, next) -> curr - next > 3;
        BiFunction<Integer, Integer, Boolean> ascendingDiff = (curr, next) -> next - curr > 3;

        assertTrue(Day02.isDifferenceSafe(safeDesc));
        assertTrue(Day02.isDifferenceSafe(safeAsc));
        assertFalse(Day02.isDifferenceSafe(unsafeDesc));
        assertFalse(Day02.isDifferenceSafe(unsafeAsc));
    }

    @Test
    public void part1Test() throws IOException {
        String testInput = Files.readString(Path.of("src/main/resources/Day02/testInput.txt"));
        String realInput = Files.readString((Path.of("src/main/resources/Day02/input.txt")));
        System.out.println(Day02.part1(realInput));
    }

    @Test
    public void part2Test() throws IOException {
        String testInput = Files.readString(Path.of("src/main/resources/Day02/testInput.txt"));
        String realInput = Files.readString((Path.of("src/main/resources/Day02/input.txt")));
        System.out.println(Day02.part2(realInput));
    }
}
