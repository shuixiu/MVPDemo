package com.content.baseui.util.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ClassAsFactory {

    public static <T> T buildInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    // @SuppressWarnings("unchecked")
    // public static <T> T getT(Object o, int i) {
    // try {
    // return ((Class<T>) ((ParameterizedType)
    // (o.getClass().getGenericSuperclass())).getActualTypeArguments()[i])
    // .newInstance();
    // } catch (InstantiationException e) {
    // e.printStackTrace();
    // } catch (IllegalAccessException e) {
    // e.printStackTrace();
    // } catch (ClassCastException e) {
    // e.printStackTrace();
    // }
    // return null;
    // }

    @SuppressWarnings("unchecked")
    public static <T> T getGenericClass(Class<?> theClass, int position) {
        Type ttType = ClassAsFactory.getSuperclassTypeParameter(theClass, position);
        if (ttType == null) {
            return null;
        }
        Class<T> clazz = (Class<T>) ttType;
        try {
            T tt = clazz.newInstance();
            return tt;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取一个继承了具有泛型的超类的 Type（类声明类型）
     *
     * @param subclass
     * @param position
     * @return
     */
    public static Type getSuperclassTypeParameter(Class<?> subclass, int position) {
        // getGenericSuperclass() 用来返回当前Class所表示的实体的直接超类TYPE
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        Type[] types = parameterized.getActualTypeArguments();
        try {
            Type type = types[position];
            return type;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}