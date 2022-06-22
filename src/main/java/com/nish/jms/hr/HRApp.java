package com.nish.jms.hr;

import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class HRApp {

	public static void main(String[] args) throws NamingException {

		InitialContext context = new InitialContext();
		Topic topic = (Topic) context.lookup("topic/myTopic");

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()) {
			Employee employee = new Employee();
			employee.setId(123);
			employee.setFirstName("nish");
			employee.setLastName("bhatt");
			employee.setDesignation("software engineer");
			employee.setEmail("nish@nish.com");
			employee.setPhone("1234567");

			for (int i = 1; i <= 10; i++) {
				jmsContext.createProducer().send(topic, employee);
			}

			System.out.println("message sent");

		}
	}

}
