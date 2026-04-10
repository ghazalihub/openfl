package openfl.printing

import openfl.display.Sprite
import openfl.geom.Rectangle

class PrintJob {
    var orientation: PrintJobOrientation = PrintJobOrientation.PORTRAIT
    var pageHeight: Int = 0
    var pageWidth: Int = 0
    var paperHeight: Int = 0
    var paperWidth: Int = 0

    fun addPage(sprite: Sprite, printArea: Rectangle? = null, options: PrintJobOptions? = null, frameNum: Int = 0) {
        // ... implementation
    }

    fun send() {
        // ... implementation
    }

    fun start(userInterface: Boolean = true): Boolean {
        return false
    }

    companion object {
        val isSupported: Boolean = false
    }
}

class PrintJobOptions(var printAsBitmap: Boolean = false)

enum class PrintJobOrientation {
    LANDSCAPE,
    PORTRAIT
}
