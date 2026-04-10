package openfl.profiler

object Telemetry {
    var connected: Boolean = false
        private set

    fun registerUserCount(name: String, count: Int) {
        // ...
    }

    fun sendMetric(name: String, value: Any) {
        // ...
    }

    fun sendSpanMetric(name: String, startTimestamp: Double, duration: Double, data: Any? = null) {
        // ...
    }
}
