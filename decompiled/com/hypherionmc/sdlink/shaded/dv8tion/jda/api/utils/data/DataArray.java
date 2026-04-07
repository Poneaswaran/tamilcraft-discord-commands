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
import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.utils.data.DataObject;
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
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.databind.type.CollectionType;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataArray
implements Iterable<Object>,
SerializableArray {
    private static final Logger log = LoggerFactory.getLogger(DataObject.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final SimpleModule module = new SimpleModule();
    private static final CollectionType listType;
    protected final List<Object> data;

    protected DataArray(List<Object> data) {
        this.data = data;
    }

    @Nonnull
    public static DataArray empty() {
        return new DataArray(new ArrayList<Object>());
    }

    @Nonnull
    public static DataArray fromCollection(@Nonnull Collection<?> col) {
        return DataArray.empty().addAll(col);
    }

    @Nonnull
    public static DataArray fromJson(@Nonnull String json) {
        try {
            return new DataArray((List)mapper.readValue(json, (JavaType)listType));
        }
        catch (IOException e) {
            throw new ParsingException(e);
        }
    }

    @Nonnull
    public static DataArray fromJson(@Nonnull InputStream json) {
        try {
            return new DataArray((List)mapper.readValue(json, (JavaType)listType));
        }
        catch (IOException e) {
            throw new ParsingException(e);
        }
    }

    @Nonnull
    public static DataArray fromJson(@Nonnull Reader json) {
        try {
            return new DataArray((List)mapper.readValue(json, (JavaType)listType));
        }
        catch (IOException e) {
            throw new ParsingException(e);
        }
    }

    @Nonnull
    public static DataArray fromETF(@Nonnull byte[] data) {
        Checks.notNull(data, "Data");
        try {
            List<Object> list = ExTermDecoder.unpackList(ByteBuffer.wrap(data));
            return new DataArray(list);
        }
        catch (Exception ex) {
            log.error("Failed to parse ETF data {}", (Object)Arrays.toString(data), (Object)ex);
            throw new ParsingException(ex);
        }
    }

    public boolean isNull(int index) {
        return index >= this.length() || this.data.get(index) == null;
    }

    public boolean isType(int index, @Nonnull DataType type) {
        return type.isType(this.data.get(index));
    }

    public int length() {
        return this.data.size();
    }

    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    @Nonnull
    public DataObject getObject(int index) {
        Map child = null;
        try {
            child = this.get(Map.class, index);
        }
        catch (ClassCastException ex) {
            log.error("Unable to extract child data", (Throwable)ex);
        }
        if (child == null) {
            throw this.valueError(index, "DataObject");
        }
        return new DataObject(child);
    }

    @Nonnull
    public DataArray getArray(int index) {
        List child = null;
        try {
            child = this.get(List.class, index);
        }
        catch (ClassCastException ex) {
            log.error("Unable to extract child data", (Throwable)ex);
        }
        if (child == null) {
            throw this.valueError(index, "DataArray");
        }
        return new DataArray(child);
    }

    @Nonnull
    public String getString(int index) {
        String value = this.get(String.class, index, UnaryOperator.identity(), String::valueOf);
        if (value == null) {
            throw this.valueError(index, "String");
        }
        return value;
    }

    @Contract(value="_, !null -> !null")
    public String getString(int index, @Nullable String defaultValue) {
        String value = this.get(String.class, index, UnaryOperator.identity(), String::valueOf);
        return value == null ? defaultValue : value;
    }

    public boolean getBoolean(int index) {
        return this.getBoolean(index, false);
    }

    public boolean getBoolean(int index, boolean defaultValue) {
        Boolean value = this.get(Boolean.class, index, Boolean::parseBoolean, null);
        return value == null ? defaultValue : value;
    }

    public int getInt(int index) {
        Integer value = this.get(Integer.class, index, Integer::parseInt, Number::intValue);
        if (value == null) {
            throw this.valueError(index, "int");
        }
        return value;
    }

    public int getInt(int index, int defaultValue) {
        Integer value = this.get(Integer.class, index, Integer::parseInt, Number::intValue);
        return value == null ? defaultValue : value;
    }

    public int getUnsignedInt(int index) {
        Integer value = this.get(Integer.class, index, Integer::parseUnsignedInt, Number::intValue);
        if (value == null) {
            throw this.valueError(index, "unsigned int");
        }
        return value;
    }

    public int getUnsignedInt(int index, int defaultValue) {
        Integer value = this.get(Integer.class, index, Integer::parseUnsignedInt, Number::intValue);
        return value == null ? defaultValue : value;
    }

    public long getLong(int index) {
        Long value = this.get(Long.class, index, Long::parseLong, Number::longValue);
        if (value == null) {
            throw this.valueError(index, "long");
        }
        return value;
    }

    public long getLong(int index, long defaultValue) {
        Long value = this.get(Long.class, index, Long::parseLong, Number::longValue);
        return value == null ? defaultValue : value;
    }

    public long getUnsignedLong(int index) {
        Long value = this.get(Long.class, index, Long::parseUnsignedLong, Number::longValue);
        if (value == null) {
            throw this.valueError(index, "unsigned long");
        }
        return value;
    }

    @Nonnull
    public OffsetDateTime getOffsetDateTime(int index) {
        OffsetDateTime value = this.getOffsetDateTime(index, null);
        if (value == null) {
            throw this.valueError(index, "OffsetDateTime");
        }
        return value;
    }

    @Contract(value="_, !null -> !null")
    public OffsetDateTime getOffsetDateTime(int index, @Nullable OffsetDateTime defaultValue) {
        OffsetDateTime value;
        try {
            value = this.get(OffsetDateTime.class, index, OffsetDateTime::parse, null);
        }
        catch (DateTimeParseException e) {
            String reason = "Cannot parse value for index %d into an OffsetDateTime object. Try double checking that %s is a valid ISO8601 timestamp";
            throw new ParsingException(String.format(reason, index, e.getParsedString()));
        }
        return value == null ? defaultValue : value;
    }

    public long getUnsignedLong(int index, long defaultValue) {
        Long value = this.get(Long.class, index, Long::parseUnsignedLong, Number::longValue);
        return value == null ? defaultValue : value;
    }

    public double getDouble(int index) {
        Double value = this.get(Double.class, index, Double::parseDouble, Number::doubleValue);
        if (value == null) {
            throw this.valueError(index, "double");
        }
        return value;
    }

    public double getDouble(int index, double defaultValue) {
        Double value = this.get(Double.class, index, Double::parseDouble, Number::doubleValue);
        return value == null ? defaultValue : value;
    }

    @Nonnull
    public DataArray add(@Nullable Object value) {
        if (value instanceof SerializableData) {
            this.data.add(((SerializableData)value).toData().data);
        } else if (value instanceof SerializableArray) {
            this.data.add(((SerializableArray)value).toDataArray().data);
        } else {
            this.data.add(value);
        }
        return this;
    }

    @Nonnull
    public DataArray addAll(@Nonnull Collection<?> values) {
        values.forEach(this::add);
        return this;
    }

    @Nonnull
    public DataArray addAll(@Nonnull DataArray array) {
        return this.addAll(array.data);
    }

    @Nonnull
    public DataArray insert(int index, @Nullable Object value) {
        if (value instanceof SerializableData) {
            this.data.add(index, ((SerializableData)value).toData().data);
        } else if (value instanceof SerializableArray) {
            this.data.add(index, ((SerializableArray)value).toDataArray().data);
        } else {
            this.data.add(index, value);
        }
        return this;
    }

    @Nonnull
    public DataArray remove(int index) {
        this.data.remove(index);
        return this;
    }

    @Nonnull
    public DataArray remove(@Nullable Object value) {
        this.data.remove(value);
        return this;
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
    public List<Object> toList() {
        return this.data;
    }

    private ParsingException valueError(int index, String expectedType) {
        return new ParsingException("Unable to resolve value at " + index + " to type " + expectedType + ": " + this.data.get(index));
    }

    @Nullable
    private <T> T get(@Nonnull Class<T> type, int index) {
        return this.get(type, index, null, null);
    }

    @Nullable
    private <T> T get(@Nonnull Class<T> type, int index, @Nullable Function<String, T> stringMapper, @Nullable Function<Number, T> numberMapper) {
        Object value;
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        Object object = value = index < this.data.size() ? this.data.get(index) : null;
        if (value == null) {
            return null;
        }
        if (type.isInstance(value)) {
            return type.cast(value);
        }
        if (type == String.class) {
            return type.cast(value.toString());
        }
        if (stringMapper != null && value instanceof String) {
            return stringMapper.apply((String)value);
        }
        if (numberMapper != null && value instanceof Number) {
            return numberMapper.apply((Number)value);
        }
        throw new ParsingException(Helpers.format("Cannot parse value for index %d into type %s: %s instance of %s", index, type.getSimpleName(), value, value.getClass().getSimpleName()));
    }

    @Override
    @Nonnull
    public Iterator<Object> iterator() {
        return this.data.iterator();
    }

    @Nonnull
    public <T> Stream<T> stream(BiFunction<? super DataArray, Integer, ? extends T> mapper) {
        return IntStream.range(0, this.length()).mapToObj(index -> mapper.apply(this, index));
    }

    @Override
    @Nonnull
    public DataArray toDataArray() {
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataArray)) {
            return false;
        }
        DataArray objects = (DataArray)o;
        return Objects.equals(this.data, objects.data);
    }

    public int hashCode() {
        return Objects.hash(this.data);
    }

    static {
        module.addAbstractTypeMapping(Map.class, HashMap.class);
        module.addAbstractTypeMapping(List.class, ArrayList.class);
        mapper.registerModule(module);
        listType = mapper.getTypeFactory().constructRawCollectionType(ArrayList.class);
    }
}

