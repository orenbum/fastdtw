fastdtw
=======
<a href="https://travis-ci.org/davidmoten/fastdtw"><img src="https://travis-ci.org/davidmoten/fastdtw.svg"/></a><br/>
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.davidmoten/fastdtw/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/com.github.davidmoten/fastdtw)

An enhancement of Stan Salvador and Philip Chan's *FastDTW* dynamic time warping [implementation](https://code.google.com/p/fastdtw/).

Status: *pre-alpha*

Improvements sought for this massaging of the original project are

* mavenize
* add maven site plugins
* add generic types
* remove unused methods, classes
* improve encapsulation
* remove Abstract and Band DTW techniques (because FastDTW is better)
* close resources properly
* add junit tests (the project had no automated unit tests)
* refactor package structure
* deploy to Maven Central for the convenience of all
* reformat whitespace
* add javadocs
* trim API to essentials
* support non-file input data
* add immutability
* add JMH benchmarks

How to use
----------------
To calculate the distance between two ```TimeSeries```:

Create the two ```TimeSeries``` objects using the builder:

```java
TimeSeries ts1 = TimeSeriesBase.builder()
                   .add(0, 123.4)
                   .add(1, 125)
                   .add(2, 126.3)
                   .build();
```

Then calculate the distance:

```java
import com.fastdtw.dtw.FastDTW;
import com.fastdtw.util.Distances;

double distance = FastDTW.getWarpInfoBetween(ts1, ts2, 10, Distances.EUCLIDEAN_DISTANCE))
                         .getDistance();
```

Benchmarks
---------------
To run benchmarks:

```
mvn clean install -P benchmark
```
