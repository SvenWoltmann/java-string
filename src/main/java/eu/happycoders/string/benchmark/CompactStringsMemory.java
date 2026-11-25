package eu.happycoders.string.benchmark;

import java.lang.management.ManagementFactory;
import java.util.Locale;
import javax.management.JMException;

/**
 * Not JMH: measures how much heap 1,000,000 distinct strings of 20 characters occupy - once with
 * Latin-1 characters only and once with a single character outside Latin-1, the euro sign.
 *
 * <p>Run it with the default settings, with {@code -XX:-UseCompactObjectHeaders}, and with {@code
 * -XX:-CompactStrings}.
 */
public class CompactStringsMemory {

  private static final int SIZE = 1_000_000;

  // Kept in fields so that the strings stay reachable during the measurements.
  private static final String[] latin1 = new String[SIZE];
  private static final String[] utf16 = new String[SIZE];

  static void main() throws JMException {
    HeapUsage before = HeapUsage.measure();
    fill(latin1, '$');
    HeapUsage afterLatin1 = HeapUsage.measure();
    fill(utf16, '€');
    HeapUsage afterUtf16 = HeapUsage.measure();

    System.out.println(
        "JDK "
            + Runtime.version()
            + " "
            + String.join(" ", ManagementFactory.getRuntimeMXBean().getInputArguments()));
    print(latin1[0], afterLatin1.minus(before));
    print(utf16[0], afterUtf16.minus(afterLatin1));
  }

  private static void fill(String[] strings, char currency) {
    char[] chars = "Price: 0000000.00 in ".substring(0, 20).toCharArray();
    chars[19] = currency;
    for (int i = 0; i < strings.length; i++) {
      for (int position = 13, rest = i; position >= 7; position--, rest /= 10) {
        chars[position] = (char) ('0' + rest % 10);
      }
      strings[i] = new String(chars);
    }
  }

  private static void print(String sample, HeapUsage usage) {
    System.out.printf(
        Locale.ROOT,
        "%-22s String: %.1f bytes, byte[]: %.1f bytes, total: %.1f bytes per string%n",
        sample,
        (double) usage.stringBytes() / SIZE,
        (double) usage.byteArrayBytes() / SIZE,
        (double) usage.totalBytes() / SIZE);
  }
}
