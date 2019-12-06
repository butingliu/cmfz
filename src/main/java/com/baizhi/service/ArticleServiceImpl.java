package com.baizhi.service;

import com.baizhi.annotation.RedisAnnotation;
import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Override
    public List<Article> queryArticleByFid(String fid) {
        return articleDao.select(new Article().setSh_id(fid));
    }

    @Override
    public void insertArticle(Article article) {
        articleDao.insertSelective(article);
    }

    @Override
    public void removeArticle(String id) {
        articleDao.deleteByPrimaryKey(new Article().setId(id));
    }

    @Override
    public void updateArticle(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
    }

    @Override
    public Article queryOneArticle(String id) {

        return articleDao.selectOne(new Article().setId(id));
    }

    @Override
    public List<Article> queryArticleByPage(Integer page, Integer rows) {
        return articleDao.selectByRowBounds(new Article(), new RowBounds((page - 1) * rows, rows));
    }

    @Override
    @RedisAnnotation()
    public List<Article> queryAllArticle() {
        return articleDao.selectAll();
    }
}
