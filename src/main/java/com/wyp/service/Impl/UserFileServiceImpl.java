package com.wyp.service.Impl;

import com.wyp.dao.UserFileDAO;
import com.wyp.entity.UserFile;
import com.wyp.service.UserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author WYP
 * @date 2021-07-11 10:48
 */
@Service
public class UserFileServiceImpl implements UserFileService {

    @Resource
    private UserFileDAO userFileDAO;

    @Override
    public List<UserFile> findByUserId(Integer id) {
        return userFileDAO.findByUserId(id);
    }

    @Override
    public void save(UserFile userFile) {
        //userFile.setIsImg(); //是否是图片 解决方案：当文件类型中 含有image时 说明当前文件类型一定是图片
        String isImg = userFile.getType().startsWith("image") ? "是" : "否";
        userFile.setIsImg(isImg);
        userFile.setDowncounts(0);
        userFile.setUploadTime(new Date());
        userFileDAO.save(userFile);
    }

    @Override
    public UserFile findById(String id) {
        return userFileDAO.findById(id);
    }

    @Override
    public void update(UserFile userFile) {
        userFileDAO.update(userFile);
    }

    @Override
    public void delete(String id) {
        userFileDAO.delete(id);
    }
}
