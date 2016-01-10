package com.djs.thames.parser;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Properties;

public class Emailer {

    private static final Logger logger = LogManager.getLogger(Emailer.class.getName());

    public void sendEmail(List<String> recipients, List<Condition> conditions){

        for(String recipient : recipients){

            String body = "Dear Subscriber  - here are the changes in conditions\r\n";
            for( Condition condition : conditions){
                body += String.format("\r\n%s %s", condition.getReachName(), condition.getStateName());
            }
            body += "\r\n\r\nThank you!";

            sendEmail(recipient, "River Thames conditions update", body);
        }
    }

    public void sendEmail(String recipient, String subject, String body ){

        try {
            HtmlEmail email = new HtmlEmail();
            email.setHostName(ConditionsProperties.getProperty(ConditionsProperties.EMAIL_HOST_NAME));
            email.setSmtpPort(ConditionsProperties.getPropertyInt(ConditionsProperties.EMAIL_SMTP_PORT, 25));
            email.setAuthenticator(new DefaultAuthenticator(
                    ConditionsProperties.getProperty(ConditionsProperties.EMAIL_USER_NAME),
                    ConditionsProperties.getProperty(ConditionsProperties.EMAIL_PASSWORD)));
            email.setSSLOnConnect(ConditionsProperties.getPropertyBool(ConditionsProperties.EMAIL_SSL, true));
            email.setFrom(ConditionsProperties.getProperty(ConditionsProperties.EMAIL_FROM));
            email.setSubject(subject);
            email.setMsg(body);
            email.addTo(recipient);
            email.send();
        }
        catch(EmailException ex){
            logger.error("Exception whilst sending email to {}", recipient);
            logger.error(ex.getMessage());
        }

    }

}
