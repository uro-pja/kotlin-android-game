package com.arkadiusz.surma.cookieclicker.infrastructure.memory

import com.arkadiusz.surma.cookieclicker.model.Game
import com.arkadiusz.surma.cookieclicker.model.GameStore
import com.arkadiusz.surma.cookieclicker.model.exceptions.EmptyGameStoreException
import com.arkadiusz.surma.cookieclicker.model.exceptions.GameAlreadyStoredException
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

class InMemoryGameStore : GameStore {
    private lateinit var game: Game

    private var stored: Boolean = false
    override fun store(game: Game) {
        if (this.stored) {
            throw GameAlreadyStoredException()
        }

        this.game = game
        this.stored = true
    }

    override fun has(): Boolean {
        return this.stored
    }

    override fun retrieve(): Game {
        if (this.stored) {
            return this.game
        }

        throw EmptyGameStoreException()
    }

    override fun flush() {
        this.stored = false
    }

}


val inMemoryGameStoreModule = Kodein.Module("In Memory Game Store") {
    bind<GameStore>() with singleton { InMemoryGameStore() }
}