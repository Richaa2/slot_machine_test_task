package com.example.slotmachinetesttask

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.slotmachinetesttask.databinding.SlotScrollBinding
import com.example.slotmachinetesttask.viewmodels.MainViewModel
import kotlin.random.Random

class MainActivity : AppCompatActivity(), EventEnd {
    private lateinit var binding: SlotScrollBinding
    private val viewModel: MainViewModel by viewModels()
    private var countDown = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.slot_scroll)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val orientation = resources.configuration.orientation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.windowInsetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContentView(binding.root)
        val image1 = binding.image1
        val image2 = binding.image2
        val image3 = binding.image3
        val image4 = binding.image4
        val image5 = binding.image5
        val image6 = binding.image6
        val image7 = binding.image7
        val image8 = binding.image8
        val image9 = binding.image9

        image1.setEventEnd(this@MainActivity)
        image2.setEventEnd(this@MainActivity)
        image3.setEventEnd(this@MainActivity)
        image4.setEventEnd(this@MainActivity)
        image5.setEventEnd(this@MainActivity)
        image6.setEventEnd(this@MainActivity)
        image7.setEventEnd(this@MainActivity)
        image8.setEventEnd(this@MainActivity)
        image9.setEventEnd(this@MainActivity)

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.rateFrameVertical?.minusFrame?.setOnClickListener {
                viewModel.minusRate(RATE_STEP)
            }
            binding.rateFrameVertical?.plusFrame?.setOnClickListener {
                viewModel.plusRate(RATE_STEP)
            }
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rateFrameHorizontal?.minusFrame?.setOnClickListener {
                viewModel.minusRate(RATE_STEP)
            }
            binding.rateFrameHorizontal?.plusFrame?.setOnClickListener {
                viewModel.plusRate(RATE_STEP)
            }
        }
        binding.button.setOnClickListener {
            if (countDown != 0) {
                return@setOnClickListener
            }
            if (((viewModel.balance.value ?: 0) >= (viewModel.rate.value ?: 0))
                && (viewModel.rate.value != 0)
            ) {
                image1.setRandomValue(
                    Random.nextInt(NUMBER_IMAGE),
                    Random.nextInt(NUMBER_ANIMATIONS).plus(MIN_NUMBER_ANIMATIONS)
                )
                image2.setRandomValue(
                    Random.nextInt(NUMBER_IMAGE),
                    Random.nextInt(NUMBER_ANIMATIONS).plus(MIN_NUMBER_ANIMATIONS)
                )
                image3.setRandomValue(
                    Random.nextInt(NUMBER_IMAGE),
                    Random.nextInt(NUMBER_ANIMATIONS).plus(MIN_NUMBER_ANIMATIONS)
                )
                image4.setRandomValue(
                    Random.nextInt(NUMBER_IMAGE),
                    Random.nextInt(NUMBER_ANIMATIONS).plus(MIN_NUMBER_ANIMATIONS)
                )
                image5.setRandomValue(
                    Random.nextInt(NUMBER_IMAGE),
                    Random.nextInt(NUMBER_ANIMATIONS).plus(MIN_NUMBER_ANIMATIONS)
                )
                image6.setRandomValue(
                    Random.nextInt(NUMBER_IMAGE),
                    Random.nextInt(NUMBER_ANIMATIONS).plus(MIN_NUMBER_ANIMATIONS)
                )
                image7.setRandomValue(
                    Random.nextInt(NUMBER_IMAGE),
                    Random.nextInt(NUMBER_ANIMATIONS).plus(MIN_NUMBER_ANIMATIONS)
                )
                image8.setRandomValue(
                    Random.nextInt(NUMBER_IMAGE),
                    Random.nextInt(NUMBER_ANIMATIONS).plus(MIN_NUMBER_ANIMATIONS)
                )
                image9.setRandomValue(
                    Random.nextInt(NUMBER_IMAGE),
                    Random.nextInt(NUMBER_ANIMATIONS).plus(MIN_NUMBER_ANIMATIONS)
                )
                viewModel.minusBalance(viewModel.rate.value ?: 0)
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.you_dont_have_enough_money),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun eventEnd(result: Int, count: Int) {
        if (countDown < 8) {
            countDown++
        } else {
            countDown = 0
            var spinCost = viewModel.rate.value ?: 0
            val image1Value = binding.image1.value
            val image2Value = binding.image2.value
            val image3Value = binding.image3.value
            val image4Value = binding.image4.value
            val image5Value = binding.image5.value
            val image6Value = binding.image6.value
            val image7Value = binding.image7.value
            val image8Value = binding.image8.value
            val image9Value = binding.image9.value
            if (image1Value == image2Value && image2Value == image3Value) {
                spinCost *= MULTIPLE_X5
            } else if (image1Value == image2Value || image2Value == image3Value || image1Value == image3Value) {
                spinCost *= MULTIPLE_X2
            }
            if (image4Value == image5Value && image5Value == image6Value) {
                spinCost *= MULTIPLE_X5
            } else if (image4Value == image5Value || image5Value == image6Value || image4Value == image6Value) {
                spinCost *= MULTIPLE_X2
            }
            if (image7Value == image8Value && image7Value == image9Value) {
                spinCost *= MULTIPLE_X5
            } else if (image7Value == image8Value || image8Value == image9Value || image7Value == image9Value) {
                spinCost *= MULTIPLE_X2
            }
            if (spinCost == viewModel.rate.value) {
                spinCost = 0
            }
            viewModel.plusBalance(spinCost)
            Toast.makeText(this, "You Win $spinCost points", Toast.LENGTH_SHORT).show()
        }
    }
}