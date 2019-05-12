package com.buddman.zepespot.models

import android.graphics.Color

data class Course (
        var sequence_name : String,
        var sequence_number : Int,
        var latitude : Float,
        var longitude : Float,
        var photo : String,
        var cleared : Boolean,
        var isSelected : Boolean = false
) {
    val bgColor: Int
        get() = Color.parseColor(if (isSelected) "#5773ff" else "#353a50")
}