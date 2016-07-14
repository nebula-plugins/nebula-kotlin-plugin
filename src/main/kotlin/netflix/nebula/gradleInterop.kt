package netflix.nebula

import groovy.lang.Closure
import org.gradle.api.Action
import org.gradle.api.Project

inline fun <T> T.groovyClosure(crossinline call: () -> Unit) = object : Closure<Unit>(this) {
    @Suppress("unused")
    fun doCall() {
        call()
    }
}

inline fun <U> Project.action(crossinline call: U.() -> Unit) = Action<U> { call(it) }
