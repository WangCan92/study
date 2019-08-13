package org.mybatis.util;

import org.mybatis.anno.MapperScan;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;

/**
 * @author wangcan
 */
public class MyImportBeanDefinationRegistara implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(
            AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        GenericBeanDefinition appConfig = (GenericBeanDefinition) beanDefinitionRegistry.getBeanDefinition("appConfig");
        Class clazz = appConfig.getBeanClass();
        MapperScan declaredAnnotation = (MapperScan) clazz.getDeclaredAnnotation(MapperScan.class);
        String pages = declaredAnnotation.value();
        System.out.println(pages);
        //扫描包

        Set<Class<?>> classes = ClassUtil.getClasses(pages);
        classes.forEach(daoclazz -> {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(daoclazz);
            GenericBeanDefinition genericBeanDefinition = (GenericBeanDefinition) builder.getBeanDefinition();
            genericBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue(daoclazz.getName());
            genericBeanDefinition.setBeanClass(MapperFactoryBean.class);
            beanDefinitionRegistry.registerBeanDefinition(daoclazz.getSimpleName(),genericBeanDefinition);
        });

    }

}
