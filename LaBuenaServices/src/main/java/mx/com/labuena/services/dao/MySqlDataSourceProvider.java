package mx.com.labuena.services.dao;

import com.google.inject.Provider;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by moracl6 on 8/3/2016.
 */

public class MySqlDataSourceProvider implements Provider<Connection> {
    private static final Logger log = Logger.getLogger(MySqlDataSourceProvider.class.getName());

    private static final String URL_FORMAT ="%s?user=%&password=%";
    @Override
    public Connection get() {

        try {
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream("WEB-INF/db.properties");

            props.load(fis);

            String server = props.getProperty("MYSQL_DB_URL");
            String user = props.getProperty("MYSQL_DB_USERNAME");
            String password = props.getProperty("MYSQL_DB_PASSWORD");

            String url = String.format(URL_FORMAT, server, user, password);

            Class.forName("com.mysql.jdbc.GoogleDriver");

            return DriverManager.getConnection(url);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
