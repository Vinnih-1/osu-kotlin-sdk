package endpoints

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class ChatEndpointsTest {

    val api = OsuApiProvider.apiAsync

    @Test
    fun createNewPM() = runTest {
        api.sendPM(11563892, "Oi, João Lima! Essa é uma mensagem do OsuKDK")
            .also {
                assertNotNull(it)
            }
    }

    @Test
    fun getChannelList() = runTest {
        api.getChannelList().also { println(it) }
    }

    @Test
    fun getChannel() = runTest {
        api.getChannel(9).also { println(it) }
    }

    @Test
    fun getChannelMessages() = runTest {
        api.getChannelMessages(9).also { println(it) }
    }

    @Test
    fun sendMessageChannel() = runTest {
        api.sendMessageChannel(9, "Oi").also { println(it) }
    }

    @Test
    fun joinChannel() = runTest {
        val channelId = 6 // #lobby channel
        val userId = 21009314 // my osu profile

        api.joinChannel(channelId, userId).also { channel ->
            api.leaveChannel(channelId, userId).also { println("Leaving from the channel ${channel.name}") }
        }
    }
}