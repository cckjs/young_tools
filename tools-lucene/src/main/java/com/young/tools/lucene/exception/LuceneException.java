package com.young.tools.lucene.exception;

public class LuceneException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8546856593890644664L;

	public LuceneException(String message,Throwable throwable){
		super(message, throwable);
	}
}
