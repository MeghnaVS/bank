package com.bms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.bms.model.Branch;

public class BranchRowMapper  implements RowMapper<Branch>{
	
		
		public Branch mapRow(ResultSet resultSet, int count) throws SQLException {
			Branch b=new Branch();
			b.setIfsc(resultSet.getString(1));
			b.setName(resultSet.getString(2));
			b.setAddress(resultSet.getString(3));
			b.setStatus(resultSet.getString(4));
			b.setBankId(resultSet.getInt(5));
			return b;
		}

	}


