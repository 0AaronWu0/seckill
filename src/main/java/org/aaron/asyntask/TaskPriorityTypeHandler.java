package org.aaron.asyntask;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aaron.asyntask.AsynTaskEnum.TaskPriotityType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class TaskPriorityTypeHandler implements TypeHandler<TaskPriotityType> {

	public void setParameter(PreparedStatement ps, int i, TaskPriotityType taskPriotityType, JdbcType jdbcType)
			throws SQLException {
		ps.setBigDecimal(i, taskPriotityType.getValue());
	}

	public TaskPriotityType getResult(ResultSet rs, String columnName) throws SQLException {
		return TaskPriotityType.getEnum(rs.getBigDecimal(columnName)) ;
	}

	public TaskPriotityType getResult(ResultSet rs, int columnIndex) throws SQLException {
		return TaskPriotityType.getEnum(rs.getBigDecimal(columnIndex));
	}

	public TaskPriotityType getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return TaskPriotityType.getEnum(cs.getBigDecimal(columnIndex));
	}

}
