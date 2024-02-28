package me.test.first.spring.boot.test;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import lombok.*;
import me.test.first.spring.boot.test.json.a.Aaa;
import me.test.first.spring.boot.test.json.c.A11;
import me.test.first.spring.boot.test.json.c.A11Mixin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.MapperFeature.USE_ANNOTATIONS;


/**
 * jackson 生成的 json 包含 '@class' 字段，且反序列化时进行class白名单检查。
 *
 * @author dangqian.zll
 * @date 2024/2/7
 * @see com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator
 * @see com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator
 * @see <a href="https://github.com/FasterXML/jackson-docs">jackson-docs</a>
 * @see <a href="https://s.tencent.com/research/report/1209.html">Jackson-databind反序列化漏洞（CVE-2020-35728）风险通告</a>
 * @see <a href="https://www.baeldung.com/jackson-inheritance">Inheritance with Jackson</a>
 * @see <a href="https://github.com/FasterXML/jackson-databind/issues/2524">activateDefaultTyping(PolymorphicTypeValidator, ...) applies to Default Typing use case, but not to @JsonTypeInfo</a>
 * @see <a href="https://blog.csdn.net/jx520/article/details/106876702">Jackson 学习笔记</a>
 * @see com.fasterxml.jackson.annotation.JsonTypeInfo
 * @see com.fasterxml.jackson.annotation.JsonTypeName
 * @see com.fasterxml.jackson.annotation.JsonSubTypes
 * @see com.fasterxml.jackson.annotation.JsonAutoDetect
 * @see com.fasterxml.jackson.annotation.JsonCreator
 * @see com.fasterxml.jackson.annotation.JsonInclude
 * @see com.fasterxml.jackson.annotation.JsonIgnore
 * @see com.fasterxml.jackson.annotation.JsonProperty
 * @see com.fasterxml.jackson.annotation.JsonPropertyOrder
 * @see com.fasterxml.jackson.databind.annotation.JsonDeserialize
 * @see com.fasterxml.jackson.databind.MapperFeature#BLOCK_UNSAFE_POLYMORPHIC_BASE_TYPES
 * @see com.fasterxml.jackson.databind.ObjectMapper#enable(com.fasterxml.jackson.databind.MapperFeature...)
 * @see com.fasterxml.jackson.databind.ObjectMapper#activateDefaultTyping(com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator, com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping, com.fasterxml.jackson.annotation.JsonTypeInfo.As)
 * @see com.fasterxml.jackson.databind.ObjectMapper#setPolymorphicTypeValidator(com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator)
 * @see com.fasterxml.jackson.databind.ObjectMapper#setDefaultTyping(com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder)
 * @see org.springframework.security.jackson2.SecurityJackson2Modules#enableDefaultTyping(com.fasterxml.jackson.databind.ObjectMapper)
 */
public class JacksonWhitelistTest {


    protected ObjectMapper customObjectMapper4WhiteList(ObjectMapper mapper) {
        if (mapper == null) {
            mapper = JsonMapper.builder()
                    .build();
        }

        //mapper.enableDefaultTyping();

        PolymorphicTypeValidator validator = BasicPolymorphicTypeValidator.builder()

                // 尽量使用 allowIfSubType， 因为 allowIfBaseType 的话，不知道具体的子类是来自哪里。

//                .allowIfBaseType("org.springframework.security")
//                .allowIfBaseType("me.test.first.spring.boot.test")
//                .allowIfBaseType("com.alibaba.fastjson")
//                .allowIfBaseType("java.util")

                .allowIfSubType("com.alibaba.fastjson")
                .allowIfSubType("java.util")
                // 添加该条目的原因：MyUserDO 的 baseType="java.lang.Object", 故无法通过父类型校验，只能通过子类型
                .allowIfSubType("me.test.first.spring.boot.test")
                // 添加该条目的原因：org.springframework.security.core.authority.SimpleGrantedAuthority 的 baseType="java.lang.Object", 故无法通过父类型校验，只能通过子类型
                .allowIfSubType("org.springframework.security")

                .build();

        // jackson 默认:
        // JsonTypeInfo.Id.CLASS
        // DefaultTyping.OBJECT_AND_NON_CONCRETE
        // JsonTypeInfo.As.WRAPPER_ARRAY

        // spring security : SecurityJackson2Modules 里使用：
        // JsonTypeInfo.Id.CLASS
        // ObjectMapper.DefaultTyping.NON_FINAL
        // JsonTypeInfo.As.PROPERTY

        // 这个只能设置1次, 与 spring security 的保持一致
        mapper.activateDefaultTyping(validator, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        // arthas watch : objectMapper._deserializationConfig._base._typeResolverBuilder
        // mapper.activateDefaultTyping(validator);

        return mapper;
    }

    /**
     * 模拟 dubbo 中相关类 初始化ObjectMapper
     *
     * @see org.apache.dubbo.spring.security.jackson.ObjectMapperCodec
     */
    protected ObjectMapper customObjectMapperLikeDubboSpringSecurity(ObjectMapper objectMapper) {
        // org.springframework.security.jackson2.CoreJackson2Module
        // -> SecurityJackson2Modules.enableDefaultTyping
        // -> 会 objectMapper.setDefaultTyping()
        // -> 内部: BasicPolymorphicTypeValidator.builder().allowIfSubType(Object.class).build()
        objectMapper.registerModule(new org.springframework.security.jackson2.CoreJackson2Module());
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

        //objectMapper.registerModule(new org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module());
        //objectMapper.registerModule(new org.springframework.security.oauth2.client.jackson2.OAuth2ClientJackson2Module());
        objectMapper.registerModule(new org.springframework.security.web.server.jackson2.WebServerJackson2Module());
        objectMapper.registerModule(new com.fasterxml.jackson.module.paramnames.ParameterNamesModule());
        objectMapper.registerModule(new org.springframework.security.web.jackson2.WebServletJackson2Module());
        objectMapper.registerModule(new org.springframework.security.web.jackson2.WebJackson2Module());
        // FIXME : 这个没有无参构造函数，如何处理？
        //objectMapper.registerModule(new org.springframework.boot.jackson.JsonMixinModule());
        //objectMapper.registerModule(new org.springframework.security.ldap.jackson2.LdapJackson2Module());


        return objectMapper;
    }


    @SneakyThrows
    @Test
    public void test01() {
        ObjectMapper objectMapper = JsonMapper.builder()
                .build();
        objectMapper = customObjectMapper4WhiteList(objectMapper);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aaa", "a1");
        User user = User.builder().name("zhang3").o(jsonObject).o2(jsonObject).build();

        String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
        // DefaultTyping.OBJECT_AND_NON_CONCRETE
        // 针对 user.o2, 由于是子类型，故默认分装了一层：["com.alibaba.fastjson.JSONObject",{"aaa":"a1"}]
        System.out.println("###### json =");
        System.out.println(jsonStr);
        // 需要设置使用 JsonTypeInfo.As.PROPERTY 才会使用 '@class' 字段
        Assertions.assertTrue(jsonStr.contains("class"));

        User newUser = objectMapper.readValue(jsonStr, User.class);
        System.out.println(newUser);
        System.out.println("newUser.o.getClass() = " + newUser.getO().getClass());
        Assertions.assertEquals("zhang3", newUser.getName());
        Assertions.assertEquals("a1", newUser.getO().get("aaa"));
    }

    @SneakyThrows
    @Test
    public void testList01() {
        ObjectMapper objectMapper = JsonMapper.builder()
                .build();
        objectMapper = customObjectMapper4WhiteList(objectMapper);

        User user1 = User.builder().name("zhang3").build();
        User user2 = User.builder().name("li4").build();
        User user = User.builder().name("wang5").children(Arrays.asList(user1, user2)).build();

        String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
        System.out.println(jsonStr);

        User newUser = objectMapper.readValue(jsonStr, User.class);
        System.out.println(newUser);
        Assertions.assertEquals("wang5", newUser.getName());
        Assertions.assertEquals("zhang3", newUser.getChildren().get(0).getName());
        Assertions.assertEquals("li4", newUser.getChildren().get(1).getName());
    }


    /**
     * @see org.springframework.security.jackson2.CoreJackson2Module
     * @see org.springframework.security.authentication.UsernamePasswordAuthenticationToken
     * @see org.springframework.security.jackson2.UsernamePasswordAuthenticationTokenMixin
     * @see org.springframework.security.jackson2.UsernamePasswordAuthenticationTokenDeserializer
     */
    @Test
    @SneakyThrows
    public void testMixInAndPolymorphicTypeValidator01() {
        ObjectMapper objectMapper = JsonMapper.builder()
                .build();
        objectMapper = customObjectMapperLikeDubboSpringSecurity(objectMapper);
        objectMapper = customObjectMapper4WhiteList(objectMapper);

        MyUserDO principal = MyUserDO.builder()
                .userId("zhang3")
                .authorities(Arrays.asList(
                        new SimpleGrantedAuthority("aaa"),
                        new SimpleGrantedAuthority("bbb")
                ))
                .build();
        Object credentials = null;
        List<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority("AAA"),
                new SimpleGrantedAuthority("BBB")
        );
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal, credentials, authorities);

        WebAuthenticationDetails details = new WebAuthenticationDetails("172.16.10.251", null);
        auth.setDetails(details);

        String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(auth);
        System.out.println(jsonStr);

        Authentication result = objectMapper.readValue(jsonStr, Authentication.class);
        Assertions.assertInstanceOf(UsernamePasswordAuthenticationToken.class, result);
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) result;
        MyUserDO userDO = (MyUserDO) token.getPrincipal();
        Assertions.assertEquals("zhang3", userDO.getUserId());
        Assertions.assertTrue(userDO.getAuthorities().stream().anyMatch(a -> Objects.equals("aaa", a.getAuthority())));
        Assertions.assertTrue(token.getAuthorities().stream().anyMatch(a -> Objects.equals("AAA", a.getAuthority())));
        Assertions.assertEquals("172.16.10.251", ((WebAuthenticationDetails) token.getDetails()).getRemoteAddress());
    }

    @SneakyThrows
    @Test
    public void testInherit01() {
        try {
            ObjectMapper objectMapper = JsonMapper.builder()
                    .build();
            PolymorphicTypeValidator validator = BasicPolymorphicTypeValidator.builder()

                    .allowIfBaseType("me.test.first.spring.boot.test.json.a")

                    //.allowIfSubType("me.test.first.spring.boot.test")

                    .build();
            // 这个只能设置1次。
            objectMapper.activateDefaultTyping(validator);
            //objectMapper.activateDefaultTyping(validator, ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE, JsonTypeInfo.As.PROPERTY);

        /*
com.fasterxml.jackson.databind.exc.MismatchedInputException: Unexpected token (START_OBJECT), expected START_ARRAY: need JSON Array to contain As.WRAPPER_ARRAY type information for class me.test.first.spring.boot.test.json.a.Aaa
 at [Source: (String)"{
  "name" : "zhang3",
  "age" : 38
}"; line: 1, column: 1]`
         */

            A11 a11 = new A11();
            a11.setName("zhang3");
            a11.setAge(38);
            String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(a11);
            System.out.println(jsonStr);

            Aaa aaa = objectMapper.readValue(jsonStr, Aaa.class);
            Assertions.fail("应当报错。A11 上未声明 @JsonTypeInfo, 默认不输出class类型信息，故对应输出的值是 JSON Object 格式，" +
                    "而 com.fasterxml.jackson.databind.ObjectMapper.activateDefaultTyping(PolymorphicTypeValidator) 则设置层默认按照 JsonTypeInfo.As.WRAPPER_ARRAY 输出class类型信息。");

            Assertions.assertTrue(aaa instanceof A11);
            A11 newA11 = (A11) aaa;
            Assertions.assertEquals("zhang3", newA11.getName());
            Assertions.assertEquals(38, newA11.getAge());

        } catch (MismatchedInputException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 使用注解。
     */
    @SneakyThrows
    @Test
    public void testMixIn01() {
        ObjectMapper objectMapper = JsonMapper.builder()
                .build();

        objectMapper.addMixIn(A11.class, A11Mixin.class);


        A11 a11 = new A11();
        a11.setName("zhang3");
        a11.setAge(38);
        JSONObject o = new JSONObject();
        o.put("aaa", "a1");
        a11.setO(o);
        Map<String, Object> o2 = new JSONObject(4);
        o2.put("bbb", "b1");
        a11.setO2(o2);

        String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(a11);
        System.out.println(jsonStr);

/* 注意：这里第二个参数不能使用接口类型，否则会报以下错误。
com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of `me.test.first.spring.boot.test.json.a.Aaa` (no Creators, like default constructor, exist): abstract types either need to be mapped to concrete types, have custom deserializer, or contain additional type information
 at [Source: (String)"{
  "@class" : "me.test.first.spring.boot.test.json.c.A11",
  "name" : "zhang3",
  "age" : 38,
  "o" : {
    "aaa" : "a1"
  },
  "o2" : {
    "bbb" : "b1"
  }
}"; line: 1, column: 1]
 */
//        Aaa aaa = objectMapper.readValue(jsonStr, Aaa.class);


        // 第二个参数使用普通类型。
        Aaa aaa = objectMapper.readValue(jsonStr, A11.class);

        Assertions.assertTrue(aaa instanceof A11);
        A11 newA11 = (A11) aaa;
        Assertions.assertEquals("zhang3", newA11.getName());
        Assertions.assertEquals(38, newA11.getAge());
        System.out.println("$.o.getClass() = " + newA11.getO().getClass());
        System.out.println("$.o2.getClass() = " + newA11.getO2().getClass());
/*
{
  "@class" : "me.test.first.spring.boot.test.json.c.A11",
  "name" : "zhang3",
  "age" : 38,
  "o" : {
    "aaa" : "a1"
  },
  "o2" : {
    "bbb" : "b1"
  }
}
$.o.getClass() = class com.alibaba.fastjson.JSONObject
$.o2.getClass() = class java.util.LinkedHashMap
 */
    }


    @SneakyThrows
    @Test
    public void testMixIn02() {
        ObjectMapper objectMapper = JsonMapper.builder()
                .build();
        objectMapper = customObjectMapperLikeDubboSpringSecurity(objectMapper);
        objectMapper = customObjectMapper4WhiteList(objectMapper);

        objectMapper.addMixIn(A11.class, A11Mixin.class);

        A11 a11 = new A11();
        a11.setName("zhang3");
        a11.setAge(38);
        String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(a11);
        System.out.println(jsonStr);

        // 注意：这里第二个参数是接口。
        Aaa aaa = objectMapper.readValue(jsonStr, Aaa.class);

        Assertions.assertTrue(aaa instanceof A11);
        A11 newA11 = (A11) aaa;
        Assertions.assertEquals("zhang3", newA11.getName());
        Assertions.assertEquals(38, newA11.getAge());
    }


    /**
     * 模拟验证
     */
    @SneakyThrows
    @Test
    public void testAuthentication01() {
        ObjectMapper objectMapper = JsonMapper.builder()
                .build();
        // org.springframework.security.jackson2.CoreJackson2Module
        // -> SecurityJackson2Modules.enableDefaultTyping
        // -> 会 objectMapper.setDefaultTyping()
        // -> 内部: BasicPolymorphicTypeValidator.builder().allowIfSubType(Object.class).build()
        objectMapper = customObjectMapperLikeDubboSpringSecurity(objectMapper);

        /* 如果启用 该 MyUserDO ，则会报下面的这个错误。
          java.lang.IllegalArgumentException: The class with me.test.first.spring.boot.test.JacksonWhitelistTest$MyUserDO and name of me.test.first.spring.boot.test.JacksonWhitelistTest$MyUserDO is not in the allowlist. If you believe this class is safe to deserialize, please provide an explicit mapping using Jackson annotations or by providing a Mixin. If the serialization is only done by a trusted source, you can also enable default typing. See https://github.com/spring-projects/spring-security/issues/4370 for details
        */
        MyUserDO principal = MyUserDO.builder()
                .userId("zhang3")
                .authorities(Arrays.asList(
                        new SimpleGrantedAuthority("aaa"),
                        new SimpleGrantedAuthority("bbb")
                ))
                .build();
        principal = null;
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, "123456", Collections.emptyList());


        String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(authentication);
        System.out.println(jsonStr);

        Authentication result = objectMapper.readValue(jsonStr, Authentication.class);
        Assertions.assertInstanceOf(UsernamePasswordAuthenticationToken.class, result);

    }

    /**
     *
     */

    @SneakyThrows
    @Test
    public void testAuthentication02() {
        ObjectMapper objectMapper = JsonMapper.builder()
                .build();

        objectMapper = customObjectMapper4WhiteList(objectMapper);
        objectMapper = customObjectMapperLikeDubboSpringSecurity(objectMapper);

        MyUserDO principal = MyUserDO.builder()
                .userId("zhang3")
                .authorities(Arrays.asList(
                        new SimpleGrantedAuthority("aaa"),
                        new SimpleGrantedAuthority("bbb")
                ))
                .build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, "123456", Collections.emptyList());


        String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(authentication);
        System.out.println(jsonStr);

        Authentication result = objectMapper.readValue(jsonStr, Authentication.class);
        Assertions.assertInstanceOf(UsernamePasswordAuthenticationToken.class, result);

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) result;
        Assertions.assertInstanceOf(MyUserDO.class, token.getPrincipal());
        MyUserDO userDO = (MyUserDO) token.getPrincipal();
        Assertions.assertEquals("zhang3", userDO.getUserId());
    }

    @SneakyThrows
    @Test
    public void test02() {
        ObjectMapper objectMapper = JsonMapper.builder()
                .disable(USE_ANNOTATIONS)
                .build();
        Map<String, Object> map = new HashMap<>(4);
        map.put("aaa", "a1");
        map.put("bbb", "b1");
        User user = User.builder().name("zhang3").o2(map).build();
        String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
        System.out.println("###### json =");
        System.out.println(jsonStr);
    }

    @SneakyThrows
    @Test
    public void test03() {
        ObjectMapper objectMapper = JsonMapper.builder()
                .serializationInclusion(NON_NULL)
                .build();
        A11 a11 = new A11();
        a11.setName("zhang3");
        a11.setAge(38);
        String jsonStr = objectMapper.writeValueAsString(a11);
        System.out.println(jsonStr);
    }

    /**
     * 运行该单测case时，需要 A11 增加注解： `@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY)`
     */
    @Disabled
    @SneakyThrows
    @Test
    public void test04() {
        ObjectMapper objectMapper = JsonMapper.builder()
                .serializationInclusion(NON_NULL)
                .build();

        PolymorphicTypeValidator validator = BasicPolymorphicTypeValidator.builder()
                // 注意：以下 任意启用 allowIfBaseType 或者 allowIfSubType 都OK。
                // 但仅仅启用 allowIfBaseType 时，不能配置成 "me.test.first.spring.boot.test.json.b" : 猜想是根据最顶层的接口来检查的 而检测失败。
                //.allowIfBaseType("me.test.first.spring.boot.test.json.a")
                .allowIfSubType("me.test.first.spring.boot.test.json.c")
                .build();
        objectMapper.activateDefaultTyping(validator, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        String jsonStr = "{\"@class\":\"me.test.first.spring.boot.test.json.c.A11\",\"name\":\"zhang3\",\"age\":38}";

        Aaa aaa = objectMapper.readValue(jsonStr, Aaa.class);

        Assertions.assertInstanceOf(A11.class, aaa);
        A11 newA11 = (A11) aaa;
        Assertions.assertEquals("zhang3", newA11.getName());
        Assertions.assertEquals(38, newA11.getAge());
    }

    @Data
    @Builder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User {
        private String name;
        private JSONObject o;
        private Map<String, Object> o2;
        private List<User> children;
    }


    @Data
    @Builder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyUserDO {
        String userId;
        List<GrantedAuthority> authorities;

    }
}
