package com.wyp.dao;

import com.wyp.entity.UserFile;

import java.util.List;

/**
 * @author WYP
 * @date 2021-07-11 10:40
 */
public interface UserFileDAO {

    //根据用户id查询所有
    List<UserFile> findByUserId(Integer id);

    //保存用户文记录
    void save(UserFile userFile);

    //根据文件id 获取文件信息
    UserFile findById(String id);

    //根据id更新下载次数
    void update(UserFile userFile);

    //根据id删除文件信息
    void delete(String id);
}
