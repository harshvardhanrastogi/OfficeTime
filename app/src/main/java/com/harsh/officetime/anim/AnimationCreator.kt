package com.harsh.officetime.anim

import android.animation.AnimatorSet
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import java.lang.ref.WeakReference

object AnimationCreator {

    fun createTouchAnim(viewRef: WeakReference<View>?, scaleFactor: Float): AnimatorSet {

        val scaleDownAnim = ValueAnimator()
        val p1 = PropertyValuesHolder.ofFloat("scaleX", 1f, scaleFactor)
        val p2 = PropertyValuesHolder.ofFloat("scaleY", 1f, scaleFactor)
        scaleDownAnim.setValues(p1, p2)

        val scaleUpAnim = ValueAnimator()
        val p11 = PropertyValuesHolder.ofFloat("scaleX", scaleFactor, 1f)
        val p21 = PropertyValuesHolder.ofFloat("scaleY", scaleFactor, 1f)
        scaleUpAnim.setValues(p11, p21)

        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(scaleDownAnim, scaleUpAnim)
        animatorSet.duration = 200
        animatorSet.interpolator = BounceInterpolator()
        val animUpdateListener = ValueAnimator.AnimatorUpdateListener {
            val scaleX: Float = it.getAnimatedValue("scaleX") as Float
            val scaleY: Float = it.getAnimatedValue("scaleY") as Float
            viewRef?.get()?.scaleX = scaleX
            viewRef?.get()?.scaleY = scaleY
        }

        scaleDownAnim.addUpdateListener(animUpdateListener)
        scaleUpAnim.addUpdateListener(animUpdateListener)
        return animatorSet
    }

    fun createLineStretchAnimation(viewRef: WeakReference<View>?): ValueAnimator {
        val stretchAnim = ValueAnimator()
        val p1 = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.2f)
        val p2 = PropertyValuesHolder.ofFloat("scaleX", 0.2f, 1f)
        stretchAnim.setValues(p1, p2)
        stretchAnim.duration = 200
        stretchAnim.interpolator = AccelerateInterpolator()
        stretchAnim.addUpdateListener {
            val scaleX: Float = it.getAnimatedValue("scaleX") as Float
            viewRef?.get()?.scaleX = scaleX
        }
        return stretchAnim
    }
}