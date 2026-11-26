package eu.happycoders.string.basics;

import java.util.Locale;

public class Ch4Searching {

  static void main() {
    String sentence = "Ein String ist eine Folge von Zeichen";

    System.out.println(sentence.contains("Folge"));
    System.out.println(sentence.indexOf("ein"));
    System.out.println(sentence.indexOf("ein", 20));
    System.out.println(sentence.lastIndexOf("e"));
    System.out.println(sentence.indexOf("Java"));
    System.out.println(sentence.startsWith("Ein") + " " + sentence.endsWith("Zeichen"));

    System.out.println("== Ignoring case");
    System.out.println(sentence.contains("string"));
    System.out.println(sentence.toLowerCase(Locale.ROOT).contains("string"));

    System.out.println("== matches()");
    System.out.println("12345".matches("\\d+"));
    System.out.println("123a5".matches("\\d+"));
  }
}
