package com.example.acera.theforum.Behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**
 * Created by acera on 2018/1/2.
 *
 */
class ScrollAwareFABBehavior(context: Context, attrs: AttributeSet) : FloatingActionButton.Behavior()
{
    private var mIsAnimatingOut = false

    companion object
    {
        private val INTERPOLATOR = FastOutSlowInInterpolator()
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, directTargetChild: View, target: View, axes: Int, type: Int): Boolean
    {
        // Ensure we react to vertical scrolling
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int)
    {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
        if (dyConsumed > 0 && !this.mIsAnimatingOut && child.visibility == View.VISIBLE)
        {
            // User scrolled down and the FAB is currently visible -> hide the FAB
            animateOut(child)
        } else if (dyConsumed < 0 && child.visibility != View.VISIBLE)
        {
            // User scrolled up and the FAB is currently not visible -> show the FAB
            child.visibility = View.VISIBLE
            animateIn(child)
        }
    }

    // Same animation that FloatingActionButton.Behavior uses to hide the FAB when the AppBarLayout exits
    private fun animateOut(button: FloatingActionButton)
    {
        ViewCompat.animate(button).translationY((button.height + getMarginBottom(button)).toFloat()).setInterpolator(INTERPOLATOR).withLayer()
                .setListener(object : ViewPropertyAnimatorListener
                {
                    override fun onAnimationStart(view: View)
                    {
                        this@ScrollAwareFABBehavior.mIsAnimatingOut = true
                    }

                    override fun onAnimationCancel(view: View)
                    {
                        this@ScrollAwareFABBehavior.mIsAnimatingOut = false
                    }

                    override fun onAnimationEnd(view: View)
                    {
                        this@ScrollAwareFABBehavior.mIsAnimatingOut = false
                        view.visibility = View.INVISIBLE
                    }
                }).start()
    }

    // Same animation that FloatingActionButton.Behavior uses to show the FAB when the AppBarLayout enters
    private fun animateIn(button: FloatingActionButton)
    {
        button.visibility = View.VISIBLE
        ViewCompat.animate(button).translationY(0f)
                .setInterpolator(INTERPOLATOR).withLayer().setListener(null)
                .start()
    }

    private fun getMarginBottom(v: View): Int
    {
        var marginBottom = 0
        val layoutParams = v.layoutParams
        if (layoutParams is ViewGroup.MarginLayoutParams)
        {
            marginBottom = layoutParams.bottomMargin
        }
        return marginBottom
    }
}