package scenes

import com.jogamp.opengl.GL2
import com.jogamp.opengl.GLAutoDrawable
import com.jogamp.opengl.glu.GLU
import framework.scenes.FrameSizeSingleton


internal interface BasicReshaper {
    val glu: GLU
    val gl: GL2
    fun reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
        val aspect = (width.toFloat() / if (height == 0) 1 else height).toDouble()
        FrameSizeSingleton.apply {
            X = x
            Y = y
            WIDTH = width
            HEIGHT = height
        }
        gl.apply {
            glMatrixMode(GL2.GL_PROJECTION)
            glLoadIdentity()
            glu.gluPerspective(45.0, aspect, 0.1, 100.0)
            glMatrixMode(GL2.GL_MODELVIEW)
            glLoadIdentity()
        }
    }
}