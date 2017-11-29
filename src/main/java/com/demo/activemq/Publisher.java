package com.demo.activemq;

import java.awt.font.TextMeasurer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Publisher {
	private String brokerUrl="tcp://127.0.0.1:61616";
	private String adminApp="127.0.0.1:8161";
	
	private Connection connection;
	private ConnectionFactory factory;
	private Session session;
	private MessageProducer producer;
	private Destination destination;
	
	public void send() throws Exception{  
        factory = new ActiveMQConnectionFactory("admin","123456",brokerUrl);  
        connection = factory.createConnection();  
        try {  
	        connection.start();  
	        session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);  
	        destination=session.createQueue("my-queue");
	        producer = session.createProducer(destination);
	        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	        sendMessage(session,producer);
	        session.commit();
        }catch (JMSException jmse) {  //Not a transacted session
            connection.close();  
            throw jmse;  
        }  
    }
	private void sendMessage(Session session, MessageProducer producer) throws Exception {
		System.out.println("publisher start send message......");
		TextMessage message=session.createTextMessage("activemq send message");
		producer.send(message);
	}  
	
	public static void main(String[] args){
		Publisher publisher=new Publisher();
		try {
			publisher.send();
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
	}
}
