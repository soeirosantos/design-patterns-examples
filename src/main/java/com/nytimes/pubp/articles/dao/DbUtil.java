package com.nytimes.pubp.articles.dao;

import com.nytimes.pubp.articles.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DbUtil {

    static Connection open() throws DaoException {
        try {
            return DriverManager.getConnection("jdbc:h2:mem:test");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    static void close(Connection connection) throws DaoException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
