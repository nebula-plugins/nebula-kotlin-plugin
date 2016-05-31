package netflix.nebula

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinPlugin

class NebulaKotlinPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.with {
            plugins.apply('kotlin')

            def kotlinVersion = loadKotlinVersion()

            dependencies {
                compile "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
            }

            configurations.all({ configuration ->
                configuration.resolutionStrategy.eachDependency { details ->
                    def requested = details.requested
                    if (requested.group.equals("org.jetbrains.kotlin") && requested.version.isEmpty()) {
                        details.useTarget("${requested.group}:${requested.name}:$kotlinVersion")
                    }
                }
            })
        }
    }

    static def loadKotlinVersion() {
        def props = new Properties()
        def propFileName = "project.properties"
        def inputStream = KotlinPlugin.getClassLoader().getResourceAsStream(propFileName)

        if (inputStream == null) {
            throw new FileNotFoundException("property file '$propFileName' not found in the classpath")
        }

        props.load(inputStream)

        def projectVersion = props["project.version"] as String
        return projectVersion
    }
}
