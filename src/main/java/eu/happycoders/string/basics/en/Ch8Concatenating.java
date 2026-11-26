package eu.happycoders.string.basics.en;

import java.util.List;
import java.util.stream.Collectors;

public class Ch8Concatenating {

  static void main() {
    String first = "Ada";
    String last = "Lovelace";
    int count = 3;

    System.out.println(first + " " + last);
    System.out.println("Count: " + count);
    System.out.println(1 + 2 + "3");
    System.out.println("1" + 2 + 3);

    String text = "Ada";
    text += " Lovelace";
    System.out.println(text);
    System.out.println(first.concat(" ").concat(last));

    System.out.println("== join()");
    System.out.println(String.join(", ", "Ada", "Grace", "Alan"));
    List<String> names = List.of("Ada", "Grace", "Alan");
    System.out.println(String.join(", ", names));
    System.out.println(names.stream().collect(Collectors.joining(", ", "[", "]")));

    System.out.println("== StringBuilder");
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <= 5; i++) {
      sb.append(i).append(' ');
    }
    System.out.println(sb.toString().strip());
  }
}
