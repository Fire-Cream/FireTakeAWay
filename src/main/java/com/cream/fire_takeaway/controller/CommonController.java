package com.cream.fire_takeaway.controller;

import com.cream.fire_takeaway.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * @Author: Cream
 * @Date: 2022-09-26-16:01
 * @Description:
 */
@RestController
@RequestMapping("/common")
@Transactional
public class CommonController {
    @Value("${fire_takeaway.path}")
    private String basePath;

    @Value("${fire_takeaway.email.emailHost}")
    private String emailHost;

    @PostMapping("/upload")
    public R upload(MultipartFile file) {
        //获得原始文件名
        String originaFileName = file.getOriginalFilename();
        //重新生成文件名
        String newName = UUID.randomUUID() + originaFileName;
        //判断目录是否存在
        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(basePath + newName));
            return R.success(newName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        try {
            //读取文件
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
            //输出问价
            ServletOutputStream servletOutputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                servletOutputStream.write(bytes, 0, len);
                servletOutputStream.flush();
            }
            //关闭资源
            servletOutputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
