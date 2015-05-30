package com.young.tools.lucene.searcher.queryparser;

import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.springframework.stereotype.Component;

@Component
public class QuerySortFactory {

	public static class SortedField{
		private String field;
		
		private String fieldType = "string";
		
		private String sortType = "asc";
		
		public SortedField(String field,String fieldType,String sortType){
			this.field = field;
			this.fieldType = fieldType;
			this.sortType = sortType;
		}
	}
	
	public Sort getSort(SortedField[] fields) {
		if (fields == null || fields.length == 0) {
			return Sort.RELEVANCE;
		}
		SortField[] sortFields = new SortField[fields.length];
		for (int i = 0; i < fields.length; i++) {
			sortFields[i] = new SortField(fields[i].field, getFieldType(fields[i]),
					"asc".equals(fields[i].sortType) ? false : true);
		}
		return new Sort(sortFields);

	}

	private Type getFieldType(SortedField field) {
		Type type = null;
		if(field.fieldType==null){
			type = Type.SCORE;
		}else if(field.fieldType.trim().equals("int")){
			type = Type.INT;
		}else if(field.fieldType.trim().equals("long")){
			type = Type.LONG;
		}else{
			type = Type.SCORE;
		}
		return type;
	}

}
