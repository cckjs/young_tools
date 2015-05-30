package com.young.tools.lucene.indexer;

import java.util.List;

public interface Index<T> {

	public void indexDocuments(List<T> domains,String primaryField) throws Exception;
	
	public void updateDocuments(List<T> domains,String primaryField) throws Exception;
	
	public void deleleDocuments(List<T> domains,String primaryField) throws Exception;
}
