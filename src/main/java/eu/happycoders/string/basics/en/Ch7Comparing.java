package eu.happycoders.string.basics.en;

import java.util.Objects;

public class Ch7Comparing {

  static void main() {
    String language = "Java";
    String prefix = "Ja";
    String built = prefix + "va";

    System.out.println(language == built);
    System.out.println(language.equals(built));
    System.out.println(language.equalsIgnoreCase("JAVA"));

    System.out.println("== compareTo()");
    System.out.println("Ada".compareTo("Alan"));
    System.out.println("ada".compareTo("Alan"));
    System.out.println("Ada".compareTo("Ada"));

    System.out.println("== null");
    String input = null;
    System.out.println("Java".equals(input));
    System.out.println(Objects.equals(input, "Java"));
    try {
      System.out.println(input.equals("Java"));
    } catch (NullPointerException e) {
      System.out.println(e.getClass().getName());
    }

    System.out.println("== switch");
    String command = "stop";
    switch (command) {
      case "start" -> System.out.println("Starting ...");
      case "stop" -> System.out.println("Stopping ...");
      default -> System.out.println("Unknown: " + command);
    }
  }
}
