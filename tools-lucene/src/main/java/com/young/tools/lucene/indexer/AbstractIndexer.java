package com.young.tools.lucene.indexer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

public abstract class AbstractIndexer<T> implements Index<T> {

	protected abstract Document domainToDoc(T domain) throws Exception;
	
	protected abstract Term primaryKey(String field,T domains);
	
	protected abstract String getIndexPath();
	
	protected List<Term> primaryKeys(String field,List<T> domains){
		if(domains==null){
			return Lists.newArrayList();
		}
		List<Term> result = Lists.newArrayList();
		for(T t:domains){
			result.add(primaryKey(field, t));
		}
		return result;
	}

	@Autowired
	protected IndexWriterFactory indexWriterFactory;
	
	@Override
	public void indexDocuments(List<T> domains,String primaryField) throws Exception {
		updateDocuments(domains,primaryField);
	}

	@Override
	public void updateDocuments(List<T> domains,String primaryField) throws Exception {
		if (CollectionUtils.isEmpty(domains)) {
			return;
		}
		List<Document> docs = new ArrayList<Document>();
		for (T domain : domains) {
			docs.add(domainToDoc(domain));
		}
		List<Term> terms = primaryKeys(primaryField, domains);
		for (int i = 0; i < docs.size(); i++) {
			indexWriterFactory.getIndexWriter(getIndexPath()).updateDocument(terms.get(i), docs.get(i));
		}
		indexWriterFactory.getIndexWriter(getIndexPath()).commit();
	}

	@Override
	public void deleleDocuments(List<T> domains,String primaryField) throws IOException {
		List<Term> terms = primaryKeys(primaryField, domains);
		if (!CollectionUtils.isEmpty(terms))
			for (Term t : terms)
				indexWriterFactory.getIndexWriter(getIndexPath()).deleteDocuments(t);
		indexWriterFactory.getIndexWriter(getIndexPath()).commit();

	}
}
