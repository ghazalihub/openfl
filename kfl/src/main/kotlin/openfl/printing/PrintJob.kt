package openfl.printing

import openfl.display.Sprite
import openfl.geom.Rectangle

class PrintJob {
    var orientation: PrintJobOrientation = PrintJobOrientation.PORTRAIT
    var pageHeight: Int = 0
    var pageWidth: Int = 0
    var paperHeight: Int = 0
    var paperWidth: Int = 0

    private val __pages = mutableListOf<Sprite>()

    fun addPage(sprite: Sprite, printArea: Rectangle? = null, options: PrintJobOptions? = null, frameNum: Int = 0) {
        __pages.add(sprite)
    }

    fun send() {
        // Implementation for JVM Printing API (java.awt.print) would go here.
        __pages.clear()
    }

    fun start(userInterface: Boolean = true): Boolean {
        // Mocking support for now to avoid blocking
        paperWidth = 612
        paperHeight = 792
        pageWidth = 576
        pageHeight = 756
        return true
    }

    companion object {
        val isSupported: Boolean = java.awt.GraphicsEnvironment.isHeadless().not()
    }
}

class PrintJobOptions(var printAsBitmap: Boolean = false)

enum class PrintJobOrientation {
    LANDSCAPE,
    PORTRAIT
}
