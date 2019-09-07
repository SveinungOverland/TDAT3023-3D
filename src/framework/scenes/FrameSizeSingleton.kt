package framework.scenes

import java.awt.Dimension

object FrameSizeSingleton {
    var X = 0
    var Y = 0
    var WIDTH = 1000
    var HEIGHT = 800

    fun getDimension(): Dimension = Dimension(WIDTH, HEIGHT)
}