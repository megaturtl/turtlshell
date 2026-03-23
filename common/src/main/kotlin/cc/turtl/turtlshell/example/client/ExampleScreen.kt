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
        addPage("Home", SimpleIcons.HOME) {
        }
        addPage("Preview", SimpleIcons.EYE) {
        }
        addPage("Search", SimpleIcons.SEARCH) {
        }
        addPage("Build", SimpleIcons.HAMMER) {
        }
        addPage("Blocklist", SimpleIcons.BLOCKED) {
        }
        addPage("Settings", SimpleIcons.GEAR) {
            body.addBlock(TextElement(Component.literal("This is the title"), theme))
            body.addBlock(TextElement(Component.literal("This is on the second row"), theme))
            body.addInline(TextElement(Component.literal("Start of row"), theme))
            body.addInline(TextElement(Component.literal("End of row"), theme))
            body.addInline(
                TextElement(
                    Component.literal("This should wrap to a new row even though it's inline"),
                    theme
                )
            )
            body.addBlock(TextElement(Component.literal("This text is biggg"), theme, 2f))
            body.addBlock(TextElement(Component.literal("This text is small"), theme, 0.5f))
            body.addInline(TextElement(Component.literal("Really big"), theme, 3f))
            body.addInline(TextElement(Component.literal("Regular inline"), theme))
            body.addInline(ButtonElement(Component.literal("Button"), theme))
            body.addInline(ToggleElement(Component.literal("Toggle"), theme))
            body.addInline(TextEntryElement(theme))
            body.addBlock(ImageElement(
                ResourceLocation.fromNamespaceAndPath("turtlshell", "icon.png"),
                128, 128, 32, justify = Justify.CENTER))
            body.addBlock(ImageElement(
                ResourceLocation.fromNamespaceAndPath("turtlshell", "icon.png"),
                128, 128, 32, justify = Justify.RIGHT))
        }
    }
}