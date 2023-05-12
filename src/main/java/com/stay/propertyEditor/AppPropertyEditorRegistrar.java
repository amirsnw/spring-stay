package com.stay.propertyEditor;

import com.stay.propertyEditor.cache.CacheConfigModel;
import com.stay.propertyEditor.cache.CachePropertyEditor;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.stereotype.Component;

@Component
public class AppPropertyEditorRegistrar implements PropertyEditorRegistrar {
    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(CacheConfigModel.class, new CachePropertyEditor());
    }
}
