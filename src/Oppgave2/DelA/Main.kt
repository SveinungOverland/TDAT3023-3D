package Oppgave2.DelA

import com.jogamp.opengl.util.gl2.GLUT
import scenes.*
import shapes.*
import javax.swing.JFrame
import javax.swing.WindowConstants

const val TITLE = "Oppgave 2.1"

val wall = pointsOf(
        Point(0f, 0f, 0f),
        Point(1f, 0f, 0f),
        Point(1f, 1f, 0f),
        Point(0f, 1f, 0f),
        Point(0f, 0f, 0f)
)
val wall2 = pointsOf(
        Point(0f, 0f, 0f),
        Point(0f, 0f, -1f),
        Point(0f, 1f, -1f),
        Point(0f, 1f, 0f),
        Point(0f, 0f, 0f)
)

val points: Points = pointsOf(
        wall,
        wall2,
        wall.shift(Point.Z(-1f)),
        wall2.shift(Point.X(1f))
).shift(Point(-.5f, -.5f, .5f)).scale(2f)

val glut = GLUT()

val scene = BasicScene(object : SceneConfig {
    override val constructor: SceneAccess = {
        addGLEventListener(this)
    }
    override val init: GLAccess = {
        glPointSize(5f)
        glLineWidth(5f)
    }
    override val drawFrame: GLAccess = {
        moveTo(Point.Z(-15f)) {
            moveTo(Point.X(-5f)) {
                rotate(45f, Point(.5f, -1f)) {
                    points.drawLineLoop(this)
                }
            }
            moveTo(Point.X(5f)) {
                glut.glutWireCube(2f)
            }
        }
    }
})

fun main() {
    JFrame().apply {
        contentPane.add(scene)
        title = TITLE
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        pack()
        isVisible = true
    }
}