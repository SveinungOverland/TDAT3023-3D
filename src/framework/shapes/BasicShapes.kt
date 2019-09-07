package shapes

import com.jogamp.opengl.GL.*
import com.jogamp.opengl.GL2
import com.jogamp.opengl.GL2.GL_POLYGON
import com.jogamp.opengl.GL2.GL_QUAD_STRIP
import com.jogamp.opengl.GL2ES3.GL_QUADS
import scenes.moveTo

open

class Point(val x: Float = 0f, val y: Float = 0f, val z: Float = 0f) {
    companion object {
        fun X(x: Float) = Point(x, 0f, 0f)
        fun Y(y: Float) = Point(0f, y, 0f)
        fun Z(z: Float) = Point(0f, 0f, z)
        fun UNI(xyz: Float) = Point(xyz, xyz, xyz)
    }

    fun normalize(scale: Float = 1f): Point {
        val max = listOf(this.x, this.y, this.z).map { if (it < 0) it * -1 else it }.max() ?: 1f
        return (this / max) * scale
    }
}

operator fun Point.plus(point: Point) = Point(this.x + point.x, this.y + point.y, this.z + point.z)
operator fun Point.minus(point: Point) = Point(this.x - point.x, this.y - point.y, this.z - point.z)
operator fun Point.times(nr: Float) = Point(this.x * nr, this.y * nr, this.z * nr)
operator fun Point.times(point: Point) = Point(this.x * point.x, this.y * point.y, this.z * point.z)
operator fun Point.div(nr: Float) = Point(this.x / nr, this.y / nr, this.z / nr)

infix fun Point.cross(point: Point): Point {
    return Point(
            this.y * point.z - this.z * point.y,
            this.z * point.x - this.x * point.z,
            this.x * point.y - this.y * point.x
    )
}

typealias Points = List<Point>
fun pointsOf(vararg points: Point): Points = listOf(*points)
fun pointsOf(vararg points: Points): Points = points.flatMap { it }

infix fun Points.shift(point: Point): Points = this.map { it + point }
infix fun Points.scale(nr: Float): Points = this.map { it * nr }
infix fun Points.scale(point: Point): Points = this.map { it * point }

fun Points.drawPoints(gl: GL2) =
        this.drawVertex3fs(gl, GL_POINTS)

fun Points.drawLines(gl: GL2) =
        this.drawVertex3fs(gl, GL_LINES)

fun Points.drawLineStrip(gl: GL2) =
        this.drawVertex3fs(gl, GL_LINE_STRIP)

fun Points.drawLineLoop(gl: GL2) =
        this.drawVertex3fs(gl, GL_LINE_LOOP)

fun Points.drawTriangles(gl: GL2) =
        this.drawVertex3fs(gl, GL_TRIANGLES)

fun Points.drawTriangleStrip(gl: GL2) =
        this.drawVertex3fs(gl, GL_TRIANGLE_STRIP)

fun Points.drawTriangleFan(gl: GL2) =
        this.drawVertex3fs(gl, GL_TRIANGLE_FAN)

fun Points.drawQuads(gl: GL2) =
        this.drawVertex3fs(gl, GL_QUADS)

fun Points.drawQuadStrip(gl: GL2) =
        this.drawVertex3fs(gl, GL_QUAD_STRIP)

fun Points.drawPolygon(gl: GL2) =
        this.drawVertex3fs(gl, GL_POLYGON)

fun Points.atEachPoint(gl: GL2, block: (Point) -> Unit) {
    this.forEach {
        gl.moveTo(it) {
            block(it)
        }
    }
}


private fun Points.drawVertex3fs(gl: GL2, type: Int) {
    gl.apply {
        glBegin(type)
        this@drawVertex3fs.forEach {
            glVertex3f(it.x, it.y, it.z)
        }
        glEnd()
    }
}