package Oppgave3

import com.jogamp.opengl.util.gl2.GLUT
import framework.scenes.AnimatedScene
import framework.scenes.BasicCamera
import framework.shapes.cubiesOf
import framework.shapes.invoke
import framework.shapes.saveState
import scenes.GLAccess
import scenes.SceneAccess
import scenes.SceneConfig
import scenes.scale
import shapes.Point
import shapes.pointsOf
import shapes.shift
import shapes.times
import javax.swing.JFrame
import javax.swing.WindowConstants

val orbitCamera = BasicCamera()
val radius = 15f
val startTime by lazy { System.currentTimeMillis() }

val glut = GLUT()

object RubixAnimator {
    val asd = listOf(Point.Axis.X_pos, Point.Axis.X_neg, Point.Axis.Y_pos, Point.Axis.Y_neg, Point.Axis.Z_pos, Point.Axis.Z_neg)
    var index = 0
    var direction = 1
    var waitFrames = 120

    val queue = arrayListOf(Command(Point.Axis.Z_pos), Command(Point.Axis.X_pos), Command(Point.Axis.X_neg), Command(Point.Axis.Z_pos), Command(Point.Axis.Y_neg))

    class Command(val axis: Point.Axis) {
        var rotation: Pair<Float, Point> = 0f to Point.fromAxis(axis)
        fun reset() {
            rotation = 0f to Point.fromAxis(axis)
        }
    }

    fun addRandom() {
        println("Adding a random command")
        queue.add(Command(asd.random()))
    }

    fun step() {
        println("Stepping animation")
        when {
            waitFrames != 0 -> {
                println("Decrementing waitFrames")
                waitFrames -= 1
            }
            index == queue.size -> {
                // addRandom()
                waitFrames = 100
                /* println("Changing direction to -1")
                direction = -1
                index -= 1
                waitFrames = 120 */
            }
            index == -1 -> {
                println("Changing direction to 1")
                direction = 1
                index = 0
                waitFrames = 120
                // for (i in 0..10) addRandom()
            }
            else -> {
                println("Animating!")
                val command = queue[index].apply {
                    rotation = rotation.first + .5f to rotation.second
                }
                println("Filter: ${command.rotation.second.let { "${it.x}, ${it.y}, ${it.z}" }}")
                val cubies = allCubies.filter { it.comparePoint(command.rotation.second) }
                if (command.rotation.first >= 90f) {
                    println("Saving state for cubies")
                    queue[index].reset()
                    cubies.saveState(command.axis)
                    // cubies.forEach{ it.saveState(cubies) }
                    index += direction
                } else {
                    println("Rotating with angle: ${command.rotation.first}")
                    cubies.forEach {
                        it.rotation = command.rotation.first to command.rotation.second * direction.toFloat()
                    }
                }
            }
        }
    }
}

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
    this.filter { it.y == 1f }.forEach { it.showTop = true }
    this.filter { it.y == -1f }.forEach { it.showBottom = true }
    this.filter { it.x == -1f }.forEach { it.showLeft = true }
    this.filter { it.x == 1f }.forEach { it.showRight = true }
    this.filter { it.z == 1f }.forEach { it.showFront = true }
    this.filter { it.z == -1f }.forEach { it.showBack = true }

    /* val axis = Point.Axis.Z_pos
    this.filter { it.comparePoint(Point.fromAxis(axis)) }.saveState(axis)
    this.filter { it.comparePoint(Point.fromAxis(axis)) }.forEach {
        it.rotation = -10f to Point.fromAxis(axis)
    } */
}

val scene = AnimatedScene(60, object : SceneConfig {
    override val constructor: SceneAccess = { }
    override val init: GLAccess = {
        glLineWidth(2f)
        glPointSize(8f)
    }
    override val drawFrame: GLAccess = {
        RubixAnimator.step()
        orbitCamera.apply {
            position = Point(
                    kotlin.math.sin(((startTime - System.currentTimeMillis()).toFloat() * 0.0003f)) * radius,
                    6f,//kotlin.math.sin(((startTime - System.currentTimeMillis()).toFloat() * 0.003f)) * 16f,
                    kotlin.math.cos(((startTime - System.currentTimeMillis()).toFloat() * 0.0003f)) * radius
            )
        }.invoke()
        scale(Point.UNI(2f)) {
            allCubies.invoke(this)
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
