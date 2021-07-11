package com.wyp.controller;

import com.wyp.entity.User;
import com.wyp.entity.UserFile;
import com.wyp.service.UserFileService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author WYP
 * @date 2021-07-11 10:06
 */
@Controller
@RequestMapping("/file")
public class fileController {

    @Resource
    private UserFileService userFileService;

    @GetMapping("/showAll")
    public String findAll(HttpSession session, Model model){
        //从session里面获取登录用户id
        User user = (User) session.getAttribute("user");
        List<UserFile> userFiles = userFileService.findByUserId(user.getId());
        model.addAttribute("files",userFiles);
        return "showAll";
    }

    //文件上传 并保存文件到数据库中
    @PostMapping("/upload")
    public String upload(MultipartFile aaa,HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
        //获取文件原始名称
        String oldFileName = aaa.getOriginalFilename();

        //获取原始文件后缀  FileNameUtils工具类 需要导包
        String extension = "." + FilenameUtils.getExtension(oldFileName);

        //生成新文件名
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + UUID.randomUUID().toString().replace("-","")+extension;

        //文件大小
        Long size = aaa.getSize();

        //文件类型
        String type = aaa.getContentType();

        //处理根据日期生成目录
        String filePath = System.getProperty("user.dir") + "/uploadFile/";
        System.out.println(filePath);
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dateDirPath = filePath + "/" + dateFormat;
        File dateDir = new File(dateDirPath);
        if(!dateDir.exists()){
            dateDir.mkdirs();
        }

        //文件上传
        aaa.transferTo(new File(dateDir,newFileName));

        //将文件信息放入数据库
        UserFile userFile = new UserFile();
        userFile.setOldFileName(oldFileName).setNewFileName(newFileName).setExt(extension).setSize(String.valueOf(size))
                .setType(type).setPath("/uploadFile/"+dateFormat).setUserId(user.getId());
        userFileService.save(userFile);
        return "redirect:/file/showAll";
    }

    //文件下载
    @GetMapping("/download")
    public void download(String openStyle,String id,HttpServletResponse response) throws IOException {
        //获取打开方式
        openStyle = openStyle == null ? "attachment":openStyle;
        //先获取文件信息
        UserFile userFile = userFileService.findById(id);
        //点击下载链接更新下载次数
        if(openStyle.equals("attachment")){
            userFile.setDowncounts(userFile.getDowncounts()+1);
            userFileService.update(userFile);
        }
        //根据文件信息中文件名字和文件路径获取文件输入流
        //String realPath = ResourceUtils.getURL("classpath:").getPath() + "/static" + userFile.getPath();
        String filePath = System.getProperty("user.dir") + "/"+userFile.getPath();
        //获取文件输入流
        FileInputStream is = new FileInputStream(new File(filePath, userFile.getNewFileName()));
        //附件下载   URLEncoder 防止中文乱码
        response.setHeader("content-disposition",openStyle+";fileName="+ URLEncoder.encode(userFile.getOldFileName(),"UTF-8"));
        //获取响应输出流
        ServletOutputStream os = response.getOutputStream();
        //文件拷贝
        IOUtils.copy(is,os);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
    }

    //删除文件
    @GetMapping("/delete")
    public String delete(String id) throws FileNotFoundException {
        //根据id查询信息
        UserFile userFile = userFileService.findById(id);
        //删除文件
        //String realPath = ResourceUtils.getURL("classpath:").getPath() + "/static" + userFile.getPath();
        String filePath = System.getProperty("user.dir") + "/"+userFile.getPath();
        File file = new File(filePath, userFile.getNewFileName());
        if(file.exists()){
            file.delete();
        }
        //删除数据库中的信息
        userFileService.delete(id);
        return "redirect:/file/showAll";
    }

    //返回当前用户所有文件列表----JSON
    @GetMapping("/findAllJSON")
    @ResponseBody
    public List<UserFile> findAllJSON(HttpSession session){
        //从session里面获取登录用户id
        User user = (User) session.getAttribute("user");
        List<UserFile> userFiles = userFileService.findByUserId(user.getId());
        return userFiles;
    }
}
