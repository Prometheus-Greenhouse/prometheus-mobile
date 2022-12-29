package tik.prometheus.rest.constants

import ir.mirrajabi.searchdialog.core.Searchable

enum class GreenhouseType(var value: String) {
    NORMAL("NORMAL"),
    NaN("NaN");
    companion object{
        fun getModels(): ArrayList<GreenhouseTypeModel>{
            val types = GreenhouseType.values()
            return types.map { GreenhouseTypeModel(it) }.toCollection(ArrayList())
        }
    }
}

data class GreenhouseTypeModel(val greenhouseType: GreenhouseType):Searchable{
    override fun getTitle(): String {
        return greenhouseType.value
    }
}