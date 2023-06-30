package self.mysql.common;

import cn.hutool.core.text.CharSequenceUtil;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;
import self.mysql.innodb.parse.entity.PageType;
import self.mysql.innodb.parse.entity.page.DefaultPageContent;
import self.mysql.innodb.parse.entity.page.PageContent;

/**
 * @author chenzibin
 * @date 2023/2/3
 */
@UtilityClass
public class SpiSelector {

    private static final Map<PageType, Class<? extends PageContent>> PAGE_CLASS_MAP;

    static {
        try {
            PAGE_CLASS_MAP = getAllClassByInterface(PageContent.class, PageSpi.class, PageSpi::value);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static PageContent getPageInstance(PageType pageType) {
        try {
            return PAGE_CLASS_MAP.getOrDefault(pageType, DefaultPageContent.class).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <A extends Annotation, K, T> Map<K, Class<? extends T>> getAllClassByInterface(Class<T> interfaceClass, Class<A> annotationClass, Function<A, K> idFunc) throws IOException, ClassNotFoundException {
        String packageName = interfaceClass.getPackage().getName();
        String resourcePath = packageName.replace('.', '/');

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> urlEnumeration = classLoader.getResources(resourcePath);

        Map<K, Class<? extends T>> map = new HashMap<>();
        while (urlEnumeration.hasMoreElements()) {
            URL url = urlEnumeration.nextElement();
            File file = new File(url.getFile());
            List<String> classNames = getAllClassByFile(file, packageName);
            for (String className : classNames) {
                Class<? extends T> implementClass = (Class<? extends T>) Class.forName(className);
                if (interfaceClass.isAssignableFrom(implementClass) && implementClass.isAnnotationPresent(annotationClass)) {
                    A a = implementClass.getAnnotation(annotationClass);
                    K k = idFunc.apply(a);
                    map.put(k, implementClass);
                }
            }
        }
        return map;
    }

    private static List<String> getAllClassByFile(File file, String packageName) {
        List<String> classNames = new ArrayList<>();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                Stream.of(files).forEach(f -> classNames.addAll(getAllClassByFile(f, packageName)));
            }
        } else if (file.getName().endsWith(".class")) {
            String className = CharSequenceUtil.format("{}.{}", packageName, CharSequenceUtil.removeSuffix(file.getName(), ".class"));
            classNames.add(className);
        }
        return classNames;
    }
}
