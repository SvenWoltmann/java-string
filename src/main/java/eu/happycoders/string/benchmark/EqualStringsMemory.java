package eu.happycoders.string.benchmark;

import java.lang.management.ManagementFactory;
import java.util.Locale;
import javax.management.JMException;

/**
 * Not JMH: measures how much heap 1,000,000 strings with only 1,000 distinct values occupy. The
 * argument {@code plain} stores the strings as they were created, {@code intern} stores the result
 * of {@link String#intern()}. Run {@code plain} once without and once with {@code
 * -XX:+UseStringDeduplication}.
 *
 * <p>With {@code -XX:NativeMemoryTracking=summary}, it also prints the native memory of the string
 * table (category "Symbol", which includes the symbol table) and of the deduplication table.
 */
public class EqualStringsMemory {

  private static final int SIZE = 1_000_000;
  private static final int DISTINCT_VALUES = 1_000;

  // Kept in a field so that the strings stay reachable during the measurements.
  private static final String[] strings = new String[SIZE];

  static void main(String[] args) throws JMException, InterruptedException {
    boolean intern =
        switch (args.length == 1 ? args[0] : "") {
          case "plain" -> false;
          case "intern" -> true;
          default -> throw new IllegalArgumentException("Argument: plain or intern");
        };

    HeapUsage before = HeapUsage.measureUntilStable();
    String nativeBefore = nativeMemory();
    for (int i = 0; i < SIZE; i++) {
      String string = createString(i % DISTINCT_VALUES);
      strings[i] = intern ? string.intern() : string;
    }
    HeapUsage usage = HeapUsage.measureUntilStable().minus(before);

    System.out.println(
        "JDK "
            + Runtime.version()
            + " "
            + String.join(" ", ManagementFactory.getRuntimeMXBean().getInputArguments())
            + " "
            + args[0]);
    System.out.printf(
        Locale.ROOT,
        "String: %,d instances, %,d bytes%nbyte[]: %,d instances, %,d bytes%ntotal:  %,d bytes%n",
        usage.strings(),
        usage.stringBytes(),
        usage.byteArrays(),
        usage.byteArrayBytes(),
        usage.totalBytes());
    System.out.println(
        "two equal strings: == "
            + (strings[0] == strings[DISTINCT_VALUES]) // NOSONAR
            + ", equals() "
            + strings[0].equals(strings[DISTINCT_VALUES]));
    System.out.println("native before: " + nativeBefore);
    System.out.println("native after:  " + nativeMemory());
  }

  /** Creates a new string object with a byte array of its own: 20 Latin-1 characters. */
  private static String createString(int value) {
    char[] chars = "Customer group 00000".toCharArray();
    for (int position = 19, rest = value; position >= 15; position--, rest /= 10) {
      chars[position] = (char) ('0' + rest % 10);
    }
    return new String(chars);
  }

  private static String nativeMemory() throws JMException {
    return HeapUsage.diagnosticCommand("vmNativeMemory", "summary")
        .lines()
        .map(String::strip)
        .filter(line -> line.matches("-\\s+(Symbol|String Deduplication) \\(.*"))
        .map(line -> line.replaceAll("\\s+", " "))
        .reduce((a, b) -> a + "; " + b)
        .orElse("not tracked");
  }
}
