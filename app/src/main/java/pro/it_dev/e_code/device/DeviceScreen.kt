package pro.it_dev.e_code.device

import android.content.Context
import android.graphics.Point
import android.hardware.display.DisplayManager
import android.view.Display

class DeviceScreen(context: Context) {
    var x: Int = 0
        private set
    var y: Int = 0
        private set
    var dp: Float = 0F
        private set
    init {
        initial(context = context)
    }

    private fun initial(display: Display, dp:Float ) {

        val size = Point()
        display.getRealSize(size)
        x = size.x
        y = size.y
        this.dp = dp
    }

    private fun initial (context: Context){
        val display = (context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager?)?.getDisplay(0) ?: return
        initial( display, context.resources.displayMetrics.density )
    }


    fun dp(size: Int): Int {
        return (size * dp).toInt()
    }
}