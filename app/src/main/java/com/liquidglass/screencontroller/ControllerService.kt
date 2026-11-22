package com.liquidglass.screencontroller

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.Context
import android.content.Intent
import android.graphics.Path
import android.os.Bundle
import android.os.Environment
import android.view.accessibility.AccessibilityEvent
import java.io.File

class ControllerService : AccessibilityService() {
    private val SCREEN_DIR = File(Environment.getExternalStorageDirectory(), "ScreenController")

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}
    override fun onInterrupt() {}

    fun tap(x: Int, y: Int) {
        val path = Path().apply { moveTo(x.toFloat(), y.toFloat()) }
        val stroke = GestureDescription.StrokeDescription(path, 0, 50)
        val gesture = GestureDescription.Builder().addStroke(stroke).build()
        dispatchGesture(gesture, null, null)
    }

    fun longPress(x: Int, y: Int, duration: Long = 800) {
        val path = Path().apply { moveTo(x.toFloat(), y.toFloat()) }
        val stroke = GestureDescription.StrokeDescription(path, 0, duration)
        val gesture = GestureDescription.Builder().addStroke(stroke).build()
        dispatchGesture(gesture, null, null)
    }

    fun swipe(x1: Int, y1: Int, x2: Int, y2: Int, duration: Long = 300) {
        val path = Path().apply { moveTo(x1.toFloat(), y1.toFloat()); lineTo(x2.toFloat(), y2.toFloat()) }
        val stroke = GestureDescription.StrokeDescription(path, 0, duration)
        val gesture = GestureDescription.Builder().addStroke(stroke).build()
        dispatchGesture(gesture, null, null)
    }

    fun screenshot(): Boolean {
        return try {
            SCREEN_DIR.mkdirs()
            val out = File(SCREEN_DIR, "screen.png")
            val proc = Runtime.getRuntime().exec("screencap -p ${out.absolutePath}")
            proc.waitFor(5, java.util.concurrent.TimeUnit.SECONDS)
            out.exists()
        } catch (e: Exception) {
            false
        }
    }

    companion object {
        var instance: ControllerService? = null
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}
