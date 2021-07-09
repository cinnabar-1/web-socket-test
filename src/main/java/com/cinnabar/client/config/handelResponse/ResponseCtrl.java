package com.cinnabar.client.config.handelResponse;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName ResponseCtrl.java
 * @Description
 * @createTime 2020-11-18  13:09:00
 */
public class ResponseCtrl {

    private static Template Template = new Template();

    public static class Template {
        private Integer code;

        private String message;

        private Object data;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }

    /*稍微异常处理*/
    public static Template in(Body body) {
        try {
            ResponseCtrl.Template.code = 200;
            ResponseCtrl.Template.message = "success";
            body.include(ResponseCtrl.Template);
        } catch (Exception e) {
            ResponseCtrl.Template.code = 202;
            ResponseCtrl.Template.message = e.getMessage();
        }
        System.out.println(Template.toString());
        return Template;
    }
}
