package com.web.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.web.common.AESEncryptor;

/**
 * Application Lifecycle Listener implementation class MyContextListener
 *
 */
@WebListener
public class MyContextListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public MyContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    	new AESEncryptor();
    	// 서버 시작되면 무조건 경로 열리게 됨
    }
	
}
