package com.wyp.service;

import com.wyp.entity.UserFile;

import java.util.List;

/**
 * @author WYP
 * @date 2021-07-11 10:47
 */
public interface UserFileService {

    List<UserFile> findByUserId(Integer id);

    void save(UserFile userFile);

    UserFile findById(String id);

    void update(UserFile userFile);

    void delete(String id);

}
