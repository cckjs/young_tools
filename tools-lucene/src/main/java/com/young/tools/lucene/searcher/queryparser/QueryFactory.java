package com.young.tools.lucene.searcher.queryparser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Component;

@Component
public class QueryFactory {

	private static final Log log = LogFactory.getLog(QueryFactory.class);

	private static final String index_doc_content = "index_doc_content";

	public Query createDefaultQuery(String query, String[] fields,
			Analyzer analyzer, Operator operator) throws ParseException {
		Query q = null;
		QueryParser parser = null;
		if (fields == null || fields.length == 0) {
			parser = new QueryParser(Version.LUCENE_48, index_doc_content,
					analyzer);
			parser.setDefaultOperator(operator);
			q = parser.parse(query);
		} else {
			BooleanQuery booleanQuery = new BooleanQuery();
			Query temp = null;
			for (String f : fields) {
				parser = new QueryParser(Version.LUCENE_48, f, analyzer);
				parser.setDefaultOperator(operator);
				temp = parser.parse(query);
				booleanQuery.add(temp, Occur.SHOULD);
			}
			q = booleanQuery;
		}
		log.info("--query=[" + q + "]");
		return q;
	}
}
