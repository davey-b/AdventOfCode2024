import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.example.Day04;
import org.junit.jupiter.api.Test;

public class Day04Test {
  Day04 underTest = new Day04();
  String testInput = Files.readString(Path.of("src/main/resources/Day04/testInput.txt"));
  String realInput = Files.readString(Path.of("src/main/resources/Day04/input.txt"));
  String input = realInput;

  public Day04Test() throws IOException {}

  @Test
  public void mainTest() throws IOException {
    int total =
        underTest.getHorizontalCount(input)
            + underTest.getVerticalCount(input)
            + underTest.getDiagonalCount(input);
    System.out.println(total);
    assertEquals(2554, total);
  }

  @Test
  public void part2() {
    List<List<String>> grid = underTest.getCharGrid(input);
    int count = 0;
    for (int i = 0; i < grid.size(); i++) {
      for (int j = 0; j < grid.get(0).size(); j++) {
        if (i + 2 < grid.size() && j + 2 < grid.get(0).size()) {
          if (underTest.testSubgrid(underTest.getSubGrid(grid, i, j))) {
            count += 1;
          }
        }
      }
    }
    System.out.println(count);
    assertEquals(1916, count);
  }

  @Test
  public void testSubgridTest() {
    List<List<String>> grid = new ArrayList<>();
    List<String> line1 = new ArrayList<>();
    line1.add("m");
    line1.add("x");
    line1.add("s");

    List<String> line2 = new ArrayList<>();
    line2.add("x");
    line2.add("a");
    line2.add("x");

    List<String> line3 = new ArrayList<>();
    line3.add("m");
    line3.add("x");
    line3.add("s");

    grid.add(line1);
    grid.add(line2);
    grid.add(line3);

    assertTrue(underTest.testSubgrid(grid));
  }

  @Test
  public void getSubGridTest() {
    String testGrid = "abcd\nefgh\nijkl\nmnop";
    List<List<String>> expected = new ArrayList<>();

    List<String> line1 = new ArrayList<>();
    line1.add("a");
    line1.add("b");
    line1.add("c");

    List<String> line2 = new ArrayList<>();
    line2.add("e");
    line2.add("f");
    line2.add("g");

    List<String> line3 = new ArrayList<>();
    line3.add("i");
    line3.add("j");
    line3.add("k");

    expected.add(line1);
    expected.add(line2);
    expected.add(line3);

    List<List<String>> actual = underTest.getSubGrid(underTest.getCharGrid(testGrid), 0, 0);
    assertEquals(expected, actual);
  }

  @Test
  public void reverseTransposeDiagonalTest() {
    String testInput = "abc\ndef\nhij";
    String expectedResult = "c\nbf\naej\ndi\nh\n";
    String actualResult = underTest.reverseTransposeDiagonal(testInput);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void transposeDiagonalTest() {
    String testInput = "abc\ndef\nhij";
    String expectedResult = "a\nbd\nceh\nfi\nj\n";
    String actualResult = underTest.transposeDiagonal(testInput);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void getHorizontalCountTest() {
    assertEquals(5, underTest.getHorizontalCount(input));
  }

  @Test
  public void getVerticalCountTest() {
    assertEquals(3, underTest.getVerticalCount(input));
  }

  @Test
  public void transposeGridTest() {
    String input = "abc\ndef\nhij";
    String expected = "adh\nbei\ncfj";
    System.out.println(expected);
    System.out.println(underTest.transposeGrid(input));
    assertEquals(expected, underTest.transposeGrid(input));
  }

  @Test
  public void getCharGridTest() {
    String input = "abc\ndef\nhij";
    List<List<String>> expected = new ArrayList<>();
    List<String> first = new ArrayList<>();
    List<String> second = new ArrayList<>();
    List<String> third = new ArrayList<>();
    first.add("a");
    first.add("b");
    first.add("c");
    expected.add(first);
    second.add("d");
    second.add("e");
    second.add("f");
    expected.add(second);
    third.add("h");
    third.add("i");
    third.add("j");
    expected.add(third);

    assertEquals(expected, underTest.getCharGrid(input));
  }
}
