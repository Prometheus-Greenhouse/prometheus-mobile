package tik.prometheus.mobile.utils

import ir.mirrajabi.searchdialog.core.Searchable

enum class UnitSystem(val value: String) {
    IMPERIAL("Imperial"),
    METRIC("Metric"),
    ANY("ANY")
}

enum class UnitGroup(val value: String) {
    LENGTH_DISTANCE("Length, Distance"),
    MASS("Mass"),
    DURATION("Duration"),
    VOLUME("Volume"),
    TEMPERATURE("Temperature"),
    SPEED("Speed"),
    DATA_VOLUME("Data Volume"),
    ELECTRICITY("Electricity"),
    OTHER("Other"),
}

enum class EUnit(val value: String, val annotate: String, val system: UnitSystem, val group: UnitGroup) {
    INCH("Inch", "in", UnitSystem.IMPERIAL, UnitGroup.LENGTH_DISTANCE),
    FOOT("Foot", "ft", UnitSystem.IMPERIAL, UnitGroup.LENGTH_DISTANCE),
    YARD("Yard", "yd", UnitSystem.IMPERIAL, UnitGroup.LENGTH_DISTANCE),
    MILE("Mile", "mi", UnitSystem.IMPERIAL, UnitGroup.LENGTH_DISTANCE),
    SQUAREFEET("Square Feet", "sq ft", UnitSystem.IMPERIAL, UnitGroup.LENGTH_DISTANCE),
    MILLIMETER("Millimeter", "mm", UnitSystem.METRIC, UnitGroup.LENGTH_DISTANCE),
    CENTIMETER("Centimeter", "cm", UnitSystem.METRIC, UnitGroup.LENGTH_DISTANCE),
    METER("Meter", "m", UnitSystem.METRIC, UnitGroup.LENGTH_DISTANCE),
    KILOMETER("Kilometer", "km", UnitSystem.METRIC, UnitGroup.LENGTH_DISTANCE),


    OUNCE("Ounce", "oz", UnitSystem.IMPERIAL, UnitGroup.MASS),
    POUND("Pound", "lb", UnitSystem.IMPERIAL, UnitGroup.MASS),
    STONE("Stone", "st", UnitSystem.IMPERIAL, UnitGroup.MASS),
    MASS_QUARTER("Quarter", "qrt", UnitSystem.IMPERIAL, UnitGroup.MASS),
    HUNDREDWEIGHT("Hundredweight", "cwt", UnitSystem.IMPERIAL, UnitGroup.MASS),
    TON("Ton", "t", UnitSystem.IMPERIAL, UnitGroup.MASS),

    MILLIGRAM("Milligram", "mg", UnitSystem.METRIC, UnitGroup.MASS),
    GRAM("Gram", "g", UnitSystem.METRIC, UnitGroup.MASS),
    KILOGRAM("Kilogram", "kg", UnitSystem.METRIC, UnitGroup.MASS),


    YEAR("Year(s)", "yr", UnitSystem.ANY, UnitGroup.DURATION),
    DURATION_QUARTER("Quarter", "qrt", UnitSystem.ANY, UnitGroup.DURATION),
    MONTH("Month(s)", "mo", UnitSystem.ANY, UnitGroup.DURATION),
    WEEK("Week(s)", "wk", UnitSystem.ANY, UnitGroup.DURATION),
    DAY("Day(s)", "d", UnitSystem.ANY, UnitGroup.DURATION),
    HOUR("Hour(s)", "hr", UnitSystem.ANY, UnitGroup.DURATION),
    MINUTE("Minute(s)", "min", UnitSystem.ANY, UnitGroup.DURATION),
    SECOND("Second(s)", "s", UnitSystem.ANY, UnitGroup.DURATION),
    MILLISECOND("Millisecond", "ms", UnitSystem.ANY, UnitGroup.DURATION),


    PINT("Pint", "pt", UnitSystem.IMPERIAL, UnitGroup.VOLUME),
    GALLON("Gallon", "gal", UnitSystem.IMPERIAL, UnitGroup.VOLUME),


    Liter("Liter", "l", UnitSystem.METRIC, UnitGroup.VOLUME),
    MILLILITER("Milliliter", "ml", UnitSystem.METRIC, UnitGroup.VOLUME),
    CUBIC_METER("Cubic Meter", "m³", UnitSystem.METRIC, UnitGroup.VOLUME),


    CELSIUS("Celsius", "°C", UnitSystem.ANY, UnitGroup.TEMPERATURE),
    FAHRENHEIT("Fahrenheit", "°F", UnitSystem.ANY, UnitGroup.TEMPERATURE),
    KELVIN("Kelvin", "K", UnitSystem.ANY, UnitGroup.TEMPERATURE),

    KILOMETER_PER_HOUR("Kilometer Per Hour", "km/h", UnitSystem.ANY, UnitGroup.SPEED),
    MILLIMETER_PER_HOUR("Millimeter Per Hour", "mm/h", UnitSystem.ANY, UnitGroup.SPEED),
    INCHES("Inches Per Hour", "in/h", UnitSystem.ANY, UnitGroup.SPEED),
    CUBIC_METERS_PERHOUR("Cubic Meters Per Hour", "m3/h", UnitSystem.ANY, UnitGroup.SPEED),
    MILE_PER_HOUR("Mile Per Hour", "mph", UnitSystem.ANY, UnitGroup.SPEED),
    METER_PER_SECOND("Meter Per Second", "m/s", UnitSystem.ANY, UnitGroup.SPEED),

    KILO("Kilo Bytes", "kb", UnitSystem.ANY, UnitGroup.DATA_VOLUME),
    MEGA("Mega Bytes", "mb", UnitSystem.ANY, UnitGroup.DATA_VOLUME),
    GIGA("Giga Bytes", "gb", UnitSystem.ANY, UnitGroup.DATA_VOLUME),

    VOLT("Volt", "V", UnitSystem.ANY, UnitGroup.ELECTRICITY),
    AMPERE("Ampere", "A", UnitSystem.ANY, UnitGroup.ELECTRICITY),
    MILLIAMPERE("Milliampere", "mA", UnitSystem.ANY, UnitGroup.ELECTRICITY),
    MICROAMPERE("Microampere", "uA", UnitSystem.ANY, UnitGroup.ELECTRICITY),
    OHM("Ohm", "Ohm", UnitSystem.ANY, UnitGroup.ELECTRICITY),
    HERTZ("Hertz", "Hz", UnitSystem.ANY, UnitGroup.ELECTRICITY),
    WATTS("Watts", "W", UnitSystem.ANY, UnitGroup.ELECTRICITY),
    KILOWATTS("Kilowatts", "kW", UnitSystem.ANY, UnitGroup.ELECTRICITY),
    KILOWATT("Kilowatt Hour", "kWh", UnitSystem.ANY, UnitGroup.ELECTRICITY),
    FARAD("Farad", "F", UnitSystem.ANY, UnitGroup.ELECTRICITY),
    SIEMEN("Siemen", "S", UnitSystem.ANY, UnitGroup.ELECTRICITY),
    HENRY("Henry", "H", UnitSystem.ANY, UnitGroup.ELECTRICITY),

    PERCENTAGE("Percentage", "%", UnitSystem.ANY, UnitGroup.OTHER),
    RPM("RPM", "rpm", UnitSystem.ANY, UnitGroup.OTHER),
    Degrees("Degrees", "°", UnitSystem.ANY, UnitGroup.OTHER),
    GRAM_PER_SQUARE_METER("Gram Per Square Meter", "g/m²", UnitSystem.ANY, UnitGroup.OTHER),
    MILLILITTER_PER_SQUARE_METER("Millilitter Per Square Meter", "ml/m²", UnitSystem.ANY, UnitGroup.OTHER),
    CUBIC("Cubic Centimeters Per Minute", "ccm", UnitSystem.ANY, UnitGroup.OTHER),
    LITTER("Litter Per Square Meter", "l/m²", UnitSystem.ANY, UnitGroup.OTHER),
    SIGNAL("Signal Strength", "dBm", UnitSystem.ANY, UnitGroup.OTHER),
    VOLUME("Volume Flow", "l/min", UnitSystem.ANY, UnitGroup.OTHER),
    POUND_PER_SQUARE_INCH("Pound Per Square Inch ", "lbf/in²", UnitSystem.ANY, UnitGroup.OTHER),
    POUND_PER_SQURE_INCH("Pound Per Square Inch ", "psi", UnitSystem.ANY, UnitGroup.OTHER),
    MICROGRAM_PER_CUBIC_METER("Microgram Per Cubic Meter", "ug/m³", UnitSystem.ANY, UnitGroup.OTHER),
    MILLIGRAM_PER_CUBIC_METER("Milligram Per Cubic Meter", "mg/m³", UnitSystem.ANY, UnitGroup.OTHER),
    PARTS_PER_MILLION("Parts Per Million", "ppm", UnitSystem.ANY, UnitGroup.OTHER),
    PARTS_PER_BILLION("Parts Per Billion", "ppb", UnitSystem.ANY, UnitGroup.OTHER),
    HECTOPASCAL("Hectopascal", "hPa", UnitSystem.ANY, UnitGroup.OTHER),
    LUX("Lux", "lx", UnitSystem.ANY, UnitGroup.OTHER),
    PASCAL("Pascal", "Pa", UnitSystem.ANY, UnitGroup.OTHER),
    KILOPASCAL("Kilopascal", "kPa", UnitSystem.ANY, UnitGroup.OTHER),
    PRESSURE_KGM("Pressure", "kg/m²", UnitSystem.ANY, UnitGroup.OTHER),
    PRESSURE_BAR("Pressure", "bar", UnitSystem.ANY, UnitGroup.OTHER),
    AIR("Air Quality Index", "AQI", UnitSystem.ANY, UnitGroup.OTHER);

    override fun toString(): String {
        return "\t %s, %s".format(value, annotate)
    }

    companion object {
        fun getModels(): ArrayList<UnitModel> {
            val units = EUnit.values();
            val groups = UnitGroup.values()

            val unitModels = arrayListOf<UnitModel>()
            for (g in groups) {
                unitModels.add(UnitModel.GroupItem(g))
                // ANY
                val items = units.filter { it.group == g && it.system == UnitSystem.ANY }.map { UnitModel.UnitItem(it) }
                unitModels.addAll(items)

                // Imperial
                val imperialItems = units.filter { it.group == g && it.system == UnitSystem.IMPERIAL }.map { UnitModel.UnitItem(it) }
                if (imperialItems.isNotEmpty()) {
                    unitModels.add(UnitModel.SystemItem(UnitSystem.IMPERIAL))
                    unitModels.addAll(imperialItems)
                }

                // Metric
                val metricItems = units.filter { it.group == g && it.system == UnitSystem.METRIC }.map { UnitModel.UnitItem(it) }
                if (metricItems.isNotEmpty()) {
                    unitModels.add(UnitModel.SystemItem(UnitSystem.METRIC))
                    unitModels.addAll(metricItems)
                }
            }
            return unitModels
        }
    }
}


sealed class UnitModel : Searchable {
    data class UnitItem(val unit: EUnit) : UnitModel() {

        override fun getTitle(): String {
            return unit.toString()
        }
    }

    data class GroupItem(val group: UnitGroup) : UnitModel() {

        override fun getTitle(): String {
            return group.value
        }
    }

    data class SystemItem(val system: UnitSystem) : UnitModel() {
        override fun getTitle(): String {
            return "-- %s".format(system.value)
        }

    }
}