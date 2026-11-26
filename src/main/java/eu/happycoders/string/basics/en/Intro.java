package eu.happycoders.string.basics.en;

public class Intro {

  static void main() {
    String greeting = "Hello";
    String name = "Ada";
    String message = greeting + ", " + name + "!";

    System.out.println(message);
    System.out.println(message.length());
    System.out.println(message.toUpperCase());
  }
}
