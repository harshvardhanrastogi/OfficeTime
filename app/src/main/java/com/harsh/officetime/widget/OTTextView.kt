package com.harsh.officetime.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.harsh.officetime.R
import com.harsh.officetime.anim.AnimationCreator
import com.harsh.officetime.font.FontProvider
import java.lang.ref.WeakReference

class OTTextView : AppCompatTextView, View.OnClickListener {

    private val fontProvider: FontProvider
    private var onClickListener: OnClickListener? = null
    internal var animate: Boolean = false

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

    override fun onClick(v: View?) {
        if (animate) {
            AnimationCreator.createScaleAnim(WeakReference(this), 0.92f).start()
        }
        onClickListener?.onClick(v)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(this)
        onClickListener = l
    }

}