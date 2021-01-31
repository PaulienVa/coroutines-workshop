package nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.application

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureWebTestClient
class RecipeHandlerTest {

    @Autowired
    private lateinit var client: WebTestClient

    @Test
    fun `get all recipes`() {
        client.get()
            .uri("/api/recipes")
            .exchange()
            .expectStatus()
            .isOk
    }

    @Test
    fun `get all recipes v0`() {
        client.get()
            .uri("/api/v0/recipes")
            .exchange()
            .expectStatus()
            .isOk
    }
}

