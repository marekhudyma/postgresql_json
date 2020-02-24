package com.marekhudyma.postgresql.json.repository;


import com.google.common.base.Stopwatch;
import com.marekhudyma.postgresql.json.IntegrationTest;
import com.marekhudyma.postgresql.json.model.InfoJson;
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
class InfoJsonRepositoryIntegrationTest {

    @Autowired
    private InfoJsonRepository underTest;

    private static final String PROPER_JSON = "{\"a\":\"b\"}";

    private static final String INVALID_JSON = "{\"a\":b}";

    @Test
    void shouldSaveAndRead() {
        InfoJson infoJson = InfoJson.builder().json(PROPER_JSON).build();

        InfoJson actual = underTest.saveAndFlush(infoJson);

        InfoJson expected = InfoJson.builder().json(PROPER_JSON).build();
        assertThat(actual).isEqualToIgnoringNullFields(expected);
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void shouldNotInsertInvalidJson() {
        InfoJson infoJson = InfoJson.builder().json(INVALID_JSON).build();

        assertThrows(DataIntegrityViolationException.class, () -> underTest.saveAndFlush(infoJson));
    }

    @Test
    void shouldFindByJsonCustomerIdProperty() {
        InfoJson infoJson1 = underTest.saveAndFlush(InfoJson.builder().json(readFromResources("json/order1.json")).build());
        InfoJson infoJson2 = underTest.saveAndFlush(InfoJson.builder().json(readFromResources("json/order2.json")).build());

        List<InfoJson> actualList = underTest.findByCustomerId("1");

        assertThat(actualList).hasSize(1);
        InfoJson actual = actualList.get(0);
        InfoJson expected = InfoJson.builder().json(readFromResources("json/order1.json")).build();
        assertThat(actual).isEqualToIgnoringNullFields(expected);
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void shouldFindByJsonProductIdProperty() {
        InfoJson infoJson1 = underTest.saveAndFlush(InfoJson.builder().json(readFromResources("json/order1.json")).build());
        InfoJson infoJson2 = underTest.saveAndFlush(InfoJson.builder().json(readFromResources("json/order2.json")).build());

        List<InfoJson> actualList = underTest.findByProductId("1");

        assertThat(actualList).hasSize(1);
        InfoJson actual = actualList.get(0);
        InfoJson expected = InfoJson.builder().json(readFromResources("json/order1.json")).build();
        assertThat(actual).isEqualToIgnoringNullFields(expected);
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void shouldInsertJsonWithDuplication() {
        InfoJson infoJson = underTest.saveAndFlush(InfoJson.builder().json(readFromResources("json/order_duplication.json")).build());

        List<InfoJson> actualListFirstKey = underTest.findByCustomerId("11");
        assertThat(actualListFirstKey).hasSize(0);

        List<InfoJson> actualListSecondKey = underTest.findByCustomerId("21");
        assertThat(actualListSecondKey).hasSize(1);
    }
}