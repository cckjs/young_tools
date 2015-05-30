package com.young.tools.common.util.mysql.id;

import com.young.tools.common.util.mysql.id.datasource.IDDataSourcePool;

public class BaseIdGenerator {

	protected IDDataSourcePool dsPool;

	public IDDataSourcePool getDsPool() {
		return dsPool;
	}

	public void setDsPool(IDDataSourcePool dsPool) {
		this.dsPool = dsPool;
	}
}
