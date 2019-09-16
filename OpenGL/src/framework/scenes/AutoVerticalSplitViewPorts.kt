package framework.scenes

typealias AutoVerticalSplitViewPorts = List<BasicViewPort>

fun autoVerticalSplitFromCameras(vararg cameras: BasicCamera): AutoVerticalSplitViewPorts {
    val width = FrameSizeSingleton.WIDTH / cameras.size
    val height = FrameSizeSingleton.HEIGHT
    return cameras.mapIndexed { index, basicCamera ->
        BasicViewPort(width * index, 0, width, height, basicCamera)
    }
}