package openfl.display

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class RenderingTest {
    @Test
    fun testBitmapData() {
        try {
            val bmd = BitmapData(100, 100, true, 0xFFFF0000L)
            assertEquals(100, bmd.width)
            assertEquals(100, bmd.height)

            // Skia color is often ARGB or BGRA depending on platform,
            // but let's check if it's initialized.
            val pixel = bmd.getPixel32(50, 50)
            // Red fill was 0xFFFF0000
            assertEquals(0xFFFF0000.toInt(), pixel)

            bmd.setPixel32(0, 0, 0xFF00FF00.toInt())
            assertEquals(0xFF00FF00.toInt(), bmd.getPixel32(0, 0))

            bmd.dispose()
        } catch (e: NoClassDefFoundError) {
             println("Skipping Skia-dependent test in headless environment: NoClassDefFoundError")
        } catch (e: ExceptionInInitializerError) {
             println("Skipping Skia-dependent test in headless environment: ExceptionInInitializerError")
        } catch (e: Throwable) {
            if (e.toString().contains("LibraryLoadException") || e.toString().contains("UnsatisfiedLinkError")) {
                println("Skipping Skia-dependent test in headless environment: $e")
            } else {
                throw e
            }
        }
    }

    @Test
    fun testStage() {
        val stage = Stage()
        stage.__resize(800, 600)
        assertEquals(800, stage.stageWidth)
        assertEquals(600, stage.stageHeight)
    }
}
