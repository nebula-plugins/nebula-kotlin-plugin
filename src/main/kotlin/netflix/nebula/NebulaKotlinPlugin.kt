package netflix.nebula

class NebulaKotlinPlugin : NebulaBaseKotlinPlugin() {
    override fun isNodepPlugin(): Boolean {
        return false
    }
}
