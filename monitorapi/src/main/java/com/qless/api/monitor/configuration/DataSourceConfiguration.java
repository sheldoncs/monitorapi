package com.qless.api.monitor.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.sql.DataSource;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Configuration
@EnableConfigurationProperties
@Component
public class DataSourceConfiguration {

	private Logger LOG = LoggerFactory.getLogger(DataSourceConfiguration.class);

	@Autowired
	private Environment env;

	private static String DB_PASS_SECRET = "/run/secrets/db_pass_secret";
	private static String DB_PASS_ENV = "DB_PASSWORD";

	@ConfigurationProperties(prefix = "spring.datasource")
	@Bean
	@Primary
	public DataSource getDataSource() {
		return DataSourceBuilder
				.create()
				.password(getDataSourcePassword())
				.build();
	}

	private String getDataSourcePassword() {
		String retVal = null;
		try {

			// Check for DataSource password as a Docker secret first
			LOG.info("Checking for Docker secret db password");
			File file = new File(DB_PASS_SECRET);

			if (file.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(file));
				StringBuffer fileContents = new StringBuffer();
				String line = br.readLine();
				while (line != null) {
					fileContents.append(line);
					line = br.readLine();
				}

				br.close();

				String contents = fileContents.toString();

				byte[] bytes = Base64.decodeBase64(contents);
				retVal = DatatypeConverter.printBase64Binary(bytes);
				LOG.info("Found Docker secret db password");

			} else {

				// Check for DataSource password as a System environment variable
				LOG.info("Checking for environment variable db password");
				if (env.getProperty(DB_PASS_ENV) != null) {
					retVal = env.getProperty(DB_PASS_ENV);
					LOG.info("Found db password as environment variable");
				}
			}
		} catch (Exception e) {
			LOG.error("\"Error occurred while retrieving db password: " + e);
		}
		return retVal;
	}

}
