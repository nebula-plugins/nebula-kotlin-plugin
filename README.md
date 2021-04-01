# Nebula Kotlin Plugin

![Support Status](https://img.shields.io/badge/nebula-maintenance-orange.svg)
[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v/https/plugins.gradle.org/m2/com.netflix.nebula/nebula-kotlin-plugin/maven-metadata.xml.svg?label=gradlePluginPortal)](https://plugins.gradle.org/plugin/nebula.kotlin)
[![Maven Central](https://img.shields.io/maven-central/v/com.netflix.nebula/nebula-kotlin-plugin)](https://maven-badges.herokuapp.com/maven-central/com.netflix.nebula/nebula-kotlin-plugin)
![Build](https://github.com/nebula-plugins/nebula-kotlin-plugin/actions/workflows/nebula.yml/badge.svg)
[![Apache 2.0](https://img.shields.io/github/license/nebula-plugins/nebula-kotlin-plugin.svg)](http://www.apache.org/licenses/LICENSE-2.0)


This plugin is in maintenance mode but will continue to receive 1.2 and 1.3 Kotlin releases. JetBrains has deprecated the existing `jvm` plugin and replaced it with the `multiplatform` plugin. If you use 1.3.70 or later you'll receive the warning:
```
The 'org.jetbrains.kotlin.platform.*' plugins are deprecated and will no longer be available in Kotlin 1.4.
Please migrate the project to the 'org.jetbrains.kotlin.multiplatform' plugin. 
See: https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html
```

The multiplatform plugin is a complete migration from the legacy plugin and provides many of the ergonomic features, such as JVM target configuration and Kotlin library version management that this plugin provided. If you have a project that will move to 1.4 once it's released you should migrate to `multiplatform`.

# Features

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
    id 'nebula.kotlin' version '1.3.70'
}

repositories {
    mavenCentral() 
}
```

## Additional library

```groovy
plugins {
    id 'nebula.kotlin' version '1.3.70'
}

repositories {
    mavenCentral() 
}

dependencies {
    implementation 'org.jetbrains.kotlin:kotlin-reflect'
}
```

## Default configuration ( >= 1.3.70)

There are cases were we only want to use kotlin for specific configurations and applying the plugin added `stdlib` to `implementation` by default.

Starting on `1.3.70`, it is possible to set the default configurations for `stdlib` via `stdlibConfiguration`. For example:

```groovy
plugins {
    id 'nebula.kotlin' version '1.3.70'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.jetbrains.kotlin:kotlin-reflect'
}

configurations {
   myConfig
}

nebulaKotlin {
     stdlibConfigurations = ["myConfig"]
}
```

If you use kotlin buildscripts:

```
nebulaKotlin {
  stdlibConfigurations.set(listOf("implementation", "testFixturesImplementation"))
}
```


# Nodep plugin ( >= 1.3.70)

This plugin will apply our opinions for default version of additional dependencies but won't add `stdlib` by default

```groovy
plugins {
    id 'nebula.kotlin-nodep' version '1.3.70'
}
```


The version for `kotlin-reflect` will be automatically set to match the Kotlin version (`1.3.70`).

## Caveats

- IntelliJ doesn't set the `-jvm-target` compiler option based on the Java SDK setting for the project, and can cause the compiler inlining failure if it tries to inline classes compiled with Gradle. Configure the JVM target in IntelliJ preferences to avoid this
