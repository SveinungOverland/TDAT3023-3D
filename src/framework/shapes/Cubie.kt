package framework.shapes

import com.jogamp.opengl.GL2
import com.jogamp.opengl.GL2ES3.GL_QUADS
import com.jogamp.opengl.util.gl2.GLUT
import scenes.*
import shapes.Point
import shapes.Points
import shapes.times

val glut = GLUT()

class Cubie(position: Point): Point(position.x, position.y, position.z) {
    var rotation = 0f to Point()
    var topColor = Color.BLACK
    var bottomColor = Color.BLACK
    var frontColor = Color.BLACK
    var backColor = Color.BLACK
    var leftColor = Color.BLACK
    var rightColor = Color.BLACK
}

typealias Cubies = List<Cubie>
fun cubiesOf(vararg points: Points): Cubies = points.flatMap { it.map { point -> Cubie(point) } }

operator fun Cubies.invoke(gl: GL2) {
    this.forEach {
        gl.rotate(it.rotation.first, it.rotation.second) {
            gl.moveTo(it * Point.UNI(1.02f)) {

                glBegin(GL_QUADS)
                withColor(it.topColor) {
                    glVertex3f( .5f, .5f, -.5f)
                    glVertex3f(-.5f, .5f, -.5f)
                    glVertex3f(-.5f, .5f,  .5f)
                    glVertex3f( .5f, .5f,  .5f)
                }
                withColor(it.bottomColor) {
                    glVertex3f( .5f, -.5f,  .5f)
                    glVertex3f(-.5f, -.5f,  .5f)
                    glVertex3f(-.5f, -.5f, -.5f)
                    glVertex3f( .5f, -.5f, -.5f)
                }
                withColor(it.frontColor) {
                    glVertex3f( .5f,  .5f, .5f)
                    glVertex3f(-.5f,  .5f, .5f)
                    glVertex3f(-.5f, -.5f, .5f)
                    glVertex3f( .5f, -.5f, .5f)
                }
                withColor(it.backColor) {
                    glVertex3f( .5f, -.5f, -.5f);
                    glVertex3f(-.5f, -.5f, -.5f);
                    glVertex3f(-.5f,  .5f, -.5f);
                    glVertex3f( .5f,  .5f, -.5f);
                }
                withColor(it.leftColor) {
                    glVertex3f(-.5f,  .5f,  .5f)
                    glVertex3f(-.5f,  .5f, -.5f)
                    glVertex3f(-.5f, -.5f, -.5f)
                    glVertex3f(-.5f, -.5f,  .5f)
                }
                withColor(it.rightColor) {
                    glVertex3f(.5f,  .5f, -.5f);
                    glVertex3f(.5f,  .5f,  .5f);
                    glVertex3f(.5f, -.5f,  .5f);
                    glVertex3f(.5f, -.5f, -.5f);
                }
                glEnd()

                gl.withColor(Color.BLACK) {
                    gl.withLineWidth(10f) {
                        glut.glutWireCube(1.05f)
                    }
                }
            }
        }
    }
}