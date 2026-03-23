package cc.turtl.turtlshell.example.client

import cc.turtl.turtlshell.api.client.gui.screen.AbstractModalScreen
import cc.turtl.turtlshell.api.client.gui.texture.SimpleIcons
import cc.turtl.turtlshell.api.client.gui.widget.element.*
import cc.turtl.turtlshell.example.client.config.ExampleConfigClient
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

class ExampleScreen : AbstractModalScreen(
    Component.translatable("ts.gui.examplescreen.title"),
    debug = ExampleConfigClient.get().general.debug
) {

    override fun initContainers() {
        addPage(Component.literal("Home"), SimpleIcons.HOME) {
        }
        addPage(Component.literal("Preview"), SimpleIcons.EYE) {
        }
        addPage(Component.literal("Search"), SimpleIcons.SEARCH) {
        }
        addPage(Component.literal("Build"), SimpleIcons.HAMMER) {
        }
        addPage(Component.literal("Blocklist"), SimpleIcons.BLOCKED) {
        }
        addPage(Component.literal("Settings"), SimpleIcons.GEAR) {
            addBlock(TextElement(Component.literal("This is the title"), theme))
            addBlock(TextElement(Component.literal("This is on the second row"), theme))
            addInline(TextElement(Component.literal("Start of row"), theme))
            addInline(TextElement(Component.literal("End of row"), theme))
            addInline(TextElement(
                Component.literal("This should wrap to a new row even though it's inline"),
                theme
            ))
            addBlock(TextElement(Component.literal("This text is biggg"), theme, 2f))
            addBlock(TextElement(Component.literal("This text is small"), theme, 0.5f))
            addInline(TextElement(Component.literal("Really big"), theme, 3f))
            addInline(TextElement(Component.literal("Regular inline"), theme))
            addInline(ButtonElement(Component.literal("Button"), theme))
            addInline(ToggleElement(Component.literal("Toggle"), theme))
            addInline(TextEntryElement(theme))
            addBlock(ImageElement(
                ResourceLocation.fromNamespaceAndPath("turtlshell", "icon.png"),
                128, 128, 32, justify = Justify.CENTER))
            addBlock(ImageElement(
                ResourceLocation.fromNamespaceAndPath("turtlshell", "icon.png"),
                128, 128, 32, justify = Justify.RIGHT))
        }
    }
}