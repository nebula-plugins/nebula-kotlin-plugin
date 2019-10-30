package netflix.nebula

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property

open class NebulaKotlinExtension(objects: ObjectFactory) {
    val stdlibConfiguration: Property<String> = objects.property(String::class.java).convention("implementation")
}