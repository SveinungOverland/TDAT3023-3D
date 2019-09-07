package scenes

import com.jogamp.opengl.GL
import com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT
import com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT
import com.jogamp.opengl.GL2
import com.jogamp.opengl.GL2.GL_CURRENT_BIT
import com.jogamp.opengl.GL2.GL_LINE_BIT
import com.jogamp.opengl.glu.GLU
import framework.scenes.BasicViewPort
import shapes.Point
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

val glu = GLU()


class Color(val r: Float, val g: Float, val b: Float, val a: Float = 1f) {
    companion object {
        val RED = Color(1f, 0f, 0f)
        val YELLOW = Color(1f, 1f, 0f)
        val GREEN = Color(0f, 1f, 0f)
        val ORANGE = Color(1f, .5f, 0f)
        val BLUE = Color(0f, 0f, 1f)
        val WHITE = Color(1f, 1f, 1f)
        val BLACK = Color(0f, 0f, 0f)
        fun random(seed: Int?): Color {
            val rnd = kotlin.random.Random(seed ?: kotlin.random.Random.nextInt())
            return Color(
                    rnd.nextFloat(),
                    rnd.nextFloat(),
                    rnd.nextFloat()
            )
        }

    }
}

@ExperimentalContracts
internal inline fun GL2.resetFrameAndApply(block: GL2.() -> Unit): GL2 {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    this.glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    this.glLoadIdentity()
    block()
    return this
}

internal inline fun GL2.moveTo(point: Point, block: GL2.() -> Unit): GL2 {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    this.glPushMatrix()
    this.glTranslatef(point.x, point.y, point.z)
    block()
    this.glPopMatrix()
    return this
}

internal inline fun GL2.rotate(angle: Float, point: Point, block: GL2.() -> Unit): GL2 {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    this.glPushMatrix()
    this.glRotatef(angle, point.x, point.y, point.z)
    block()
    this.glPopMatrix()
    return this
}

internal inline fun GL2.withColor(color: Color, block: GL2.() -> Unit): GL2 {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    this.glPushAttrib(GL_CURRENT_BIT)
    this.glColor4f(color.r, color.g, color.b, color.a)
    block()
    this.glPopAttrib()
    return this
}

internal inline fun GL2.withLineWidth(lineWidth: Float, block: GL2.() -> Unit): GL2 {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    this.glPushAttrib(GL_LINE_BIT)
    this.glLineWidth(lineWidth)
    block()
    this.glPopAttrib()
    return this
}

internal inline fun GL2.scale(point: Point, block: GL2.() -> Unit): GL2 {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    this.glPushMatrix()
    this.glScalef(point.x, point.y, point.z)
    block()
    this.glPopMatrix()
    return this
}

internal fun GL2.drawPoint(point: Point) {
    this.glBegin(GL.GL_POINTS)
    this.glVertex3f(point.x, point.y, point.z)
    this.glEnd()
}

internal inline fun GL2.withViewPorts(viewPorts: List<BasicViewPort>, block: GL2.() -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.AT_LEAST_ONCE)
    }
    viewPorts.forEach {
        this.glViewport(it.x, it.y, it.width, it.height)
        this.glLoadIdentity()
        it.camera.invoke()
        block()
    }
}
internal fun GL2.basicInit() {
    this.apply {
        glClearColor(0f, 0f, 0f, 0f)
        glEnable(GL2.GL_DEPTH_TEST)
        glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST)
        glShadeModel(GL2.GL_SMOOTH)
    }
}