package com.example.java;

import com.google.common.io.Files;
import io.atomix.cluster.messaging.impl.NettyUnicastService;
import org.junit.Test;
import org.onosproject.yang.compiler.api.YangCompilationParam;
import org.onosproject.yang.compiler.api.YangCompilerException;
import org.onosproject.yang.compiler.datamodel.YangNode;
import org.onosproject.yang.compiler.parser.exceptions.ParserException;
import org.onosproject.yang.compiler.parser.impl.YangUtilsParserManager;
import org.onosproject.yang.compiler.tool.DefaultYangCompilationParam;
import org.onosproject.yang.compiler.tool.YangCompilerManager;
import org.onosproject.yang.compiler.translator.tojava.JavaCodeGeneratorUtil;
import org.onosproject.yang.compiler.utils.io.YangPluginConfig;
import org.onosproject.yang.compiler.utils.io.impl.YangFileScanner;
import org.onosproject.yang.compiler.utils.io.impl.YangIoUtils;
import org.onosproject.yang.runtime.DefaultAnnotation;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import org.onlab.util.FilePathValidator;
import org.onosproject.yang.compiler.tool.DefaultYangCompilationParam;
import org.onosproject.yang.compiler.tool.YangCompilerManager;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.google.common.io.ByteStreams.toByteArray;
import static com.google.common.io.Files.createParentDirs;
import static com.google.common.io.Files.write;
import static java.nio.file.Files.walkFileTree;
import static com.google.common.io.ByteStreams.toByteArray;
import static org.onosproject.yang.compiler.utils.io.impl.YangIoUtils.deleteDirectory;

public class BaseTest {
    // TODO this should probably be defined on YRT Serializer side
    /**
     * {@link } parameter Dataformat specifying XML.
     */
    private static final String DATAFORMAT_XML = "xml";

    private static final String XMLNS_XC = "xmlns:xc";
    private static final String NETCONF_1_0_BASE_NAMESPACE =
            "urn:ietf:params:xml:ns:netconf:base:1.0";

    private static final String ZIP_MAGIC = "PK";
    private final YangUtilsParserManager manager = new YangUtilsParserManager();
    private static final String DIR = "target/unionTranslator/";
    private static final String DIR1 = System.getProperty("user.dir") + File
            .separator + DIR;

    YangCompilerManager utilManager = new YangCompilerManager();

    /**
     * Annotation to add xc namespace declaration.
     * {@value #XMLNS_XC}={@value #NETCONF_1_0_BASE_NAMESPACE}
     */
    private static final DefaultAnnotation XMLNS_XC_ANNOTATION =
            new DefaultAnnotation(XMLNS_XC, NETCONF_1_0_BASE_NAMESPACE);

    private static final String XC_OPERATION = "xc:operation";

    @Test
    public void test1() {
        StringBuilder rpc = new StringBuilder();

        // - Add NETCONF envelope
        rpc.append("<rpc xmlns=\"").append(NETCONF_1_0_BASE_NAMESPACE).append('"')
                .append(">");

        rpc.append("<edit-config>");
        rpc.append("<target>");
        // TODO directly writing to running for now
        rpc.append("<running/>");
        rpc.append("</target>\n");
        rpc.append("<config ")
                .append(XMLNS_XC).append("=\"").append(NETCONF_1_0_BASE_NAMESPACE).append("\">");
        rpc.append('\n');
        rpc.append("</config>");
        rpc.append("</edit-config>");
        rpc.append("</rpc>");

        System.out.println(rpc.toString());
    }


    @Test
    public void test2() {

        YangNode rootNode = null;
        YangPluginConfig yangPlugin = new YangPluginConfig();
//        yangPlugin.setCodeGenerateForSbi();
        boolean codeGenl = true;
        boolean translateComplete = true;
        try {
            JavaCodeGeneratorUtil.translate(rootNode, yangPlugin, codeGenl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks union translation should not result in any exception.
     */
    @Test
    public void processUnionTranslator()
            throws IOException, ParserException {
        deleteDirectory(DIR);
        YangNode node = manager.getDataModel("src/test/resources/inhand/ietf-interfaces@2018-02-20.yang");

        YangPluginConfig yangPluginConfig = new YangPluginConfig();
        yangPluginConfig.setCodeGenDir(DIR);

        JavaCodeGeneratorUtil.generateJavaCode(node, yangPluginConfig);
        YangPluginConfig.compileCode(DIR1);
//        YangIoUtils.deleteDirectory(DIR);
    }




    @Test
    public void test() throws IOException {

        YangCompilerManager yangCompilerManager = new YangCompilerManager();
        String dir = "target/inhand/";
        try {
            deleteDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String searchDir = "src/test/resources/inhand";
        List<String> fileList = YangFileScanner.getYangFiles(searchDir);
        Class c = yangCompilerManager.getClass();
        File javaRoot = new File("D:\\code\\java\\src\\test\\resources\\inhand", "");
        DefaultYangCompilationParam.Builder param = DefaultYangCompilationParam.builder()

                .setCodeGenDir(new File(javaRoot, "src").toPath())
                .setMetadataGenDir(new File(javaRoot, "schema").toPath())
                .setModelId("uplinkcopy@2020-11-05");
        String file = "D:/code/java/src/test/resources/inhand/uplinkcopy@2020-11-05.yang";
        param.addYangFile(Paths.get(file));
        Set<Path> paths = new HashSet<>();
        fileList.forEach(f->{
            paths.add(Paths.get(f));
        });

        YangPluginConfig config = new YangPluginConfig();
        synchronized(YangCompilerManager.class) {
            try {
                String codeGenDir = "target/inhand/";
                String resourceGenDir = "src/test/resources/inhand";
                config.setCodeGenDir(codeGenDir);
                config.resourceGenDir(resourceGenDir);
                yangCompilerManager.setYangFileInfoSet(yangCompilerManager.createYangFileInfoSet(paths));
                if (!yangCompilerManager.getYangFileInfoSet().isEmpty()) {
                    YangIoUtils.createDirectories(resourceGenDir);
                    yangCompilerManager.parseYangFileInfoSet();
                    yangCompilerManager.createYangNodeSet();

                    try {
                        yangCompilerManager.resolveDependenciesUsingLinker();
                    } catch (Exception var9) {
                        throw var9;
                    }

                    yangCompilerManager.translateToJava(config);
                    return;
                }
            } catch (ParserException | IOException var10) {
                var10.printStackTrace();
            }

        }
    }

}
