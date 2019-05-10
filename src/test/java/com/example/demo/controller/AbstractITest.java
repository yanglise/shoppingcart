package com.example.demo.controller;

import com.example.demo.repository.CartRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class AbstractITest {

    protected static final MockHttpSession DEFAULT_SESSION = new MockHttpSession(null, "session_id");

    protected MockMvc mockMvc;

    private MongodExecutable mongodExecutable;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected CartRepository cartRepository;

    @Autowired
    protected OrderRepository orderRepository;

    @Before
    public void before() throws IOException {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).alwaysDo(MockMvcResultHandlers.print()).build();
        IMongodConfig mongoConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION).net(new Net("localhost", Network.getFreeServerPort(), Network.localhostIsIPv6())).build();
        MongodStarter mongodStarter = MongodStarter.getDefaultInstance();
        mongodExecutable = mongodStarter.prepare(mongoConfig);
        mongodExecutable.start();
    }

    @After
    public void after() {
        productRepository.deleteAll();
        cartRepository.deleteAll();
        orderRepository.deleteAll();
        mongodExecutable.stop();
    }

    protected String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
