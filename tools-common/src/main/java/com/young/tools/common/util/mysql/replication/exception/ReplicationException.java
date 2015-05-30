package com.young.tools.common.util.mysql.replication.exception;

import java.sql.SQLException;

public class ReplicationException extends SQLException{

	public ReplicationException(String string,Throwable exception) {
		super(string, exception);
	}

	public ReplicationException(String string) {
		super(string);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -7265841703483011566L;

}
