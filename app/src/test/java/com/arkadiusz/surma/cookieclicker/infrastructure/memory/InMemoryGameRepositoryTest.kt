package com.arkadiusz.surma.cookieclicker.infrastructure.memory

import com.arkadiusz.surma.cookieclicker.model.Game
import org.junit.Test

import org.junit.Assert.*

class InMemoryGameRepositoryTest {

    @Test
    fun getRanking() {
        val testedRepository = InMemoryGameRepository()

        val game = Game()
        game.increasePoints()
        game.finish()

        val game2 = Game()
        game2.increasePoints()
        game2.increasePoints()
        game2.finish()

        testedRepository.add(game)
        testedRepository.add(game2)
        val ranking = testedRepository.getRanking()
        assertEquals(2, ranking.first().getPoints())
    }

    @Test
    fun add() {
        val testedRepository = InMemoryGameRepository()

        val game = Game()
        game.increasePoints()

        assertEquals(0, testedRepository.findAll().size)

        testedRepository.add(game)

        assertEquals(1, testedRepository.findAll().size)
        val retrievedGame = testedRepository.findAll().first()
        assertEquals(1, retrievedGame.getPoints())
    }
}