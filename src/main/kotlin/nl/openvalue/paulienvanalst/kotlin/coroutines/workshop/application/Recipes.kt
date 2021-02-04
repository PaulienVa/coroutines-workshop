package nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.application

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.references.Recipe
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.annotation.Id
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Flux

@Configuration
open class RoutingConfiguration {

    @Bean
    open fun mainRouter(recipeHandler: RecipeHandler): RouterFunction<ServerResponse> = coRouter {
        (GET("/api/v0/recipes") or GET("/api/recipes")).invoke(recipeHandler::findAll)
    }

}

@Component
open class RecipeHandler(private val recipesRepository2: RecipesRepository2) {

    suspend fun findAll(request: ServerRequest): ServerResponse {

        val recipes = flowOf(
            pancakes, scrambledEggs, fondue
        )

        val recipes2 = recipesRepository2.findAll()
            .transform {
                emit(Recipe(it.name, it.duration, listOf(), ""))
            }
        return ok().bodyAndAwait(recipes2)
    }
}


private val pancakes = Recipe(
    "pancakes",
    20,
    listOf("2 eggs", "250gr flower", "500mL milk", "bit of salt"),
    "Mix everything and bake pancakes"
)
private val scrambledEggs = Recipe(
    "scrambled eggs",
    10,
    listOf("4 eggs", "2 tbsp milk", "bit of salt"),
    "Mix everything and bake and fold the eggs over"
)
private val fondue = Recipe(
    "fondue",
    45,
    listOf("cheese", "wine", "kirsch", "garlic"),
    "Cook the wine, add the cheese, add the kirsch and garlic"
)

@Repository
interface RecipesRepository : ReactiveCrudRepository<RecipeRow, Long> {

    @Query("SELECT * FROM recipes;")
    override fun findAll(): Flux<RecipeRow>
}

@Repository
interface RecipesRepository2 : CoroutineCrudRepository<RecipeRow, Long> {

    @Query("SELECT * FROM recipes;")
    override fun findAll(): Flow<RecipeRow>
}

@Table
data class RecipeRow(@Id var id:Long?, var name : String, var duration: Int)
