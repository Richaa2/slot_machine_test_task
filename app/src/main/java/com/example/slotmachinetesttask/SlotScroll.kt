package com.example.slotmachinetesttask

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.slotmachinetesttask.databinding.SlotImageScrollBinding

class SlotScroll : FrameLayout {
    private lateinit var binding: SlotImageScrollBinding

    private lateinit var eventEnd: EventEnd
    private var lastResult = 0
    private var oldValue = 0

    companion object {
        private const val ANIMATION_DURATION = 150
    }

    val value: Int
        get() = Integer.parseInt(binding.nextImage.tag.toString())

    fun setEventEnd(eventEnd: EventEnd) {
        this.eventEnd = eventEnd
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        binding = SlotImageScrollBinding.inflate(LayoutInflater.from(context), this, true)
        binding.nextImage.translationY = height.toFloat()
    }

    fun setRandomValue(image: Int, numRoll: Int) {

        binding.currentImage.animate()
            .translationY(-height.toFloat())
            .setDuration(ANIMATION_DURATION.toLong()).start()
        binding.nextImage.translationY = binding.nextImage.height.toFloat()
        binding.nextImage.animate()
            .translationY(0f).setDuration(ANIMATION_DURATION.toLong())
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator) {
                }

                override fun onAnimationStart(animation: Animator) {
                    if (binding.currentImage.visibility == INVISIBLE
                    ) {
                        binding.currentImage.visibility = VISIBLE
                    }
                }

                override fun onAnimationEnd(animation: Animator) {
                    setImage(binding.currentImage, oldValue % 5)
                    binding.currentImage.translationY = 0f
                    if (oldValue != numRoll) {
                        setRandomValue(image, numRoll)
                        oldValue++
                    } else {
                        lastResult = 0
                        oldValue = 0
                        binding.currentImage.visibility = INVISIBLE
                        setImage(binding.nextImage, image)
                        eventEnd.eventEnd(image % 5, numRoll)
                    }
                }

                override fun onAnimationCancel(animation: Animator) {
                }

            }).start()
    }

    private fun setImage(currentImage: ImageView?, value: Int) {
        when (value) {
            Utils.red -> currentImage?.setImageResource(R.drawable.red)
            Utils.green -> currentImage?.setImageResource(R.drawable.green)
            Utils.blue -> currentImage?.setImageResource(R.drawable.blue)
            Utils.q -> currentImage?.setImageResource(R.drawable.q)
            Utils.k -> currentImage?.setImageResource(R.drawable.k)
        }
        currentImage?.tag = value
        lastResult = value
    }
}