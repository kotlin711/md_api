package org.example.api.web.controller.manager;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;
import org.example.api.model.entity.MdFile;
import org.example.api.model.result.PageResult;
import org.example.api.model.result.Result;
import org.example.api.web.service.impl.FileServiceImpl;
import org.example.api.web.util.FileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author crying711
 * @since 2022-06-17
 */
@RestController
@RequestMapping("/file")
@Slf4j

public class FileController {





    @Autowired
    FileServiceImpl fileService;


    @Value("${web.uploadpath}")
    private String upload_path;



    /**
     * 获取文件
     * @param page 页面
     * @param limit 数量
     * @param type  类型
     * @return  文件列表
     */
    @GetMapping("/page")
    public PageResult page(Long page, Long limit,@RequestParam(defaultValue = "0",name = "type") Integer type) {
        LambdaQueryWrapper<MdFile> lambdaQueryWrapper =new LambdaQueryWrapper<MdFile>();
        if (type!=0){
            lambdaQueryWrapper.eq(MdFile::getType,type);
        }
        return PageResult.build(fileService.page(  new Page<MdFile>(page,limit),lambdaQueryWrapper).getRecords(), fileService.count());
    }
    @PostMapping("/del/{id}")
    public Result del(@PathVariable String id){
        if (fileService.removeById(id)) {
            return Result.ok();
        }
        return Result.fail();
    }

    @PostMapping("/dels")
    public Result dels(String id){
        if (fileService.removeByIds(Arrays.stream(id.split(",")).collect(Collectors.toList()))) {
            return Result.ok();
        }
        return Result.fail();
    }

//    https://blog.csdn.net/IT_Most/article/details/109628047

    /**
     * 上传文件
     * @param file
     * @param  save_mode
     * @return
     * @throws IOException
     */
    @PostMapping("/upload/{save_mode}")
    public Result upload(@RequestParam("file") MultipartFile file,  @PathVariable int save_mode) throws IOException {


        String fileName = file.getOriginalFilename();
        log.info("上传的文件名为：{}",  fileName);
         String suffixName = fileName.substring(fileName.lastIndexOf("."));
        int file_type = FileType.getInstance().get_file_type(suffixName);
        long fileSize = file.getSize();
        MdFile mdFile = new MdFile();
        mdFile.setSaveModel(save_mode);
        mdFile.setName(fileName);
        mdFile.setSize(fileSize);
        mdFile.setType(file_type);
        String file_path = UUID.randomUUID().toString().replace("-","") +suffixName;
        if (save_mode==1){
            File dir = new File(new File(upload_path).getAbsolutePath() +"/"+file_path.replace(" ", ""));
            String link ="/data/"+file_path.replace(" ", "");
            mdFile.setLink(link);
            file.transferTo(dir);
            HashMap<String, Object> stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("link",link);
            stringStringHashMap.put("file_type",file_type);
            stringStringHashMap.put("fileSize",fileSize);
            fileService.save(mdFile);
            return  Result.ok(stringStringHashMap);
        }else if (save_mode==2){

            fileService.save(mdFile);return Result.ok();

        }
        return Result.fail();
    }


}
