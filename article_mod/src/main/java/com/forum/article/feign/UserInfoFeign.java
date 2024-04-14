package com.forum.article.feign;

import com.forum.article.vo.UserNameVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author lituizi
 */
@FeignClient(name = "UserInfoFeign", url = "http://localhost:8080/feign/server")
public interface UserInfoFeign {
    @RequestMapping("/getUserNames")
    List<UserNameVo> getUserNames(@RequestBody List<Long> ids);
}
