import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

public class Day04Test {
  String testInput = Files.readString(Path.of("src/main/resources/Day04/testInput.txt"));
  String realInput = Files.readString(Path.of("src/main/resources/Day04/input.txt"));
  String input = realInput;

  String leftToRightRegex = "XMAS";
  Pattern leftToRightPattern = Pattern.compile(leftToRightRegex);

  String rightToLeftRegex = "SAMX";
  Pattern rightToLeftPattern = Pattern.compile(rightToLeftRegex);

  public Day04Test() throws IOException {}

  @Test
  public void mainTest() throws IOException {
    int total = getHorizontalCount(input) + getVerticalCount(input) + getDiagonalCount(input);
    System.out.println(total);
    assertEquals(2554, total);
  }

  @Test
  public void part2() {
    List<List<String>> grid = getCharGrid(input);
    int count = 0;
    for (int i = 0; i < grid.size(); i++) {
      for (int j = 0; j < grid.get(0).size(); j++) {
        if (i + 2 < grid.size() && j + 2 < grid.get(0).size()) {
          if (testSubgrid(getSubGrid(grid, i, j))) {
            count += 1;
          }
        }
      }
    }
    System.out.println(count);
  }

  boolean testSubgrid(List<List<String>> subgrid) {
    String test =
        subgrid.get(0).get(0).toLowerCase()
            + subgrid.get(1).get(1).toLowerCase()
            + subgrid.get(2).get(2).toLowerCase();
    if (!"mas".equals(test) && !"sam".equals(test)) {
      return false;
    }
    test =
        subgrid.get(0).get(2).toLowerCase()
            + subgrid.get(1).get(1).toLowerCase()
            + subgrid.get(2).get(0).toLowerCase();
    return "mas".equals(test) || "sam".equals(test);
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

    assertTrue(testSubgrid(grid));
  }

  List<List<String>> getSubGrid(List<List<String>> grid, int x, int y) {
    List<List<String>> subGrid = new ArrayList<>();

    List<String> line1 = new ArrayList<>();
    line1.add(grid.get(x).get(y));
    line1.add(grid.get(x).get(y + 1));
    line1.add(grid.get(x).get(y + 2));

    List<String> line2 = new ArrayList<>();
    line2.add(grid.get(x + 1).get(y));
    line2.add(grid.get(x + 1).get(y + 1));
    line2.add(grid.get(x + 1).get(y + 2));

    List<String> line3 = new ArrayList<>();
    line3.add(grid.get(x + 2).get(y));
    line3.add(grid.get(x + 2).get(y + 1));
    line3.add(grid.get(x + 2).get(y + 2));

    subGrid.add(line1);
    subGrid.add(line2);
    subGrid.add(line3);

    return subGrid;
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

    List<List<String>> actual = getSubGrid(getCharGrid(testGrid), 0, 0);
    assertEquals(expected, actual);
  }

  Integer getHorizontalCount(String input) {
    Integer total = 0;
    Matcher leftToRightMatcher = leftToRightPattern.matcher(input);
    Matcher rightToLeftMatcher = rightToLeftPattern.matcher(input);
    while (leftToRightMatcher.find()) {
      total += 1;
    }
    while (rightToLeftMatcher.find()) {
      total += 1;
    }
    return total;
  }

  Integer getVerticalCount(String input) {
    return getHorizontalCount(transposeGrid(input));
  }

  Integer getDiagonalCount(String input) {
    String diagonalGrid = transposeDiagonal(input);
    String reverseDiagonalGrid = reverseTransposeDiagonal(input);
    int count = 0;
    count += getHorizontalCount(diagonalGrid);
    count += getHorizontalCount(reverseDiagonalGrid);
    return count;
  }

  List<List<String>> getCharGrid(String input) {
    return input
        .lines()
        .map(
            line ->
                line.chars().mapToObj(e -> String.valueOf((char) e)).collect(Collectors.toList()))
        .collect(Collectors.toList());
  }

  String transposeGrid(String input) {
    List<List<String>> charGrid = getCharGrid(input);
    int rows = charGrid.get(0).size();
    int cols = charGrid.size();
    List<List<String>> transposedGrid =
        IntStream.range(0, rows)
            .mapToObj(
                row ->
                    IntStream.range(0, cols)
                        .mapToObj(col -> charGrid.get(col).get(row))
                        .collect(Collectors.toList()))
            .collect(Collectors.toList());

    String thing = "";

    for (int i = 0; i < cols; i++) {
      for (int j = 0; j < rows; j++) {
        thing = thing + transposedGrid.get(i).get(j);
      }
      if (i < rows - 1) {
        thing = thing + "\n";
      }
    }
    return thing;
  }

  String transposeDiagonal(String input) {
    List<List<String>> charGrid = getCharGrid(input);
    String transposedGrid = "";
    Integer n = charGrid.size();
    for (int k = 0; k < (n * 2) - 1; k++) {
      String someDiagonal = "";
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (i + j == k) {
            someDiagonal = someDiagonal + charGrid.get(i).get(j);
          }
        }
      }
      transposedGrid = transposedGrid + someDiagonal;
      transposedGrid = transposedGrid + "\n";
    }
    return transposedGrid;
  }

  String reverseTransposeDiagonal(String input) {
    List<List<String>> charGrid = getCharGrid(input);
    String transposedGrid = "";
    Integer n = charGrid.size();
    for (int k = -(n - 1); k <= (n - 1); k++) {
      String someDiagonal = "";
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (i - j == k) {
            someDiagonal = someDiagonal + charGrid.get(i).get(j);
          }
        }
      }
      transposedGrid = transposedGrid + someDiagonal;
      transposedGrid = transposedGrid + "\n";
    }
    return transposedGrid;
  }

  @Test
  public void reverseTransposeDiagonalTest() {
    String testInput = "abc\ndef\nhij";
    String expectedResult = "c\nbf\naej\ndi\nh\n";
    String actualResult = reverseTransposeDiagonal(testInput);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void transposeDiagonalTest() {
    String testInput = "abc\ndef\nhij";
    String expectedResult = "a\nbd\nceh\nfi\nj\n";
    String actualResult = transposeDiagonal(testInput);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void getHorizontalCountTest() {
    assertEquals(5, getHorizontalCount(input));
  }

  @Test
  public void getVerticalCountTest() {
    assertEquals(3, getVerticalCount(input));
  }

  @Test
  public void transposeGridTest() {
    String input = "abc\ndef\nhij";
    String expected = "adh\nbei\ncfj";
    System.out.println(expected);
    System.out.println(transposeGrid(input));
    assertEquals(expected, transposeGrid(input));
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

    assertEquals(expected, getCharGrid(input));
  }
}
