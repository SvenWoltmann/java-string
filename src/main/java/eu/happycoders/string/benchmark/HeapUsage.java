package eu.happycoders.string.benchmark;

import java.lang.management.ManagementFactory;
import javax.management.JMException;
import javax.management.ObjectName;

/**
 * Instances and bytes of {@code String} and {@code byte[]} on the heap, read from a class histogram
 * - the same as {@code jcmd <pid> GC.class_histogram}, which runs a full garbage collection first.
 */
record HeapUsage(long strings, long stringBytes, long byteArrays, long byteArrayBytes) {

  static HeapUsage measure() throws JMException {
    long strings = 0;
    long stringBytes = 0;
    long byteArrays = 0;
    long byteArrayBytes = 0;
    for (String line : diagnosticCommand("gcClassHistogram").lines().toList()) {
      String[] fields = line.trim().split("\\s+");
      if (fields.length >= 4 && fields[3].equals("java.lang.String")) {
        strings = Long.parseLong(fields[1]);
        stringBytes = Long.parseLong(fields[2]);
      } else if (fields.length >= 4 && fields[3].equals("[B")) {
        byteArrays = Long.parseLong(fields[1]);
        byteArrayBytes = Long.parseLong(fields[2]);
      }
    }
    return new HeapUsage(strings, stringBytes, byteArrays, byteArrayBytes);
  }

  /**
   * Measures until the number of byte arrays has stayed the same (within 100) for five measurements
   * in a row, half a second apart. String deduplication runs concurrently after a garbage
   * collection, so a single measurement could come too early.
   */
  static HeapUsage measureUntilStable() throws JMException, InterruptedException {
    HeapUsage last = measure();
    int stable = 0;
    while (stable < 5) {
      Thread.sleep(500);
      HeapUsage current = measure();
      stable = Math.abs(current.byteArrays - last.byteArrays) <= 100 ? stable + 1 : 0;
      last = current;
    }
    return last;
  }

  HeapUsage minus(HeapUsage other) {
    return new HeapUsage(
        strings - other.strings,
        stringBytes - other.stringBytes,
        byteArrays - other.byteArrays,
        byteArrayBytes - other.byteArrayBytes);
  }

  long totalBytes() {
    return stringBytes + byteArrayBytes;
  }

  static String diagnosticCommand(String operation, String... arguments) throws JMException {
    return (String)
        ManagementFactory.getPlatformMBeanServer()
            .invoke(
                new ObjectName("com.sun.management:type=DiagnosticCommand"),
                operation,
                new Object[] {arguments},
                new String[] {String[].class.getName()});
  }
}
