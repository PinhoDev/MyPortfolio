package se.linerotech.module202.project1.weatherActivity

class ConvertTime {

    fun convertAmToPm(localTime: String): String {
        val (hour, minute) = localTime.substringAfter(EMPTY).split(COLON)
        val period = if (hour.toInt() >= TWELVE) PM else AM

        val newHour = if (hour.toInt() > TWELVE) {
            (hour.toInt() - TWELVE).toString().padStart(2, ZERO)
        } else {
            hour
        }

        return newHour.plus(COLON).plus(minute).plus(period)
    }

    companion object {
        private const val EMPTY = ' '
        private const val COLON = ':'
        private const val ZERO = '0'
        private const val PM = " PM"
        private const val AM = " AM"
        private const val TWELVE = 12
    }
}
