package com.harsh.officetime

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.harsh.officetime.font.FontProvider

class OTTextView : AppCompatTextView {

    private val fontProvider: FontProvider

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        fontProvider = FontProvider(context)
        val typeArray: TypedArray? = context.obtainStyledAttributes(attrs, R.styleable.OTTextView)
        val textStyle = typeArray?.getInt(R.styleable.OTTextView_android_textStyle, Typeface.NORMAL)
        val fontType = typeArray?.getInt(R.styleable.OTTextView_fontEnum, 0)
        typeArray?.recycle()

        when {
            fontType == 0 && textStyle == Typeface.NORMAL -> typeface = fontProvider.getDroidSerifRegular()
            fontType == 0 && textStyle == Typeface.BOLD -> typeface = fontProvider.getDroidSerifBold()
            fontType == 1 -> typeface = fontProvider.getRobotoReverseItalic()
        }
    }

}