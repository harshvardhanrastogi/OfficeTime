package com.harsh.officetime.font

import android.content.Context
import android.graphics.Typeface

class FontProvider(private val context: Context) {

    fun getRobotoReverseItalic(): Typeface = Typeface.createFromAsset(context.assets, "font/RobotReaversItalic.ttf")
    fun getDroidSerifRegular(): Typeface = Typeface.createFromAsset(context.assets, "font/DroidSerifRegular.ttf")
    fun getDroidSerifBold(): Typeface = Typeface.createFromAsset(context.assets, "font/DroidSerifBold.ttf")

}