package com.arkadiusz.surma.cookieclicker.infrastructure

class Logger {
    private var text: String = ""

    private var callback: (() -> Unit)? = null

    fun log(msg: String) {
        text += "$msg\n"
        callback?.invoke()
    }
}