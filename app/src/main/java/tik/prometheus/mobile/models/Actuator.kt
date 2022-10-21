package tik.prometheus.mobile.models

import tik.prometheus.mobile.utils.Utils
import tik.prometheus.rest.constants.ActuatorType
import tik.prometheus.rest.models.Greenhouse

class Actuator(
    var id: Long,
    var label: String?,
    var type: ActuatorType,
    var unit: String?,
    var localId: String,
    var greenhouse: Greenhouse? = null,
    var north: Float? = 0f,
    var west: Float? = 0f,
    var height: Float? = 0f,
    var state: ActuatorState = ActuatorState()
) {
    class ActuatorState(
        var running: Boolean = false
    ) {
        override fun toString(): String = Utils.reflectionToString(this)
    }

    override fun toString(): String = Utils.reflectionToString(this)
}