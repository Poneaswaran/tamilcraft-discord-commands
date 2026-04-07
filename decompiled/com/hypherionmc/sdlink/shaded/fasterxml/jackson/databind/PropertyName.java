/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.SerializableString;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.io.SerializedString;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.util.InternCache;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.cfg.MapperConfig;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;
import java.util.Objects;

public class PropertyName
implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String _USE_DEFAULT = "";
    private static final String _NO_NAME = "";
    public static final PropertyName USE_DEFAULT = new PropertyName("", null);
    public static final PropertyName NO_NAME = new PropertyName(new String(""), null);
    protected final String _simpleName;
    protected final String _namespace;
    protected SerializableString _encodedSimple;

    public PropertyName(String simpleName) {
        this(simpleName, null);
    }

    public PropertyName(String simpleName, String namespace) {
        this._simpleName = ClassUtil.nonNullString(simpleName);
        this._namespace = namespace;
    }

    protected Object readResolve() {
        if (this._namespace == null && (this._simpleName == null || "".equals(this._simpleName))) {
            return USE_DEFAULT;
        }
        return this;
    }

    public static PropertyName construct(String simpleName) {
        if (simpleName == null || simpleName.isEmpty()) {
            return USE_DEFAULT;
        }
        return new PropertyName(InternCache.instance.intern(simpleName), null);
    }

    public static PropertyName construct(String simpleName, String ns) {
        if (simpleName == null) {
            simpleName = "";
        }
        if (ns == null && simpleName.isEmpty()) {
            return USE_DEFAULT;
        }
        return new PropertyName(InternCache.instance.intern(simpleName), ns);
    }

    public static PropertyName merge(PropertyName name1, PropertyName name2) {
        if (name1 == null) {
            return name2;
        }
        if (name2 == null) {
            return name1;
        }
        if (name1 == NO_NAME) {
            return name1;
        }
        String ns = PropertyName._nonEmpty(name1._namespace, name2._namespace);
        String simple = PropertyName._nonEmpty(name1._simpleName, name2._simpleName);
        if (ns == name1._namespace && simple == name1._simpleName) {
            return name1;
        }
        if (ns == name2._namespace && simple == name2._simpleName) {
            return name2;
        }
        return PropertyName.construct(simple, ns);
    }

    private static String _nonEmpty(String str1, String str2) {
        if (str1 == null) {
            return str2;
        }
        if (str2 == null) {
            return str1;
        }
        if (str1.isEmpty()) {
            return str2;
        }
        return str1;
    }

    public PropertyName internSimpleName() {
        if (this._simpleName.isEmpty()) {
            return this;
        }
        String interned = InternCache.instance.intern(this._simpleName);
        if (interned == this._simpleName) {
            return this;
        }
        return new PropertyName(interned, this._namespace);
    }

    public PropertyName withSimpleName(String simpleName) {
        if (simpleName == null) {
            simpleName = "";
        }
        if (simpleName.equals(this._simpleName)) {
            return this;
        }
        return new PropertyName(simpleName, this._namespace);
    }

    public PropertyName withNamespace(String ns) {
        if (ns == null ? this._namespace == null : ns.equals(this._namespace)) {
            return this;
        }
        return new PropertyName(this._simpleName, ns);
    }

    public String getSimpleName() {
        return this._simpleName;
    }

    public SerializableString simpleAsEncoded(MapperConfig<?> config) {
        SerializableString sstr = this._encodedSimple;
        if (sstr == null) {
            sstr = config == null ? new SerializedString(this._simpleName) : config.compileString(this._simpleName);
            this._encodedSimple = sstr;
        }
        return sstr;
    }

    public String getNamespace() {
        return this._namespace;
    }

    public boolean hasSimpleName() {
        return !this._simpleName.isEmpty();
    }

    public boolean hasSimpleName(String str) {
        return this._simpleName.equals(str);
    }

    public boolean hasNamespace() {
        return this._namespace != null;
    }

    public boolean isEmpty() {
        return this._namespace == null && this._simpleName.isEmpty();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        PropertyName other = (PropertyName)o;
        if (this._simpleName == null ? other._simpleName != null : !this._simpleName.equals(other._simpleName)) {
            return false;
        }
        if (this._namespace == null) {
            return null == other._namespace;
        }
        return this._namespace.equals(other._namespace);
    }

    public int hashCode() {
        return Objects.hashCode(this._simpleName) * 31 + Objects.hashCode(this._namespace);
    }

    public String toString() {
        if (this._namespace == null) {
            return this._simpleName;
        }
        return "{" + this._namespace + "}" + this._simpleName;
    }
}

