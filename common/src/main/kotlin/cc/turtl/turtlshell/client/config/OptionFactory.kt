package cc.turtl.turtlshell.client.config

import cc.turtl.turtlshell.client.TurtlShellKeybinds
import cc.turtl.turtlshell.client.config.custom.KeyController
import cc.turtl.turtlshell.mixin.accessor.KeyMappingAccessor
import com.mojang.blaze3d.platform.InputConstants
import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.OptionDescription
import dev.isxander.yacl3.api.controller.*
import net.minecraft.client.KeyMapping
import net.minecraft.network.chat.Component
import java.awt.Color
import java.util.function.Consumer
import java.util.function.Supplier

object OptionFactory {

    fun toggleTick(
        translationKey: String, defaultValue: Boolean,
        getter: Supplier<Boolean>, setter: Consumer<Boolean>
    ): Option<Boolean> = Option.createBuilder<Boolean>()
        .name(Component.translatable(translationKey))
        .description(OptionDescription.of(Component.translatable("$translationKey.description")))
        .binding(defaultValue, getter, setter)
        .controller(TickBoxControllerBuilder::create)
        .build()

    fun toggleOnOff(
        translationKey: String, defaultValue: Boolean,
        getter: Supplier<Boolean>, setter: Consumer<Boolean>
    ): Option<Boolean> = Option.createBuilder<Boolean>()
        .name(Component.translatable(translationKey))
        .description(OptionDescription.of(Component.translatable("$translationKey.description")))
        .binding(defaultValue, getter, setter)
        .controller { opt -> BooleanControllerBuilder.create(opt).coloured(true) }
        .build()

    fun floatSlider(
        translationKey: String, defaultValue: Float,
        getter: Supplier<Float>, setter: Consumer<Float>,
        min: Float, max: Float, step: Float
    ): Option<Float> = Option.createBuilder<Float>()
        .name(Component.translatable(translationKey))
        .description(OptionDescription.of(Component.translatable("$translationKey.description")))
        .binding(defaultValue, getter, setter)
        .controller { opt -> FloatSliderControllerBuilder.create(opt).range(min, max).step(step) }
        .build()

    fun intSlider(
        translationKey: String, defaultValue: Int,
        getter: Supplier<Int>, setter: Consumer<Int>,
        min: Int, max: Int, step: Int
    ): Option<Int> = Option.createBuilder<Int>()
        .name(Component.translatable(translationKey))
        .description(OptionDescription.of(Component.translatable("$translationKey.description")))
        .binding(defaultValue, getter, setter)
        .controller { opt -> IntegerSliderControllerBuilder.create(opt).range(min, max).step(step) }
        .build()

    fun <T : Enum<T>> enumCycler(
        translationKey: String, defaultValue: T,
        getter: Supplier<T>, setter: Consumer<T>, enumClass: Class<T>
    ): Option<T> = Option.createBuilder<T>()
        .name(Component.translatable(translationKey))
        .description(OptionDescription.of(Component.translatable("$translationKey.description")))
        .binding(defaultValue, getter, setter)
        .controller { opt -> EnumControllerBuilder.create(opt).enumClass(enumClass) }
        .build()

    fun textField(
        translationKey: String, defaultValue: String,
        getter: Supplier<String>, setter: Consumer<String>
    ): Option<String> = Option.createBuilder<String>()
        .name(Component.translatable(translationKey))
        .description(OptionDescription.of(Component.translatable("$translationKey.description")))
        .binding(defaultValue, getter, setter)
        .controller(StringControllerBuilder::create)
        .build()

    fun colorPicker(
        translationKey: String, defaultValue: Color,
        getter: Supplier<Color>, setter: Consumer<Color>
    ): Option<Color> = Option.createBuilder<Color>()
        .name(Component.translatable(translationKey))
        .description(OptionDescription.of(Component.translatable("$translationKey.description")))
        .binding(defaultValue, getter, setter)
        .controller(ColorControllerBuilder::create)
        .build()

    fun keyMappingPicker(translationKey: String, keyMapping: KeyMapping): Option<InputConstants.Key> =
        Option.createBuilder<InputConstants.Key>()
            .name(Component.translatable(translationKey))
            .description(OptionDescription.of(Component.translatable("$translationKey.description")))
            .binding(
                keyMapping.defaultKey,
                { (keyMapping as KeyMappingAccessor).`turtlshell$getKey`() },
                { v -> TurtlShellKeybinds.rebind(keyMapping, v) }
            )
            .customController(::KeyController)
            .build()

    fun hotkeyPicker(
        translationKey: String, defaultValue: InputConstants.Key,
        getter: Supplier<InputConstants.Key>, setter: Consumer<InputConstants.Key>
    ): Option<InputConstants.Key> = Option.createBuilder<InputConstants.Key>()
        .name(Component.translatable(translationKey))
        .description(OptionDescription.of(Component.translatable("$translationKey.description")))
        .binding(defaultValue, getter, setter)
        .customController(::KeyController)
        .build()
}