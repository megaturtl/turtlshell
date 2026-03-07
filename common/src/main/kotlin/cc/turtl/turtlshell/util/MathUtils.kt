package cc.turtl.turtlshell.util

import kotlin.math.floor

/** Linearly interpolates between two int values [a] and [b] by factor [t] (0.0 to 1.0). */
fun lerp(a: Int, b: Int, t: Float) = (a + floor(t * (b - a).toFloat())).toInt()