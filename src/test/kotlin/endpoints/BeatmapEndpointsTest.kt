package endpoints

import ModeEnum
import OsuApiProvider
import kotlinx.coroutines.test.runTest
import models.ScoreLegacy
import kotlin.test.Test
import kotlin.test.assertNotNull

class BeatmapEndpointsTest {

    val api = OsuApiProvider.api

    @Test
    fun getUserBeatmapScore() = runTest {
        val score = api.getUserBeatmapScore(2598933, 21009314)
        assertNotNull(score)
    }

    @Test
    fun getUserBeatmapScores() = runTest {
        val scores = api.getUserBeatmapScores(2598933, 21009314)
        assertNotNull(scores)
    }

    @Test
    fun getBeatmapScores() = runTest {
        val scores = api.getBeatmapScores(2598933)
        assertNotNull(scores)
    }

    @Test
    fun getBeatmaps() = runTest {
        val beatmaps = api.getBeatmaps(listOf(2598933, 1872396, 2069841))
        assert(beatmaps.size == 3)
    }

    @Test
    fun getBeatmap() = runTest {
        val beatmap = api.getBeatmap(2598933)
        assertNotNull(beatmap)
    }

    @Test
    fun getBeatmapAttributes() = runTest {
        val beatmap = api.getBeatmapAttributes(2598933, listOf(ScoreLegacy.Mod.DOUBLE_TIME, ScoreLegacy.Mod.HARD_ROCK), ModeEnum.OSU)
        assertNotNull(beatmap)
    }
}