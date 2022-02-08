package by.urbel.hotel.controller.listener;

import by.urbel.hotel.controller.listener.exception.ListenerException;
import by.urbel.hotel.dao.impl.pool.ConnectionPool;
import by.urbel.hotel.dao.impl.pool.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger logger= LogManager.getLogger(ContextListener.class.getName());
    private static final String ERROR_TO_INIT_POOL="Error while initializing pool database";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPool.getInstance().init();
        } catch (ConnectionPoolException e) {
            logger.fatal(ERROR_TO_INIT_POOL);
            throw new ListenerException(ERROR_TO_INIT_POOL, e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool.getInstance().destroy();
    }
}