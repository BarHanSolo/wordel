package com.wordel.br.services

import com.google.gson.Gson
import com.wordel.br.dto.WebSocketMessageDTO
import com.wordel.logic.EventTypeEnum
import com.wordel.logic.Eventable
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession

class WordlService : WebSocketHandler{
    override fun afterConnectionEstablished(session: WebSocketSession) {
        session.sendMessage(TextMessage("kurwa"))
    }

    override fun handleMessage(session: WebSocketSession, message: WebSocketMessage<*>) {
        val gson = Gson()
        val payload = message.payload.toString()
        println(payload)
        val myObject = gson.fromJson(payload, WebSocketMessageDTO::class.java)
        when (myObject.eventType) {
            EventTypeEnum.GUESS -> println("guess")
            EventTypeEnum.GUESS_RESPONSE -> println("guess response")
            EventTypeEnum.GUESS_OTHER_PLAYER -> println("guess other")
            EventTypeEnum.ROUND_ENDED -> println("end of round")
        }
    }

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        TODO("Not yet implemented")
    }

    override fun afterConnectionClosed(session: WebSocketSession, closeStatus: CloseStatus) {
        TODO("Not yet implemented")
    }

    override fun supportsPartialMessages(): Boolean {
        return true
    }
}