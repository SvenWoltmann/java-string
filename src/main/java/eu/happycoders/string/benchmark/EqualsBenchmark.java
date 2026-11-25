package eu.happycoders.string.benchmark;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

/**
 * {@code equals()} against {@code ==} on two Latin-1 strings of the same length. The results are
 * close to the resolution of JMH; compare them with {@code baseline()}.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(
    value = 5,
    jvmArgsAppend = {"-Xms4g", "-Xmx4g", "-XX:+AlwaysPreTouch"})
@State(Scope.Benchmark)
public class EqualsBenchmark {

  /** The relation between the two strings. */
  public enum Pair {
    SAME_OBJECT,
    EQUAL,
    DIFFERENT_AT_END
  }

  @Param private Pair pair;

  @Param({"20", "200"})
  private int length;

  private String first;
  private String second;

  @Setup
  public void setUp() {
    first = "a".repeat(length);
    second =
        switch (pair) {
          case SAME_OBJECT -> first;
          case EQUAL -> "a".repeat(length);
          case DIFFERENT_AT_END -> "a".repeat(length - 1) + "b";
        };
  }

  @Benchmark
  public boolean equalsMethod() {
    return first.equals(second);
  }

  @Benchmark
  @SuppressWarnings({"java:S4973", "StringEquality"})
  public boolean identity() {
    return first == second;
  }

  @Benchmark
  public boolean baseline() {
    return first != null;
  }
}
