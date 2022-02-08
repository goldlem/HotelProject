package by.urbel.hotel.dao.impl.pool;

import by.urbel.hotel.dao.impl.pool.exception.ConnectionPoolException;
import com.mysql.cj.jdbc.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private static final String CONNECTION_POOL_PROPERTIES = "connectionPool.properties";
    private static final String URL_NAME = "db.url";
    private static final String DEFAULT_POOL_SIZE_NAME = "db.PoolSize";
    private static final String DEFAULT_CONNECTION_AWAITING_TIMEOUT_NAME = "db.defaultConnectionAwaitingTimeout";

    private final BlockingQueue<PooledConnection> freeConnections = new LinkedBlockingQueue<>();
    private final List<PooledConnection> usedConnections = new LinkedList<>();
    private final Properties connectionPoolProperties;
    private final AtomicBoolean isInitialized;
    private final AtomicBoolean isPoolClosing;
    private final Lock initLock;

    private String url;
    private int poolSize;
    private int defaultConnectionAwaitingTimeout;

    private static final ConnectionPool INSTANCE = new ConnectionPool();

    private ConnectionPool() {
        isInitialized = new AtomicBoolean(false);
        isPoolClosing = new AtomicBoolean(false);
        initLock = new ReentrantLock();
        connectionPoolProperties = new Properties();
    }

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }

    public void init() throws ConnectionPoolException {
        initLock.lock();
        if (!isInitialized.get()) {
            try {
                Driver driverMySql = new Driver();
                DriverManager.registerDriver(driverMySql);
                propertiesInit();
                createConnections();
                isInitialized.set(true);
            } catch (SQLException e) {
                throw new ConnectionPoolException("Connection pool is not initialized ", e);
            } finally {
                initLock.unlock();
            }
        }
    }

    public Connection getConnection() throws ConnectionPoolException {
        if (!isPoolClosing.get()) {
            PooledConnection connection = null;
            if (!freeConnections.isEmpty()) {
                try {
                    connection = freeConnections.poll(defaultConnectionAwaitingTimeout, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    LOGGER.error("Connection is not gotten", e);
                    Thread.currentThread().interrupt();
                }
                usedConnections.add(connection);
            }
            return connection;
        }
        throw new ConnectionPoolException("Connections are closing now");
    }

    void releaseConnection(PooledConnection connection) {
        if (connection != null) {
            usedConnections.remove(connection);
            freeConnections.add(connection);
        } else {
            LOGGER.warn("Connection is not released");
        }
    }

    public void destroy() {
        isPoolClosing.set(true);
        initLock.lock();
        usedConnections.addAll(freeConnections);
        freeConnections.clear();
        for (PooledConnection connection : usedConnections) {
            try {
                connection.getConnection().close();
            } catch (SQLException e) {
                LOGGER.warn("Connection is not closed", e);
            }
        }
        usedConnections.clear();
        isInitialized.set(false);
        isPoolClosing.set(false);
        initLock.unlock();
    }

    private void propertiesInit() throws ConnectionPoolException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONNECTION_POOL_PROPERTIES)) {
            connectionPoolProperties.load(inputStream);
            if (connectionPoolProperties.isEmpty()) {
                throw new ConnectionPoolException("DB properties has not been loaded");
            }
            this.url = connectionPoolProperties.getProperty(URL_NAME);
            this.poolSize = Integer.parseInt(connectionPoolProperties.getProperty(DEFAULT_POOL_SIZE_NAME));
            this.defaultConnectionAwaitingTimeout = Integer.parseInt(connectionPoolProperties.getProperty(DEFAULT_CONNECTION_AWAITING_TIMEOUT_NAME));
        } catch (IOException e) {
            throw new ConnectionPoolException("DB properties has not been loaded ", e);
        }
    }

    private void createConnections() {
        for (int counter = 0; counter < poolSize; ++counter) {
            try {
                PooledConnection pooledConnection = new PooledConnection(DriverManager.getConnection(url, connectionPoolProperties));
                pooledConnection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                freeConnections.add(pooledConnection);
            } catch (SQLException e) {
                LOGGER.warn("Connection isn't created", e);
            }
        }
    }
}
