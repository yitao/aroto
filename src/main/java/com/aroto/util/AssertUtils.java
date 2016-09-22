package com.aroto.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by yitao on 2016/9/22.
 */

public abstract class AssertUtils {
    public AssertUtils() {
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }

    public static void isTrue(boolean expression, String message) {
        if(!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(boolean expression, RuntimeException throwIfAssertFail) {
        if(!expression) {
            throw throwIfAssertFail;
        }
    }

    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    public static void isNull(Object object, String message) {
        if(object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isNull(Object object, RuntimeException throwIfAssertFail) {
        if(object != null) {
            throw throwIfAssertFail;
        }
    }

    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    public static void notNull(Object object, String message) {
        if(object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object object, RuntimeException throwIfAssertFail) {
        if(object == null) {
            throw throwIfAssertFail;
        }
    }

    public static void isBlank(String text) {
        isBlank(text, "[Assertion failed] - this String argument must have length; it must not be null or empty");
    }

    public static void isBlank(String text, String message) {
        if(!StringUtils.isBlank(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isBlank(String text, RuntimeException throwIfAssertFail) {
        if(!StringUtils.isBlank(text)) {
            throw throwIfAssertFail;
        }
    }

    public static void hasText(String text) {
        hasText(text, "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
    }

    public static void hasText(String text, String message) {
        if(!StringUtils.isBlank(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void hasText(String text, RuntimeException throwIfAssertFail) {
        if(!StringUtils.isBlank(text)) {
            throw throwIfAssertFail;
        }
    }

    public static void doesNotContain(String textToSearch, String substring) {
        doesNotContain(textToSearch, substring, "[Assertion failed] - this String argument must not contain the substring [" + substring + "]");
    }

    public static void doesNotContain(String textToSearch, String substring, String message) {
        if(StringUtils.isBlank(textToSearch) && StringUtils.isBlank(substring) && textToSearch.indexOf(substring) != -1) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void doesNotContain(String textToSearch, String substring, RuntimeException throwIfAssertFail) {
        if(StringUtils.isBlank(textToSearch) && StringUtils.isBlank(substring) && textToSearch.indexOf(substring) != -1) {
            throw throwIfAssertFail;
        }
    }

    public static void notEmpty(Object[] array) {
        notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
    }

    public static void notEmpty(Object[] array, String message) {
        if(ArrayUtils.isEmpty(array)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(Object[] array, RuntimeException throwIfAssertFail) {
        if(ArrayUtils.isEmpty(array)) {
            throw throwIfAssertFail;
        }
    }

    public static void noNullElements(Object[] array, String message) {
        if(array != null) {
            Object[] arr$ = array;
            int len$ = array.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Object element = arr$[i$];
                if(element == null) {
                    throw new IllegalArgumentException(message);
                }
            }
        }

    }

    public static void noNullElements(Object[] array) {
        noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
    }

    public static void noNullElements(Object[] array, RuntimeException throwIfAssertFail) {
        if(array != null) {
            Object[] arr$ = array;
            int len$ = array.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Object element = arr$[i$];
                if(element == null) {
                    throw throwIfAssertFail;
                }
            }
        }

    }

    public static void notEmpty(Collection<?> collection, String message) {
        if(CollectionUtils.isEmpty(collection)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(Collection<?> collection) {
        notEmpty(collection, "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
    }

    public static void notEmpty(Collection<?> collection, RuntimeException throwIfAssertFail) {
        if(CollectionUtils.isEmpty(collection)) {
            throw throwIfAssertFail;
        }
    }

    public static void notEmpty(Map<?, ?> map, String message) {
        if(MapUtils.isEmpty(map)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(Map<?, ?> map) {
        notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
    }

    public static void notEmpty(Map<?, ?> map, RuntimeException throwIfAssertFail) {
        if(MapUtils.isEmpty(map)) {
            throw throwIfAssertFail;
        }
    }

    public static void isInstanceOf(Class<?> type, Object obj, String message) {
        notNull(type, (String)"Type to check against must not be null");
        if(!type.isInstance(obj)) {
            throw new IllegalArgumentException(message + "Object of class [" + (obj != null?obj.getClass().getName():"null") + "] must be an instance of " + type);
        }
    }

    public static void isInstanceOf(Class<?> clazz, Object obj) {
        isInstanceOf(clazz, obj, "");
    }

    public static void isInstanceOf(Class<?> type, Object obj, RuntimeException throwIfAssertFail) {
        notNull(type, (String)"Type to check against must not be null");
        if(!type.isInstance(obj)) {
            throw throwIfAssertFail;
        }
    }

    public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
        notNull(superType, (String)"Type to check against must not be null");
        if(subType == null || !superType.isAssignableFrom(subType)) {
            throw new IllegalArgumentException(message + subType + " is not assignable to " + superType);
        }
    }

    public static void isAssignable(Class<?> superType, Class<?> subType) {
        isAssignable(superType, subType, "");
    }

    public static void state(boolean expression, String message) {
        if(!expression) {
            throw new IllegalStateException(message);
        }
    }

    public static void state(boolean expression) {
        state(expression, "[Assertion failed] - this state invariant must be true");
    }
}
