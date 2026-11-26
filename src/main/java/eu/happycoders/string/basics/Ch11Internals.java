package eu.happycoders.string.basics;

public class Ch11Internals {

  static void main() {
    String a = "Java";
    String b = "Java";
    String c = new String("Java");
    String d = "Ja" + "va";
    String prefix = "Ja";
    String e = prefix + "va";

    System.out.println(a == b);
    System.out.println(a == c);
    System.out.println(a == d);
    System.out.println(a == e);

    final String constantPrefix = "Ja";
    System.out.println(a == constantPrefix + "va");

    System.out.println("== intern()");
    System.out.println(a == c.intern());
    System.out.println(a == e.intern());

    System.out.println("== hashCode()");
    System.out.println("Java".hashCode());
    System.out.println("Aa".hashCode() + " " + "BB".hashCode());
    System.out.println("".hashCode());
  }
}
