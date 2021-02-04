# Integration with frameworks

In this workshop, two main server-side frameworks will be introduced, that can help you developing your Kotlin application using coroutines:
- Spring WebFlux
- Ktor

Spring is already well-known coming from the Java ecosystem. Ktor might sound new, as it is a Kotlin only framework.

Until now the main function launched the cooking logic, let's create a true application to make it a bit more realistic

## Spring WebFlux
 
### Adding dependencies

Add the following dependencies to make sure Spring is integrated in the project (in this workshop, Spring boot's latest stable release is used):

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.4.2</version>
</parent>

<dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>
    <dependency>
      <groupId>com.fasterxml.jackson.module</groupId>
      <artifactId>jackson-module-kotlin</artifactId>
    </dependency>
    <dependency>
      <groupId>io.projectreactor.kotlin</groupId>
      <artifactId>reactor-kotlin-extensions</artifactId>
    </dependency>
    <dependency>
      <groupId>org.jetbrains.kotlin</groupId>
      <artifactId>kotlin-reflect</artifactId>
    </dependency>
    <dependency>
      <groupId>org.jetbrains.kotlinx</groupId>
      <artifactId>kotlinx-coroutines-reactor</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>io.projectreactor</groupId>
      <artifactId>reactor-test</artifactId>
      <scope>test</scope>
    </dependency>
</dependencies>
```
To the `<build>` section of the pom add:

```xml
    <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
            <mainClass>nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.application.SpringWebFluxApplication</mainClass>
        </configuration>
    </plugin>
```

As a Java framework is introduced, null safety has to be enforced at the Java side as well. Therefore, the following configuration had to be added to `kotlin-maven-plugin` in the `<build>` section:

```xml
    <configuration>
        <args>
            <arg>-Xjsr305=strict</arg>
        </args>
    </configuration>
```

### Build an endpoint
Now off we go!

In the package `application`, open the class [SpringWebFluxApplication.kt](./../src/main/kotlin/nl/openvalue/paulienvanalst/kotlin/coroutines/workshop/application/SpringWebFluxApplication.kt).

Uncomment the outcommented code.

This still looks like the classic Spring boot class (but with a Kotlin flavor).

Now let's implement some REST calls in [Recipes.kt](./../src/main/kotlin/nl/openvalue/paulienvanalst/kotlin/coroutines/workshop/application/Recipes.kt).


Uncomment the outcommented code.

There, two classes are already defined: 

- `RecipeHandler`
- `RoutingConfiguration`

The first one, is returning all the recipes, the second one, is defining the REST calls.

The first one is already implemented (for now), but for the second one, the REST call for retrieving all the recipes has to be implemented.
The `coRouter` block will accept a DSL for implementing the REST API.
Implement the two endpoints `/api/recipes` and `/api/v0/recipes` returning all the recipes, using the `Handler`. Hint use the official [documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/languages.html#webflux-fn).

Verify the implementation using the test [RecipeHandlerTest](./../src/test/kotlin/nl/openvalue/paulienvanalst/kotlin/coroutines/workshop/application/RecipeHandlerTest.kt) or by launching the application and calling the endpoint using curl:

```bash
 curl -X GET http://localhost:8080/api/recipes/
 curl -X GET http://localhost:8080/api/v0/recipes/
```

Now, this might not feel very special and REST endpoints are only really reactive if the whole is non-blocking, so also the integration with the database layer.

### add a database layer
Let's integrate an h2 database:

```xml
<dependency>
    <groupId>io.r2dbc</groupId>
    <artifactId>r2dbc-h2</artifactId>
    <version>0.8.4.RELEASE</version>
</dependency>
<dependency>
    <groupId>io.r2dbc</groupId>
    <artifactId>r2dbc-spi</artifactId>
    <version>0.8.3.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-r2dbc</artifactId>
    <version>1.2.3</version>
</dependency>
```

Now annotate the [SpringWebFluxApplication.kt](./../src/main/kotlin/nl/openvalue/paulienvanalst/kotlin/coroutines/workshop/application/SpringWebFluxApplication.kt) with the following:

```
@EnableR2dbcRepositories
```

from the package `org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories`.

And add the following configuration in the same file:

```
@Configuration
open class DatabaseConfiguration {
    @Bean
    open fun initializer(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(connectionFactory)
        val populator = CompositeDatabasePopulator()
        populator.addPopulators(ResourceDatabasePopulator(ClassPathResource("schema.sql")))
        populator.addPopulators(ResourceDatabasePopulator(ClassPathResource("data.sql")))
        initializer.setDatabasePopulator(populator)
        return initializer
    }
}
```


Now implement a `ReactiveCrudRepository<RecipeRow, Long>` using `RecipeRow` as (implementing with Spring means extending the interface):

```kotlin
@Table
data class RecipeRow(@Id var id:Long?, var name : String, var duration: Integer)
```

This repository should implement one function to retrieve all the recipes from the database (using the annnnotation `@Query` using the query `"SELECT * FROM recipes;"`).

Return the values from the query to the handler, so that the endpoint is now returning data from the database.

Do make sure the values returned by the endpoint are of `Recipe` and not of type `RecipeRow`. (Hint: use `transform {}` and `emit()`).

It is ok to leave the ingredient to be an empty list and preparation an empty String.

Now let's implement the exact same repository but implementing (or extending the interface `CoroutineCrudRepository`)

Have a look at the difference.


## Ktor

[Ktor](https://ktor.io/learn/) is a full Kotlin framework to build backend applications. 
Building a Ktor application is very different than a Spring application and can be interesting as well.

Now let's starting building one!

First add the following dependencies:

```xml
<dependency>
    <groupId>io.ktor</groupId>
    <artifactId>ktor-server-core</artifactId>
    <version>${ktor.version}</version>
</dependency>

<dependency>
    <groupId>io.ktor</groupId>
    <artifactId>ktor-server-netty</artifactId>
    <version>${ktor.version}</version>
</dependency>

<dependency>
    <groupId>io.ktor</groupId>
    <artifactId>ktor-serialization</artifactId>
    <version>${ktor.version}</version>
</dependency>

<dependency>
    <groupId>io.ktor</groupId>
    <artifactId>ktor-jackson</artifactId>
    <version>${ktor.version}</version>
</dependency>

<dependency>
    <groupId>org.jetbrains.kotlinx</groupId>
    <artifactId>kotlinx-serialization-runtime-common</artifactId>
    <version>0.20.0</version>
</dependency>

<dependency>
    <groupId>io.ktor</groupId>
    <artifactId>ktor-server-tests</artifactId>
    <version>${ktor.version}</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>1.4.200</version>
</dependency>
<dependency>
    <groupId>org.jetbrains.exposed</groupId>
    <artifactId>exposed-core</artifactId>
    <version>0.29.1</version>
</dependency>
<dependency>
    <groupId>org.jetbrains.exposed</groupId>
    <artifactId>exposed-dao</artifactId>
    <version>0.29.1</version>
</dependency>
<dependency>
    <groupId>org.jetbrains.exposed</groupId>
    <artifactId>exposed-jdbc</artifactId>
    <version>0.29.1</version>
</dependency>
```

Open the file [KtorApplication.kt](./../src/main/kotlin/nl/openvalue/paulienvanalst/kotlin/coroutines/workshop/ktor/application/KtorApplication.kt)..

Start with the standard main function and add the following:


```kotlin
embeddedServer(
    Netty,
    port = 8080,
    module = Application::recipes
).start(wait = true)
```

This will start the application on a NettyServer on port 8080.

The module here is not yet defined, so let's add that as well. This will be a REST endpoint to retrieve the recipes.

### Build an endpoint

Now implement this one, defining the extension function of the class Application (from `io.ktor.application.*`)

```kotlin
fun Application.recipes() {
    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }

    install(Routing) {
        recipeEndpoint()
    }
}
```

Now implement the endpoint `recipeEnpoint`:
```kotlin
fun Route.recipeEndpoint() {
    route("api/") {
        get("recipes/") {
            val recipes = 
            // INSERT a static list of Recipe here
            call.respond(recipes)
        }
    }
}
```

Now run your application in IntelliJ and call the endpoint to see the result.


### add a database layer

For this we are going to use [Exposed from JetBrains](https://github.com/JetBrains/Exposed).

First let's define the RecipeRow table (still in the same file):

```kotlin
object RecipeRow : Table("recipe_row") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val duration = integer("duration")
    override val primaryKey = PrimaryKey(id)
}
```

Now that we declared the table, let's initialize the database.
Still in the same file, declare the following method:

```kotlin
fun initDatabase() {

    transaction {
        createMissingTablesAndColumns(RecipeRow)
    }

    transaction {
        RecipeRow.insert {
            it[name] = "pancakes"
            it[duration] = 10
        }
    }
}
```

In the `Application.recipes()` module, call the `initDatabase()` method. Now we want to retrieve this value from the database.

So let's create a repository, with an async query: 

```kotlin
class RecipeRepository {
    suspend fun getRecipes(): List<Recipe> = suspendedTransactionAsync {
       // QUERY to retrieve all the recipes (Use the object RecipeRow)
    }.await()

    private fun toRecipe(row: ResultRow): Recipe {
        return Recipe(row[RecipeRow.name], row[RecipeRow.duration], listOf(), "")
    }
}
```

In the `Application.recipe()` module, instantiate the repository `val recipeRepository = RecipeRepository()` and add it as a dependency to the `recipeEndpoint()`.
Now instead of retrieve a static list of recipes, retrieve the values from the database.

The endpoint should now return a 200 (OK) respons, with a body: 

```json
[
  {
    "name": "pancakes",
    "duration": 10,
    "ingredients": [],
    "preparation": ""
  }
]
```
