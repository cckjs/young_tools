package com.young.tools.lucene.indexer;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource(name = "indexer", value = { "classpath:lucene/indexer.properties" })
public class IndexerConfig {

	@Bean
	public IndexWriterFactory indexWriterFactory() throws IOException {
		return new IndexWriterFactory();
	}
}
