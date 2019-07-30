package com.thsnoopy.testrunner;

import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestRunnerApplicationTests {
    @BeforeClass
    public static void beforeClass() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "64");
    }

    @Test
    public void trigger() {
        RestTemplate restTemplate = new RestTemplate();
        int numOfConcurrentCall = 8;
        URI uri = new UriTemplate("http://localhost:8080/requestPost").expand();

        List<Integer> list = new ArrayList();

        for (int i = 0; i < numOfConcurrentCall; i ++) {
            list.add(i);
        }

        list.parallelStream()
                .forEach(val -> {
//                    try {
                        restTemplate.getForObject(uri, String.class);
//                    } catch (Exception e) {
//                        log.error("err", e);
//                    }
                });
    }
}
