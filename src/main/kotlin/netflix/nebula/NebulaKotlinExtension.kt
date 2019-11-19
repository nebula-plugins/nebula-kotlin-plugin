package netflix.nebula

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property

open class NebulaKotlinExtension(objects: ObjectFactory) {
    @Deprecated("Please use stdlibConfigurations instead")
    val stdlibConfiguration: Property<String> = objects.property(String::class.java)
    val stdlibConfigurations: ListProperty<String> = objects.listProperty(String::class.java).convention(listOf("implementation"))
}