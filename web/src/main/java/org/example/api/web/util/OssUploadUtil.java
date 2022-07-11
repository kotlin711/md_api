package org.example.api.web.util;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public final class OssUploadUtil {


    private static OssUploadUtil ossUploadUtil;
    private final String accessKey;
    private final String secretKey;
    private final String bucketName;
    private final String domain;

    private OssUploadUtil(String accessKey, String secretKey, String bucketName, String domain) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucketName = bucketName;
        this.domain = domain;

    }

    public static OssUploadUtil getInstance(String accessKey, String secretKey, String bucketName, String domain) {
        if (ossUploadUtil == null) {
            ossUploadUtil = new OssUploadUtil(accessKey, secretKey, bucketName, domain);
        }
        return ossUploadUtil;
    }

    public static OssUploadUtil getInstance() {
        if (ossUploadUtil != null) return ossUploadUtil;
        return null;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(OssUploadUtil.getInstance("TBQrzXwJq8kJaubeqCmqVtJjGwxleMgkqAB2IIQ_", "QOCGfx_RN0BSPIOuljfSaOymrUnjcs5Z78nHtCGH"
                , "testjack111", "reh8cnc2r.sabkt.gdipper.com").upload(Files.readAllBytes(Paths.get("pom.xml")), "aaaa.xml"));
        System.out.println(OssUploadUtil.getInstance().get_path("aaaa.xml"));
    }

    public boolean upload(byte[] data, String filename) throws QiniuException {
        Configuration cfg = new Configuration();
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String token = auth.uploadToken(bucketName);
        Response r = uploadManager.put(data, filename, token);
        return r.statusCode == 200;
    }

    public String get_path(String filename) {
        return this.domain + "/" + filename;
    }


}
