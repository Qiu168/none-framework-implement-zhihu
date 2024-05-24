package com.huangTaiQi.www.helper;


import com.huangTaiQi.www.dao.UpdateUserSettings;
import com.huangTaiQi.www.dao.impl.AnswerDao;
import com.huangTaiQi.www.dao.impl.CommentDao;
import com.huangTaiQi.www.dao.impl.QuestionDao;
import com.huangTaiQi.www.model.UserSettings;
import com.huangTaiQi.www.model.dto.UserDTO;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.core.annotation.bean.Autowired;
import com.my_framework.www.core.annotation.stereotype.Component;
import com.my_framework.www.utils.CollectionUtil;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.huangTaiQi.www.constant.TypeConstants.*;

/**
 * @author _qqiu
 */
@Component
public class UpdateUserSettingsHelper {
    @Autowired
    AnswerDao answerDao;
    @Autowired
    QuestionDao questionDao;
    @Autowired
    CommentDao commentDao;
    /**
     * 检查冗余字段是否与当前相等
     * @param entityList 集合
     * @return 相等返回true
     */
    private boolean updateSettings(List<UserSettings> entityList){
        UserDTO user = UserHolder.getUser();
        if(user==null){
            return true;
        }
        for (UserSettings userSetting : entityList) {
            // 如果用户ID和当前用户ID不同，则跳过
            if (!Objects.equals(userSetting.getUserId(), user.getId())) {
                continue;
            }
            // 如果用户名或头像与当前用户不同，则返回false
            return Objects.equals(userSetting.getUsername(), user.getUsername()) && Objects.equals(userSetting.getAvatar(), user.getAvatar());
        }
        return true;
    }
    private static boolean updateSettings(UserSettings userSetting){
        UserDTO user = UserHolder.getUser();
        if(user==null){
            return true;
        }
        if(Objects.equals(userSetting.getUserId(), user.getId())){
            return Objects.equals(userSetting.getUsername(), user.getUsername()) && Objects.equals(userSetting.getAvatar(), user.getAvatar());
        }
        return true;
    }
    public void checkUserSettings(String type,List<?> list) throws SQLException {
        if(CollectionUtil.isEmpty(list)){
            return;
        }
        boolean success = updateSettings((List<UserSettings>) list);
        if(!success){
            updateSettings(type);
        }
    }
    public void checkUserSettings(String type,UserSettings entity) throws SQLException {
        if(entity==null){
            return;
        }
        boolean success = updateSettings(entity);
        if(!success){
            updateSettings(type);
        }
    }
    private void updateSettings(String type) throws SQLException {
        Map<String,UpdateUserSettings> daoMap=new HashMap<>(3);
        daoMap.put(ANSWER,answerDao);
        daoMap.put(QUESTION,questionDao);
        daoMap.put(COMMENT,commentDao);
        //有不一样
        UserDTO user = UserHolder.getUser();
        daoMap.get(type).updateSettings(user.getId(),user.getAvatar(),user.getUsername());
    }
//    private void checkUserSettings(List<?> answer) throws SQLException {
//        boolean success = UpdateUserSettingsHelper.updateSettings((List<UserSettings>) answer);
//        if(!success){
//            //有不一样
//            UserDTO user = UserHolder.getUser();
//            answerDao.updateSettings(user.getId(),user.getAvatar(),user.getUsername());
//        }
//    }
//    private void checkUserSettings(UserSettings answer) throws SQLException {
//        boolean success = UpdateUserSettingsHelper.updateSettings(answer);
//        if(!success){
//            //有不一样
//            UserDTO user = UserHolder.getUser();
//            answerDao.updateSettings(user.getId(),user.getAvatar(),user.getUsername());
//        }
//    }
}
