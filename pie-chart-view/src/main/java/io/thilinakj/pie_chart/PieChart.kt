package io.thilinakj.pie_chart

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card

import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// if we want to stabilize the Pie Chart at y axis we should rotate canvas by 90 degrees in anti clockwise dir..
private const val defaultRotationAngle = -90f

// 90f is used to complete 1/4 of the rotation. By default canvas starts drawing from x axis.
private const val finalRotationAngle = 90f * 11f

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    chartData: PieChartData,
    errorView: @Composable (() -> Unit)? = null,
) {
    val visibleRecords: List<PieChartData.Record> = chartData.records.filter { it.value > 0 }
    val sumOfRecords = visibleRecords.sumOf { it.value }
    val showDataMalformedMessage: Boolean = when (chartData.recordValueType) {
        PieChartData.RecordValueType.RawData -> {
            sumOfRecords <= 0
        }

        PieChartData.RecordValueType.Angle -> {
            sumOfRecords <= 0 || sumOfRecords > 360
        }

        PieChartData.RecordValueType.Percentage -> {
            sumOfRecords <= 0 || sumOfRecords > 100
        }
    }
    if (showDataMalformedMessage) {
        return errorView?.invoke() ?: Text(
            modifier = Modifier.padding(start = 15.dp),
            text = "Data is malformed.",
            fontWeight = FontWeight.Medium,
            color = Color.Red,
        )
    }
    visibleRecords.forEach {
        it.angle = if (chartData.recordValueType == PieChartData.RecordValueType.Angle) {
            it.value.toFloat()
        } else {
            360 * it.value.toFloat() / sumOfRecords.toFloat()
        }
    }

    var animationPlayed by remember { mutableStateOf(false) }
    var animDuration = 1000
    val shouldAnimate = when (chartData.animate) {
        is PieChartData.AnimationState.Animate -> {
            animDuration = chartData.animate.timeOut
            // to play the animation only once when the function is Created or Recomposed
            LaunchedEffect(key1 = true) {
                animationPlayed = true
            }
            true
        }

        else -> {
            false
        }
    }

    val rotationAnimation by animateFloatAsState(
        targetValue = if (animationPlayed && shouldAnimate) finalRotationAngle else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing,
        ),
        label = "",
    )

    var componentWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    val scaleAnimation by animateFloatAsState(
        targetValue = if (animationPlayed) componentWidth.value else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing,
        ),
        label = "",
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                componentWidth = with(density) {
                    it.size.width.toDp()
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        var strokeWidth: Dp = 0.dp
        var strokeCap = StrokeCap.Butt
        val isDonutType = when (chartData.chartType) {
            is PieChartData.ChartType.PieChartDonut -> {
                strokeWidth = chartData.chartType.strokeWidth
                strokeCap = chartData.chartType.strokeCap
                true
            }

            else -> false
        }
        val viewPadding = if (isDonutType) strokeWidth / 2f else 0.dp
        Box(
            modifier = if (shouldAnimate)
                Modifier.size(scaleAnimation.dp) else Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Canvas(
                modifier = Modifier
                    .padding(viewPadding)
                    .aspectRatio(1 / 1f)
                    .fillMaxWidth()
                    .rotate(degrees = if (shouldAnimate) rotationAnimation else defaultRotationAngle),
            ) {
                var lastValue = 0f
                visibleRecords.forEach {
                    drawArc(
                        color = it.color,
                        startAngle = lastValue,
                        sweepAngle = it.angle,
                        useCenter = !isDonutType,
                        style = if (isDonutType) {
                            Stroke(
                                cap = strokeCap,
                                width = strokeWidth.toPx(),
                            )
                        } else {
                            Fill
                        },
                    )
                    lastValue += it.angle
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChartInfoView(
    modifier: Modifier = Modifier,
    records: List<PieChartData.Record>,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    itemView: @Composable ((record: PieChartData.Record) -> Unit)? = null,
) {
    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
    ) {
        records.forEach {
            itemView?.invoke(it) ?: ChartInfoItemView(
                record = it,
            )
        }
    }
}


@Composable
fun ChartInfoItemView(
    record: PieChartData.Record,
) {
    Card(
        modifier = Modifier.padding(5.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(record.color)
            )
            Spacer(Modifier.size(8.dp))
            Text(record.label)
        }
    }
}