package com.nytimes.pubp.articles.dao.exception;

import java.sql.SQLException;

public class DaoException extends Exception {

    public DaoException(SQLException e) {
        super(e);
    }
}
