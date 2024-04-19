package com.wordel.br.dto

import com.wordel.logic.EventTypeEnum
import com.wordel.logic.Eventable

data class WebSocketMessageDTO(override val eventType: EventTypeEnum) : Eventable