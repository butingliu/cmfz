package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.List;

public interface ArticleService {
    //根据父id查询指定数据
    List<Article> queryArticleByFid(String fid);
    //添加文章
    void insertArticle(Article article);
    //删除文章
    void removeArticle(String id);
    //更新文章
    void updateArticle(Article article);
    //查找指定文章--6.文章详情接口
    Article queryOneArticle(String id);
    //分页查询文章
    List<Article> queryArticleByPage(Integer page,Integer rows);
    //查询全部文章
    List<Article> queryAllArticle();


    //void queryOneArticle(String );
}
