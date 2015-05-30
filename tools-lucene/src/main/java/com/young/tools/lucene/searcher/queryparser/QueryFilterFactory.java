package com.young.tools.lucene.searcher.queryparser;

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermQuery;
import org.springframework.stereotype.Component;

@Component
public class QueryFilterFactory {

	public Filter queryToFilter(Map<String, String> filters) {
        if(filters==null||filters.size()==0){
        	return null;
        }
        BooleanQuery query = new BooleanQuery();
        Query tempQuery = null;
        for(Map.Entry<String, String> entry:filters.entrySet()){
        	if(entry.getValue().contains("(")||entry.getValue().contains("[")){
        		tempQuery = createNumbericQuery(entry.getKey(),entry.getValue());
        		query.add(tempQuery, Occur.MUST);
        	}else{
        		tempQuery = createTermQuery(entry.getKey(),entry.getValue());
        		query.add(tempQuery, Occur.MUST);
        	}
        }
        return new QueryWrapperFilter(query);
	}

	private Query createTermQuery(String field,String value) {
		Query termQuery = new TermQuery(new Term(field,value));
		return termQuery;
	}

	private Query createNumbericQuery(String field,String value) {
		boolean containLeft = value.contains("[");
		boolean containRight = value.contains("]");
		String[] values = value.replaceAll("[\\[()\\]]", "").split(",");
		Query rangeQuery = NumericRangeQuery.newIntRange(field, Integer.parseInt(values[0]), Integer.parseInt(values[1]), containLeft, containRight);
		return rangeQuery;
	}
	public static void main(String[] args) {
		QueryFilterFactory factory = new QueryFilterFactory();
		System.out.println("(1,2]".replaceAll("[\\[()\\]]", ""));
		Map<String,String> filters = new HashMap<String,String>();
		filters.put("type", "1");
		filters.put("audit_industry", "教育厅");
		filters.put("annual", "[2011,2014]");
		System.out.println(factory.queryToFilter(filters));
	}
}
