package com.harsh.attandance.font

import android.content.Context
import android.graphics.Typeface

class FontProvider(private val context: Context) {

    fun getRobotoReverseItalic(): Typeface = Typeface.createFromAsset(context.assets, "font/RobotReaversItalic.ttf")

}