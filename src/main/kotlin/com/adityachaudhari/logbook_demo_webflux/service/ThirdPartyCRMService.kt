package com.adityachaudhari.logbook_demo_webflux.service

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono

@Service
class ThirdPartyCRMService(private val crmWebClient: WebClient) {

    fun getPost(id: Int): Mono<String> {
        return crmWebClient.get()
            .uri("/posts/{id}", id)
            .retrieve()
            .bodyToMono(String::class.java)
            .onErrorResume(WebClientResponseException.NotFound::class.java) { e ->
                Mono.just("GET not found: ${e.message}")
            }

    }

    fun createPost(post: Map<String, Any>): Mono<String> {
        return crmWebClient.post()
            .uri("/posts")
            .bodyValue(post)
            .retrieve()
            .bodyToMono(String::class.java)
            .onErrorResume(WebClientResponseException.NotFound::class.java) { e ->
                Mono.just("Post not found: ${e.message}")
            }
    }
}
