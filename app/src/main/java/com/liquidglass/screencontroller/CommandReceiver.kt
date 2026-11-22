package com.liquidglass.screencontroller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class CommandReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val svc = ControllerService.instance ?: return
        val action = intent.getStringExtra("action") ?: return
        when (action) {
            "tap" -> svc.tap(intent.getIntExtra("x", 0), intent.getIntExtra("y", 0))
            "longpress" -> svc.longPress(intent.getIntExtra("x", 0), intent.getIntExtra("y", 0), intent.getLongExtra("duration", 800))
            "swipe" -> svc.swipe(intent.getIntExtra("x", 0), intent.getIntExtra("y", 0), intent.getIntExtra("x2", 0), intent.getIntExtra("y2", 0), intent.getLongExtra("duration", 300))
        }
    }
}
