/*
    https://help.aliyun.com/document_detail/62184.html 是openapi的sdk文档，不包含innerapi
    http://ramdoc.alibaba.net/doc/cloud-product-join-ram-guide/support-ram/tech/sts-token.html
 */

import com.alibaba.aliyunid.client.AliyunidClient;
import com.alibaba.aliyunid.client.exception.OAuthException;
import com.alibaba.aliyunid.client.model.OAuthPair;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.ims.model.v20170430.GetUserResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.ims.model.v20170430.GetUserRequest;
import com.aliyuncs.profile.IClientProfile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class RamOpenApi {
    public static void main2(String fileName) {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(fileName));
            final String regionId = (String) p.get("regionId");
            final String accessKeyId = (String) p.get("accessKeyId");
            final String secret = (String) p.get("secret");
            final String userId = (String) p.get("userId");
            Map<String, String> imsMap = new HashMap<>();
            imsMap.put("Ims", "ims.aliyuncs.com");
            IClientProfile profile = DefaultProfile.getProfile(regionId, imsMap,
                    accessKeyId,
                    secret);
            DefaultAcsClient client = new DefaultAcsClient(profile);

            final GetUserRequest request = new GetUserRequest();
            request.setUserId(userId);
            final GetUserResponse response = client.getAcsResponse(request);
            System.out.println(response.getUser().getTenantId());
        } catch (IOException e) {
            System.out.println("ini文件不存在:" + fileName);
            System.exit(1);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
            System.out.println("===============");
            System.out.println("ErrorCode:" + e.getErrCode());
            System.out.println("ErrorMessage:" + e.getErrMsg());
        }
    }
    public static void main(String [] args){
        RamOpenApi.main2(args[1]);
    }
}