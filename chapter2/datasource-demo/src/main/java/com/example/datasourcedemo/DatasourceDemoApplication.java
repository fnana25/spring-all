package com.example.datasourcedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@SpringBootApplication
public class DatasourceDemoApplication implements CommandLineRunner{

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(DatasourceDemoApplication.class);
	}

	@Override
	public void run(String... args) throws Exception {
		showConnection();
		showData();
	}

	public void showConnection() throws SQLException {
		log.info(dataSource.toString());
		Connection con = dataSource.getConnection();
		log.info(con.toString());
		con.close();
	}

	public void showData(){
		jdbcTemplate.queryForList("select * from FOO").forEach(row->log.info(row.toString()));
	}
}
