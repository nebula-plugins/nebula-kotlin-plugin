package netflix.nebula

import nebula.test.IntegrationSpec

class NebulaKotlinPluginIntegrationSpec extends IntegrationSpec {
    String kotlinVersion

    def setup() {
        kotlinVersion = NebulaKotlinPlugin.loadKotlinVersion()
        buildFile << """\
        apply plugin: 'nebula.kotlin'

        repositories {
            mavenCentral()
            maven { url 'https://dl.bintray.com/kotlin/kotlin-eap-1.1' }
        }
        """.stripIndent()
    }

    def 'plugin applies'() {
        given:
        buildFile.delete()
        buildFile << """
        apply plugin: 'nebula.kotlin'
        """

        when:
        runTasksSuccessfully('help')

        then:
        noExceptionThrown()
    }

    def 'default standard library is added'() {
        given:
        buildFile << """
        sourceCompatibility = JavaVersion.VERSION_1_6
        """

        when:
        def result = runTasksSuccessfully('dependencies')

        then:
        result.standardOutput.contains("\\--- org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion\n")
    }

    def 'jdk7 standard library is added when source compatibility is VERSION_1_7'() {
        given:
        buildFile << """
        sourceCompatibility = JavaVersion.VERSION_1_7
        """

        when:
        def result = runTasksSuccessfully('dependencies')

        then:
        result.standardOutput.contains("\\--- org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion\n")
    }

    def 'jdk8 standard library is added when source compatibility is VERSION_1_8'() {
        given:
        buildFile << """
        sourceCompatibility = JavaVersion.VERSION_1_8
        """

        when:
        def result = runTasksSuccessfully('dependencies')

        then:
        result.standardOutput.contains("\\--- org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion\n")
    }

    def 'kotlin library versions are set if omitted'() {
        given:
        buildFile << """
        dependencies {
            compile "org.jetbrains.kotlin:kotlin-reflect"
        }
        """

        when:
        def result = runTasksSuccessfully('dependencies')

        then:
        result.standardOutput.contains("+--- org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion\n")
    }


    def 'plugin applies latest kotlin without conflicts while doing dependency lock'() {
        given:
        buildFile.delete()
        buildFile << """
        buildscript {
            repositories {
                maven {
                  url "https://plugins.gradle.org/m2/"
                }
            }
            dependencies {
                 classpath 'com.netflix.nebula:nebula-kotlin-plugin:1.2.70'
                 classpath "com.netflix.nebula:gradle-dependency-lock-plugin:6.1.1"
            }
        }

        repositories {
            mavenCentral()
        }
        
        subprojects {
                apply plugin: 'nebula.dependency-lock'
                apply plugin: 'nebula.kotlin'
                
                kotlin {
                    experimental {
                        coroutines 'enable'
                    }
                }
                
                dependencies {
                    testCompile 'junit:junit:latest.release'
                }
        }

 
        """
        addSubproject("sub1", """
            apply plugin: 'groovy'

            repositories {
                mavenCentral()
            }
            
            dependencies {
                implementation group: 'com.google.guava', name: 'guava', version: '26.0-jre'
            }
        """)

        addSubproject("sub2", """
            apply plugin: 'java-library'
            
            repositories {
                mavenCentral()
            }
            
            dependencies {
                compile group: 'com.google.guava', name: 'guava', version: '26.0-jre'
            }
        """)

        when:
        runTasksSuccessfully('sub1:generateLock', 'sub1:saveLock')

        then:
        noExceptionThrown()
    }
}
