package com.djs.thames.parser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		ConditionParser parser = new ConditionParser();

		List<Condition> conditions = parser.getConditions();

		if( conditions != null){
//			for( Condition condition: conditions){
//				System.out.println( condition.getReachName() + " : " + condition.getStateName());
//			}

			SqlServerDAL dal = new SqlServerDAL("jdbc:sqlserver://dsaund-9020;database=DS_Conditions_Test;user=sa;password=P0rtra1t;");
			dal.persistConditions(conditions);

			List<Condition> latestConditions = dal.getConditions();
			showConditions(latestConditions, "Latest conditions");

			Calendar cal = new GregorianCalendar();
			cal.set(2016, 0, 5, 17, 27, 0);

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
