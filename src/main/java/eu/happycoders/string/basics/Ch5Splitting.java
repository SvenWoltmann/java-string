package eu.happycoders.string.basics;

import java.util.Arrays;

public class Ch5Splitting {

  static void main() {
    String name = "Ada Lovelace";
    System.out.println(name.substring(4));
    System.out.println(name.substring(0, 3));

    System.out.println("== trim() and strip()");
    String padded = "\u2003Ada\u2003";
    System.out.println(padded.length());
    System.out.println(padded.trim().length());
    System.out.println(padded.strip().length());

    System.out.println("== split()");
    String csv = "Ada,Grace,,Alan,,";
    System.out.println(Arrays.toString(csv.split(",")));
    System.out.println(Arrays.toString(csv.split(",", -1)));
    System.out.println(Arrays.toString(csv.split(",", 2)));

    System.out.println(Arrays.toString("1.2.3".split(".")));
    System.out.println(Arrays.toString("1.2.3".split("\\.")));
    System.out.println(Arrays.toString("a|b".split("|")));

    System.out.println(Arrays.toString("1+2-3".splitWithDelimiters("[+-]", 0)));

    System.out.println("== lines()");
    System.out.println("eins\nzwei\r\ndrei".lines().toList());
  }
}
