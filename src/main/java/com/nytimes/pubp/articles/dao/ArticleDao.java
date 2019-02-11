package com.nytimes.pubp.articles.dao;

import com.nytimes.pubp.articles.dao.exception.DaoException;
import com.nytimes.pubp.articles.domain.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class ArticleDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleDao.class);

    public void save(Article article) throws DaoException {
        //TODO - stub for demonstration only

        Connection conn = DbUtil.open();

        LOGGER.info("Save to database {}", article);

        DbUtil.close(conn);
    }
}
