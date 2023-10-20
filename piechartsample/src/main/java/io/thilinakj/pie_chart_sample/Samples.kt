package io.thilinakj.pie_chart_sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.thilinakj.pie_chart.ChartInfoView
import io.thilinakj.pie_chart.PieChart
import io.thilinakj.pie_chart.PieChartData
import io.thilinakj.pie_chart_sample.ui.theme.Color14
import io.thilinakj.pie_chart_sample.ui.theme.Color1
import io.thilinakj.pie_chart_sample.ui.theme.Color2
import io.thilinakj.pie_chart_sample.ui.theme.Color3
import io.thilinakj.pie_chart_sample.ui.theme.Color4
import io.thilinakj.pie_chart_sample.ui.theme.Color5
import io.thilinakj.pie_chart_sample.ui.theme.Color6


fun getSampleScreens(): List<Pair<String, @Composable (() -> Unit)>> = listOf(
    "Simple Filled Chart Example" to { SimpleFilledChartExample() },
    "Simple Donut Chart Example" to { SimpleDonutChartExample() },

    "Input Type Raw Data Chart Example" to { InputTypeRawDataChartExample() },
    "Input Type Angle Data Chart Example" to { InputTypeAngleDataChartExample() },
    "Input Type Percentage Data Chart Example" to { InputTypePercentageDataChartExample() },

    "Pie Chart With Fixed Size Example" to { PieChartWithFixedSizeExample() },
    "Pie Chart With Adaptive Size Example" to { PieChartWithAdaptiveSizeExample() },

    "Adjust Stroke Width In Donut Chart Example" to { AdjustStrokeWidthInDonutChartExample() },
    "Customize Animation State In Chart Example" to { CustomizeAnimationStateInChartExample() },
    "Customize Background In Chart Example" to { CustomizeBackgroundInChartExample() },

    "Chart With Segment Info Example" to { ChartWithSegmentInfoExample() },
    "Chart With Customized Segment Info Example" to { ChartWithCustomizedSegmentInfoExample() },
)

@Composable
fun SimpleFilledChartExample() {
    PieChart(
        modifier = Modifier
            .fillMaxWidth(fraction = 0.4f),
        chartData = PieChartData(
            records = getSampleRecordData(),
            recordValueType = PieChartData.RecordValueType.RawData,
            chartType = PieChartData.ChartType.PieChartFilled,
        ),
    )
}

@Composable
fun SimpleDonutChartExample() {
    PieChart(
        modifier = Modifier
            .fillMaxWidth(fraction = 0.4f),
        chartData = PieChartData(
            records = getSampleRecordData(),
            recordValueType = PieChartData.RecordValueType.RawData,
            chartType = PieChartData.ChartType.PieChartDonut(40.dp),
        ),
    )
}

@Composable
fun InputTypeRawDataChartExample() {
    val records = mutableListOf(
        PieChartData.Record(Color5, "A", 10.0),
        PieChartData.Record(Color2, "B", 20.0),
        PieChartData.Record(Color3, "C", 40.0),
        PieChartData.Record(Color4, "D", 80.0),
        PieChartData.Record(Color1, "E", 160.0),
    )
    PieChart(
        modifier = Modifier
            .size(150.dp),
        chartData = PieChartData(
            records = records,
            recordValueType = PieChartData.RecordValueType.RawData,
            chartType = PieChartData.ChartType.PieChartFilled,
        ),
    )
}

@Composable
fun InputTypeAngleDataChartExample() {
    val records = mutableListOf(
        PieChartData.Record(Color1, "A", 10.0),
        PieChartData.Record(Color2, "B", 20.0),
        PieChartData.Record(Color3, "C", 40.0),
        PieChartData.Record(Color4, "D", 80.0),
        PieChartData.Record(Color5, "E", 160.0),
        PieChartData.Record(Color6, "F", 50.0),
    )
    PieChart(
        modifier = Modifier
            .size(150.dp),
        chartData = PieChartData(
            records = records,
            recordValueType = PieChartData.RecordValueType.Angle,
            chartType = PieChartData.ChartType.PieChartFilled,
        ),
    )
}

@Composable
fun InputTypePercentageDataChartExample() {
    val records = listOf(
        PieChartData.Record(Color1, "A", 10.0),
        PieChartData.Record(Color2, "B", 20.0),
        PieChartData.Record(Color3, "C", 40.0),
        PieChartData.Record(Color4, "D", 5.0),
        PieChartData.Record(Color5, "E", 15.0),
        PieChartData.Record(Color6, "F", 10.0),
    )
    PieChart(
        modifier = Modifier
            .size(150.dp),
        chartData = PieChartData(
            records = records,
            recordValueType = PieChartData.RecordValueType.Percentage,
            chartType = PieChartData.ChartType.PieChartFilled,
        ),
    )
}

@Composable
fun PieChartWithFixedSizeExample() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        PieChart(
            modifier = Modifier
                .size(100.dp),
            chartData = PieChartData(
                records = getSampleRecordData(),
                recordValueType = PieChartData.RecordValueType.RawData,
                chartType = PieChartData.ChartType.PieChartFilled,
            ),
        )
        PieChart(
            modifier = Modifier
                .size(100.dp),
            chartData = PieChartData(
                records = getSampleRecordData(),
                recordValueType = PieChartData.RecordValueType.RawData,
                chartType = PieChartData.ChartType.PieChartDonut(30.dp),
            ),
        )
    }
}

@Composable
fun PieChartWithAdaptiveSizeExample() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PieChart(
            modifier = Modifier
                .weight(4f),
            chartData = PieChartData(
                records = getSampleRecordData(),
                recordValueType = PieChartData.RecordValueType.RawData,
                chartType = PieChartData.ChartType.PieChartFilled,
            ),
        )
        PieChart(
            modifier = Modifier
                .weight(6f),
            chartData = PieChartData(
                records = getSampleRecordData(),
                recordValueType = PieChartData.RecordValueType.RawData,
                chartType = PieChartData.ChartType.PieChartDonut(30.dp),
            ),
        )
    }
}

@Composable
fun AdjustStrokeWidthInDonutChartExample() {
    var sliderPosition by remember { mutableStateOf(30f) }
    val recordData by rememberSaveable { mutableStateOf(getSampleRecordData()) }
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = "Stroke/Ring width: ${sliderPosition.toInt()} dp"
        )
        Slider(
            modifier = Modifier.padding(8.dp),
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 0f..100f,
            steps = 9
        )
    }
    PieChart(
        modifier = Modifier
            .size(200.dp),
        chartData = PieChartData(
            records = recordData,
            recordValueType = PieChartData.RecordValueType.RawData,
            chartType = PieChartData.ChartType.PieChartDonut(sliderPosition.dp),
        ),
    )
}

@Composable
fun CustomizeAnimationStateInChartExample() {
    var sliderPosition by rememberSaveable { mutableStateOf(1000f) }
    val recordData by rememberSaveable { mutableStateOf(getSampleRecordData()) }
    var showContent by remember { mutableStateOf(false) }
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = "Animation time: ${sliderPosition.toInt()} ms"
        )
        Slider(
            modifier = Modifier.padding(8.dp),
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 0f..5000f,
            onValueChangeFinished = {
                showContent = false
            },
            steps = 19
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            onClick = { showContent = !showContent }) {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = if (showContent) "Hide Charts" else "Show Charts"
            )
        }
        if (showContent) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                PieChart(
                    modifier = Modifier
                        .weight(1f),
                    chartData = PieChartData(
                        records = recordData,
                        recordValueType = PieChartData.RecordValueType.RawData,
                        chartType = PieChartData.ChartType.PieChartFilled,
                        animate = PieChartData.AnimationState.NoAnimation
                    ),
                )
                PieChart(
                    modifier = Modifier
                        .weight(1f),
                    chartData = PieChartData(
                        records = recordData,
                        recordValueType = PieChartData.RecordValueType.RawData,
                        chartType = PieChartData.ChartType.PieChartFilled,
                        animate = PieChartData.AnimationState.Animate(sliderPosition.toInt())
                    ),
                )
                PieChart(
                    modifier = Modifier
                        .weight(1f),
                    chartData = PieChartData(
                        records = recordData,
                        recordValueType = PieChartData.RecordValueType.RawData,
                        chartType = PieChartData.ChartType.PieChartDonut(30.dp),
                        animate = PieChartData.AnimationState.Animate(sliderPosition.toInt())
                    ),
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                    text = "No Animation"
                )
                Text(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                    text = "Filled Animation"
                )
                Text(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                    text = "Donut Animation"
                )
            }
        }
    }

}

@Composable
fun CustomizeBackgroundInChartExample() {
    PieChart(
        modifier = Modifier
            .background(Color14)
            .size(150.dp),
        chartData = PieChartData(
            records = getSampleRecordData(),
            recordValueType = PieChartData.RecordValueType.RawData,
            chartType = PieChartData.ChartType.PieChartDonut(30.dp),
        ),
    )
}

@Composable
fun ChartWithSegmentInfoExample() {
    val chartRecords = getSampleRecordData(amount = 8)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PieChart(
            modifier = Modifier
                .size(150.dp),
            chartData = PieChartData(
                records = chartRecords,
                recordValueType = PieChartData.RecordValueType.RawData,
                chartType = PieChartData.ChartType.PieChartDonut(30.dp),
                animate = PieChartData.AnimationState.NoAnimation
            ),
        )
        ChartInfoView(records = chartRecords)
    }
}

@Composable
fun ChartWithCustomizedSegmentInfoExample() {
    val chartRecords = getSampleRecordData(amount = 8)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PieChart(
            modifier = Modifier
                .size(150.dp),
            chartData = PieChartData(
                records = chartRecords,
                recordValueType = PieChartData.RecordValueType.RawData,
                chartType = PieChartData.ChartType.PieChartDonut(30.dp),
                animate = PieChartData.AnimationState.NoAnimation
            ),
        )
        ChartInfoView(
            records = chartRecords,
            horizontalArrangement = Arrangement.Center,
            itemView = { record: PieChartData.Record ->
                ElevatedButton(
                    modifier = Modifier.padding(horizontal = 3.dp),
                    onClick = { /* Do something! */ },
                ) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = "Localized description",
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                        tint = record.color,
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("My ${record.label}")
                }
            }
        )
    }
}
