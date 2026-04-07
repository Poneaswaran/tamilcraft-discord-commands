/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Contract
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.exceptions.ParsingException;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.MiscUtil;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataType;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.SerializableArray;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.SerializableData;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.etf.ExTermDecoder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.etf.ExTermEncoder;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Checks;
import com.hypherionmc.sdlink.shaded.dv8tion.jda.internal.utils.Helpers;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonProcessingException;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.JavaType;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.ObjectMapper;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.SerializationFeature;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.module.SimpleModule;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.type.MapType;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nonnull;
import com.hypherionmc.sdlink.shaded.javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataObject
implements SerializableData {
    private static final Logger log = LoggerFactory.getLogger(DataObject.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final SimpleModule module = new SimpleModule();
    private static final MapType mapType;
    protected final Map<String, Object> data;

    protected DataObject(@Nonnull Map<String, Object> data) {
        this.data = data;
    }

    @Nonnull
    public static DataObject empty() {
        return new DataObject(new HashMap<String, Object>());
    }

    @Nonnull
    public static DataObject fromJson(@Nonnull byte[] data) {
        try {
            Map map = (Map)mapper.readValue(data, (JavaType)mapType);
            return new DataObject(map);
        }
        catch (IOException ex) {
            throw new ParsingException(ex);
        }
    }

    @Nonnull
    public static DataObject fromJson(@Nonnull String json) {
        try {
            Map map = (Map)mapper.readValue(json, (JavaType)mapType);
            return new DataObject(map);
        }
        catch (IOException ex) {
            throw new ParsingException(ex);
        }
    }

    @Nonnull
    public static DataObject fromJson(@Nonnull InputStream stream) {
        try {
            Map map = (Map)mapper.readValue(stream, (JavaType)mapType);
            return new DataObject(map);
        }
        catch (IOException ex) {
            throw new ParsingException(ex);
        }
    }

    @Nonnull
    public static DataObject fromJson(@Nonnull Reader stream) {
        try {
            Map map = (Map)mapper.readValue(stream, (JavaType)mapType);
            return new DataObject(map);
        }
        catch (IOException ex) {
            throw new ParsingException(ex);
        }
    }

    @Nonnull
    public static DataObject fromETF(@Nonnull byte[] data) {
        Checks.notNull(data, "Data");
        try {
            Map<String, Object> map = ExTermDecoder.unpackMap(ByteBuffer.wrap(data));
            return new DataObject(map);
        }
        catch (Exception ex) {
            log.error("Failed to parse ETF data {}", (Object)Arrays.toString(data), (Object)ex);
            throw new ParsingException(ex);
        }
    }

    public boolean hasKey(@Nonnull String key) {
        return this.data.containsKey(key);
    }

    public boolean isNull(@Nonnull String key) {
        return this.data.get(key) == null;
    }

    public boolean isType(@Nonnull String key, @Nonnull DataType type) {
        return type.isType(this.data.get(key));
    }

    @Nonnull
    public DataObject getObject(@Nonnull String key) {
        return this.optObject(key).orElseThrow(() -> this.valueError(key, "DataObject"));
    }

    @Nonnull
    public Optional<DataObject> optObject(@Nonnull String key) {
        Map child = null;
        try {
            child = this.get(Map.class, key);
        }
        catch (ClassCastException ex) {
            log.error("Unable to extract child data", (Throwable)ex);
        }
        return child == null ? Optional.empty() : Optional.of(new DataObject(child));
    }

    @Nonnull
    public DataArray getArray(@Nonnull String key) {
        return this.optArray(key).orElseThrow(() -> this.valueError(key, "DataArray"));
    }

    @Nonnull
    public Optional<DataArray> optArray(@Nonnull String key) {
        List child = null;
        try {
            child = this.get(List.class, key);
        }
        catch (ClassCastException ex) {
            log.error("Unable to extract child data", (Throwable)ex);
        }
        return child == null ? Optional.empty() : Optional.of(new DataArray(child));
    }

    @Nonnull
    public Optional<Object> opt(@Nonnull String key) {
        return Optional.ofNullable(this.data.get(key));
    }

    @Nonnull
    public Object get(@Nonnull String key) {
        Object value = this.data.get(key);
        if (value == null) {
            throw this.valueError(key, "any");
        }
        return value;
    }

    @Nonnull
    public String getString(@Nonnull String key) {
        String value = this.getString(key, null);
        if (value == null) {
            throw this.valueError(key, "String");
        }
        return value;
    }

    @Contract(value="_, !null -> !null")
    public String getString(@Nonnull String key, @Nullable String defaultValue) {
        String value = this.get(String.class, key, UnaryOperator.identity(), String::valueOf);
        return value == null ? defaultValue : value;
    }

    public boolean getBoolean(@Nonnull String key) {
        return this.getBoolean(key, false);
    }

    public boolean getBoolean(@Nonnull String key, boolean defaultValue) {
        Boolean value = this.get(Boolean.class, key, Boolean::parseBoolean, null);
        return value == null ? defaultValue : value;
    }

    public long getLong(@Nonnull String key) {
        Long value = this.get(Long.class, key, MiscUtil::parseLong, Number::longValue);
        if (value == null) {
            throw this.valueError(key, "long");
        }
        return value;
    }

    public long getLong(@Nonnull String key, long defaultValue) {
        Long value = this.get(Long.class, key, Long::parseLong, Number::longValue);
        return value == null ? defaultValue : value;
    }

    public long getUnsignedLong(@Nonnull String key) {
        Long value = this.get(Long.class, key, Long::parseUnsignedLong, Number::longValue);
        if (value == null) {
            throw this.valueError(key, "unsigned long");
        }
        return value;
    }

    public long getUnsignedLong(@Nonnull String key, long defaultValue) {
        Long value = this.get(Long.class, key, Long::parseUnsignedLong, Number::longValue);
        return value == null ? defaultValue : value;
    }

    public int getInt(@Nonnull String key) {
        Integer value = this.get(Integer.class, key, Integer::parseInt, Number::intValue);
        if (value == null) {
            throw this.valueError(key, "int");
        }
        return value;
    }

    public int getInt(@Nonnull String key, int defaultValue) {
        Integer value = this.get(Integer.class, key, Integer::parseInt, Number::intValue);
        return value == null ? defaultValue : value;
    }

    public int getUnsignedInt(@Nonnull String key) {
        Integer value = this.get(Integer.class, key, Integer::parseUnsignedInt, Number::intValue);
        if (value == null) {
            throw this.valueError(key, "unsigned int");
        }
        return value;
    }

    public int getUnsignedInt(@Nonnull String key, int defaultValue) {
        Integer value = this.get(Integer.class, key, Integer::parseUnsignedInt, Number::intValue);
        return value == null ? defaultValue : value;
    }

    public double getDouble(@Nonnull String key) {
        Double value = this.get(Double.class, key, Double::parseDouble, Number::doubleValue);
        if (value == null) {
            throw this.valueError(key, "double");
        }
        return value;
    }

    public double getDouble(@Nonnull String key, double defaultValue) {
        Double value = this.get(Double.class, key, Double::parseDouble, Number::doubleValue);
        return value == null ? defaultValue : value;
    }

    @Nonnull
    public OffsetDateTime getOffsetDateTime(@Nonnull String key) {
        OffsetDateTime value = this.getOffsetDateTime(key, null);
        if (value == null) {
            throw this.valueError(key, "OffsetDateTime");
        }
        return value;
    }

    @Contract(value="_, !null -> !null")
    public OffsetDateTime getOffsetDateTime(@Nonnull String key, @Nullable OffsetDateTime defaultValue) {
        OffsetDateTime value;
        try {
            value = this.get(OffsetDateTime.class, key, OffsetDateTime::parse, null);
        }
        catch (DateTimeParseException e) {
            String reason = "Cannot parse value for %s into an OffsetDateTime object. Try double checking that %s is a valid ISO8601 timestmap";
            throw new ParsingException(String.format(reason, key, e.getParsedString()));
        }
        return value == null ? defaultValue : value;
    }

    @Nonnull
    public DataObject remove(@Nonnull String key) {
        this.data.remove(key);
        return this;
    }

    @Nonnull
    public DataObject putNull(@Nonnull String key) {
        this.data.put(key, null);
        return this;
    }

    @Nonnull
    public DataObject put(@Nonnull String key, @Nullable Object value) {
        if (value instanceof SerializableData) {
            this.data.put(key, ((SerializableData)value).toData().data);
        } else if (value instanceof SerializableArray) {
            this.data.put(key, ((SerializableArray)value).toDataArray().data);
        } else {
            this.data.put(key, value);
        }
        return this;
    }

    @Nonnull
    public DataObject rename(@Nonnull String key, @Nonnull String newKey) {
        Checks.notNull(key, "Key");
        Checks.notNull(newKey, "Key");
        if (!this.data.containsKey(key)) {
            return this;
        }
        this.data.put(newKey, this.data.remove(key));
        return this;
    }

    @Nonnull
    public Collection<Object> values() {
        return this.data.values();
    }

    @Nonnull
    public Set<String> keys() {
        return this.data.keySet();
    }

    @Nonnull
    public byte[] toJson() {
        try {
            ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
            mapper.writeValue(outputStream2, this.data);
            return outputStream2.toByteArray();
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Nonnull
    public byte[] toETF() {
        ByteBuffer buffer = ExTermEncoder.pack(this.data);
        return Arrays.copyOfRange(buffer.array(), buffer.arrayOffset(), buffer.arrayOffset() + buffer.limit());
    }

    public String toString() {
        try {
            return mapper.writeValueAsString(this.data);
        }
        catch (JsonProcessingException e) {
            throw new ParsingException(e);
        }
    }

    @Nonnull
    public String toPrettyString() {
        try {
            return mapper.writer(new DefaultPrettyPrinter()).with(SerializationFeature.INDENT_OUTPUT).with(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS).writeValueAsString(this.data);
        }
        catch (JsonProcessingException e) {
            throw new ParsingException(e);
        }
    }

    @Nonnull
    public Map<String, Object> toMap() {
        return this.data;
    }

    @Override
    @Nonnull
    public DataObject toData() {
        return this;
    }

    private ParsingException valueError(String key, String expectedType) {
        return new ParsingException("Unable to resolve value with key " + key + " to type " + expectedType + ": " + this.data.get(key));
    }

    @Nullable
    private <T> T get(@Nonnull Class<T> type, @Nonnull String key) {
        return this.get(type, key, null, null);
    }

    @Nullable
    private <T> T get(@Nonnull Class<T> type, @Nonnull String key, @Nullable Function<String, T> stringParse, @Nullable Function<Number, T> numberParse) {
        Object value = this.data.get(key);
        if (value == null) {
            return null;
        }
        if (type.isInstance(value)) {
            return type.cast(value);
        }
        if (type == String.class) {
            return type.cast(value.toString());
        }
        if (value instanceof Number && numberParse != null) {
            return numberParse.apply((Number)value);
        }
        if (value instanceof String && stringParse != null) {
            return stringParse.apply((String)value);
        }
        throw new ParsingException(Helpers.format("Cannot parse value for %s into type %s: %s instance of %s", key, type.getSimpleName(), value, value.getClass().getSimpleName()));
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DataObject)) {
            return false;
        }
        return ((DataObject)obj).toMap().equals(this.toMap());
    }

    public int hashCode() {
        return this.toMap().hashCode();
    }

    static {
        module.addAbstractTypeMapping(Map.class, HashMap.class);
        module.addAbstractTypeMapping(List.class, ArrayList.class);
        mapper.registerModule(module);
        mapType = mapper.getTypeFactory().constructRawMapType(HashMap.class);
    }
}

