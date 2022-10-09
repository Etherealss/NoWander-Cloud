import com.fasterxml.jackson.core.type.TypeReference;
import com.nowander.common.core.pojo.Msg;
import com.nowander.common.core.utils.JsonUtil;
import com.nowander.discussion.infrastructure.feign.user.info.UserBriefDTO;

import java.util.List;

/**
 * @author wtk
 * @date 2022-10-09
 */
public class JsonDecodeTest {
    public static void main(String[] args) {
        String s = "{\"success\":true,\"code\":200,\"message\":\"OK\",\"data\":[{\"id\":1,\"username\":\"123123\",\"email\":\"111111@qq.com\",\"nickname\":\"李四1\",\"birthday\":1601913600000,\"sex\":false,\"avatar\":\"D:\\\\WanderFourAvatar\\\\default-girl.png\",\"userType\":\"大学生\",\"likedCount\":0,\"collectedCount\":0},{\"id\":2,\"username\":\"234234\",\"email\":\"123123@qq.com\",\"nickname\":\"小莫\",\"birthday\":null,\"sex\":false,\"avatar\":\"D:\\\\WanderFourAvatar\\\\default-girl.png\",\"userType\":\"高中生\",\"likedCount\":0,\"collectedCount\":0},{\"id\":4,\"username\":\"zhaoliu\",\"email\":\"123456@qq.com\",\"nickname\":\"张三\",\"birthday\":1601913600000,\"sex\":true,\"avatar\":\"D:\\\\WanderFourAvatar\\\\default-boy.png\",\"userType\":\"高中生\",\"likedCount\":0,\"collectedCount\":0}]}";
        Msg<List<UserBriefDTO>> msg = JsonUtil.toObject(s, new TypeReference<Msg<List<UserBriefDTO>>>() {
        });
        System.out.println(msg);
    }
}
