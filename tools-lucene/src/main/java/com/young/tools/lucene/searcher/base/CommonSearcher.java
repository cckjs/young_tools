package com.young.tools.lucene.searcher.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopFieldCollector;
import org.springframework.beans.factory.annotation.Autowired;

import com.young.tools.lucene.searcher.ScoreDomain;
import com.young.tools.lucene.searcher.Search;
import com.young.tools.lucene.searcher.SearcherFactory;

public abstract class CommonSearcher implements Search {

	@Autowired
	private SearcherFactory searcherFactory;
	
	protected abstract String getIndexPath();

	private <T extends ScoreDomain> SearchResult<T> collect(Query query, BeanMapper<T> mapper,
			Filter filter, int max_hit, Sort sort) throws IOException {
		long start = System.currentTimeMillis();
		TopDocs docs = null;
		if (sort == null) {
			sort = Sort.RELEVANCE;
		}
		docs = searcherFactory.getSearcher(getIndexPath()).search(query, filter, max_hit,
				sort,true,true);
		return collectResult(docs, mapper, start);
	}

	private <T extends ScoreDomain> SearchResult<T> collectResult(TopDocs docs,
			BeanMapper<T> mapper, long start) throws IOException {
		List<T> documents = new ArrayList<T>();
		SearchResult<T> result = new SearchResult<T>();
		result.setHitNum(docs.totalHits);
		ScoreDoc[] doc = docs.scoreDocs;
		Document document = null;
		T t = null;
		for (ScoreDoc d : doc) {
			document = searcherFactory.getSearcher(getIndexPath()).doc(d.doc);
			t = mapper.mapper(document);
			t.setScore(d.score);
			if (t != null) {
				documents.add(t);
			}
		}
		result.setDocs(documents);
		result.setQtime(System.currentTimeMillis() - start);
		return result;
	}

	@Override
	public <T extends ScoreDomain> SearchResult<T> search(Query query, BeanMapper<T> mapper,
			Filter filter, int topN, Sort sort) throws IOException {
		return collect(query, mapper, filter, topN, sort);
	}

	@Override
	public <T extends ScoreDomain> SearchResult<T> search(Query query, BeanMapper<T> mapper,
			Filter filter, int start, int pageSize, Sort sort)
			throws IOException {
		long startTime = System.currentTimeMillis();
		if (sort == null) {
			sort = Sort.RELEVANCE;
		}
		TopFieldCollector c = TopFieldCollector.create(sort, start + pageSize,
				false, true, true, false);
		searcherFactory.getSearcher(getIndexPath()).search(query, filter,c);
		TopDocs docs = c.topDocs(start, pageSize);
		return collectResult(docs, mapper, startTime);
	}

}
