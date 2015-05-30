package com.young.tools.lucene.searcher.base;

import java.util.Map;

import org.apache.lucene.queryparser.classic.QueryParser.Operator;

public class QueryDomain {

	public void setOperator(QueryOperator operator) {
		this.operator = operator;
	}

	public QueryDomain(String queryString,String[] queryFields, PageDomain page,
			QueryOperator operator, SortDomain sortDomain,Map<String,String> filters) {
		this.queryString = queryString;
		this.queryFields = queryFields;
		this.page = page;
		this.operator = operator;
		this.sortDomain = sortDomain;
		this.filters = filters;
	}

	public enum QueryOperator {
		AND, OR;
	}

	public Operator getLuceneOperator(QueryOperator queryOperator) {
		if (QueryOperator.AND == queryOperator) {
			return Operator.AND;
		} else
			return Operator.OR;
	}

	public static class PageDomain {
		private int pageSize;
		private int pageNum;

		public int getStart() {
			return pageSize * pageNum;
		}

		public PageDomain(int pageSize, int pageNum) {
			this.pageNum = pageNum;
			this.pageSize = pageSize;
		}

		public int getPageSize() {
			return pageSize;
		}

		public int getPageNum() {
			return pageNum;
		}
	}

	public static class SortDomain {

		private String[] fields;

		private String[] sortType;

		public SortDomain(String[] fields, String[] sortType) {
			this.fields = fields;
			this.sortType = sortType;
		}

		public String[] getFields() {
			return fields;
		}

		public String[] getSortType() {
			return sortType;
		}
	}

	private String queryString;
	
	private String[] queryFields;
	
	private Map<String,String> filters;

	public String[] getQueryFields() {
		return queryFields;
	}

	private PageDomain page;

	private QueryOperator operator;

	private SortDomain sortDomain;

	public Map<String, String> getFilters() {
		return filters;
	}

	public SortDomain getSortDomain() {
		return sortDomain;
	}

	public String getQueryString() {
		return queryString;
	}

	public PageDomain getPage() {
		return page;
	}

	public QueryOperator getOperator() {
		return operator;
	}

}
