package eu.happycoders.string.benchmark;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
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
 * Deduplicating strings that are already known: {@link String#intern()} against a {@link
 * ConcurrentHashMap} with {@code putIfAbsent()}, single-threaded.
 *
 * <p>Every invocation creates a new string of 20 Latin-1 characters from a byte array, as a parser
 * would: the string is a new object and its hash code is not computed yet. {@code create()} does
 * only that and is the baseline; subtract it from the other two.
 *
 * <p>The strings are visited in shuffled order. Each forked JVM runs one benchmark method, so the
 * string table and the map are never filled at the same time.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(
    value = 5,
    jvmArgsAppend = {"-Xms4g", "-Xmx4g", "-XX:+AlwaysPreTouch"})
@State(Scope.Benchmark)
public class InternBenchmark {

  @Param({"1000", "100000", "1000000"})
  private int size;

  private byte[][] sources;
  private ConcurrentHashMap<String, String> map;

  /** Keeps the interned strings reachable: the string table holds them only weakly. */
  private String[] canonical;

  private int index;

  /** Fills the string table; only the benchmark methods that take this state need it. */
  @State(Scope.Benchmark)
  public static class InternedStrings {
    @Setup
    public void setUp(InternBenchmark benchmark) {
      for (int i = 0; i < benchmark.size; i++) {
        benchmark.canonical[i] = benchmark.createString(i).intern();
      }
    }
  }

  /** Fills the map; only the benchmark methods that take this state need it. */
  @State(Scope.Benchmark)
  public static class MappedStrings {
    @Setup
    public void setUp(InternBenchmark benchmark) {
      for (int i = 0; i < benchmark.size; i++) {
        String string = benchmark.createString(i);
        benchmark.map.put(string, string);
      }
    }
  }

  @Setup
  public void setUp() {
    List<byte[]> list = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      list.add("Customer no. %07d".formatted(i).getBytes(StandardCharsets.ISO_8859_1));
    }
    Collections.shuffle(list, new Random(42));
    sources = list.toArray(byte[][]::new);
    canonical = new String[size];
    map = new ConcurrentHashMap<>();
  }

  @Benchmark
  public String create() {
    return createString(nextIndex());
  }

  @Benchmark
  public String intern(InternedStrings ignored) {
    return createString(nextIndex()).intern();
  }

  @Benchmark
  public String putIfAbsent(MappedStrings ignored) {
    String string = createString(nextIndex());
    String existing = map.putIfAbsent(string, string);
    return existing != null ? existing : string;
  }

  private String createString(int i) {
    return new String(sources[i], StandardCharsets.ISO_8859_1);
  }

  private int nextIndex() {
    int current = index;
    index = current + 1 == size ? 0 : current + 1;
    return current;
  }
}
