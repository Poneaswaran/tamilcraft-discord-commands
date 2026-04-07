/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.util;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.annotation.JsonInclude;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JavaType;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.PropertyMetadata;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.PropertyName;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.cfg.MapperConfig;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.type.TypeFactory;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.util.ClassUtil;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SimpleBeanPropertyDefinition
extends BeanPropertyDefinition {
    protected final AnnotationIntrospector _annotationIntrospector;
    protected final AnnotatedMember _member;
    protected final PropertyMetadata _metadata;
    protected final PropertyName _fullName;
    protected final JsonInclude.Value _inclusion;

    protected SimpleBeanPropertyDefinition(AnnotationIntrospector intr, AnnotatedMember member, PropertyName fullName, PropertyMetadata metadata, JsonInclude.Value inclusion) {
        this._annotationIntrospector = intr;
        this._member = member;
        this._fullName = fullName;
        this._metadata = metadata == null ? PropertyMetadata.STD_OPTIONAL : metadata;
        this._inclusion = inclusion;
    }

    public static SimpleBeanPropertyDefinition construct(MapperConfig<?> config, AnnotatedMember member) {
        return new SimpleBeanPropertyDefinition(config.getAnnotationIntrospector(), member, PropertyName.construct(member.getName()), null, EMPTY_INCLUDE);
    }

    public static SimpleBeanPropertyDefinition construct(MapperConfig<?> config, AnnotatedMember member, PropertyName name) {
        return SimpleBeanPropertyDefinition.construct(config, member, name, null, EMPTY_INCLUDE);
    }

    public static SimpleBeanPropertyDefinition construct(MapperConfig<?> config, AnnotatedMember member, PropertyName name, PropertyMetadata metadata, JsonInclude.Include inclusion) {
        JsonInclude.Value inclValue = inclusion == null || inclusion == JsonInclude.Include.USE_DEFAULTS ? EMPTY_INCLUDE : JsonInclude.Value.construct(inclusion, null);
        return new SimpleBeanPropertyDefinition(config.getAnnotationIntrospector(), member, name, metadata, inclValue);
    }

    public static SimpleBeanPropertyDefinition construct(MapperConfig<?> config, AnnotatedMember member, PropertyName name, PropertyMetadata metadata, JsonInclude.Value inclusion) {
        return new SimpleBeanPropertyDefinition(config.getAnnotationIntrospector(), member, name, metadata, inclusion);
    }

    @Override
    public BeanPropertyDefinition withSimpleName(String newName) {
        if (this._fullName.hasSimpleName(newName) && !this._fullName.hasNamespace()) {
            return this;
        }
        return new SimpleBeanPropertyDefinition(this._annotationIntrospector, this._member, new PropertyName(newName), this._metadata, this._inclusion);
    }

    @Override
    public BeanPropertyDefinition withName(PropertyName newName) {
        if (this._fullName.equals(newName)) {
            return this;
        }
        return new SimpleBeanPropertyDefinition(this._annotationIntrospector, this._member, newName, this._metadata, this._inclusion);
    }

    public BeanPropertyDefinition withMetadata(PropertyMetadata metadata) {
        if (metadata.equals(this._metadata)) {
            return this;
        }
        return new SimpleBeanPropertyDefinition(this._annotationIntrospector, this._member, this._fullName, metadata, this._inclusion);
    }

    public BeanPropertyDefinition withInclusion(JsonInclude.Value inclusion) {
        if (this._inclusion == inclusion) {
            return this;
        }
        return new SimpleBeanPropertyDefinition(this._annotationIntrospector, this._member, this._fullName, this._metadata, inclusion);
    }

    @Override
    public String getName() {
        return this._fullName.getSimpleName();
    }

    @Override
    public PropertyName getFullName() {
        return this._fullName;
    }

    @Override
    public boolean hasName(PropertyName name) {
        return this._fullName.equals(name);
    }

    @Override
    public String getInternalName() {
        return this.getName();
    }

    @Override
    public PropertyName getWrapperName() {
        if (this._annotationIntrospector == null || this._member == null) {
            return null;
        }
        return this._annotationIntrospector.findWrapperName(this._member);
    }

    @Override
    public boolean isExplicitlyIncluded() {
        return false;
    }

    @Override
    public boolean isExplicitlyNamed() {
        return false;
    }

    @Override
    public PropertyMetadata getMetadata() {
        return this._metadata;
    }

    @Override
    public JavaType getPrimaryType() {
        if (this._member == null) {
            return TypeFactory.unknownType();
        }
        return this._member.getType();
    }

    @Override
    public Class<?> getRawPrimaryType() {
        if (this._member == null) {
            return Object.class;
        }
        return this._member.getRawType();
    }

    @Override
    public JsonInclude.Value findInclusion() {
        return this._inclusion;
    }

    @Override
    public List<PropertyName> findAliases() {
        List<PropertyName> propertyNames;
        AnnotatedMember ann = this.getPrimaryMember();
        if (ann != null && (propertyNames = this._annotationIntrospector.findPropertyAliases(ann)) != null) {
            return propertyNames;
        }
        return Collections.emptyList();
    }

    @Override
    public boolean hasGetter() {
        return this.getGetter() != null;
    }

    @Override
    public boolean hasSetter() {
        return this.getSetter() != null;
    }

    @Override
    public boolean hasField() {
        return this._member instanceof AnnotatedField;
    }

    @Override
    public boolean hasConstructorParameter() {
        return this._member instanceof AnnotatedParameter;
    }

    @Override
    public AnnotatedMethod getGetter() {
        if (this._member instanceof AnnotatedMethod && ((AnnotatedMethod)this._member).getParameterCount() == 0) {
            return (AnnotatedMethod)this._member;
        }
        return null;
    }

    @Override
    public AnnotatedMethod getSetter() {
        if (this._member instanceof AnnotatedMethod && ((AnnotatedMethod)this._member).getParameterCount() == 1) {
            return (AnnotatedMethod)this._member;
        }
        return null;
    }

    @Override
    public AnnotatedField getField() {
        return this._member instanceof AnnotatedField ? (AnnotatedField)this._member : null;
    }

    @Override
    public AnnotatedParameter getConstructorParameter() {
        return this._member instanceof AnnotatedParameter ? (AnnotatedParameter)this._member : null;
    }

    @Override
    public Iterator<AnnotatedParameter> getConstructorParameters() {
        AnnotatedParameter param = this.getConstructorParameter();
        if (param == null) {
            return ClassUtil.emptyIterator();
        }
        return Collections.singleton(param).iterator();
    }

    @Override
    public AnnotatedMember getPrimaryMember() {
        return this._member;
    }
}

