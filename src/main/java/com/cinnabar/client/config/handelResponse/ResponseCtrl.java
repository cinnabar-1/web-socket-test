package com.cinnabar.client.config.handelResponse;

import com.cinnabar.client.config.handelException.CommonException;
import lombok.extern.log4j.Log4j2;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName ResponseCtrl.java
 * @Description
 * @createTime 2020-11-18  13:09:00
 */
@Log4j2
public class ResponseCtrl {

    /*稍微异常处理*/
    public static <T> ResponseTemplate<T> in(Body<T> body) {
        ResponseTemplate<T> respTemplate = new ResponseTemplate<>();
        try {
            respTemplate.setCode(200);
            respTemplate.setMessage("success");
            body.include(respTemplate);
        } catch (CommonException c) {
            log.debug("CommonException" + c);
            respTemplate.setCode(202);
            respTemplate.setMessage(c.getMessage());
        } catch (Exception e) {
            log.debug("Exception" + e);
            respTemplate.setCode(202);
            respTemplate.setMessage(e.getMessage());
        }
        return respTemplate;
    }
}
