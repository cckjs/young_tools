package com.young.tools.lucene.searcher.base;

import java.io.Serializable;
import java.util.List;

import org.apache.lucene.search.ScoreDoc;

@SuppressWarnings("serial")
public class SearchResult<T> implements Serializable {
	public List<T> getDocs() {
		return docs;
	}

	public void setDocs(List<T> docs) {
		this.docs = docs;
	}

	private int hitNum;

	private List<T> docs;

	private long qtime;
	
	public int getHitNum() {
		return hitNum;
	}

	public void setHitNum(int hitNum) {
		this.hitNum = hitNum;
	}

	public long getQtime() {
		return qtime;
	}

	public void setQtime(long qtime) {
		this.qtime = qtime;
	}

}
