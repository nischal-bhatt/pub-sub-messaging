package com.nish.jms.security;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.nish.jms.hr.Employee;

public class SecurityApp {

	public static void main(String[] args) throws NamingException, JMSException, InterruptedException {

		 InitialContext context = new InitialContext();
		 Topic topic = (Topic) context.lookup("topic/myTopic");
		 
		 try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				 JMSContext jmsContext = cf.createContext()) {
			 
			 jmsContext.setClientID("securityApp");
			 JMSConsumer consumer= jmsContext.createDurableConsumer(topic,"subscription1");
			 consumer.close();
			 
			 Thread.sleep(10000);
			 
			 consumer= jmsContext.createDurableConsumer(topic,"subscription1");
			 Message receive = consumer.receive();
			 Employee emp = receive.getBody(Employee.class);
			System.out.println(emp);
			
			consumer.close();
			jmsContext.unsubscribe("subscription1");
			 
		 }
	}

}
