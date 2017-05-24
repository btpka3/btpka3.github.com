package me.test.first.spring.boot.feign.conf

import com.fasterxml.classmate.TypeResolver
import groovy.transform.CompileStatic
import io.swagger.jaxrs.config.BeanConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.context.request.async.DeferredResult
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.ParameterBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.builders.ResponseMessageBuilder
import springfox.documentation.schema.AlternateTypeRule
import springfox.documentation.schema.ModelRef
import springfox.documentation.schema.WildcardType
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger.web.ApiKeyVehicle
import springfox.documentation.swagger.web.SecurityConfiguration
import springfox.documentation.swagger.web.UiConfiguration
import springfox.documentation.swagger2.annotations.EnableSwagger2

import java.time.LocalDate

import static com.google.common.base.Predicates.and
import static com.google.common.base.Predicates.not
import static springfox.documentation.builders.PathSelectors.regex
import static springfox.documentation.schema.AlternateTypeRules.newRule

@Configuration
@EnableSwagger2
@CompileStatic
class SwaggerConf extends WebMvcConfigurerAdapter {

    @Bean
    BeanConfig swaggerBeanConfig() {
        BeanConfig config = new BeanConfig()
        config.setTitle("Swagger sample app")
        config.setVersion("1.0.0");
        config.setBasePath("/api");
        config.setSchemes(["http"] as String[]);
        config.setResourcePackage("me.test.first.spring.boot.feign.resource")
        config.setScan(true)
        return config;
    }

//    /**
//     * swagger 生成的文档中对于model 会包含 "metaClass" 字段，通过该配置统一移除。
//     * 否则，需要每个model上追加 `@JsonIgnoredProperties(["metaClass"])`
//     *
//     * 参考：
//     * http://wiki.fasterxml.com/JacksonFeatureJsonFilter
//     * https://github.com/swagger-api/swagger-core/issues/519#event-167556860
//     */
//    @Bean
//    FilterProvider filterProvider() {
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider()
//
//        println '=================================== filterProvider'
//        // FIXME : 为何不追加 @CompileStatic 下面会报错？
//        filterProvider.addFilter("me.test.first.spring.boot.swagger",
//                SimpleBeanPropertyFilter.serializeAllExcept(["metaClass"] as Set<String>))
//        return filterProvider
//    }

//    /**
//     *
//     * 配置 springfox && swagger， 防止 jackson 生成 "mataClass" JSON 字段。
//     * http://springfox.github.io/springfox/docs/current/
//     *
//     */
//    @Bean
//    ApplicationListener<ObjectMapperConfigured> objectMapperConfiguredListener(FilterProvider filterProvider) {
//        return new ApplicationListener<ObjectMapperConfigured>() {
//            void onApplicationEvent(ObjectMapperConfigured event) {
//                println "=========================== " + event
//               // event.objectMapper.setFilterProvider(filterProvider)
//            }
//        }
//    }
//
//    @Bean
//    JacksonModuleRegistrar jacksonModuleRegistrar(FilterProvider filterProvider) {
//        return new JacksonModuleRegistrar() {
//            void maybeRegisterModule(ObjectMapper objectMapper) {
//                println "=========================== jacksonModuleRegistrar"
//                //objectMapper.setFilterProvider(filterProvider)
//            }
//        }
//    }

    @Bean
    Docket petApi() {

        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("宠物商店API标题")
                .description("宠物商店API描述")
                .contact(new Contact("张三", "https://github.com/btpka3/", "btpka3@163.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .version("2.0")
                .build()

        ApiKey apiKey = new ApiKey("mykey", "api_key", "header")

        AlternateTypeRule rule1 = newRule(
                typeResolver.resolve(DeferredResult.class, typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                typeResolver.resolve(WildcardType.class))

        // 全局响应信息
        ResponseMessage responseMessage = new ResponseMessageBuilder()
                .code(500)
                .message("500 message")
                .responseModel(new ModelRef("Error"))
                .build()

        SecurityContext securityContext = SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(regex("/.*"))
                .build()

        // 全局 header
        Parameter parameter = new ParameterBuilder()
                .name("nonce")
                .description("防止缓存，一次性")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true)
                .build()

        Docket docket = new Docket(DocumentationType.SWAGGER_2)

        docket.select()  // => ApiSelectorBuilder
                .apis(RequestHandlerSelectors.any())
                .paths(and(not(regex("/error.*")), not(regex("/"))))
                .build()

        docket
                .ignoredParameterTypes(MetaClass)  // XXX 重要，对于groovy而言
                .apiInfo(apiInfo)
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(rule1)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, [responseMessage])
                .securitySchemes([apiKey])
                .securityContexts([securityContext])
        //.enableUrlTemplating(true)
                .globalOperationParameters([parameter])
                .tags(
                new Tag("Pets", "宠物相关API"),
                new Tag("test", "测试项关API")

        )
        //.additionalModels(typeResolver.resolve(AdditionalModel.class))
        return docket
    }


    @Autowired
    private TypeResolver typeResolver;


    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return [new SecurityReference("mykey", authorizationScopes)];
    }

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(
                "test-app-client-id",
                "test-app-client-secret",
                "test-app-realm",
                "test-app",
                "apiKey",
                ApiKeyVehicle.HEADER,
                "api_key",
                "," /*scope separator*/);
    }

    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration(
                "validatorUrl",// url
                "none",       // docExpansion          => none | list
                "alpha",      // apiSorter             => alpha
                "schema",     // defaultModelRendering => schema
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
                false,        // enableJsonEditor      => true | false
                true,         // showRequestHeaders    => true | false
                60000L);      // requestTimeout => in milliseconds, defaults to null (uses jquery xh timeout)
    }

}
