package eu.happycoders.string.basics.en;

import java.util.Locale;

public class Ch4Searching {

  static void main() {
    String sentence = "A String is a sequence of characters";

    System.out.println(sentence.contains("sequence"));
    System.out.println(sentence.indexOf("a"));
    System.out.println(sentence.indexOf("a", 13));
    System.out.println(sentence.lastIndexOf("e"));
    System.out.println(sentence.indexOf("Java"));
    System.out.println(sentence.startsWith("A") + " " + sentence.endsWith("characters"));

    System.out.println("== Ignoring case");
    System.out.println(sentence.contains("string"));
    System.out.println(sentence.toLowerCase(Locale.ROOT).contains("string"));

    System.out.println("== matches()");
    System.out.println("12345".matches("\\d+"));
    System.out.println("123a5".matches("\\d+"));
  }
}
