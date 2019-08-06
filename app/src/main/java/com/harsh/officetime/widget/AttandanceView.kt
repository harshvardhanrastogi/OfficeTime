package com.harsh.officetime.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

class AttandanceView : ViewGroup {

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, -1)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    }

}