package com.harsh.officetime.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.harsh.officetime.font.FontProvider

class OTEditText : AppCompatEditText {

    constructor (context: Context) : super(context) {
        typeface = FontProvider(context).getDroidSerifBold()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        typeface = FontProvider(context).getDroidSerifBold()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle) {
        typeface = FontProvider(context).getDroidSerifBold()
    }


}