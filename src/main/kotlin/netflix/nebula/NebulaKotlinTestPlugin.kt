package netflix.nebula

class NebulaKotlinTestPlugin : NebulaBaseKotlinPlugin() {
    override fun isOnlyTestPlugin(): Boolean {
        return true
    }
}
