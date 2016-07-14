package netflix.nebula

import nebula.test.IntegrationSpec

class NebulaKotlinPluginIntegrationSpec extends IntegrationSpec {
    String kotlinVersion

    def setup() {
        kotlinVersion = NebulaKotlinPlugin.loadKotlinVersion()
    }

    def 'plugin applies'() {
        given:
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
        apply plugin: 'nebula.kotlin'

        repositories {
            mavenCentral()
        }

        sourceCompatibility = JavaVersion.VERSION_1_6
        """

        when:
        def result = runTasksSuccessfully('dependencies')

        then:
        result.standardOutput.contains("\\--- org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion\n")
    }

    def 'jre7 standard library is added when source compatibility is VERSION_1_7'() {
        given:
        buildFile << """
        apply plugin: 'nebula.kotlin'

        repositories {
            mavenCentral()
        }

        sourceCompatibility = JavaVersion.VERSION_1_7
        """

        when:
        def result = runTasksSuccessfully('dependencies')

        then:
        result.standardOutput.contains("\\--- org.jetbrains.kotlin:kotlin-stdlib-jre7:$kotlinVersion\n")
    }

    def 'jre8 standard library is added when source compatibility is VERSION_1_8'() {
        given:
        buildFile << """
        apply plugin: 'nebula.kotlin'

        repositories {
            mavenCentral()
        }

        sourceCompatibility = JavaVersion.VERSION_1_8
        """

        when:
        def result = runTasksSuccessfully('dependencies')

        then:
        result.standardOutput.contains("\\--- org.jetbrains.kotlin:kotlin-stdlib-jre8:$kotlinVersion\n")
    }

    def 'kotlin library versions are set if omitted'() {
        given:
        buildFile << """
        apply plugin: 'nebula.kotlin'

        repositories {
            mavenCentral()
        }

        dependencies {
            compile "org.jetbrains.kotlin:kotlin-reflect"
        }
        """

        when:
        def result = runTasksSuccessfully('dependencies')

        then:
        result.standardOutput.contains("+--- org.jetbrains.kotlin:kotlin-reflect: -> $kotlinVersion\n")
    }
}
