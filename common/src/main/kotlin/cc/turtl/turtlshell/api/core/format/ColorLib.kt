package cc.turtl.turtlshell.api.core.format

import java.awt.Color

/**
 * A collection of commonly used colors.
 */
object ColorLib {
    // --- Basic Colors ---
    val WHITE: Color = Color(0xFFFFFF)
    val BLACK: Color = Color(0x000000)
    val LIGHT_GRAY: Color = Color(0xAAAAAA)
    val DARK_GRAY: Color = Color(0x555555)

    // --- UI Colors ---
    val DARK_SLATE: Color = Color(0x0f0f17)
    val SLATE: Color = Color(0x181825)
    val LIGHT_SLATE: Color = Color(0x353749)

    val OFF_WHITE: Color = Color(0xDBEAFF)

    // --- Rainbow Palette ---
    val RED: Color = Color(0xe43d4f)
    val ORANGE: Color = Color(0xe76c20)
    val YELLOW: Color = Color(0xf7de38)
    val GREEN: Color = Color(0x73e04d)
    val BLUE: Color = Color(0x3b6efa)
    val PURPLE: Color = Color(0x8729fa)
    val PINK: Color = Color(0xF46997)

    // --- Extended Palette ---
    val GOLD: Color = Color(0xFFD700)
    val INDIGO: Color = Color(0x4B0082)
    val MAGENTA: Color = Color(0xFF00FF)
    val BROWN: Color = Color(0x8B4513)
    val AQUA: Color = Color(0x40E0D0)
    val MINT: Color = Color(0x98FF98)
    val TEAL: Color = Color(0x008080)

    class Gradient(vararg val colors: Color) {
        fun rgb(): IntArray = colors.map { it.rgb }.toIntArray()
    }

    object Gradients {
        val POSITIVE = Gradient(RED, YELLOW, GREEN)
        val NEGATIVE = Gradient(GREEN, YELLOW, RED)
        val RAINBOW = Gradient(RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE, PINK)
    }
}

/**
 * Returns the color as an ARGB int.
 */
val Color.argb: Int
    get() = (alpha shl 24) or (red shl 16) or (green shl 8) or blue

/**
 * Returns a copy of this color with the given opacity applied.
 *
 * @param amount opacity from 0.0 (fully transparent) to 1.0 (fully opaque)
 */
fun Color.opacity(amount: Float): Color =
    Color(red, green, blue, (amount.coerceIn(0f, 1f) * 255).toInt())