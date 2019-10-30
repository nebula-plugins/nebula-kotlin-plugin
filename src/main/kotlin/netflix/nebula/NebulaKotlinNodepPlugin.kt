package netflix.nebula

class NebulaKotlinNodepPlugin : NebulaBaseKotlinPlugin() {
    override fun isNodepPlugin(): Boolean {
        return true
    }
}
