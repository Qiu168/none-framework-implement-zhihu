package com.huangTaiQi.www.controller.impl;

import com.huangTaiQi.www.controller.BaseController;
import com.huangTaiQi.www.controller.ILikeController;
import com.huangTaiQi.www.service.impl.LikeServiceImpl;
import com.my_framework.www.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static com.huangTaiQi.www.constant.RegexConstants.NUMBER_REGEX;

/**
 * @author 14629
 */
@Controller
@RequestMapping("like")
public class LikeControllerImpl extends BaseController implements ILikeController {
    @Autowired
    LikeServiceImpl likeService;
    @Override
    @RequestMapping
    public void isLike(@Pattern(regex = NUMBER_REGEX) @RequestParam("answerId") String answerId,
                       HttpServletResponse response) throws Exception {
        String like = likeService.isLike(answerId);
        response.getWriter().write(like);
    }
    @Override
    @RequestMapping
    public void likeAnswer(@Pattern(regex = NUMBER_REGEX) @RequestParam("answerId") String answerId,
                           HttpServletResponse response) throws Exception {
        String json = likeService.likeAnswer(answerId);
        response.getWriter().write(json);
    }

}
