package eu.happycoders.string.basics;

import java.nio.charset.StandardCharsets;

public class Ch1Creating {

  static void main() {
    String literal = "Java";

    char[] chars = {'J', 'a', 'v', 'a'};
    String fromChars = new String(chars);

    byte[] bytes = {74, 97, 118, 97};
    String fromBytes = new String(bytes, StandardCharsets.UTF_8);

    System.out.println(literal + " " + fromChars + " " + fromBytes);

    System.out.println("== Escape sequences");
    System.out.println("Sie sagte: \"Hallo\"");
    System.out.println("C:\\Users\\ada");
    System.out.println("Name:\tAda\nName:\tGrace");
    System.out.println("Kaffee: \u2615");

    System.out.println("== Text block");
    String json =
        """
        {
          "name": "Ada"
        }
        """;
    System.out.print(json);

    System.out.println("== stripIndent() and indent()");
    String sql = "    SELECT name\n      FROM users";
    System.out.println(sql.stripIndent());
    System.out.print(sql.stripIndent().indent(2));

    System.out.println("== translateEscapes()");
    String raw = "Name:\\tAda\\nName:\\tGrace";
    System.out.println(raw);
    System.out.println(raw.translateEscapes());
  }
}
