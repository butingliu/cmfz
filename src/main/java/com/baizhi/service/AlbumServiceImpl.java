package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;

    @Override
    public List<Album> queryAllAlbum() {
        return albumDao.selectAll();
    }

    @Override
    public void insertAlbum(Album album) {
        albumDao.insert(album);
    }

    @Override
    public void removeAlbum(String id) {
        albumDao.deleteByPrimaryKey(new Album().setId(id));
    }

    @Override
    public void updateAlbum(Album album) {
        albumDao.updateByPrimaryKeySelective(album);
    }

    @Override
    public List<Album> queryAlbumByPage(Integer page, Integer rows) {
        return albumDao.selectByRowBounds(new Album(), new RowBounds((page - 1) * rows, rows));
    }

    @Override
    public Album queryOneAlbum(String id) {
        return albumDao.selectOne(new Album().setId(id));
    }

    @Override
    public void updateAlbum2(Album album) {
        albumDao.updateByPrimaryKey(album);
    }
}
