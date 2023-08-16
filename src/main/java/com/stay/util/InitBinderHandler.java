package com.stay.util;

import com.stay.domain.dto.FullNameDTO;
import com.stay.domain.dto.util.FullNamePropertyEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class InitBinderHandler {

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(FullNameDTO.class, new FullNamePropertyEditor());
    }
}