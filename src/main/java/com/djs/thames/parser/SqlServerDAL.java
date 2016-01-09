package com.djs.thames.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SqlServerDAL extends DataAccessLayer {

	private static final Logger logger = LogManager.getLogger(SqlServerDAL.class.getName());
	private static final int numReaches = 45;

	public SqlServerDAL(String connectionString){
		super(connectionString);
	}

	public void persistConditions(List<Condition> conditions){

		String sql = "INSERT INTO Conditions (SnapshotTime ";

		for( int i=0; i<numReaches; i++){
			sql += ", R" + i;
		}
		sql += ") values (CURRENT_TIMESTAMP ";

		for( int i=0; i<numReaches; i++){
			sql += ", " + getState(conditions, i);
		}
		sql += ")";

		try {
			Connection connection = getConnection();
			if (connection != null) {

				Statement stmt = connection.createStatement();
				stmt.execute(sql);
			}
		}
		catch(SQLException ex){
			logger.error("Exception whilst executing SQL Statement to persist conditions");
			logger.error(ex.getMessage());
		}
	}

	private int getState(List<Condition> conditions, int index){

		// The reaches are probably in order
		if( conditions.get(index).getReach() == index){
			return conditions.get(index).getState();
		}

		// If not, do it the long way
		for(int i=0; i<conditions.size(); i++){
			if( conditions.get(i).getReach() == index){
				return conditions.get(i).getState();
			}
		}

		// Return a default value
		return 0;
	}

	public List<Condition> getConditions(){

		String sql = "SELECT * FROM Conditions " +
					 "WHERE SnapshotTime = (SELECT MAX(SnapshotTime) FROM Conditions )";

		return getConditions(sql);
	}

	public List<Condition> getConditions(Calendar timestamp){

		String time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(timestamp.getTime());

		String sql = "SELECT * FROM Conditions " +
					 "WHERE SnapshotTime = (SELECT MAX(SnapshotTime) FROM Conditions WHERE SnapshotTime < '" +
				     time + "')";

		return getConditions(sql);
	}

	private List<Condition> getConditions(String sql){

		List<Condition> conditions = new ArrayList<Condition>();

		try {
			Connection connection = getConnection();
			if (connection != null) {
				Statement stmt = connection.createStatement();

				ResultSet results = stmt.executeQuery(sql);

				if(results != null){
					if( results.next()) {
						Timestamp timestamp = results.getTimestamp("SnapshotTime");

						for (int i = 0; i < numReaches; i++) {
							Condition condition = new Condition();
							condition.setReach(i);
							condition.setState(results.getShort("R" + i));
							conditions.add(condition);
						}
					} else {
						logger.error("No records returned from SQL query to retrieve conditions");
						logger.error(sql);
					}
				}
				else{
					logger.error("Failed to execute SQL query to retrieve conditions");
					logger.error(sql);
				}
			}
		}
		catch(SQLException ex){
			logger.error("An exception occurred whilst retrieving conditions");
			logger.error(ex);
		}

		return conditions;
	}

	public Connection getConnection() {
		try {
			Class.forName(ConditionsProperties.getSqlServerDriver());
		}
		catch (ClassNotFoundException ex) {
			logger.error("Unable to find SQL Server JDBC driver ({}) - {}", ConditionsProperties.getSqlServerDriver(), ex.getMessage());
		}

		Connection connection = null;
		try {
			connection = DriverManager.getConnection(connString);
		}
		catch (SQLException ex) {
			logger.error("SQL Server connection failed - {}", ex.getMessage());
		}

		return connection;
	}

}
