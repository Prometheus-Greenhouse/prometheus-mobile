package tik.prometheus.rest.constants

import ir.mirrajabi.searchdialog.core.Searchable

enum class SensorType(val value: String) {
    HUMIDITY("HUMIDITY"),
    TEMPERATURE("TEMPERATURE"),
    SOIL_MOISTURE("SOIL_MOISTURE"),
    WATER("WATER"),
    NaN("NaN");

    companion object {
        fun getModels(): ArrayList<SensorTypeModel> {
            val types = SensorType.values()
            return types.map { SensorTypeModel(it) }.toCollection(ArrayList())
        }
    }
}

data class SensorTypeModel(val sensorType: SensorType) : Searchable {
    override fun getTitle(): String {
        return sensorType.value
    }
}