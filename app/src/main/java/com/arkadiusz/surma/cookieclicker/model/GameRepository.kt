package com.arkadiusz.surma.cookieclicker.model

interface GameRepository {
    fun getRanking(): List<Game>
    fun add(game: Game)
    fun findAll(): Collection<Game>
}
