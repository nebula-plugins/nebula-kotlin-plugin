package netflix.nebula

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.attributes.Attribute
import org.gradle.api.plugins.JavaPluginConvention
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.FileNotFoundException
import java.util.*

class NebulaKotlinPlugin : Plugin<Project> {

    //TODO: keep track of https://youtrack.jetbrains.com/issue/KT-26834 and see if this gets fixed in 1.2.72 as assigned
    private val AFFECTED_KOTLIN_VERSIONS = arrayOf("1.2.70", "1.2.71")
    private val DEPENDENCIES_METADATA_SUFFIX = "DependenciesMetadata"
    private val AFFECTED_CONFIGURATIONS = listOf("apiDependenciesMetadata", "apiElements", "runtimeElements")

    companion object {
        @JvmStatic
        fun loadKotlinVersion(): String {
            val props = Properties()
            val propFileName = "project.properties"
            val inputStream = KotlinPluginWrapper::class.java.classLoader.getResourceAsStream(propFileName) ?: throw FileNotFoundException("property file '$propFileName' not found in the classpath")

            props.load(inputStream)

            val projectVersion = props ["project.version"] as String
            return projectVersion
        }
    }

    override fun apply(project: Project) {
        kotlin.with(project) {
            plugins.apply(KotlinPlatformJvmPlugin::class.java)

            val kotlinVersion = loadKotlinVersion()

            afterEvaluate {
                val kotlinOptions = tasks.filter { it is KotlinCompile }.map { (it as KotlinCompile).kotlinOptions }
                val sourceCompatibility = convention.getPlugin(JavaPluginConvention::class.java).sourceCompatibility
                val jdkSuffix = when {
                    sourceCompatibility == JavaVersion.VERSION_1_7 -> {
                        "-jdk7"
                    }
                    sourceCompatibility >= JavaVersion.VERSION_1_8 -> {
                        kotlinOptions.forEach { it.jvmTarget = "1.8" }
                        "-jdk8"
                    }
                    else -> ""
                }
                dependencies.add("compile", "org.jetbrains.kotlin:kotlin-stdlib$jdkSuffix:$kotlinVersion")
            }

            configurations.all({ configuration ->
                if(kotlinVersion in AFFECTED_KOTLIN_VERSIONS && configuration.name.endsWith(DEPENDENCIES_METADATA_SUFFIX)) {
                    if(configuration.name in AFFECTED_CONFIGURATIONS) {
                        configuration.attributes.attribute(KotlinPlatformType.attribute, KotlinPlatformType.jvm)
                    } else {
                        configuration.setCanBeResolved(false)
                    }
                }

                configuration.resolutionStrategy.eachDependency { details ->
                    val requested = details.requested
                    if (requested.group.equals("org.jetbrains.kotlin") && requested.version.isNullOrEmpty()) {
                        details.useTarget("${requested.group}:${requested.name}:$kotlinVersion")
                    }
                }
            })
        }
    }
}
