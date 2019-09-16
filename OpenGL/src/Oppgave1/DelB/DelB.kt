package DelB


import scenes.*
import shapes.*
import javax.swing.JFrame
import javax.swing.WindowConstants


const val TITTEL = "Oppgave 1"


val points: Points = pointsOf(
        Point(0.0f, 2.0f),
        Point(1.5f, 1.5f),
        Point(2.0f, 0.0f),
        Point(1.5f, -1.5f),
        Point(0.0f, -2.0f),
        Point(-1.5f, -1.5f),
        Point(-2.0f, 0.0f),
        Point(-1.5f, 1.5f)
)

val scene = BasicScene(object : SceneConfig {
    override val constructor: SceneAccess = {
        addGLEventListener(this)
    }
    override val drawFrame: GLAccess = {
        val scale = 5f
        moveTo(Point.Z(-20f)) {
            moveTo(Point.Y(.6f * scale)) {
                moveTo(Point.X(-2f * scale)) {
                    withColor(Color.random(1)) {
                        points.drawPoints(this)
                    }
                }
                moveTo(Point.X(-1f * scale)) {
                    withColor(Color.random(2)) {
                        points.drawLines(this)
                    }
                }
                moveTo(Point.X(0f)) {
                    withColor(Color.random(3)) {
                        points.drawLineStrip(this)
                    }
                }
                moveTo(Point.X(1f * scale)) {
                    withColor(Color.random(123)) {
                        points.drawLineLoop(this)
                    }
                }
                moveTo(Point.X(2f * scale)) {
                    withColor(Color.random(5)) {
                        points.drawTriangles(this)
                    }
                }
            }
            moveTo(Point.Y(-.6f * scale)) {
                moveTo(Point.X(-2f * scale)) {
                    withColor(Color.random(6)) {
                        points.drawTriangleStrip(this)
                    }
                }
                moveTo(Point.X(-1f * scale)) {
                    withColor(Color.random(7)) {
                        points.drawTriangleFan(this)
                    }
                }
                moveTo(Point.X(0f)) {
                    withColor(Color.random(8)) {
                        points.drawQuads(this)
                    }
                }
                moveTo(Point.X(1f * scale)) {
                    withColor(Color.random(9)) {
                        points.drawQuadStrip(this)
                    }
                }
                moveTo(Point.X(2f * scale)) {
                    withColor(Color.random(10)) {
                        points.drawPolygon(this)
                    }
                }
            }
        }
    }
    override val init: GLAccess = {
        glPointSize(5f)
        glLineWidth(5f)
    }
})

fun main() {
    JFrame().apply {
        contentPane.add(scene)
        title = TITTEL
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        pack()
        isVisible = true
    }
}


