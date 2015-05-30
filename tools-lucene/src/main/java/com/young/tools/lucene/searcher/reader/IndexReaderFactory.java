package com.young.tools.lucene.searcher.reader;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;
@Component
public class IndexReaderFactory {

	private Map<String, IndexReader> pool = new Hashtable<String, IndexReader>();

	private static final Object lock = new Object();

	public IndexReader openReader(String indexPath) throws IOException {
		synchronized (lock) {
			if (pool.containsKey(indexPath))
				return pool.get(indexPath);
			// 如果索引是实时更新的那么怎么搞
			IndexReader indexReader = DirectoryReader.open(FSDirectory
					.open(new File(indexPath)));
			pool.put(indexPath, indexReader);
			return indexReader;
		}
	}

	public IndexReader reOpenReader(String indexPath) throws IOException {
		synchronized (lock) {
			IndexReader oldReader = pool.get(indexPath);
			if (oldReader == null) {
				oldReader = DirectoryReader.open(FSDirectory.open(new File(
						indexPath)));
				pool.put(indexPath, oldReader);
				return oldReader;
			}
			IndexReader newReader = DirectoryReader
					.openIfChanged((DirectoryReader) oldReader);
			if (newReader != null) {
				pool.put(indexPath, newReader);
				return newReader;
			}
			return oldReader;
		}
	}
}
