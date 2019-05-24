package com.arkadiusz.surma.cookieclicker.infrastructure.memory

import com.arkadiusz.surma.cookieclicker.model.Game
import com.arkadiusz.surma.cookieclicker.model.GameRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

class InMemoryGameRepository : GameRepository {
    private val games: MutableCollection<Game> = mutableListOf()

    override fun findAll(): Collection<Game> {
        return this.games
    }

    override fun getRanking(): List<Game> {
        // shouldn't be mutable outside
        return this.games.sortedWith(compareBy { it.getScore() }).asReversed()
    }

    override fun add(game: Game) {
        this.games.add(game)
    }
}

val inMemoryGameRepositoryModule = Kodein.Module("In Memory Game Repository") {
    bind<GameRepository>() with singleton { InMemoryGameRepository() }
}