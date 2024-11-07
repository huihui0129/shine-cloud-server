//package com.hh.handler;
//
//import com.hh.enums.IEnum;
//import com.hh.exception.ResultCode;
//import com.hh.utils.Result;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import javax.naming.AuthenticationException;
//import java.util.List;
//
///**
// * @author 辉辉 2022/8/18 19:37
// */
//@RestControllerAdvice
//@Slf4j
//public class GlobalExceptionHandler {
//
//    /**
//     * 500 服务器内部异常
//     * 处理全局运行时异常
//     *
//     * @param e
//     * @return
//     */
//    @ExceptionHandler(value = RuntimeException.class)
//    public Result<IEnum> runtimeExceptionHandler(RuntimeException e) {
//        // 处理异常 日志
//        String message = e.getMessage();
//        log.error(message);
//        // http
//        // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("服务器维护中");
//        return Result.error(ResultCode.SYSTEM_ERROR);
//    }
//
//    /**
//     * 登陆异常
//     *
//     * @param e
//     * @return
//     */
//    @ExceptionHandler(value = AuthenticationException.class)
//    public ResponseEntity<String> authenticationExceptionHandler(AuthenticationException e) {
//        // 处理异常 日志
//        String message = e.getMessage();
//        log.error(message);
//        // http
//        return ResponseEntity.badRequest().body("账号密码异常");
//    }
//
//    /**
//     * 非法的参数异常
//     *
//     * @param e
//     * @return
//     */
//    @ExceptionHandler(value = IllegalArgumentException.class)
//    public ResponseEntity<String> illegalArgumentExceptionHandler(IllegalArgumentException e) {
//        // 处理异常 日志
//        String message = e.getMessage();
//        log.error(message);
//        // http
//        return ResponseEntity.badRequest().body(message);
//    }
//
//    /**
//     * 方法参数校验异常
//     *
//     * @param bindException
//     * @return
//     */
//    @ExceptionHandler({MethodArgumentNotValidException.class})
//    public ResponseEntity<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException bindException) {
//        BindingResult bindingResult = bindException.getBindingResult();
//        System.out.println(bindingResult);
//        StringBuilder sb = new StringBuilder("数据校验失败，原因是:");
//        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//        for (FieldError fieldError : fieldErrors) {
//            sb.append("字段:").append(fieldError.getField()).append(",").append(fieldError.getDefaultMessage()).append("!");
//        }
//        return ResponseEntity.badRequest().body(sb.toString());
//    }
//
////    private void sendMail(Exception e) {
////        StringWriter sw = new StringWriter();
////        PrintWriter pw = new PrintWriter(sw);
////        e.printStackTrace(pw);
////        // 异常跟踪栈
////        String message = sw.toString();
////        String name = e.getClass().getName();
////        ExceptionMessage messageEntity = new ExceptionMessage();
////        messageEntity.setTo("2493386028@qq.com");
////        messageEntity.setName(name);
////        messageEntity.setMessage(message);
////        // 发消息给mq
////        rabbitTemplate.convertAndSend(QueueConstant.SystemConstant.AUTHOR_MESSAGE_EX, QueueConstant.SystemConstant.AUTHOR_MESSAGE_KEY, JSON.toJSONString(messageEntity));
////    }
//
//}
