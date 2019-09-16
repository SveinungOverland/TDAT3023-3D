package framework.scenes

import com.jogamp.opengl.GLAutoDrawable
import com.jogamp.opengl.util.FPSAnimator
import scenes.BasicScene
import scenes.SceneConfig


class AnimatedScene(val targetFps: Int, config: SceneConfig) : BasicScene(config) {

    override fun init(drawable: GLAutoDrawable) {
        super.init(drawable)
        FPSAnimator(drawable, targetFps).start()
    }

}