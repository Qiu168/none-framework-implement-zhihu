import com.huangTaiQi.www.model.dto.UserDTO;
import com.huangTaiQi.www.model.entity.UserEntity;
import com.huangTaiQi.www.utils.BeanUtil;
import com.huangTaiQi.www.utils.code.Md5Utils;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Map;

public class BeanUtilTest {
    @Test
    public void testBeanToMap() throws Exception {
        UserEntity user=new UserEntity();

        user.setAvatar("123");
        user.setBlacklist(6L);
        user.setCreateTime(new Timestamp(System.currentTimeMillis()));
        user.setEmail("123");
        user.setFans(666L);
        user.setFollowee(777L);
        user.setGender(true);
        user.setIntroduce("dsaasdads");
        user.setId(1L);
        user.setRoleId(22L);
        user.setUsername("username");
        user.setPassword("password");
        UserDTO dto = BeanUtil.copyProperties(user, UserDTO.class);
//        user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        Map<String, String> stringStringMap = BeanUtil.beanToMap(dto);
        System.out.println(stringStringMap);
        UserDTO userDTO=new UserDTO();
        BeanUtil.fillBeanWithMap(userDTO,stringStringMap);
        System.out.println(userDTO);


    }
}
