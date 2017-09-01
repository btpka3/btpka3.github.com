

- [](https://stackoverflow.com/questions/33901724/configurationproperties-with-enum-values-in-different-case)



- EnvironmentPostProcessor
- ConfigFileApplicationListener
- PropertySourcesPropertyResolver






```text
StandardServletEnvironment#()
    AbstractEnvironment#()
        #customizePropertySources()
            - 向最后追加 "servletConfigInitParams" StubPropertySource 
            - 向最后追加 "servletContextInitParams" StubPropertySource 
            - 向最后追加 "jndiProperties" JndiPropertySource 
            - 向最后追加 "systemProperties" MapPropertySource 
            - 向最后追加 "systemEnvironment" SystemEnvironmentPropertySource
ConfigFileApplicationListener
    #postProcessEnvironment()
        #addPropertySources()
            - 在 "systemEnvironment" 后面追加 RandomValuePropertySource
            - 内部根据不同的 profile 进行初始化
            #loadIntoGroup
                YamlPropertySourceLoader#
                    SpringProfileDocumentMatcher#extractSpringProfiles
                        - 追加 PropertiesPropertySource
            - ConfigFileApplicationListener$ConfigurationPropertySource


# 如何将各种配置文件中的值绑定到Javabean 上的？

PropertiesConfigurationFactory#doBindPropertiesToTarget()
    #getPropertySourcesPropertyValues()
        new PropertySourcesPropertyValues()
            #processPropertySource()
                PropertySourcesPropertyResolver#getProperty()
                    ConfigurationPropertiesBindingPostProcessor$FlatPropertySources#getProperty()  // [environmentProperties,localProperties]

PropertiesConfigurationFactory#propertySources = {
    class : ConfigurationPropertiesBindingPostProcessor$FlatPropertySources
    propertySources : [ { 
        class : PropertySourcesPlaceholderConfigurer$1
        name  : 'environmentProperties',
        source: {
            class           : StandardServletEnvironment,
            propertySource  : {
                class               : MutablePropertySources,
                propertySourceList  : [{
                    class: PropertySource$StubPropertySource,
                    name : 'servletConfigInitParams'
                }, {
                    class: MapPropertySource,
                    name : 'systemProperties'
                }, {
                    class: SystemEnvironmentPropertySource,
                    name : 'systemEnvironment'
                }, {
                    class: RandomValuePropertySource,
                    name : 'random'
                }, {
                    class: MapPropertySource,
                    name : 'applicationConfig: [classpath:/application.yml]'
                }, {
                    class: MapPropertySource,
                    name : 'refresh'
                }]
            }
        }
    }, {
        class : PropertiesPropertySource,
        name  : 'localProperties',
        source: <Properties>  // empty in my test
    }]
}
```




```text
# AA_BB_CC 这样的枚举类在 配置文件中可以以怎样的形式配置？

RelaxedDataBinder#bind()
  RelaxedConversionService#convert()
    1. try DefaultConvertionService#convert()

       # only support `A_VALUE`
       StringToEnumConverterFactory#StringToEnum#convert()

    2. then GenericConversionService#convert()

       # the config key can be :
       # 0 = "a-value"
       # 1 = "a_value"
       # 2 = "aValue"
       # 3 = "avalue"
       # 4 = "A-VALUE"
       # 5 = "A_VALUE"
       # 6 = "AVALUE"
       RelaxedConversionService$StringToEnumIgnoringCaseConverterFactory$StringToEnum#convert()```