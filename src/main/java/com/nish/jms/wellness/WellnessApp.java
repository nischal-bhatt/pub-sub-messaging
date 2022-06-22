package com.nish.jms.wellness;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.nish.jms.hr.Employee;

public class WellnessApp {

	public static void main(String[] args) throws NamingException, JMSException {

		 InitialContext context = new InitialContext();
		 Topic topic = (Topic) context.lookup("topic/myTopic");
		 
		 try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				 JMSContext jmsContext = cf.createContext()) {
			 
			 
			 JMSConsumer consumer= jmsContext.createSharedConsumer(topic,"sharedConsumer");
			 JMSConsumer consumer2= jmsContext.createSharedConsumer(topic,"sharedConsumer");
			 
			 
			 
			 for (int i = 1 ; i <=10 ;i+=2)
			 {
			 Message receive = consumer.receive();
			 Employee emp = receive.getBody(Employee.class);
			System.out.println("from consumer 1 " + emp);
			
			Message receive2 = consumer2.receive();
			 Employee emp2 = receive2.getBody(Employee.class);
			System.out.println("from consumer 2 " + emp2);
			 }
			 
		 }
	}

}
