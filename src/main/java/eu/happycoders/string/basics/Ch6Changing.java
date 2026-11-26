package eu.happycoders.string.basics;

import java.util.Locale;

public class Ch6Changing {

  static void main() {
    System.out.println("1.000.000".replace(".", ""));
    System.out.println("1.000.000".replaceAll(".", ""));
    System.out.println("1.000.000".replaceAll("\\.", ""));

    System.out.println("a1b22c333".replaceAll("\\d+", "#"));
    System.out.println("a1b22c333".replaceFirst("\\d+", "#"));

    System.out.println("Hallo".replace("l", ""));

    System.out.println("== Upper and lower case");
    System.out.println("Straße".toUpperCase(Locale.ROOT));
    Locale turkish = Locale.forLanguageTag("tr");
    System.out.println("title".toUpperCase(turkish));
    System.out.println("TITLE".toLowerCase(turkish));
    System.out.println("title".toUpperCase(Locale.ROOT));

    System.out.println("== repeat() and reverse");
    System.out.println("-".repeat(10));
    System.out.println(new StringBuilder("Hallo").reverse().toString());

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
