package cc.turtl.turtlshell.util.format

import net.minecraft.ChatFormatting
import net.minecraft.util.FastColor
import net.minecraft.util.Mth
import java.awt.Color

object ColorUtils {
    // --- Basic Colors ---
    val WHITE: Color = Color(0xFFFFFF)
    val BLACK: Color = Color(0x000000)
    val LIGHT_GRAY: Color = Color(0xAAAAAA)
    val DARK_GRAY: Color = Color(0x555555)

    // --- Rainbow Palette ---
    val RED: Color = Color(0xE13538)
    val ORANGE: Color = Color(0xF9844A)
    val YELLOW: Color = Color(0xF9C74F)
    val GREEN: Color = Color(0x41D73B)
    val BLUE: Color = Color(0x2D73B0)
    val PURPLE: Color = Color(0x6C44C3)
    val PINK: Color = Color(0xF46997)

    // --- Extended Palette ---
    val CRIMSON: Color = Color(0xDC143C)
    val CORAL: Color = Color(0xFF7F50)
    val GOLD: Color = Color(0xFFD700)
    val LIME: Color = Color(0x32CD32)
    val INDIGO: Color = Color(0x4B0082)
    val MAGENTA: Color = Color(0xFF00FF)
    val BROWN: Color = Color(0x8B4513)
    val AQUA: Color = Color(0x40E0D0)
    val LAVENDER: Color = Color(0xDEDEFC)
    val MINT: Color = Color(0x98FF98)
    val TEAL: Color = Color(0x008080)
    private val MC_PALETTE = intArrayOf(
        0x000000, 0x0000AA, 0x00AA00, 0x00AAAA, 0xAA0000, 0xAA00AA, 0xFFAA00, 0xAAAAAA,
        0x555555, 0x5555FF, 0x55FF55, 0x55FFFF, 0xFF5555, 0xFF55FF, 0xFFFF55, 0xFFFFFF
    )

    /**
     * Converts an RGB color to an ARGB integer with custom transparency.
     * * @param rgb     The source color (0xRRGGBB)
     *
     * @param opacity The alpha percentage (0.0 to 1.0)
     * @return ARGB (0xAARRGGBB)
     */
    fun argb(rgb: Int, opacity: Float): Int {
        return FastColor.ARGB32.color(FastColor.as8BitChannel(opacity), rgb)
    }

    /**
     * Finds the closest vanilla [ChatFormatting] color based on perceptual distance.
     * * @param rgb The source color (0xRRGGBB)
     *
     * @return The closest matching legacy format
     */
    fun legacy(rgb: Int): ChatFormatting? {
        return getClosestFormat(rgb)
    }

    /**
     * Calculates a color at a specific point along a multi-color gradient.
     * * @param ratio  The position in the gradient (0.0 to 1.0)
     *
     * @param colors The RGB color stops defining the gradient
     * @return The interpolated ARGB color
     */
    fun getGradient(ratio: Float, vararg colors: Int): Int {
        var ratio = ratio
        if (colors.size == 0) return -0x1
        if (colors.size == 1) return argb(colors[0], 1f)

        ratio = Mth.clamp(ratio, 0.0f, 1.0f)
        val segmentSize = 1.0f / (colors.size - 1)
        val segment = Mth.clamp((ratio / segmentSize).toInt(), 0, colors.size - 2)
        val localRatio = (ratio - (segment * segmentSize)) / segmentSize

        return FastColor.ARGB32.lerp(localRatio, colors[segment], argb(colors[segment + 1], 1f))
    }

    private fun getClosestFormat(rgb: Int): ChatFormatting? {
        val r = FastColor.ARGB32.red(rgb)
        val g = FastColor.ARGB32.green(rgb)
        val b = FastColor.ARGB32.blue(rgb)

        var closestIndex = 0
        var minDistance = Long.Companion.MAX_VALUE

        for (i in MC_PALETTE.indices) {
            val pr = FastColor.ARGB32.red(MC_PALETTE[i])
            val pg = FastColor.ARGB32.green(MC_PALETTE[i])
            val pb = FastColor.ARGB32.blue(MC_PALETTE[i])

            val dr = r - pr
            val dg = g - pg
            val db = b - pb

            // Perceptual weighting: Human eyes perceive Green more strongly than Red or Blue.
            val distance = (2L * dr * dr) + (4L * dg * dg) + (3L * db * db)

            if (distance < minDistance) {
                minDistance = distance
                closestIndex = i
            }
        }
        return ChatFormatting.getById(closestIndex)
    }
}