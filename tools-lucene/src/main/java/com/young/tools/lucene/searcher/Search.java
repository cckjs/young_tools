package com.young.tools.lucene.searcher;

import java.io.IOException;

import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;

import com.young.tools.lucene.searcher.base.BeanMapper;
import com.young.tools.lucene.searcher.base.SearchResult;

public interface Search {

	/**
	 * topN搜索
	 * @param query
	 * @param mapper
	 * @param filter
	 * @param topN
	 * @param sort
	 * @return
	 * @throws IOException
	 */
	public <T extends ScoreDomain> SearchResult<T> search(Query query, BeanMapper<T> mapper,
			Filter filter, int topN, Sort sort) throws IOException;
    /**
     * 分页搜索
     * @param query
     * @param mapper
     * @param filter
     * @param start
     * @param pageSize
     * @param sort
     * @return
     * @throws IOException
     */
	public <T extends ScoreDomain> SearchResult<T> search(Query query, BeanMapper<T> mapper,
			Filter filter, int start, int pageSize, Sort sort)
			throws IOException;
}
