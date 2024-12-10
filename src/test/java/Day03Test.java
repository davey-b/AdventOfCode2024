import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Day03Test {

  @Test
  public void someTest() throws IOException {
    String input = Files.readString(Path.of("src/main/resources/Day03/input.txt"));

    String newRegex = "mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)";
    Pattern pattern = Pattern.compile(newRegex);
    Matcher matcher = pattern.matcher(input);

    Integer total = 0;
    Boolean enabled = true;

    while (matcher.find()) {
      if("do()".equals(matcher.group())) {
        enabled = true;
      } else if("don't()".equals(matcher.group())) {
        enabled = false;
      } else if (enabled) {
        total += parseInt(matcher.group(1)) * parseInt(matcher.group(2));
      }
    }
    System.out.println(total);
  }
}
