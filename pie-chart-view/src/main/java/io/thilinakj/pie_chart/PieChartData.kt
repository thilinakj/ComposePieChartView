package io.thilinakj.pie_chart

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp

data class PieChartData(
    val records: List<Record>,
    val recordValueType: RecordValueType = RecordValueType.RawData,
    val chartType: ChartType = ChartType.PieChartFilled,
    val animate: AnimationState = AnimationState.Animate(),
) {
    data class Record(
        val color: Color,
        val label: String,
        val value: Double,
    ) {
        /**
         * @param angle Internal purposes only.
         * */
        internal var angle: Float = 0f
    }

    enum class RecordValueType {
        RawData,
        Angle,
        Percentage,
    }

    sealed class ChartType {
        object PieChartFilled : ChartType()
        class PieChartDonut(
            val strokeWidth: Dp,
            val strokeCap: StrokeCap = StrokeCap.Butt
        ) : ChartType()
    }

    sealed class AnimationState {
        object NoAnimation : AnimationState()
        class Animate(val timeOut: Int = 500) : AnimationState()
    }
}
