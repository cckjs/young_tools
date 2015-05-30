package com.young.tools.lucene.searcher;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import org.apache.lucene.search.IndexSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.young.tools.lucene.searcher.reader.IndexReaderFactory;

@Component
public class SearcherFactory {

	@Autowired
	private IndexReaderFactory indexReaderFactory;

	private Map<String, IndexSearcher> pool = new Hashtable<String, IndexSearcher>();

	private static final Object lock = new Object();

	private static int seachCount = 0;
	
	private static final Random rand = new Random();

	public IndexSearcher getSearcher(String indexPath) throws IOException {
		IndexSearcher searcher = null;
		synchronized (lock) {
			if (seachCount >= 100||rand.nextInt(100)<20) {
				searcher = openRealSearcher(indexPath);
				seachCount = 0;
			} else {
				searcher = openSearcher(indexPath);
				seachCount++;
			}
			return searcher;
		}
	}

	private IndexSearcher openSearcher(String index_path) throws IOException {
		if (pool.containsKey(index_path))
			return pool.get(index_path);
		// 如果索引是实时更新的那么怎么搞
		IndexSearcher searcher = new IndexSearcher(
				indexReaderFactory.openReader(index_path));
		pool.put(index_path, searcher);
		return searcher;
	}

	private IndexSearcher openRealSearcher(String index_path)
			throws IOException {
		IndexSearcher searcher = new IndexSearcher(
				indexReaderFactory.reOpenReader(index_path));
		pool.put(index_path, searcher);
		return searcher;
	}
}
