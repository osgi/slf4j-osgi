<h1>SLF4J Binding for OSGi Log Service</h1>

This workspace repository contains the SLF4J Binding for OSGi Log Service 1.4 and later.

## Contributing

Want to hack on slf4j-osgi? See [CONTRIBUTING.md](CONTRIBUTING.md) for information on building, testing and contributing changes.

They are probably not perfect, please let us know if anything feels
wrong or incomplete.

## Versions

### 1.7

slf4j-osgi versions 1.7.x are for use with slf4j-api versions 1.7.x.
The slf4j-osgi bundle is a fragment which attaches to the slf4j-api bundle to provide the ILoggerFactory implementation.

### 2.0

slf4j-osgi versions 2.0.x are for use with slf4j-api versions 2.0.x.
In slf4j 2.0, the mechanism by which slf4j-api locates the ILoggerFactory implementation was changed to use the ServiceLoader mechanism.

Both slf4j-api and slf4j-osgi now use the [OSGi Service Loader Mediator](https://docs.osgi.org/specification/osgi.cmpn/8.0.0/service.loader.html) specification to perform the mediation between the ServiceLoader consumer and provider.
When using slf4j-osgi, you will also need to make sure an implementation of the OSGi Service Loader Mediator specification, such as [Apache Aries SPI Fly](https://aries.apache.org/documentation/modules/spi-fly.html), is installed.
For slf4j-osgi, [`org.apache.aries.spifly.dynamic.framework.extension`](https://aries.apache.org/documentation/modules/spi-fly.html#_dynamic_weaving_framework_extension) is recommended.

## Building

We use Maven to build and the repo includes `mvnw`.
You can use your system `mvn` but we require a recent version.

- `./mvnw clean install` - Assembles and tests the project

[![Build Status](https://github.com/osgi/slf4j-osgi/workflows/CI%20Build/badge.svg)](https://github.com/osgi/slf4j-osgi/actions?query=workflow%3A%22CI%20Build%22)

## Repository

Snapshot slf4j-osgi artifacts are available from the Sonatype OSS snapshot repository:

[https://oss.sonatype.org/content/repositories/snapshots/](https://oss.sonatype.org/content/repositories/snapshots/)

## License

The contents of this repository are made available to the public under the terms of the [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0).
Bundles may depend on non Apache Licensed code.

