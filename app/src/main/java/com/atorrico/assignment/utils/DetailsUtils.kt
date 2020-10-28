package com.atorrico.assignment.utils

import android.graphics.Bitmap
import android.graphics.Color
import androidx.palette.graphics.Palette
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

fun getYear(releaseDate: String): String {
    val year = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        LocalDate.parse(releaseDate).year.toString()
    } else {
        releaseDate.split("-")[0]
    }

    return year
}

fun getDominantColor(bitmap: Bitmap?): Int {
    val swatchesTemp = Palette.from(bitmap!!).generate().swatches
    val swatches: List<Palette.Swatch> = ArrayList(swatchesTemp)
    Collections.sort(swatches) { swatch1, swatch2 -> swatch2!!.population - swatch1!!.population }
    return if (swatches.isNotEmpty()) swatches[0].rgb else Color.BLUE
}