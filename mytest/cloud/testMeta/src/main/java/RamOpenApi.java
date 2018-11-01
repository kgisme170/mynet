import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.ram.model.v20150501.*;
/*
    https://help.aliyun.com/document_detail/62184.html 是openapi的sdk文档，不包含innerapi
    http://ramdoc.alibaba.net/doc/cloud-product-join-ram-guide/support-ram/tech/sts-token.html
 */

public class RamOpenApi {

    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("运行参数有3个: [regionId], [accessKeyId], [secret]");
        }
        String regionId = args[1];
        String accessKeyId = args[2];
        String secret = args[3];
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        final CreateUserRequest request = new CreateUserRequest();
        request.setUserName("ramtest001");
        try {
            final CreateUserResponse response = client.getAcsResponse(request);

            System.out.println("UserName: " + response.getUser().getUserName());
            System.out.println("CreateTime: " + response.getUser().getCreateDate());
        } catch (ClientException e) {
            System.out.println("Failed.");
            System.out.println("Error code: " + e.getErrCode());
            System.out.println("Error message: " + e.getErrMsg());
        }

    }
}