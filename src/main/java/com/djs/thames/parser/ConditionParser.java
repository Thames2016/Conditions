package com.djs.thames.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConditionParser {

    static final Logger logger = LogManager.getLogger(ConditionParser.class.getName());

	private static final int maxTries = 5;
    private static final int retrySeconds = 10;

    public List<Condition> getConditions(){

		List<Condition> conditions = new ArrayList<Condition>();

		Document doc = null;

        int tries = 0;
        while( doc == null && tries++ < maxTries ) {
            try {
                doc = Jsoup.connect(ConditionsProperties.getConditionsUrl()).get();
            } catch (IOException ex) {
                logger.error("Failed to retrieve page at {}", ConditionsProperties.getConditionsUrl());
                logger.error("Exception: {}", ex.getClass().toString());
            }

            if( doc == null){

                logger.warn("Failed to get page from environment agency - attempt number {}", tries);

                try {
                    Thread.sleep(retrySeconds * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if( doc != null) {

            Elements tables = doc.getElementsByClass("advices");
            if (tables.size() == 3) {

                for (Element table : tables) {

                    Elements rows = table.getElementsByTag("tr");
                    for (Element row : rows) {

                        Elements cells = row.getElementsByTag("td");
                        if (cells.size() == 2) {

                            String reachName = cells.get(0).text();
                            String stateName = cells.get(1).text();

                            Condition condition = Condition.createCondition(reachName, stateName);

                            if (condition != null) {
                                conditions.add(condition);
                            } else {
                                logger.error("Failed to create a condition for [reach,state] = [{},{}]", reachName, stateName);
                            }
                        }
                    }
                }
            } else {
                logger.error("Unexpected page format - number of tables = {}", tables.size());
            }
        }

		return conditions;
	}

}
