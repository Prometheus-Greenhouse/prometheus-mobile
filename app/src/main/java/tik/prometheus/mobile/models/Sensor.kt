package tik.prometheus.mobile.models

class Sensor(
    var id: Int,
    var localId: String,
    var address: String?,
    var type: String?,
    var unit: String?,
    var topic: String,
)