package com.djs.thames.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConditionParser {

	public List<Condition> getConditions(){

		List<Condition> conditions = new ArrayList<Condition>();

		Document doc = null;

		try {
			doc = Jsoup.connect("http://riverconditions.environment-agency.gov.uk/").get();
		}
		catch(IOException ex){
			// TODO
		}

		Elements tables = doc.getElementsByClass("advices");
		if( tables.size() == 3){

			for( Element table: tables) {
				Elements rows = table.getElementsByTag("tr");

				for( Element row: rows){

					Elements cells = row.getElementsByTag("td");

					if( cells.size() == 2) {

						String reachName = cells.get(0).text();
						String stateName = cells.get(1).text();

						Condition condition = Condition.createCondition(reachName, stateName);

						if( condition != null){
							conditions.add(condition);
						}
						else {
							// TODO - log it
						}
					}
					else {
						// TODO - incorrect result
					}
				}
			}
		}
		else{
			// TODO - incorrect result
		}

		return conditions;
	}

}
