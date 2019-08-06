package com.harsh.officetime.widget

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.harsh.officetime.font.FontManager

class TitleTextView : AppCompatTextView {

    private val fontManager: FontManager

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        fontManager = FontManager(context)
        setTypeface(fontManager.getRobotoReverseItalic(), Typeface.NORMAL)
    }



}