package framework.scenes

import shapes.Point

class OrbitCamera(position: Point = Point(), lookAt: Point = Point(), rotation: Point = Point()) : BasicCamera(position, lookAt, rotation) {
    /*val radius = ((position - lookAt) * orbitScale) // Define (x - x0), (y - y0), (z - z0)
            .let { it * it } // Make it (x - x0)^2, (y - y0)^2, (z - z0)^2
            .let { it.x + it.y + it.z } // Make it (x - x0)^2 + (y - y0)^2 + (z - z0)^2
            .let { sqrt(it) } // square root the sum to get the radius*/
}
/* internal operator fun OrbitCamera.invoke(angleFromStart: Double) {
    this.position = this.position * (Point.UNI(kotlin.math.sin(angleFromStart).toFloat()) * this.orbitScale * radius)
    this()
} */