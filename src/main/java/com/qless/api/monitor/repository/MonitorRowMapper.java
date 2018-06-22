package com.qless.api.monitor.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.qless.api.monitor.model.Monitor;

public class MonitorRowMapper implements RowMapper<Monitor> {

	@Override
	public Monitor mapRow(ResultSet row, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Monitor monitor = new Monitor();
		monitor.setUserid(row.getString("user_id"));
		monitor.setMerchantId(row.getLong("merchant_id"));
		monitor.setLastname(row.getString("last_name"));
		return monitor;
	}

}
