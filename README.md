fastdtw
=======
<a href="https://travis-ci.org/davidmoten/fastdtw"><img src="https://travis-ci.org/davidmoten/fastdtw.svg"/></a><br/>
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.davidmoten/fastdtw/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/com.github.davidmoten/fastdtw)

Based on Stan Salvador and Philip Chan's "FastDTW" dynamic time warping [implementation](https://code.google.com/p/fastdtw/).

Improvements sought for this massaging of the original project are

* mavenize
* add maven site plugins
* add generic types
* remove unused methods, classes
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