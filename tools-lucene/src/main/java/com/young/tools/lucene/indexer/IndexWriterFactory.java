package com.young.tools.lucene.indexer;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IndexWriterFactory {

	private static final Object lock = new Object();

	private static Map<String,IndexWriter> writerPool = new Hashtable<String,IndexWriter>();
	
	@Autowired
	private Analyzer analyzer;
	
	public IndexWriter getIndexWriter(String indexPath) throws IOException{
		synchronized(lock){
			if(writerPool.containsKey(indexPath)){
				return writerPool.get(indexPath);
			}else{
				IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_48, analyzer);
				config.setOpenMode(OpenMode.CREATE_OR_APPEND);
				IndexWriter writer = new IndexWriter(FSDirectory.open(new File(indexPath)), config);
				writerPool.put(indexPath, writer);
				return writer;
			}
		}
	}
}
