import org.example.Day01;
import org.example.DistanceLists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01Test {
    String input = Files.readString(Path.of("src/main/resources/testInput.txt"));;
    public Day01Test() throws IOException {
    }

    @Test
    public void part1() throws IOException {
        Integer result = Day01.part1(input);
        assertEquals(11, result);
    }

    @Test
    public void part2() throws IOException {
        assertEquals(31, Day01.part2(input));
    }

    @Test
    public void frequencies() {
        Map<Integer, Integer> expected = Map.of(
                1, 0,
                2, 0,
                3, 9,
                4, 1);

        DistanceLists distanceLists = Day01.getDistanceLists(List.of(input.split("\r\n")));
        HashMap<Integer, Integer> frequencies = Day01.getFrequencies(distanceLists);
        assertEquals(expected, frequencies);
    }

    @Test
    public void similarityScore() {
        DistanceLists distanceLists = Day01.getDistanceLists(List.of(input.split("\r\n")));
        HashMap<Integer, Integer> frequencies = Day01.getFrequencies(distanceLists);
        assertEquals(31, Day01.getSimilarityScore(frequencies));
    }
}
