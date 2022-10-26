package tik.prometheus.rest.models

import tik.prometheus.mobile.models.Actuator
import tik.prometheus.mobile.models.Sensor


class Greenhouse(
    var id: Long,
    var farmId: Long? = null,
    var type: String? = "NaN",
    var area: Float? = Float.NaN,
    var height: Float? = Float.NaN,
    var width: Float? = Float.NaN,
    var length: Float? = Float.NaN,
    var cultivationArea: Float? = Float.NaN,
    var label: String? = null,
    var sensors: List<Sensor> = emptyList(),
    var actuators: List<Actuator> = emptyList()
)

class GreenhouseReq(
    var id: Long? = null,
    var farmId: Long? = null,
    var type: String,
    var area: Float,
    var label: String? = null,
    var height: Float,
    var width: Float,
    var length: Float,
    var cultivationArea: Float,
)