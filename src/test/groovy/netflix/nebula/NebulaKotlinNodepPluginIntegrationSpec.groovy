package netflix.nebula


import nebula.test.IntegrationSpec

class NebulaKotlinNodepPluginIntegrationSpec extends IntegrationSpec {
    String kotlinVersion

    def setup() {
        kotlinVersion = NebulaKotlinPlugin.loadKotlinVersion()
        buildFile << """\
        apply plugin: 'nebula.kotlin-nodep'

        repositories {
            mavenCentral()
            maven { url 'https://dl.bintray.com/kotlin/kotlin-eap' }
        }
        """.stripIndent()
    }

    def 'plugin applies'() {
        given:
        buildFile.delete()
        buildFile << """
        apply plugin: 'nebula.kotlin-nodep'
        """

        when:
        runTasksSuccessfully('help')

        then:
        noExceptionThrown()
    }

    def 'default standard library is added to test only'() {
        given:
        buildFile << """
        sourceCompatibility = JavaVersion.VERSION_1_6
        """

        when:
        def resultCompileClasspath = runTasksSuccessfully('dependencies', '--configuration', 'compileClasspath')

        then:
        !resultCompileClasspath.standardOutput.contains("\\--- org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion\n")
    }

    def 'jdk7 standard library is added when source compatibility is VERSION_1_7 for test configuration only'() {
        given:
        buildFile << """
        sourceCompatibility = JavaVersion.VERSION_1_7
        """

        when:
        def resultCompileClasspath = runTasksSuccessfully('dependencies', '--configuration', 'compileClasspath')

        then:
        !resultCompileClasspath.standardOutput.contains("\\--- org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion\n")
    }

    def 'jdk8 standard library is added when source compatibility is VERSION_1_8 for test configuration only'() {
        given:
        buildFile << """
        sourceCompatibility = JavaVersion.VERSION_1_8
        """

        when:
        def resultCompileClasspath = runTasksSuccessfully('dependencies', '--configuration', 'compileClasspath')

        then:
        !resultCompileClasspath.standardOutput.contains("\\--- org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion\n")
    }
}
