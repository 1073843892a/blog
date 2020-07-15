
package  com.lvlei.blog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

//所有controller由这里拦截
@ControllerAdvice
public class ControllerExceptionHandler{
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    //所有的Exception和Exception的子类
    @ExceptionHandler(Exception.class)
    public ModelAndView Handler(HttpServletRequest request,Exception e)throws Exception{
        logger.error("Request URL:{},Exception{}",request.getRequestURL(),e);
        ModelAndView modelAndView=new ModelAndView();
        //找到有状态码的异常，交给springboot处理而不是用ControllerAdvice来处理
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }
        modelAndView.addObject("url",request.getRequestURL());
        modelAndView.addObject("exception",e);
        modelAndView.setViewName("error/error");
        return modelAndView;
    }
}