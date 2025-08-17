package com.xk857.framework.processor.check;

import com.xk857.framework.processor.annotation.MyApiResponse;
import com.xk857.framework.processor.annotation.RawResponse;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes({
        "com.xk857.framework.processor.annotation.MyApiResponse",
        "com.xk857.framework.processor.annotation.RawResponse"
})
@SupportedSourceVersion(SourceVersion.RELEASE_17) // 声明支持的Java版本
public class MutualExclusionProcessor extends AbstractProcessor {

    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // 核心检查逻辑：查找所有被 @MyApiResponse 注解的元素
        for (Element element : roundEnv.getElementsAnnotatedWith(MyApiResponse.class)) {
            // 检查同一个元素上是否也存在 @RawResponse 注解
            if (element.getAnnotation(RawResponse.class) != null) {
                // 如果同时存在，报告一个编译错误！
                messager.printMessage(
                        Diagnostic.Kind.ERROR,
                        "Semantic Error: @MyApiResponse and @RawResponse cannot be used on the same element.",
                        element // 定位到出错的元素上
                );
            }
        }

        // 反向也检查一遍，确保万无一失
        for (Element element : roundEnv.getElementsAnnotatedWith(RawResponse.class)) {
            if (element.getAnnotation(MyApiResponse.class) != null) {
                messager.printMessage(
                        Diagnostic.Kind.ERROR,
                        "Semantic Error: @RawResponse and @MyApiResponse cannot be used on the same element.",
                        element
                );
            }
        }

        return true;
    }
}
