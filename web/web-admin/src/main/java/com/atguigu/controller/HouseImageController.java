package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.HouseImageService;
import com.atguigu.entity.HouseImage;
import com.atguigu.result.Result;
import com.atguigu.util.QiniuUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@Controller
@RequestMapping("/houseImage")
public class HouseImageController {

    @Reference
    private HouseImageService houseImageService;

    // 去上传图片的页面
    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String goUploadPage(@PathVariable("houseId") Long houseId, @PathVariable("type") Integer type, Map map) {

        // 将房源id和图片的类型放到request域里
        map.put("houseId", houseId);
        map.put("type", type);


        return "house/upload";
    }


    /**
     * @description: 删除房源或房产图片
     * @author: ChenXW
     * @date: 2023/2/6 11:37
     */
    @ResponseBody
    @RequestMapping("/upload/{houseId}/{type}")
    public Result upload(@PathVariable("houseId") Long houseId, @PathVariable("type") Integer type,
                         @RequestParam("file") MultipartFile[] files) {

        try {
            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    // 获取字节数组
                    byte[] bytes = file.getBytes();
                    // 获取图片的名字
                    String originalFilename = file.getOriginalFilename();
                    // 通过UUID随机生成一个字符串作为上传到七牛云的图片的名字
                    String newFileName = UUID.randomUUID().toString();

                    // 通过QiniuUtil 工具类上传图片到七牛云
                    QiniuUtil.upload2Qiniu(bytes, newFileName);

                    HouseImage houseImage = new HouseImage();
                    houseImage.setHouseId(houseId);
                    houseImage.setType(type);
                    houseImage.setImageName(originalFilename);
                    // 设置图片的路径
                    houseImage.setImageUrl("http://rpmyvhai5.hn-bkt.clouddn.com/" + newFileName);
                    // 调用HouseImageService中保存的方法
                    houseImageService.insert(houseImage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();


        }
        return Result.ok();

    }


    /**
     * @description: 删除房源或房产图片
     * @author: ChenXW
     * @date: 2023/2/6 12:03
     */
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId, @PathVariable("id") Long id) {
        // 调用删除方法
        houseImageService.delete(id);

        return "redirect:/house/" + houseId;
    }

}
