package netflix.nebula

import nebula.test.IntegrationSpec

class NebulaKotlinPluginIntegrationSpec extends IntegrationSpec {
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

    def 'kotlin library versions are set if omitted'() {
        given:
        buildFile << """
        apply plugin: 'nebula.kotlin'

        repositories {
            mavenCentral()
        }

        dependencies {
            compile "org.jetbrains.kotlin:kotlin-stdlib"
        }
        """

        when:
        def result = runTasksSuccessfully('dependencies')

        then:
        result.standardOutput.contains("\\--- org.jetbrains.kotlin:kotlin-stdlib: -> 1.0.0\n")
    }
}
