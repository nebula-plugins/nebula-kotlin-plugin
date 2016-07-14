# Nebula Kotlin Plugin

![Support Status](https://img.shields.io/badge/nebula-supported-brightgreen.svg)
[![Build Status](https://travis-ci.org/nebula-plugins/nebula-kotlin-plugin.svg?branch=master)](https://travis-ci.org/nebula-plugins/nebula-kotlin-plugin)
[![Coverage Status](https://coveralls.io/repos/nebula-plugins/nebula-kotlin-plugin/badge.svg?branch=master&service=github)](https://coveralls.io/github/nebula-plugins/nebula-kotlin-plugin?branch=master)
[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/nebula-plugins/nebula-kotlin-plugin?utm_source=badgeutm_medium=badgeutm_campaign=pr-badge)
[![Apache 2.0](https://img.shields.io/github/license/nebula-plugins/nebula-kotlin-plugin.svg)](http://www.apache.org/licenses/LICENSE-2.0)

Provides the Kotlin plugin via the Gradle plugin portal, automatically depends on the standard library, and allows Kotlin library versions to be omitted.

# Basic Build

The plugin simplifies a basic Kotlin build script to:

```groovy
plugins {
    id 'nebula.kotlin' version '1.0.2'
} 

repositories {
    mavenCentral() // or jcenter()
}
```

# Standard library

The plugin automatically adds the Kotlin standard library, and on Kotlin 1.1 and later, automatically selects the appropriate jre version for the library based on the `sourceCompatibility` configured for the project.

# Additional library

```groovy
plugins {
    id 'nebula.kotlin' version '1.0.2'
} 

repositories {
    mavenCentral() // or jcenter()
}

dependencies {
    compile 'org.jetbrains.kotlin:kotlin-reflect'
}
```

The version for `kotlin-reflect` will be automatically set to match the Kotlin version (`1.0.2`).

# Milestones

Milestone plugin versions are also available, but require the milestone bintray repository for the Kotlin Gradle plugin dependencies to resolve. For example, for the 1.1 milestones on On Gradle 2.14 and later, use:

```groovy
pluginRepositories {
    maven {
        url 'https://dl.bintray.com/kotlin/kotlin-eap-1.1'
    }
    gradlePluginPortal()
}
```

On earlier releases:

```groovy
buildscript {
    repositories.maven { url 'https://dl.bintray.com/kotlin/kotlin-eap-1.1' }
}
```
