package eu.happycoders.string.basics;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class Ch10Converting {

  static void main() {
    int number = Integer.parseInt("42");
    String text = String.valueOf(number);
    System.out.println(number + 1);
    System.out.println(text + 1);

    double d = Double.parseDouble("3.14");
    System.out.println(d * 2);
    System.out.println(String.valueOf(true) + " " + String.valueOf('x'));

    System.out.println("== char[]");
    char[] chars = "Java".toCharArray();
    chars[0] = 'L';
    System.out.println(new String(chars));

    System.out.println("== byte[]");
    byte[] utf8 = "Grüße".getBytes(StandardCharsets.UTF_8);
    System.out.println(utf8.length);
    System.out.println(new String(utf8, StandardCharsets.UTF_8));

    System.out.println("== Array and list");
    String[] parts = "Ada,Grace,Alan".split(",");
    List<String> list = Arrays.asList(parts);
    System.out.println(list);
    System.out.println(String.join(";", list));
  }
}
