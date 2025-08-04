package events.impl

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
@Polymorphic
@OptIn(ExperimentalSerializationApi::class)
sealed class Event(

) {
    abstract val id: Int?
    abstract val createdAt: String?

    companion object {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
            explicitNulls = true
            namingStrategy = JsonNamingStrategy.Builtins.SnakeCase
            classDiscriminator = "type"
            serializersModule = SerializersModule {
                polymorphic(Event::class) {
                    subclass(AchievementEvent::class, AchievementEvent.serializer())
                    subclass(BeatmapPlaycountEvent::class, BeatmapPlaycountEvent.serializer())
                    subclass(BeatmapsetApproveEvent::class, BeatmapsetApproveEvent.serializer())
                    subclass(BeatmapsetDeleteEvent::class, BeatmapsetDeleteEvent.serializer())
                    subclass(BeatmapsetReviveEvent::class, BeatmapsetReviveEvent.serializer())
                    subclass(BeatmapsetUpdateEvent::class, BeatmapsetUpdateEvent.serializer())
                    subclass(BeatmapsetUploadEvent::class, BeatmapsetUploadEvent.serializer())
                    subclass(RankEvent::class, RankEvent.serializer())
                    subclass(RankLostEvent::class, RankLostEvent.serializer())
                    subclass(UserSupportAgainEvent::class, UserSupportAgainEvent.serializer())
                    subclass(UserSupportFirstEvent::class, UserSupportFirstEvent.serializer())
                    subclass(UserSupportGiftEvent::class, UserSupportGiftEvent.serializer())
                    subclass(UsernameChangeEvent::class, UsernameChangeEvent.serializer())
                }
            }
        }
    }
}