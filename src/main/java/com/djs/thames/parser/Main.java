package com.djs.thames.parser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		ConditionsProperties.initialise();

		ConditionParser parser = new ConditionParser();

		List<Condition> conditions = parser.getConditions();

		if( conditions != null){

			SqlServerDAL dal = new SqlServerDAL(ConditionsProperties.getSqlConnection());
			dal.persistConditions(conditions);

			List<Condition> latestConditions = dal.getConditions();
			showConditions(latestConditions, "Latest conditions");

			Calendar cal = new GregorianCalendar();
			cal.set(2016, Calendar.JANUARY, 5, 17, 27, 0);

			List<Condition> earlierConditions = dal.getConditions(cal);
			showConditions(earlierConditions, "Conditions at " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(cal.getTime()));
		}

	}

	private static void showConditions(List<Condition> conditions, String description){

		System.out.println("------------------------------------------------------");
		System.out.println(description);
		System.out.println("------------------------------------------------------");

		for( Condition condition: conditions){
			System.out.println( condition.getReachName() + " : " + condition.getStateName());
		}
	}
}
