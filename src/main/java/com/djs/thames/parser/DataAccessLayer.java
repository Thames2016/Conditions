package com.djs.thames.parser;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class DataAccessLayer {

	protected String connString = "";

	public DataAccessLayer( String connectionString){
		connString = connectionString;
	}

	public abstract void persistConditions(List<Condition> conditions);
	public abstract List<Condition> getConditions();
	public abstract List<Condition> getConditions(Calendar timestamp);
}
