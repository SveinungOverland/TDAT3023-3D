package scenes

import com.jogamp.opengl.GL2
import com.jogamp.opengl.GLAutoDrawable
import com.jogamp.opengl.GLEventListener
import com.jogamp.opengl.awt.GLCanvas
import com.jogamp.opengl.glu.GLU
import framework.scenes.FrameSizeSingleton

open class BasicScene(config: SceneConfig) : GLCanvas(), GLEventListener, BasicReshaper, SceneConfig by config {


    override lateinit var gl: GL2
    override val glu = GLU()

    init {
        addGLEventListener(this)
        this.constructor(this)
        this.preferredSize = FrameSizeSingleton.getDimension()
    }
    override fun reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int)
        = super<BasicReshaper>.reshape(drawable, x, y, width, height)

    override fun display(drawable: GLAutoDrawable) {
        gl.resetFrameAndApply {
            drawFrame()
        }
    }
    override fun init(drawable: GLAutoDrawable) {
        gl = drawable.gl.gL2.apply { basicInit(); init() }
    }
    override fun dispose(drawable: GLAutoDrawable) { }
}