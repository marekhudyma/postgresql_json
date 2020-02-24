package com.marekhudyma.postgresql.json;

import com.google.common.base.Stopwatch;
import com.marekhudyma.postgresql.json.model.InfoJsonB;
import com.marekhudyma.postgresql.json.repository.InfoJsonBRepository;
import com.marekhudyma.postgresql.json.repository.InfoJsonRepository;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.marekhudyma.postgresql.json.utils.Resources.readFromResources;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    private InfoJsonBRepository infoJsonBRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("-------------------------------------------- JSONB ");
        String jsonBig = readFromResources("json/order_big.json");

        for (int i = 0; i < 100; i++) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            for (int j = 0; j < 1_000; j++) {
                infoJsonBRepository.saveAndFlush(InfoJsonB.builder().json(
                        jsonBig.replace("$CUSTOMER_ID", String.valueOf(i * 1_000 + j))
                ).build());
            }
            System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
    }
}
