package com.demo.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Receiver {
	private String brokerUrl="tcp://127.0.0.1:61616";
	private String adminApp="127.0.0.1:8161";//web app admin访问
	
	private Connection connection;
	private ConnectionFactory factory;
	private Session session;
	private MessageConsumer consumer;
	private Destination destination;
	
	public void reveive() throws Exception{  
//        factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
//                ActiveMQConnection.DEFAULT_PASSWORD,brokerUrl);
		factory = new ActiveMQConnectionFactory("admin","123456",brokerUrl);
        connection = factory.createConnection();  
        try {  
	        connection.start();  
	        
	        session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);  
	        destination=session.createQueue("my-queue");
	        consumer = session.createConsumer(destination);
	        while(true){
	        	TextMessage message=(TextMessage)consumer.receive();
	        	if(null!=message){
	        		System.out.println("receiver receive message......");
	        		System.out.println(message.getText());
	        	}else{
	        		break;
	        	}
	        }
        }catch (JMSException jmse) {  
            connection.close();  
            throw jmse;  
        }  
    }
	
	public static void main(String[] args){
		Receiver receiver=new Receiver();
		try {
			receiver.reveive();
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
	}
}

