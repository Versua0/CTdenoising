package com.common.ctdenoising.controller;


import com.baomidou.mybatisplus.service.IService;
import com.common.ctdenoising.entity.Files;
import com.common.ctdenoising.response.ResponseCode;
import com.common.ctdenoising.response.Result;
import com.common.ctdenoising.service.FileService;
import com.common.ctdenoising.service.FileStateService;
import com.common.ctdenoising.service.Impl.RestTemplateMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class FileController {


    @Autowired
    private FileService fileService;
    @Autowired
    private FileStateService fileStateService;


    Queue<String> queue =new LinkedList(); //test_patient

    @RequestMapping(value="/multi/uploadMultiImage",method=RequestMethod.POST)
    public Result uploadMultiImage(@RequestParam("files") MultipartFile[] files,HttpServletRequest request){
        //files 就是前端传来的多文件数组
        if (files.length<=0) {
            return new Result(ResponseCode.FILE_EMPTY.getCode(), ResponseCode.FILE_EMPTY.getMsg(), null);
        }
        for(MultipartFile file :files){
            fileService.upLoadFiles(file,request);
        }
        return new Result(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), "数据上传成功");
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Result upLoadFile(@RequestParam("file") MultipartFile multipartFile,HttpServletRequest request) {
        if (multipartFile.isEmpty()) {
            return new Result(ResponseCode.FILE_EMPTY.getCode(), ResponseCode.FILE_EMPTY.getMsg(), null);
        }
        return fileService.upLoadFiles(multipartFile,request);

    }
    @RequestMapping(value="/uploadCompleted",method=RequestMethod.GET)
    public Result upLoadConnectedState(HttpServletRequest request){
        //return fileStateService.UpdateUploadStateByIp(request);
        return fileService.processFiles(request);
    }
    @RequestMapping(value = "/download/{id}",method = RequestMethod.GET)
    public void downloadFiles(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response){
        OutputStream outputStream=null;
        InputStream inputStream=null;
        BufferedInputStream bufferedInputStream=null;
        byte[] bytes=new byte[1024];
        Files files = fileService.getFileById(id);
        String fileName = files.getFileName();
        // 获取输出流
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" +  new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            response.setContentType("application/force-download");
            inputStream=fileService.getFileInputStream(files);
            bufferedInputStream=new BufferedInputStream(inputStream);
            outputStream = response.getOutputStream();
            int i=bufferedInputStream.read(bytes);
            while (i!=-1){
                outputStream.write(bytes,0,i);
                i=bufferedInputStream.read(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (inputStream!=null){
                    inputStream.close();
                }
                if (outputStream!=null){
                    outputStream.close();
                }
                if (bufferedInputStream!=null){
                    bufferedInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @RequestMapping(value="/getResult",method=RequestMethod.GET)
    public Result getUrlsByIp(HttpServletRequest request,HttpServletResponse response){
        return fileService.returnUrls(request,response);
    }

}
