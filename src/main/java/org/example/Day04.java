package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day04 {
  String leftToRightRegex = "XMAS";
  Pattern leftToRightPattern = Pattern.compile(leftToRightRegex);

  String rightToLeftRegex = "SAMX";
  Pattern rightToLeftPattern = Pattern.compile(rightToLeftRegex);

  public Day04() throws IOException {}

  public boolean testSubgrid(List<List<String>> subgrid) {
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

  public List<List<String>> getSubGrid(List<List<String>> grid, int x, int y) {
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

  public Integer getHorizontalCount(String input) {
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

  public Integer getVerticalCount(String input) {
    return getHorizontalCount(transposeGrid(input));
  }

  public Integer getDiagonalCount(String input) {
    String diagonalGrid = transposeDiagonal(input);
    String reverseDiagonalGrid = reverseTransposeDiagonal(input);
    int count = 0;
    count += getHorizontalCount(diagonalGrid);
    count += getHorizontalCount(reverseDiagonalGrid);
    return count;
  }

  public List<List<String>> getCharGrid(String input) {
    return input
        .lines()
        .map(
            line ->
                line.chars().mapToObj(e -> String.valueOf((char) e)).collect(Collectors.toList()))
        .collect(Collectors.toList());
  }

  public String transposeGrid(String input) {
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

  public String transposeDiagonal(String input) {
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

  public String reverseTransposeDiagonal(String input) {
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
}
