package netflix.nebula

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinPlugin
import java.io.FileNotFoundException
import java.util.*

class NebulaKotlinPlugin : Plugin<Project> {
    companion object {
        @JvmStatic
        fun loadKotlinVersion(): String {
            val props = Properties()
            val propFileName = "project.properties"
            val inputStream = KotlinPlugin::class.java.classLoader.getResourceAsStream(propFileName) ?: throw FileNotFoundException("property file '$propFileName' not found in the classpath")

            props.load(inputStream)

            val projectVersion = props ["project.version"] as String
            return projectVersion
        }
    }

    override fun apply(project: Project) {
        kotlin.with(project) {
            plugins.apply("kotlin")

            val kotlinVersion = loadKotlinVersion()

            repositories.maven { it.setUrl("https://dl.bintray.com/kotlin/kotlin-eap-1.1") }
            dependencies.add("compile", "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

            configurations.all({ configuration ->
                configuration.resolutionStrategy.eachDependency { details ->
                    val requested = details.requested
                    if (requested.group.equals("org.jetbrains.kotlin") && requested.version.isEmpty()) {
                        details.useTarget("${requested.group}:${requested.name}:$kotlinVersion")
                    }
                }
            })
        }
    }
}
