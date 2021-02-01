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

### Build and endpoint
Now off we go!

In the package `application`, open the class [SpringWebFluxApplication.kt](./../src/main/kotlin/nl/openvalue/paulienvanalst/kotlin/coroutines/workshop/application/SpringWebFluxApplication.kt).
This still looks like the classic Spring boot class (but with a Kotlin flavor).

Now let's implement some REST calls in [Recipes.kt](./../src/main/kotlin/nl/openvalue/paulienvanalst/kotlin/coroutines/workshop/application/Recipes.kt).

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


Now implement a `ReactiveCrudRepository<RecipeRow, Long>` using `RecipeRow as:

```kotlin
@Table
data class RecipeRow(@Id var id:Long?, var name : String, var duration: Integer)
```

This repository should implement one function to retrieve all the recipes from the database (`"SELECT * FROM recipes;"`).

Return the values from the query to the handler, so that the endpoint is now returning data from the database.

Do make sure the values returned by the endpoint are of `Recipe` and not of type `RecipeRow`. (Hint: use `transform {}` and `emit()`).


## Ktor
