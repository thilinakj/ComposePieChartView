package io.thilinakj.pie_chart_sample

import io.thilinakj.pie_chart.PieChartData
import io.thilinakj.pie_chart_sample.ui.theme.Colors
import kotlin.random.Random
import kotlin.random.nextInt

fun getSampleRecordData(amount : Int = Random.nextInt(4..12)):List<PieChartData.Record>{
    val colors = Colors.shuffled()
    val list = mutableListOf<PieChartData.Record>()
    for (i in 0 until amount){
        list.add( PieChartData.Record(colors[i], "Item $i", (i+1)*10.0))
    }
    return list
}