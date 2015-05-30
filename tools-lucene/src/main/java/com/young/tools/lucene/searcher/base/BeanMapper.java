package com.young.tools.lucene.searcher.base;

import org.apache.lucene.document.Document;

public interface BeanMapper<T> {

	public T mapper(Document document);
}
