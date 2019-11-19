package netflix.nebula

import nebula.test.IntegrationSpec

class NebulaKotlinPluginTestFixturesIntegrationSpec extends IntegrationSpec {
    String kotlinVersion

    def setup() {
        kotlinVersion = NebulaKotlinPlugin.loadKotlinVersion()
        buildFile << """\
        apply plugin: 'nebula.kotlin'
        apply plugin: 'java-test-fixtures'

        repositories {
            mavenCentral()
            maven { url 'https://dl.bintray.com/kotlin/kotlin-eap' }
        }
        """.stripIndent()
    }

    def 'jdk8 standard library is added to custom configuration'() {
        given:
        buildFile << """
        sourceCompatibility = JavaVersion.VERSION_1_8
        
        nebulaKotlin {
            stdlibConfigurations = ["implementation", "testFixturesImplementation"]
        }
        """

        when:
        def resultCompileClasspath = runTasksSuccessfully('dependencies', '--configuration', 'compileClasspath')

        then:
        resultCompileClasspath.standardOutput.contains("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

        when:
        def resultTestFixturesImplementation = runTasksSuccessfully('dependencies', '--configuration', 'testFixturesImplementation')

        then:
        resultTestFixturesImplementation.standardOutput.contains("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    }
}
