package eu.happycoders.string.basics.en;

public class Ch2Immutable {

  static void main() {
    String name = "ada";
    name.toUpperCase();
    System.out.println(name);

    name = name.toUpperCase();
    System.out.println(name);
  }
}
