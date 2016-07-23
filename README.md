[![Build Status](https://travis-ci.org/guusdk/libidn-java.svg?branch=master)](https://travis-ci.org/guusdk/libidn-java)&nbsp;[![Coverity Scan Build Status](https://scan.coverity.com/projects/9503/badge.svg)](https://scan.coverity.com/projects/guusdk-libidn-java)
# GNU IDN Library - Libidn (Java)

This is a Java port of the [GNU IDN Library](https://www.gnu.org/software/libidn/)

# Build instructions
This project requires Java 6 or later. To build the project, [Apache Maven](https://maven.apache.org) is used.

## Building project artifacts
To build the project artifacts, execute Maven from the root directory of the project:

```
$ mvn clean verify
```

Afterwards, the artifacts of interest will be generated in ```libidn/target```

## Signing project artifacts
Some distribution channels require project artifacts to be signed. To facilitate this, an optional Maven project profile is available, named ```sign```.

```
$ mvn clean verify -P sign
```

Signing artifacts requires the availability of GnuPG on the build system, configured with at least one key. The ```sign``` profile is implemented using the [Maven GPG plugin](http://maven.apache.org/plugins/maven-gpg-plugin/usage.html), where more configuration options are documented.

