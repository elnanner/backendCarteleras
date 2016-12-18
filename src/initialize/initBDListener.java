package initialize;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * Application Lifecycle Listener implementation class initBDListener
 *
 */
public class initBDListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public initBDListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
        InitCarteleras i=context.getBean(InitCarteleras.class);
     	i.init();   	
    }
	
}
