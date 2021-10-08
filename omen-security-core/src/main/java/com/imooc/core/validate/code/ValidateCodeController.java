package com.imooc.core.validate.code;

import com.imooc.core.validate.code.support.ValidateProcessorHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author suxing.zhang
 * @date 2021/8/31 21:59
 **/
@RestController
@RequiredArgsConstructor
public class ValidateCodeController {

    private final ValidateProcessorHolder validateProcessorHolder;

    @GetMapping("/code/{type}")
    public void createValidateCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws IOException {
        validateProcessorHolder.findValidateProcessorByType(type).create(new ServletWebRequest(request, response));
    }
}
