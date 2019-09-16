package Oppgave2.DelB

import com.jogamp.opengl.glu.GLU
import com.jogamp.opengl.util.gl2.GLUT
import framework.scenes.BasicCamera
import framework.scenes.autoVerticalSplitFromCameras
import scenes.*
import shapes.Point
import shapes.cross
import shapes.plus
import shapes.times
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JFrame
import javax.swing.WindowConstants

val glu = GLU()
val glut = GLUT()

val scale = 1f
var index = 0

val cameras = autoVerticalSplitFromCameras(
        BasicCamera(Point.Z(15f)),
        BasicCamera(Point.X(15f)),
        BasicCamera(Point(15f, 0f, 15f))
)

val scene = BasicScene(object : SceneConfig {
    override val constructor: SceneAccess = {

        addKeyListener(object : KeyAdapter() {
            override fun keyTyped(event: KeyEvent) {
                if (event.keyChar.toInt() == 32) {
                    cameras.forEach {
                        it.camera.apply {
                            position += (position cross Point.Y(1f)).normalize(scale) * -1f
                        }
                    }
                    repaint()
                }
            }
            override fun keyPressed(p0: KeyEvent) { }
            override fun keyReleased(p0: KeyEvent) { }
        })
    }
    override val init : GLAccess = {
        glPointSize(5f)
        glLineWidth(5f)
    }
    override val drawFrame: GLAccess = {
            withViewPorts(cameras) {
                withColor(Color.random(5)) {
                    moveTo(Point(-5f, -.5f, -.5f)) {
                        rotate(32f, Point(2f, 4f, 5f)) {
                            scale(Point.UNI(2f)) {
                                glut.glutWireCube(2f)
                            }
                        }
                    }
                }

                withColor(Color.random(100)) {
                    rotate(60f, Point.Z(2f)) {
                        moveTo(Point(4f, -1f, 5f)) {
                            scale(Point(2f, 5f, 1f)) {
                                glut.glutWireCube(2f)
                            }
                        }
                    }
                }

                withColor(Color.random(3)) {
                    scale(Point.UNI(.3f)) {
                        rotate(12f, Point.Y(2f)) {
                            moveTo(Point(6f, 0f, 3f)) {
                                glut.glutWireCube(3f)
                            }
                        }
                    }
                }
            }
        }
})

fun main() {
    JFrame().apply {
        contentPane.add(scene)
        title = "2.2"
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        println("Adding event listener")
        pack()
        isVisible = true
    }
}
