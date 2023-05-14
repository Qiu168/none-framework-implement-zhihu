package com.huangTaiQi.www.helper;

import com.huangTaiQi.www.dao.SelectById;
import com.huangTaiQi.www.dao.impl.BlackListDao;
import com.huangTaiQi.www.model.UserSettings;
import com.huangTaiQi.www.model.dto.UserDTO;
import com.huangTaiQi.www.model.entity.BlackListEntity;
import com.huangTaiQi.www.model.vo.IsSuccessVO;
import com.huangTaiQi.www.utils.UserHolder;

/**
 * @author 14629
 */
public class CheckBlackListHelper {
    public static IsSuccessVO checkBlackList(BlackListDao blackListDao, SelectById userSettingsDao, String id) throws Exception {
        UserDTO user = UserHolder.getUser();
        UserSettings entity = userSettingsDao.selectById(id);
        BlackListEntity blackList = blackListDao.selectBlackList(entity.getUserId(), String.valueOf(user.getId()));
        if(blackList!=null){
            return new IsSuccessVO(false,"你已被拉黑");
        }
        return null;
    }
}
