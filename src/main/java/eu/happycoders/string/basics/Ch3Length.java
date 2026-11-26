package eu.happycoders.string.basics;

public class Ch3Length {

  static void main() {
    String word = "Java";
    System.out.println(word.length());
    System.out.println(word.charAt(0) + " " + word.charAt(3));

    System.out.println("== isEmpty() and isBlank()");
    System.out.println("".isEmpty() + " " + "".isBlank());
    System.out.println("   ".isEmpty() + " " + "   ".isBlank());

    System.out.println("== Iterating");
    for (int i = 0; i < word.length(); i++) {
      System.out.print("[" + word.charAt(i) + "]");
    }
    System.out.println();

    for (char c : word.toCharArray()) {
      System.out.print("[" + c + "]");
    }
    System.out.println();

    long count = word.chars().filter(c -> c == 'a').count();
    System.out.println(count);

    System.out.println("== Emoji");
    String text = "Hi 👋";
    System.out.println(text.length());
    System.out.println(text.codePointCount(0, text.length()));
    System.out.println(text.codePoints().count());
  }
}
