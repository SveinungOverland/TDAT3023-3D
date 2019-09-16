package framework.scenes

import com.jogamp.opengl.glu.GLU
import shapes.Point

val glu = GLU()

open class BasicCamera(var position: Point = Point(), var lookAt: Point = Point(), var rotation: Point = Point(0f, 1f, 0f)) {

    fun invoke() {
        glu.gluLookAt(
                this.position.x, this.position.y, this.position.z,
                this.lookAt.x, this.lookAt.y, this.lookAt.z,
                this.rotation.x ,this.rotation.y ,this.rotation.z
        )
    }

}