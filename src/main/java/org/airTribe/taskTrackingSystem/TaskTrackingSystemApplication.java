package org.airTribe.taskTrackingSystem;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
public class TaskTrackingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskTrackingSystemApplication.class, args);
	}

	@Bean
	public ApplicationRunner printEndpoints(RequestMappingHandlerMapping requestMappingHandlerMapping) {
		return args -> {

			System.out.println("Exposed endpoints:");
			requestMappingHandlerMapping
					.getHandlerMethods()
					.forEach((key, value) -> {
				System.out.println(key.getMethodsCondition()+ "\t:\t"
						+key.getPatternValues().toArray()[0] + "\t->\t" + value);
			});
		};
	}

	@Bean
	public ApplicationRunner runSql(JdbcTemplate jdbcTemplate) {
		return args -> {

			File sqlFile = ResourceUtils.getFile("classpath:Load.sql");

			try (BufferedReader reader = new BufferedReader(new FileReader(sqlFile))) {
				String sql = reader.lines().collect(Collectors.joining("\n"));

				jdbcTemplate.update(sql);

				System.out.println("SQL executed from file after application startup.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
	}
}
