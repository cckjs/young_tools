package com.young.tools.lucene.analyzer;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;

@Configuration
@ComponentScan
@PropertySource(name = "analyzer", value = { "classpath:lucene/analyzer.properties" })
public class AnalyzerConfig {

	@Autowired
	private Environment env;

	@Bean
	public Analyzer analyzer() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		String analyzer_class = env.getProperty("analyzer.analyzer",
				MMSegAnalyzer.class.getName());
		if (StringUtils.isBlank(analyzer_class))
			analyzer_class = MMSegAnalyzer.class.getName();
		Analyzer analyzer = (Analyzer) Class.forName(analyzer_class)
				.newInstance();
		return analyzer;
	}
}
