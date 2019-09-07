package Oppgave3

import com.jogamp.opengl.util.gl2.GLUT
import framework.scenes.AnimatedScene
import framework.scenes.BasicCamera
import framework.shapes.cubiesOf
import framework.shapes.invoke
import scenes.*
import shapes.Point
import shapes.pointsOf
import shapes.shift
import javax.swing.JFrame
import javax.swing.WindowConstants

val orbitCamera = BasicCamera()
val radius = 15f
val startTime by lazy { System.currentTimeMillis() }

val glut = GLUT()


val singlePlaneCenterPoints = pointsOf(
        Point(-1f, 1f),
        Point(0f, 1f),
        Point(1f, 1f),
        Point(-1f, 0f),
        Point(0f, 0f),
        Point(1f, 0f),
        Point(-1f, -1f),
        Point(0f, -1f),
        Point(1f, -1f)
)

val allCubies = cubiesOf(
        singlePlaneCenterPoints shift Point.Z(-1f),
        singlePlaneCenterPoints,
        singlePlaneCenterPoints shift Point.Z(1f)
).apply {
    this.filter { it.y == 1f }.map { it.topColor = Color.BLUE }
    this.filter { it.y == -1f }.map { it.bottomColor = Color.GREEN }
    this.filter { it.x == -1f }.map { it.leftColor = Color.YELLOW }
    this.filter { it.x == 1f }.map { it.rightColor = Color.ORANGE }
    this.filter { it.z == 1f }.map { it.frontColor = Color.RED }
    this.filter { it.z == -1f }.map { it.backColor = Color.WHITE }
    this.filter {
        it.x == -1f
    }.map {
        it.rotation = 32f to Point.X(1f)
    }
    this.filter {
        it.y == 1f
    }.map {
        it.rotation = 20f to Point.Y(1f)
    }
}

val scene = AnimatedScene(60, object : SceneConfig {
    override val constructor: SceneAccess = { }
    override val init: GLAccess = {
        glLineWidth(2f)
        glPointSize(8f)
    }
    override val drawFrame: GLAccess = {
        orbitCamera.apply {
            position = Point(
                    kotlin.math.sin(((startTime - System.currentTimeMillis()).toFloat() * 0.0003f)) * radius,
                    kotlin.math.sin(((startTime - System.currentTimeMillis()).toFloat() * 0.003f)) * 16f,
                    kotlin.math.cos(((startTime - System.currentTimeMillis()).toFloat() * 0.0003f)) * radius
            )
        }.invoke()
        scale(Point.UNI(2f)) {
            allCubies(this)
        }
    }
})

fun main() {
    JFrame().apply {
        contentPane.add(scene)
        title = "3 Rubix"
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        pack()
        isVisible = true
    }
}
