package com.knight;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
class KnightApplicationTest {
    @Test
    public void test() {
        log.info("===========测试开始============");
        String sql="select * from PS.PROTECTED_IPC";
//        JSONArray result= ClickHouseConfig.exeSql(sql);
        log.info("===========查询技术============");
        log.info("clickhouse查询结果为：{}",sql);
    }


}
