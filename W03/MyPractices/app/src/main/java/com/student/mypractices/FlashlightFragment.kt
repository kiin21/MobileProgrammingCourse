package com.student.mypractices

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment

class FlashlightFragment : Fragment() {

    private var isFlashlightOn = false
    private var cameraManager: CameraManager? = null
    private var cameraId: String? = null
    private var originalBrightness: Float = 0f

    private companion object {
        const val PREFS_NAME = "FlashlightPrefs"
        const val KEY_IS_FLASHLIGHT_ON = "is_flashlight_on"
        const val KEY_BACKGROUND_COLOR = "background_color"
    }

    private lateinit var rootContainer: FrameLayout
    private lateinit var btnPower: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_flashlight, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rootContainer = view.findViewById(R.id.rootContainer)
        btnPower = view.findViewById(R.id.btnPower)

        cameraManager = requireContext().getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            cameraId = cameraManager?.cameraIdList?.get(0)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

        originalBrightness = requireActivity().window.attributes.screenBrightness

        loadSavedState()

        btnPower.setOnClickListener {
            toggleFlashlight()
        }

        setupColorButtons(view)
    }

    private fun setupColorButtons(view: View) {
        view.findViewById<Button>(R.id.btnColorRed).setOnClickListener {
            setBackgroundColor(Color.RED)
        }
        view.findViewById<Button>(R.id.btnColorGreen).setOnClickListener {
            setBackgroundColor(Color.GREEN)
        }
        view.findViewById<Button>(R.id.btnColorBlue).setOnClickListener {
            setBackgroundColor(Color.BLUE)
        }
        view.findViewById<Button>(R.id.btnColorYellow).setOnClickListener {
            setBackgroundColor(Color.YELLOW)
        }
        view.findViewById<Button>(R.id.btnColorCyan).setOnClickListener {
            setBackgroundColor(Color.CYAN)
        }
        view.findViewById<Button>(R.id.btnColorMagenta).setOnClickListener {
            setBackgroundColor(Color.MAGENTA)
        }
        view.findViewById<Button>(R.id.btnColorWhite).setOnClickListener {
            setBackgroundColor(Color.WHITE)
        }
    }

    private fun toggleFlashlight() {
        if (cameraId != null) {
            try {
                isFlashlightOn = !isFlashlightOn
                cameraManager?.setTorchMode(cameraId!!, isFlashlightOn)
                updatePowerButtonIcon()
            } catch (e: CameraAccessException) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Flashlight not available", Toast.LENGTH_SHORT).show()
                fallbackBrightnessControl()
            }
        } else {
            Toast.makeText(requireContext(), "Flashlight not available", Toast.LENGTH_SHORT).show()
            fallbackBrightnessControl()
        }
    }

    private fun fallbackBrightnessControl() {
        isFlashlightOn = !isFlashlightOn
        val layoutParams: WindowManager.LayoutParams = requireActivity().window.attributes
        if (isFlashlightOn) {
            layoutParams.screenBrightness = 1.0f
            rootContainer.setBackgroundColor(Color.WHITE)
        } else {
            layoutParams.screenBrightness = originalBrightness
            rootContainer.setBackgroundColor(Color.BLACK)
        }
        requireActivity().window.attributes = layoutParams
        updatePowerButtonIcon()
    }

    private fun setBackgroundColor(color: Int) {
        rootContainer.setBackgroundColor(color)
        saveCurrentState()
    }

    private fun updatePowerButtonIcon() {
        val iconRes = if (isFlashlightOn) {
            android.R.drawable.presence_online
        } else {
            android.R.drawable.ic_lock_power_off
        }
        btnPower.setImageResource(iconRes)
    }

    private fun loadSavedState() {
        val prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        isFlashlightOn = prefs.getBoolean(KEY_IS_FLASHLIGHT_ON, false)
        val savedColor = prefs.getInt(KEY_BACKGROUND_COLOR, Color.BLACK)
        rootContainer.setBackgroundColor(savedColor)
        updatePowerButtonIcon()

        if (isFlashlightOn && cameraId != null) {
            try {
                cameraManager?.setTorchMode(cameraId!!, true)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        saveCurrentState()
        if (isFlashlightOn && cameraId != null) {
            try {
                cameraManager?.setTorchMode(cameraId!!, false)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
            isFlashlightOn = false
        }
    }

    private fun saveCurrentState() {
        val prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit {
            putBoolean(KEY_IS_FLASHLIGHT_ON, isFlashlightOn)
            val backgroundColor = (rootContainer.background as? ColorDrawable)?.color ?: Color.BLACK
            putInt(KEY_BACKGROUND_COLOR, backgroundColor)
        }
    }
}