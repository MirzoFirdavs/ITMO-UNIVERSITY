package com.github.mirzofirdavs.animation

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.random.Random

class FlyingView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    class FlyingObject(
        var x: Int,
        var y: Int,
        var angle: Int,
        var size: Int
    ) {
        fun step() {
            x += Random.nextInt(0, 3)
            y += Random.nextInt(0, 3)
            angle += 5
            if (angle == 360) {
                angle = 0
            }
        }
    }

    private var valueImage: Drawable
    private var flyingObject: FlyingObject = FlyingObject(0, 0, 0, 100)

    init {
        val a: TypedArray = context.obtainStyledAttributes(
            attrs, R.styleable.FlyingView
        )
        try {
            valueImage = ContextCompat.getDrawable(
                context,
                a.getResourceId(R.styleable.FlyingView_valueImage, 0)
            )!!
        } finally {
            a.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val size = flyingObject.size
        valueImage.setBounds(
            -size,
            -size,
            0,
            0
        )
        flyingObject.step()
        if (flyingObject.x >= this@FlyingView.right + size ||
            flyingObject.y >= this@FlyingView.bottom + size
        ) {
            flyingObject.x = 0
            flyingObject.y = 0
            flyingObject.angle = 0
        }
        canvas.save()
        canvas.translate(flyingObject.x.toFloat(), flyingObject.y.toFloat())
        canvas.rotate(flyingObject.angle.toFloat())
        valueImage.draw(canvas)
        canvas.restore()
        invalidate()
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = state as SavedState
        super.onRestoreInstanceState(savedState.superState)
        flyingObject = savedState.flyingObject
    }

    override fun onSaveInstanceState(): Parcelable {
        return SavedState(super.onSaveInstanceState(), flyingObject)
    }

    class SavedState : BaseSavedState {
        var flyingObject: FlyingObject

        constructor(superState: Parcelable?, input: FlyingObject) : super(superState) {
            this.flyingObject = input
        }

        constructor(superState: Parcel) : super(superState) {
            this.flyingObject = FlyingObject(superState.readInt(), superState.readInt(), superState.readInt(), superState.readInt())
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(flyingObject.x)
            out.writeInt(flyingObject.y)
            out.writeInt(flyingObject.angle)
            out.writeInt(flyingObject.size)
        }

        companion object CREATOR : Parcelable.Creator<SavedState?> {
            override fun createFromParcel(source: Parcel): SavedState {
                return SavedState(source)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}