# Nebula Kotlin Plugin


![Support Status](https://img.shields.io/badge/nebula-supported-brightgreen.svg)
[![Build Status](https://travis-ci.org/nebula-plugins/nebula-kotlin-plugin.svg?branch=master)](https://travis-ci.org/nebula-plugins/nebula-kotlin-plugin)
[![Coverage Status](https://coveralls.io/repos/nebula-plugins/nebula-kotlin-plugin/badge.svg?branch=master&service=github)](https://coveralls.io/github/nebula-plugins/nebula-kotlin-plugin?branch=master)
[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/nebula-plugins/nebula-kotlin-plugin?utm_source=badgeutm_medium=badgeutm_campaign=pr-badge)
[![Apache 2.0](https://img.shields.io/github/license/nebula-plugins/nebula-kotlin-plugin.svg)](http://www.apache.org/licenses/LICENSE-2.0)

Provides the Kotlin plugin via the Gradle plugin portal, and adds ergonomic improvements over the default plugin:

- Automatically depends on the standard library
- Allows Kotlin library versions to be omitted, inferring them automatically from the plugin version
- For Kotlin 1.1 and later, sets the `-jvm-target` and uses the jre standard library based on the `sourceCompatibility`
	- Use the https://github.com/nebula-plugins/gradle-java-cross-compile-plugin to set the `targetJdk` if desired
- Bundles the `kotlin-allopen` and `kotlin-noarg` plugins to allow them to be applied without adding them manually to the classpath

# Quick Start

Refer to the [Gradle Plugin Portal](https://plugins.gradle.org/plugin/nebula.kotlin) for instructions on how to apply the main plugin.

# Basic Build

The plugin simplifies a basic Kotlin build script to:

```groovy
plugins {
    id 'nebula.kotlin' version '1.3.60'
}

repositories {
    mavenCentral() // or jcenter()
}
```

## Additional library

```groovy
plugins {
    id 'nebula.kotlin' version '1.3.60'
}

repositories {
    mavenCentral() // or jcenter()
}

dependencies {
    implementation 'org.jetbrains.kotlin:kotlin-reflect'
}
```

## Default configuration ( >= 1.3.60)

There are cases were we only want to use kotlin for specific configurations and applying the plugin added `stdlib` to `implementation` by default.

Starting on `1.3.60`, it is possible to set the default configuration for `stdlib` via `stdlibConfiguration`. For example:

```groovy
plugins {
    id 'nebula.kotlin' version '1.3.60'
}

repositories {
    mavenCentral() // or jcenter()
}

dependencies {
    implementation 'org.jetbrains.kotlin:kotlin-reflect'
}

configurations {
   myConfig
}

nebulaKotlin {
     stdlibConfiguration = "myConfig"
}
```


# Nodep plugin ( >= 1.3.60)

This plugin will apply our opinions for default version of additional dependencies but won't add `stdlib` by default

```groovy
plugins {
    id 'nebula.kotlin-nodep' version '1.3.60'
}
```


The version for `kotlin-reflect` will be automatically set to match the Kotlin version (`1.3.60`).

## Caveats

- IntelliJ doesn't set the `-jvm-target` compiler option based on the Java SDK setting for the project, and can cause the compiler inlining failure if it tries to inline classes compiled with Gradle. Configure the JVM target in IntelliJ preferences to avoid this
