package com.arkadiusz.surma.cookieclicker.model

interface GameStore {
    fun has(): Boolean
    fun store(game: Game)
    fun retrieve(): Game
    fun flush()
}