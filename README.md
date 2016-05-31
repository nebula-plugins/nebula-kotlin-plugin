# Nebula Kotlin Plugin

![Support Status](https://img.shields.io/badge/nebula-supported-brightgreen.svg)
[![Build Status](https://travis-ci.org/nebula-plugins/nebula-kotlin-plugin.svg?branch=master)](https://travis-ci.org/nebula-plugins/nebula-kotlin-plugin)
[![Coverage Status](https://coveralls.io/repos/nebula-plugins/nebula-kotlin-plugin/badge.svg?branch=master&service=github)](https://coveralls.io/github/nebula-plugins/nebula-kotlin-plugin?branch=master)
[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/nebula-plugins/nebula-kotlin-plugin?utm_source=badgeutm_medium=badgeutm_campaign=pr-badge)
[![Apache 2.0](https://img.shields.io/github/license/nebula-plugins/nebula-kotlin-plugin.svg)](http://www.apache.org/licenses/LICENSE-2.0)

Provides the Kotlin plugin via the Gradle plugin portal and allows Kotlin library versions to be omitted. It simplifies a basic Kotlin build script to:

```groovy
    plugins {
        id 'nebula.kotlin' version '1.0.0'
    } 

    repositories {
        mavenCentral()
    }

    dependencies {
        compile 'org.jetbrains.kotlin:kotlin-stdlib'
    }
```

In this case `kotlin-stdlib` will be automatically set to `1.0.0`.

# Quick Start

Refer to the [Gradle Plugin Portal](https://plugins.gradle.org/plugin/nebula.kotlin) for instructions on how to apply the plugin.
