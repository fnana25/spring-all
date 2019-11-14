package com.example.simplejdbcdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @uthor : fengna
 * @create 2019/11/1
 * <description>：TODO
 */
@Slf4j
@Repository
public class FooDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;

    public void insertData() {
        Arrays.asList("b", "c").forEach(e -> jdbcTemplate.update("INSERT INTO FOO(BAR) VALUES(?)", e));
        HashMap<String, String> row = new HashMap<>();
        row.put("bar", "c");
        Number id = simpleJdbcInsert.execute(row);
        log.info("id : {}", id);
    }

    public void listData() {
        List<Foo> list = jdbcTemplate.query("SELECT * FROM FOO ",
                (rs, num) -> Foo.builder()
                        .id(rs.getLong(1))
                        .bar(rs.getString(2)).build()
        );
        list.forEach(e -> log.info("foo {}", e));
    }
}