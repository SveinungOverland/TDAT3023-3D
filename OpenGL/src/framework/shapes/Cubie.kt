package framework.shapes

import com.jogamp.opengl.GL2
import com.jogamp.opengl.GL2ES3
import com.jogamp.opengl.util.gl2.GLUT
import scenes.*
import shapes.Point
import shapes.Points
import shapes.times
import kotlin.math.abs

val glut = GLUT()

class Cubie(position: Point): Point(position.x, position.y, position.z) {
    var rotation      = 0f to Point()
    var topColor      = Color.BLUE
    var bottomColor   = Color.GREEN
    var frontColor    = Color.RED
    var backColor     = Color.WHITE
    var leftColor     = Color.YELLOW
    var rightColor    = Color.ORANGE
    var showTop = false
    var showBottom = false
    var showFront = false
    var showBack = false
    var showLeft = false
    var showRight = false

    fun comparePoint(point: Point): Boolean {
        // println("Comparing: ${x},${y},${z} and ${point.x},${point.y},${point.z}")
        return (x == point.x && abs(point.x) > 0) || (y == point.y && abs(point.y) > 0) || (z == point.z && abs(point.z) > 0)
    }

}

typealias Cubies = List<Cubie>
fun cubiesOf(vararg points: Points): Cubies = points.flatMap { it.map { point -> Cubie(point) } }

operator fun Cubies.invoke(gl: GL2) {
    this.forEach {
        gl.rotate(it.rotation.first, it.rotation.second) {
            gl.moveTo(it * Point.UNI(1.02f)) {
                glBegin(GL2ES3.GL_QUADS)
                withColor(if (it.showTop) it.topColor else Color.BLACK) {
                    glVertex3f( .5f, .5f, -.5f)
                    glVertex3f(-.5f, .5f, -.5f)
                    glVertex3f(-.5f, .5f,  .5f)
                    glVertex3f( .5f, .5f,  .5f)
                }
                withColor(if (it.showBottom) it.bottomColor else Color.BLACK) {
                    glVertex3f( .5f, -.5f,  .5f)
                    glVertex3f(-.5f, -.5f,  .5f)
                    glVertex3f(-.5f, -.5f, -.5f)
                    glVertex3f( .5f, -.5f, -.5f)
                }
                withColor(if (it.showFront) it.frontColor else Color.BLACK) {
                    glVertex3f( .5f,  .5f, .5f)
                    glVertex3f(-.5f,  .5f, .5f)
                    glVertex3f(-.5f, -.5f, .5f)
                    glVertex3f( .5f, -.5f, .5f)
                }
                withColor(if (it.showBack) it.backColor else Color.BLACK) {
                    glVertex3f( .5f, -.5f, -.5f);
                    glVertex3f(-.5f, -.5f, -.5f);
                    glVertex3f(-.5f,  .5f, -.5f);
                    glVertex3f( .5f,  .5f, -.5f);
                }
                withColor(if (it.showLeft) it.leftColor else Color.BLACK) {
                    glVertex3f(-.5f,  .5f,  .5f)
                    glVertex3f(-.5f,  .5f, -.5f)
                    glVertex3f(-.5f, -.5f, -.5f)
                    glVertex3f(-.5f, -.5f,  .5f)
                }
                withColor(if (it.showRight) it.rightColor else Color.BLACK) {
                    glVertex3f(.5f,  .5f, -.5f);
                    glVertex3f(.5f,  .5f,  .5f);
                    glVertex3f(.5f, -.5f,  .5f);
                    glVertex3f(.5f, -.5f, -.5f);
                }
                glEnd()

                gl.withColor(Color.BLACK) {
                    gl.withLineWidth(10f) {
                        glut.glutWireCube(1.02f)
                    }
                }
            }
        }
    }
}

fun Cubies.saveState(axis: Point.Axis) {
    fun cubieMap(cubie: Cubie) = object {
        val x = cubie.x
        val y = cubie.y
        val z = cubie.z
        val topColor = cubie.topColor.copy()
        val bottomColor = cubie.bottomColor.copy()
        val frontColor = cubie.frontColor.copy()
        val backColor = cubie.backColor.copy()
        val leftColor = cubie.leftColor.copy()
        val rightColor = cubie.rightColor.copy()
    }
    val top = this.filter { it.y == 1f }.map(::cubieMap)
    val bottom = this.filter { it.y == -1f }.map(::cubieMap)
    val front = this.filter { it.z == 1f }.map(::cubieMap)
    val back = this.filter { it.z == -1f }.map(::cubieMap)
    val left = this.filter { it.x == -1f }.map(::cubieMap)
    val right = this.filter { it.x == 1f }.map(::cubieMap)

    this.forEach { point -> when(axis) {
            Point.Axis.X_pos -> {
                point.frontColor = top.firstOrNull { it.z == -point.y }?.topColor ?: Color.BLACK
                point.bottomColor = front.firstOrNull { it.y == point.z }?.frontColor ?: Color.BLACK
                point.backColor = bottom.firstOrNull { it.z == -point.y }?.bottomColor ?: Color.BLACK
                point.topColor = back.firstOrNull { it.y == point.z }?.backColor ?: Color.BLACK
            }
            Point.Axis.X_neg -> {
                point.frontColor = bottom.firstOrNull { it.z == point.y }?.bottomColor ?: Color.BLACK
                point.bottomColor = back.firstOrNull { it.y == point.z }?.backColor ?: Color.BLACK
                point.backColor = top.firstOrNull { it.z == point.y }?.topColor ?: Color.BLACK
                point.topColor = front.firstOrNull { it.y == point.z }?.frontColor ?: Color.BLACK
            }
            Point.Axis.Y_pos -> {
                point.frontColor = left.firstOrNull { it.z == point.x }?.leftColor ?: Color.BLACK
                point.rightColor = front.firstOrNull { it.x == point.z }?.frontColor ?: Color.BLACK
                point.backColor = right.firstOrNull { it.z == point.x }?.rightColor ?: Color.BLACK
                point.leftColor = back.firstOrNull { it.x == point.z }?.backColor ?: Color.BLACK
            }
            Point.Axis.Y_neg -> {
                point.frontColor = right.firstOrNull { it.z == -point.x }?.rightColor ?: Color.BLACK
                point.rightColor = back.firstOrNull { it.x == point.z }?.backColor ?: Color.BLACK
                point.backColor = left.firstOrNull { it.z == point.x }?.leftColor ?: Color.BLACK
                point.leftColor = front.firstOrNull { it.x == point.z }?.frontColor ?: Color.BLACK
            }
            Point.Axis.Z_neg -> {
                point.topColor = left.firstOrNull { it.y == point.x }?.leftColor ?: Color.BLACK
                point.rightColor = top.firstOrNull { it.x == point.y }?.topColor ?: Color.BLACK
                point.bottomColor = right.firstOrNull { it.y == point.x }?.rightColor ?: Color.BLACK
                point.leftColor = bottom.firstOrNull { it.x == point.y }?.bottomColor ?: Color.BLACK
            }
            Point.Axis.Z_pos -> {
                point.topColor = right.firstOrNull { it.y == point.x }?.rightColor ?: Color.BLACK
                point.rightColor = bottom.firstOrNull { it.x == point.y }?.bottomColor ?: Color.BLACK
                point.bottomColor = left.firstOrNull { it.y == point.x }?.leftColor ?: Color.BLACK
                point.leftColor = top.firstOrNull { it.x == point.y }?.topColor ?: Color.BLACK
            }
        }
        point.rotation = 0f to Point()
    }
}
