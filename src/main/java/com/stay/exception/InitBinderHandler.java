package com.stay.exception;

import com.stay.propertyEditor.passenger.FullNameModel;
import com.stay.propertyEditor.passenger.FullNamePropertyEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class InitBinderHandler {

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(FullNameModel.class, new FullNamePropertyEditor());
    }
}