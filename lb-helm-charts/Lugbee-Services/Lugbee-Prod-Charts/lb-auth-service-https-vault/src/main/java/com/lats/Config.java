package com.lats;

import com.datastax.driver.core.Cluster;
import com.google.common.base.Strings;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.URI;

@Configuration
@ComponentScan(basePackages = "com.lats.*")
@EnableSwagger2
public class Config extends AbstractVaultConfiguration {

    //======= SWAGGER PROPERTIES ========
    @Value("${swagger.apiVersion}")
    private String API_VERSION;
    @Value("${swagger.licenseText}")
    private String LICENSE_TEXT;
    @Value("${swagger.title}")
    private String TITLE;
    @Value("${swagger.description}")
    private String DESCRIPTION;


    //======= CASSANDRA PROPERTIES ===========
    @Value("${cassandra.keyspace}")
    private String KEYSPACE;
    @Value("${cassandra.userName}")
    private String USER_NAME;
    @Value("${cassandra.password}")
    private String PASSWORD;
    @Value("${cassandra.contactPoints}")
    private String CONTACT_POINTS;
    @Value("${cassandra.port}")
    private int PORT;

    //======= REDIS PROPERTIES =======
    @Value("${redis.password}")
    private String REDIS_PASSWORD;
    @Value("${redis.host}")
    private String REDIS_HOST;
    @Value("${redis.port}")
    private int REDIS_PORT;

    //======= REDIS SENTINAL PROPERTIES =======

//    @Value("${redis.sentinel.master}")
//    private String REDIS_SENTINAL_MASTER;
//    @Value("${redis.sentinel.host1}")
//    private String REDIS_SENTINAL_HOST1;
//    @Value("${redis.sentinel.host2}")
//    private String REDIS_SENTINAL_HOST2;
//    @Value("${redis.sentinel.host3}")
//    private String REDIS_SENTINAL_HOST3;
//    @Value("${redis.sentinel.port1}")
//    private String REDIS_SENTINAL_PORT1;
//    @Value("${redis.sentinel.port2}")
//    private String REDIS_SENTINAL_PORT2;
//    @Value("${redis.sentinel.port3}")
//    private String REDIS_SENTINAL_PORT3;

    @Value("${vault.host}")
    private String VAULT_HOST;
    @Value("${vault.token}")
    private String VAULT_TOKEN;
    @Value("${vault.port}")
    private int VAULT_PORT;
    @Value("${vault.uri}")
    private String VAULT_URI;


    //======= SWAGGER CONFIGURATION ========

    protected ApiInfo apiInfo() {

        return new ApiInfoBuilder().license(LICENSE_TEXT).description(DESCRIPTION)
                .version(API_VERSION).title(TITLE).build();
    }

    @Bean
    protected Docket docket() {

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                //.pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    //======= CASSANDRA CONFIGURATION ===========

    @Bean
    protected Cluster cluster() {


        Cluster cluster = Cluster.builder().addContactPoint(CONTACT_POINTS).withPort(PORT).withCredentials(USER_NAME, PASSWORD).build();
        return cluster;
    }

    @Bean
    protected CassandraMappingContext mappingContext() {

        return new CassandraMappingContext();
    }

    @Bean
    protected CassandraConverter converter() {

        return new MappingCassandraConverter(mappingContext());
    }

    @Bean
    protected CassandraSessionFactoryBean cassandraSession() {

        CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();

        try {


            session.setCluster(cluster());
            session.setKeyspaceName(KEYSPACE);
            session.setConverter(converter());
            session.setSchemaAction(SchemaAction.NONE);

            System.out.println("");
            System.out.println("============");
            System.out.println("");
            System.out.println("CASSANDRA CONNECTION ESTABLISHING");
            System.out.println("");
            System.out.println("KEYSPACE : " + KEYSPACE);
            System.out.println("");
            System.out.println("============");
            System.out.println("");


        } catch (Exception execption) {

            System.out.println("");
            System.out.println("----------");
            System.out.println("");
            System.out.println("EXCEPTION OCCURED IN CASSANDRA CONNECTION ESTABLISHMENT");
            System.out.println("");
            System.out.println(execption.getMessage());
            System.out.println("");
            System.out.println("----------");
            System.out.println("");

        }

        return session;

    }

    @Bean
    protected CassandraOperations cassandraTemplate() {
        return new CassandraTemplate(cassandraSession().getObject());

    }

    //======= REDIS(JEDIS) CONFIGURATION ===========

/*    @Bean
    public JedisPool createJedisPool() throws IOException {

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        *//*poolConfig.setMaxTotal(20);
        poolConfig.setMinIdle(2);
        poolConfig.setMaxIdle(5);*//*

        JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.2.554", 6279, 10000,
                "password");
            *//*JedisConnectionFactory factory = new JedisConnectionFactory(poolConfig);
            factory.setHostName("localhost");
            factory.setUsePool(true);
            factory.setPort(6379);*//*

        return pool;
    }*/


/*
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {

        System.out.println("");
        System.out.println("-----------------");
        System.out.println("");
        System.out.println("REDIS PROPERTIES");
        System.out.println("");
        System.out.println("RedisHost : " + REDIS_HOST);
        System.out.println("RedisPort : " + REDIS_PORT);
        System.out.println("RedisPassword : " + REDIS_PASSWORD);
        System.out.println("");
        System.out.println("-----------------");
        System.out.println("");


        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(REDIS_HOST, REDIS_PORT);

        if (REDIS_PASSWORD.equals("none")) {
            redisStandaloneConfiguration.setPassword(RedisPassword.none());
        }
        else{
            redisStandaloneConfiguration.setPassword(RedisPassword.of(REDIS_PASSWORD));
        }
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);

        // return new JedisConnectionFactory();
        return jedisConnectionFactory;
    }

*/

    /**
     * jedis
     */

//    @Bean
//    public RedisConnectionFactory jedisConnectionFactory() {
//
//        System.out.println("");
//        System.out.println("--------------------------");
//        System.out.println("");
//        System.out.println("SENTINAL REDIS PROPERTIES");
//        System.out.println("");
//        System.out.println("REDIS_SENTINAL_MASTER : " + REDIS_SENTINAL_MASTER);
//        System.out.println("");
//        System.out.println("REDIS_SENTINAL_HOST1 : " + REDIS_SENTINAL_HOST1);
//        System.out.println("REDIS_SENTINAL_HOST1 : " + REDIS_SENTINAL_HOST2);
//        System.out.println("REDIS_SENTINAL_HOST1 : " + REDIS_SENTINAL_HOST3);
//        System.out.println("");
//        System.out.println("REDIS_SENTINAL_PORT1 : " + REDIS_SENTINAL_PORT1);
//        System.out.println("REDIS_SENTINAL_PORT2 : " + REDIS_SENTINAL_PORT2);
//        System.out.println("REDIS_SENTINAL_PORT3 : " + REDIS_SENTINAL_PORT3);
//        System.out.println("");
//        System.out.println("REDIS_PASSWORD : " + REDIS_PASSWORD);
//        System.out.println("");
//        System.out.println("--------------------------");
//        System.out.println("");
//        System.out.println("CONFIGURATING JEDIS FACTORY");
//        System.out.println("");
//
//        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
//                .master(REDIS_SENTINAL_MASTER)
//                .sentinel(REDIS_SENTINAL_HOST1, Integer.parseInt(REDIS_SENTINAL_PORT1))
//                .sentinel(REDIS_SENTINAL_HOST2, Integer.parseInt(REDIS_SENTINAL_PORT2))
//                .sentinel(REDIS_SENTINAL_HOST3, Integer.parseInt(REDIS_SENTINAL_PORT3));
//
//        if (REDIS_PASSWORD == null || REDIS_PASSWORD.equals("none")) {
//
//            System.out.println("");
//            System.out.println("--------------");
//            System.out.println("");
//            System.out.println("NOT SETTING Password");
//            System.out.println("");
//            System.out.println("--------------");
//            System.out.println("");
//
//            sentinelConfig.setPassword(RedisPassword.none());
//            //  jedisConnectionFactory.getSentinelConfiguration().setPassword(password);
//
//        } else {
//
//            System.out.println("");
//            System.out.println("--------------");
//            System.out.println("");
//            System.out.println("Setting Password");
//            System.out.println("");
//            System.out.println("REDIS_PASSWORD : " + REDIS_PASSWORD);
//            System.out.println("");
//            System.out.println("--------------");
//            System.out.println("");
//
//            sentinelConfig.setPassword(RedisPassword.of(REDIS_PASSWORD));
//
//        }
//
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(sentinelConfig);
//
//        System.out.println("");
//        System.out.println("JEDIS FACTORY CONFIGURATION DONE");
//        System.out.println("");
//
//        return jedisConnectionFactory;
//    }

//    @Bean
//    RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        try {
//            redisTemplate.setConnectionFactory(jedisConnectionFactory());
//            redisTemplate.setKeySerializer(new StringRedisSerializer());
//            redisTemplate.setValueSerializer(new StringRedisSerializer());
//            redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//            redisTemplate.setHashValueSerializer(new StringRedisSerializer());
//            System.out.println("");
//            System.out.println("============");
//            System.out.println("");
//            System.out.println("REDIS CONNECTION ESTABLISHING");
//            System.out.println("");
//            System.out.println("============");
//            System.out.println("");
//        } catch (Exception execption) {
//
//            System.out.println("");
//            System.out.println("----------");
//            System.out.println("");
//            System.out.println("EXCEPTION OCCURED IN REDIS CONNECTION ESTABLISHMENT");
//            System.out.println("");
//            System.out.println(execption.getMessage());
//            System.out.println("");
//            System.out.println("----------");
//            System.out.println("");
//
//        }
//
//        return redisTemplate;
//    }
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                connector.setProperty("relaxedQueryChars", "|{}[]");
            }
        });
        return factory;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public ClientAuthentication clientAuthentication() {
        return new TokenAuthentication(VAULT_TOKEN);
    }

    @Override
    public VaultEndpoint vaultEndpoint() {

        System.out.println("");
        System.out.println("VAULT_URI: " + VAULT_URI);
        System.out.println("VAULT_HOST: " + VAULT_HOST);
        System.out.println("");

        if (Strings.isNullOrEmpty(VAULT_URI)) {
            VaultEndpoint endpoint = new VaultEndpoint();
            endpoint.setHost(VAULT_HOST);
            endpoint.setPort(VAULT_PORT);
            endpoint.setScheme("http");

            return endpoint;
//            return VaultEndpoint.create(VAULT_HOST, VAULT_PORT);
        } else {
            URI uri = URI.create(VAULT_URI);
            return VaultEndpoint.from(uri);
        }
    }

}
