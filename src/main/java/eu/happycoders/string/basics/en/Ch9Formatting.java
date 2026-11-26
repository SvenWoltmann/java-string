package eu.happycoders.string.basics.en;

import java.util.Locale;

public class Ch9Formatting {

  static void main() {
    String name = "Grace";
    int points = 42;

    System.out.println(String.format("%s has %d points.", name, points));
    System.out.println("%s has %d points.".formatted(name, points));

    System.out.println(String.format(Locale.GERMANY, "%.2f", Math.PI));
    System.out.println(String.format(Locale.US, "%.2f", Math.PI));

    String row = "%-6s|%5d|%,10d%n";
    System.out.print(String.format(Locale.US, row, "Ada", 7, 1234567));
    System.out.print(String.format(Locale.US, row, "Grace", 42, 89));
  }
}
