package com.marekhudyma.postgresql.json.repository;


import com.google.common.base.Stopwatch;
import com.marekhudyma.postgresql.json.IntegrationTest;
import com.marekhudyma.postgresql.json.model.InfoJson;
import com.marekhudyma.postgresql.json.model.InfoJsonB;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static com.marekhudyma.postgresql.json.utils.Resources.readFromResources;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@IntegrationTest
@Transactional
class InfoJsonBRepositoryIntegrationTest {

    @Autowired
    private InfoJsonBRepository underTest;

    private static final String PROPER_JSON = "{\"a\":\"b\"}";

    private static final String INVALID_JSON = "{\"a\":b}";

    @Test
    void shouldSaveAndRead() {
        InfoJsonB infoBJson = InfoJsonB.builder().json(PROPER_JSON).build();

        InfoJsonB actual = underTest.saveAndFlush(infoBJson);

        InfoJsonB expected = InfoJsonB.builder().json(PROPER_JSON).build();
        assertThat(actual).isEqualToIgnoringNullFields(expected);
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void shouldNotInsertInvalidJson() {
        InfoJsonB infoJsonB = InfoJsonB.builder().json(INVALID_JSON).build();

        assertThrows(DataIntegrityViolationException.class, () -> underTest.saveAndFlush(infoJsonB));
    }

    @Test
    void shouldFindByJsonCustomerIdProperty() {
        InfoJsonB infoJson1 = underTest.saveAndFlush(InfoJsonB.builder().json(readFromResources("json/order1.json")).build());
        InfoJsonB infoJson2 = underTest.saveAndFlush(InfoJsonB.builder().json(readFromResources("json/order2.json")).build());

        List<InfoJsonB> actualList = underTest.findByCustomerId("1");

        assertThat(actualList).hasSize(1);
        InfoJsonB actual = actualList.get(0);
        InfoJsonB expected = InfoJsonB.builder().json(readFromResources("json/order1.json")).build();
        assertThat(actual).isEqualToIgnoringNullFields(expected);
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void shouldFindByJsonProductIdProperty() {
        InfoJsonB infoJson1 = underTest.saveAndFlush(InfoJsonB.builder().json(readFromResources("json/order1.json")).build());
        InfoJsonB infoJson2 = underTest.saveAndFlush(InfoJsonB.builder().json(readFromResources("json/order2.json")).build());

        List<InfoJsonB> actualList = underTest.findByProductId("1");

        assertThat(actualList).hasSize(1);
        InfoJsonB actual = actualList.get(0);
        InfoJsonB expected = InfoJsonB.builder().json(readFromResources("json/order1.json")).build();
        assertThat(actual).isEqualToIgnoringNullFields(expected);
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void shouldInsertJsonWithDuplication() {
        InfoJsonB infoJson = underTest.saveAndFlush(InfoJsonB.builder().json(readFromResources("json/order_duplication.json")).build());

        List<InfoJsonB> actualListFirstKey = underTest.findByCustomerId("11");
        assertThat(actualListFirstKey).hasSize(0);

        List<InfoJsonB> actualListSecondKey = underTest.findByCustomerId("21");
        assertThat(actualListSecondKey).hasSize(1);
    }

}