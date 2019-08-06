package com.harsh.officetime.font

import android.content.Context
import android.graphics.Typeface

class FontManager(private val context: Context) {

    fun getRobotoReverseItalic(): Typeface = Typeface.createFromAsset(context.assets, "font/RobotReaversItalic.ttf")

}