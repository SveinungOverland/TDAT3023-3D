package scenes

import com.jogamp.opengl.GL2

typealias GLAccess = GL2.() -> Unit
typealias SceneAccess = BasicScene.() -> Unit

interface SceneConfig {
    val drawFrame: GLAccess
    val init: GLAccess
    val constructor: SceneAccess
}