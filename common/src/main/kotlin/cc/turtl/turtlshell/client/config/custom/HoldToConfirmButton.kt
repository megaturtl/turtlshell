package cc.turtl.turtlshell.client.config.custom

import com.google.common.collect.ImmutableSet
import dev.isxander.yacl3.api.*
import dev.isxander.yacl3.gui.YACLScreen
import net.minecraft.network.chat.Component
import java.util.function.BiConsumer

class HoldToConfirmButton private constructor(
    private val name: Component,
    private val description: OptionDescription,
    val action: BiConsumer<YACLScreen, HoldToConfirmButton>,
    val buttonText: Component,
    val holdingText: Component,
    val holdTimeTicks: Int,
) : Option<BiConsumer<YACLScreen, HoldToConfirmButton>> {

    private val stateManager = StateManager.createImmutable(action)
    private val controller = HoldToConfirmController(this, buttonText)
    private var available = true

    override fun name(): Component = name
    override fun description(): OptionDescription = description

    @Deprecated("Deprecated in YACL")
    override fun tooltip(): Component = description.text()
    override fun controller(): Controller<BiConsumer<YACLScreen, HoldToConfirmButton>> = controller
    override fun stateManager(): StateManager<BiConsumer<YACLScreen, HoldToConfirmButton>> = stateManager

    @Deprecated("Deprecated in YACL")
    override fun binding(): Binding<BiConsumer<YACLScreen, HoldToConfirmButton>> = EmptyBinding
    override fun available(): Boolean = available
    override fun setAvailable(available: Boolean) {
        this.available = available
    }

    override fun flags(): ImmutableSet<OptionFlag> = ImmutableSet.of()
    override fun changed(): Boolean = false
    override fun pendingValue(): BiConsumer<YACLScreen, HoldToConfirmButton> = throw UnsupportedOperationException()
    override fun requestSet(value: BiConsumer<YACLScreen, HoldToConfirmButton>) = throw UnsupportedOperationException()
    override fun applyValue(): Boolean = false
    override fun forgetPendingValue() {}
    override fun requestSetDefault() {}
    override fun isPendingValueDefault(): Boolean = throw UnsupportedOperationException()
    override fun addEventListener(listener: OptionEventListener<BiConsumer<YACLScreen, HoldToConfirmButton>>) {}

    @Deprecated("Deprecated in YACL")
    override fun addListener(changedListener: BiConsumer<Option<BiConsumer<YACLScreen, HoldToConfirmButton>>, BiConsumer<YACLScreen, HoldToConfirmButton>>) {
    }

    private object EmptyBinding : Binding<BiConsumer<YACLScreen, HoldToConfirmButton>> {
        override fun getValue(): BiConsumer<YACLScreen, HoldToConfirmButton> = throw UnsupportedOperationException()
        override fun setValue(value: BiConsumer<YACLScreen, HoldToConfirmButton>) {}
        override fun defaultValue(): BiConsumer<YACLScreen, HoldToConfirmButton> = throw UnsupportedOperationException()
    }

    class Builder {
        private var name: Component? = null
        private var description: OptionDescription = OptionDescription.EMPTY
        private var action: BiConsumer<YACLScreen, HoldToConfirmButton>? = null
        private var buttonText: Component = Component.translatable("turtlshell.button.hold_to_confirm")
        private var holdingText: Component = Component.translatable("turtlshell.button.release_to_cancel")
        private var holdTimeTicks: Int = 30

        fun name(name: Component) = apply { this.name = name }
        fun description(description: OptionDescription) = apply { this.description = description }
        fun action(action: BiConsumer<YACLScreen, HoldToConfirmButton>) = apply { this.action = action }
        fun buttonText(text: Component) = apply { this.buttonText = text }
        fun holdingText(text: Component) = apply { this.holdingText = text }
        fun holdTimeTicks(ticks: Int) = apply { this.holdTimeTicks = ticks }

        fun build(): HoldToConfirmButton {
            requireNotNull(name) { "Name must be set" }
            requireNotNull(action) { "Action must be set" }
            return HoldToConfirmButton(name!!, description, action!!, buttonText, holdingText, holdTimeTicks)
        }
    }

    companion object {
        fun builder() = Builder()
    }
}