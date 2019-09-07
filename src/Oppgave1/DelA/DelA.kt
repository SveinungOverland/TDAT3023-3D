package DelA

import com.jogamp.opengl.GL2
import com.jogamp.opengl.GLAutoDrawable
import com.jogamp.opengl.GLEventListener
import com.jogamp.opengl.awt.GLCanvas
import com.jogamp.opengl.glu.GLU
import com.jogamp.opengl.util.gl2.GLUT

import javax.swing.*
import java.awt.*
import com.jogamp.opengl.GL.GL_TRIANGLES
import com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT
import com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT


const val TITTEL = "Oppgave 1"
const val CANVAS_WIDTH = 800
const val CANVAS_HEIGHT = 600
const val distance = 10


fun main() {
    JFrame().apply {
        contentPane.add(Oppgave1().apply {
            preferredSize = Dimension(CANVAS_WIDTH, CANVAS_HEIGHT)
        })
        title = TITTEL
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        pack()
        isVisible = true
    }
}

class Oppgave1 : GLCanvas(), GLEventListener {
    lateinit var gl: GL2
    val glu = GLU()
    val glut = GLUT()
    var timeAtStart = System.currentTimeMillis()

    init {
        this.addGLEventListener(this)
    }

    override fun init(drawable: GLAutoDrawable) {
        gl = drawable.gl.gL2.apply {
            glClearColor(0f, 0f, 0f, 0f)
            glEnable(GL2.GL_DEPTH_TEST)
            glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST)
            glShadeModel(GL2.GL_SMOOTH)
            glPointSize(20f)
        }
    }

    override fun dispose(drawable: GLAutoDrawable) { }

    override fun display(drawable: GLAutoDrawable) {
        gl.apply {
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT) //Clear the screen and the depth buffer
            glLoadIdentity()

            glTranslatef(-1.5f, 0.0f, -8.0f) // Move left 1.5 Units and 7 units into the screen
            glColor3f(1.0f, 0.0f, 0.0f)      // Set the color to red

            glBegin(GL_TRIANGLES)          // Start drawing using the polygon primitive GL_TRIANGLES
            glVertex3f(0.0f, 1.0f, 0.0f)   // Top
            glVertex3f(-1.0f, -1.0f, 0.0f) // Bottom left
            glVertex3f(1.0f, -1.0f, 0.0f)  // Bottom right
            glEnd()                        // Finished drawing the triangle
        }
    }

    override fun reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
        val aspect = width.toFloat() / if (height == 0) 1 else height

        gl.apply {
            glMatrixMode(GL2.GL_PROJECTION)
            glLoadIdentity()
            glu.gluPerspective(45.0, aspect.toDouble(), 0.1, 100.0)

            glMatrixMode(GL2.GL_MODELVIEW)
            glLoadIdentity()
        }
    }
}