package cc.turtl.turtlshell.api.client.config.custom

import com.google.gson.*
import com.mojang.blaze3d.platform.InputConstants
import java.lang.reflect.Type

class KeyAdapter : JsonSerializer<InputConstants.Key>, JsonDeserializer<InputConstants.Key> {

    override fun serialize(key: InputConstants.Key, type: Type, ctx: JsonSerializationContext): JsonElement =
        JsonObject().apply {
            addProperty("type", key.type.name)
            addProperty("value", key.value)
        }

    override fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): InputConstants.Key {
        val obj = json.asJsonObject
        val keyType = InputConstants.Type.valueOf(obj.get("type").asString)
        val value = obj.get("value").asInt
        return keyType.getOrCreate(value)
    }
}