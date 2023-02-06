package com.atguigu;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

public class QiniuTest {


    // 测试向七牛云上传图片
    @Test
    public void testUpload() {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "aY-JDfoTSitsi_cOy8P5F2i6n8RFpLUi5VY0wLnS";
        String secretKey = "MeWaD8Lk4-krAbK-0_kWxUzOgwFCPI9WbZfFh840";
        String bucket = "shf230206";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png，可支持中文
//        String localFilePath = "C:/Users/ChenXW/Pictures/Camera Roll/0b5f3510de09418d98baaedffe1a7f3b.jpg";
        String localFilePath = "C:/Users/ChenXW/Pictures/Camera Roll/21e8398470f1486583e548fe8db26d24.jpg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
//        String key = null;
        String key = "test1";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException e) {
                e.printStackTrace();
            }
        }
    }


    // 删除空间中的文件
    @Test
    public void testDelete(){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        String accessKey = "aY-JDfoTSitsi_cOy8P5F2i6n8RFpLUi5VY0wLnS";
        String secretKey = "MeWaD8Lk4-krAbK-0_kWxUzOgwFCPI9WbZfFh840";
        String bucket = "shf230206";
        String key = "test1";//文件名称
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

}
