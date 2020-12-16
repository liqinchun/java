package com.example.java.models;

import com.example.java.yangtools.AbstractYangModelRegistrator;
import com.example.java.yangtools.YangClassLoaderRegistry;
import org.onosproject.yang.model.YangModel;
import org.onosproject.yang.runtime.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.common.collect.ImmutableMap;
import org.onosproject.yang.model.DefaultYangModuleId;
import org.onosproject.yang.model.YangModuleId;
import org.onosproject.yang.runtime.ModelRegistrationParam.Builder;


import java.util.HashMap;
import java.util.Map;

import static org.onosproject.yang.runtime.helperutils.YangApacheUtils.getYangModel;

//@Component
public class InahndModelRegistry {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    private final Class<?> loaderClass;

    protected Map<YangModuleId, AppModuleInfo> appInfo;
    protected YangModel model;
    private ModelRegistrationParam registrationParam;

    @Autowired
    protected YangModelRegistry modelRegistry;

    @Autowired
    protected YangClassLoaderRegistry sourceResolver;

    public InahndModelRegistry() {
        this.loaderClass = InahndModelRegistry.class;
        this.appInfo = getAppInfo();
        log.info("Starting...");
        model = getYangModel(loaderClass);
        log.info("ModelId: {}", model.getYangModelId());
        ModelRegistrationParam.Builder b =
                DefaultModelRegistrationParam.builder().setYangModel(model);
        registrationParam = getAppInfo(b).setYangModel(model).build();
        sourceResolver.registerClassLoader(model.getYangModelId(), loaderClass.getClassLoader());
        modelRegistry.registerModel(registrationParam);
        log.info("Started");

    }

    private static Map<YangModuleId, AppModuleInfo> getAppInfo() {
        Map<YangModuleId, AppModuleInfo> appInfo = new HashMap<>();

        return ImmutableMap.copyOf(appInfo);
    }

    protected ModelRegistrationParam.Builder getAppInfo(Builder b) {
        if (appInfo != null) {
            appInfo.entrySet().stream().filter(
                    entry -> model.getYangModule(entry.getKey()) != null).forEach(
                    entry -> b.addAppModuleInfo(entry.getKey(), entry.getValue()));
        }
        return b;
    }
}
