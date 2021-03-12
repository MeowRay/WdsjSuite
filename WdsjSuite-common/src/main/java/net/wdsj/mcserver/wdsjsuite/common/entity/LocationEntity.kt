package net.wdsj.mcserver.wdsjsuite.common.entity

/**
 * @author  Arthur
 * @date  2021/2/25 21:50
 * @version 1.0
 */
data class LocationEntity(
    val world: String,
    val x: Double,
    val y: Double,
    val z: Double,
    val pitch: Float = 0F,
    val yaw: Float = 0F
) {

    fun toLocationData(): String {
        return "$world,$x,$y,$z,$pitch,$yaw"
    }

    companion object {
        fun fromLocationData(string: String): LocationEntity {
            val split = string.split(",")
            if (split.size > 4) {
                return LocationEntity(
                    split[0],
                    split[1].toDouble(),
                    split[2].toDouble(),
                    split[3].toDouble(),
                    split[4].toFloat(),
                    split[5].toFloat()
                )
            }
            return LocationEntity(split[0], split[1].toDouble(), split[2].toDouble(), split[3].toDouble())
        }
    }

}