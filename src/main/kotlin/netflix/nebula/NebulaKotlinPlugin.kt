package netflix.nebula

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.jetbrains.kotlin.gradle.plugin.KotlinPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
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

            val kotlinCompile = tasks.getByName("compileKotlin") as KotlinCompile
            val kotlinOptions = kotlinCompile.kotlinOptions
            afterEvaluate {
                val sourceCompatibility = convention.getPlugin(JavaPluginConvention::class.java).sourceCompatibility
                val jreSuffix = when {
                    sourceCompatibility == JavaVersion.VERSION_1_7 -> {
                        "-jre7"
                    }
                    sourceCompatibility >= JavaVersion.VERSION_1_8 -> {
                        kotlinOptions.jvmTarget = '1.8'
                        "-jre8"
                    }
                    else -> ""
                }
                dependencies.add("compile", "org.jetbrains.kotlin:kotlin-stdlib$jreSuffix:$kotlinVersion")
            }

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
