# Java String

The code of the HappyCoders.eu articles on strings in Java – one package per article.

## Running the code

The code uses Java 25 language features and needs Java 25 or newer. Build it with `mvn package`.

## Java String – `eu.happycoders.string`

Memory measurements in `eu.happycoders.string.benchmark` (not JMH; class histograms after a full garbage collection):

* `CompactStringsMemory` – 1,000,000 distinct strings of 20 characters, once Latin-1 only, once with a euro sign
* `EqualStringsMemory` – 1,000,000 strings with 1,000 distinct values: as created (`plain`, optionally with `-XX:+UseStringDeduplication`) and interned (`intern`)

```
java -Xmx4g -cp target/benchmarks.jar eu.happycoders.string.benchmark.CompactStringsMemory
java -Xmx4g -XX:NativeMemoryTracking=summary -XX:+UseG1GC -XX:+UseStringDeduplication -cp target/benchmarks.jar eu.happycoders.string.benchmark.EqualStringsMemory plain
java -Xmx4g -XX:NativeMemoryTracking=summary -XX:+UseG1GC -cp target/benchmarks.jar eu.happycoders.string.benchmark.EqualStringsMemory intern
```

JMH benchmarks in the same package (`settled=false` is the first run of 2026-11-25; `settled=true` is not measured yet):

* `InternBenchmark` – `intern()` against `ConcurrentHashMap.putIfAbsent()` on strings that are already known
* `EqualsBenchmark` – `equals()` against `==`

```
java -jar target/benchmarks.jar "string.benchmark.(Intern|Equals)Benchmark" -rf json -rff results/string/apple-m5-pro/jmh-jdk27.json
```

Results (JDK 27, Apple M5 Pro): `results/string/apple-m5-pro/`
