package com.young.tools.lucene.searcher;

import org.apache.lucene.analysis.Analyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan
public class SearcherConfig {

	@Autowired
	private Environment env;
	@Autowired
	private Analyzer analyzer;
	
	
}
