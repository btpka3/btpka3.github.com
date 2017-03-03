package me.test.first.spring.boot.swagger.resource

import io.swagger.annotations.Contact
import io.swagger.annotations.ExternalDocs
import io.swagger.annotations.Info
import io.swagger.annotations.License
import io.swagger.annotations.SwaggerDefinition
import io.swagger.annotations.Tag


// https://github.com/springfox/springfox/issues/1257
@SwaggerDefinition(  // 不起作用
        info = @Info(
                description = "Gets the weather",
                version = "V12.0.12",
                title = "The Weather API",
                termsOfService = "http://theweatherapi.io/terms.html",
                contact = @Contact(
                        name = "Rain Moore",
                        email = "rain.moore@theweatherapi.io",
                        url = "http://theweatherapi.io"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        consumes = ["application/json", "application/xml"],
        produces = ["application/json", "application/xml"],
        schemes = [SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS],
        tags = [
            @Tag(name = "Private", description = "Tag used to denote operations as private")
        ],
        externalDocs = @ExternalDocs(value = "Meteorology", url = "http://theweatherapi.io/meteorology.html")
)
public interface TheWeatherApiConfig {
}