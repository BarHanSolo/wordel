package com.wordel.br.config

import com.wordel.br.services.WordlService
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration @EnableWebSocket
class WebsocketConfig : WebSocketConfigurer{
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(WordlService(), "/ws").setAllowedOrigins("*")
    }
}