/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.annotation.JsonCreator;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.annotation.JsonFormat;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.annotation.JsonInclude;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JavaType;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.introspect.AnnotatedAndMetadata;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.type.TypeBindings;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.util.Annotations;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.util.Converter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BeanDescription {
    protected final JavaType _type;

    protected BeanDescription(JavaType type) {
        this._type = type;
    }

    public JavaType getType() {
        return this._type;
    }

    public Class<?> getBeanClass() {
        return this._type.getRawClass();
    }

    public boolean isRecordType() {
        return this._type.isRecordType();
    }

    public boolean isNonStaticInnerClass() {
        return this.getClassInfo().isNonStaticInnerClass();
    }

    public abstract AnnotatedClass getClassInfo();

    public abstract ObjectIdInfo getObjectIdInfo();

    public abstract boolean hasKnownClassAnnotations();

    @Deprecated
    public abstract TypeBindings bindingsForBeanType();

    @Deprecated
    public abstract JavaType resolveType(Type var1);

    public abstract Annotations getClassAnnotations();

    public abstract List<BeanPropertyDefinition> findProperties();

    public abstract Set<String> getIgnoredPropertyNames();

    public abstract List<BeanPropertyDefinition> findBackReferences();

    @Deprecated
    public abstract Map<String, AnnotatedMember> findBackReferenceProperties();

    public abstract List<AnnotatedConstructor> getConstructors();

    public abstract List<AnnotatedAndMetadata<AnnotatedConstructor, JsonCreator.Mode>> getConstructorsWithMode();

    public abstract List<AnnotatedMethod> getFactoryMethods();

    public abstract List<AnnotatedAndMetadata<AnnotatedMethod, JsonCreator.Mode>> getFactoryMethodsWithMode();

    public abstract AnnotatedConstructor findDefaultConstructor();

    @Deprecated
    public abstract Constructor<?> findSingleArgConstructor(Class<?> ... var1);

    @Deprecated
    public abstract Method findFactoryMethod(Class<?> ... var1);

    public AnnotatedMember findJsonKeyAccessor() {
        return null;
    }

    public abstract AnnotatedMember findJsonValueAccessor();

    public abstract AnnotatedMember findAnyGetter();

    public abstract AnnotatedMember findAnySetterAccessor();

    public abstract AnnotatedMethod findMethod(String var1, Class<?>[] var2);

    @Deprecated
    public abstract AnnotatedMethod findJsonValueMethod();

    @Deprecated
    public AnnotatedMethod findAnySetter() {
        AnnotatedMember m = this.findAnySetterAccessor();
        if (m instanceof AnnotatedMethod) {
            return (AnnotatedMethod)m;
        }
        return null;
    }

    @Deprecated
    public AnnotatedMember findAnySetterField() {
        AnnotatedMember m = this.findAnySetterAccessor();
        if (m instanceof AnnotatedField) {
            return m;
        }
        return null;
    }

    public abstract JsonInclude.Value findPropertyInclusion(JsonInclude.Value var1);

    public abstract JsonFormat.Value findExpectedFormat();

    @Deprecated
    public JsonFormat.Value findExpectedFormat(JsonFormat.Value defValue) {
        JsonFormat.Value v = this.findExpectedFormat();
        if (defValue == null) {
            return v;
        }
        if (v == null) {
            return defValue;
        }
        return defValue.withOverrides(v);
    }

    public abstract Converter<Object, Object> findSerializationConverter();

    public abstract Converter<Object, Object> findDeserializationConverter();

    public String findClassDescription() {
        return null;
    }

    public abstract Map<Object, AnnotatedMember> findInjectables();

    public abstract Class<?> findPOJOBuilder();

    public abstract JsonPOJOBuilder.Value findPOJOBuilderConfig();

    public abstract Object instantiateBean(boolean var1);

    public abstract Class<?>[] findDefaultViews();
}

