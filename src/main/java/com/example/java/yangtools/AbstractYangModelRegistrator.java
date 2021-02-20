package com.example.java.yangtools;



import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.onosproject.yang.compiler.tool.YangCompilerManager;
import org.onosproject.yang.compiler.utils.io.YangPluginConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.onosproject.yang.model.YangModel;
import org.onosproject.yang.model.YangModuleId;
import org.onosproject.yang.runtime.AppModuleInfo;
import org.onosproject.yang.runtime.DefaultModelRegistrationParam;
import org.onosproject.yang.runtime.ModelRegistrationParam;
import org.onosproject.yang.runtime.ModelRegistrationParam.Builder;
import org.onosproject.yang.runtime.YangModelRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.onosproject.yang.compiler.utils.io.impl.YangFileScanner.getYangFiles;
import static org.onosproject.yang.compiler.utils.io.impl.YangIoUtils.deleteDirectory;
import static org.onosproject.yang.runtime.helperutils.YangApacheUtils.getYangModel;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class AbstractYangModelRegistrator {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    private final Class<?> loaderClass;

    protected Map<YangModuleId, AppModuleInfo> appInfo;
    protected YangModel model;
    private ModelRegistrationParam registrationParam;

    private static final String DIR = "target/augmentTranslator/";
    private static final String COMP = System.getProperty("user.dir") + File
            .separator + DIR;
    public static void main(String[] args) throws IOException {
        YangCompilerManager utilManager = new YangCompilerManager();
        String dir = "target/inhand/";
        deleteDirectory(dir);
        String searchDir = "src/test/resources/inhand";

        Set<Path> paths = new HashSet<>();
        for (String file : getYangFiles(searchDir)) {
            paths.add(Paths.get(file));
        }
        utilManager.createYangFileInfoSet(paths);
        utilManager.parseYangFileInfoSet();
        utilManager.createYangNodeSet();
        utilManager.resolveDependenciesUsingLinker();

        YangPluginConfig yangPluginConfig = new YangPluginConfig();
        yangPluginConfig.setCodeGenDir(dir);

        utilManager.translateToJava(yangPluginConfig);
        String dir1 = System.getProperty("user.dir") + File.separator + dir;
        //compileCode(dir1);
        System.out.println("===============finish============dir1："+dir1);
        //deleteDirectory("target/inhand/");
    }
    /**
     * Binds the specified YANG model registry.
     *
     * @param registry model registry
//     */
//    protected void bindModelRegistry(YangModelRegistry registry) {
//        this.modelRegistry = registry;
//    }
//
//    /**
//     * Unbinds the specified YANG model registry.
//     *
//     * @param registry model registry
//     */
//    protected void unbindModelRegistry(YangModelRegistry registry) {
//        this.modelRegistry = null;
//    }
//
//    /**
//     * Binds the specified YANG source resolver registry.
//     *
//     * @param resolver model source resolver
//     */
//    protected void bindSourceResolver(YangClassLoaderRegistry resolver) {
//        this.sourceResolver = resolver;
//    }
//
//    /**
//     * Unbinds the specified YANG source resolver registry.
//     *
//     * @param resolver model source resolver
//     */
//    protected void unbindSourceResolver(YangClassLoaderRegistry resolver) {
//        this.sourceResolver = null;
//    }

    /**
     * Creates a model registrator primed with the class-loader of the specified
     * class.
     *
     * @param loaderClass class whose class loader is to be used for locating
     *                    schema data
     */
    protected AbstractYangModelRegistrator(Class<?> loaderClass) {
        this.loaderClass = loaderClass;
    }

    /**
     * Creates a model registrator primed with the class-loader of the specified
     * class and application info.
     *
     * @param loaderClass class whose class loader is to be used for locating
     *                    schema data
     * @param appInfo     application information
     */
    protected AbstractYangModelRegistrator(Class<?> loaderClass,
                                           Map<YangModuleId, AppModuleInfo> appInfo) {
        this.loaderClass = loaderClass;
        this.appInfo = appInfo;
    }


}