/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.json;

import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonLocation;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.JsonParser;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.ObjectCodec;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.StreamReadCapability;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.base.ParserBase;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.io.CharTypes;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.io.IOContext;
import com.hypherionmc.sdlink.shaded.fasterxml.jackson.core.util.JacksonFeatureSet;
import java.io.IOException;

public abstract class JsonParserBase
extends ParserBase {
    protected static final int FEAT_MASK_TRAILING_COMMA = JsonParser.Feature.ALLOW_TRAILING_COMMA.getMask();
    protected static final int FEAT_MASK_LEADING_ZEROS = JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS.getMask();
    protected static final int FEAT_MASK_NON_NUM_NUMBERS = JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS.getMask();
    protected static final int FEAT_MASK_ALLOW_MISSING = JsonParser.Feature.ALLOW_MISSING_VALUES.getMask();
    protected static final int FEAT_MASK_ALLOW_SINGLE_QUOTES = JsonParser.Feature.ALLOW_SINGLE_QUOTES.getMask();
    protected static final int FEAT_MASK_ALLOW_UNQUOTED_NAMES = JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES.getMask();
    protected static final int FEAT_MASK_ALLOW_JAVA_COMMENTS = JsonParser.Feature.ALLOW_COMMENTS.getMask();
    protected static final int FEAT_MASK_ALLOW_YAML_COMMENTS = JsonParser.Feature.ALLOW_YAML_COMMENTS.getMask();
    protected static final int[] INPUT_CODES_LATIN1 = CharTypes.getInputCodeLatin1();
    protected static final int[] INPUT_CODES_UTF8 = CharTypes.getInputCodeUtf8();
    protected ObjectCodec _objectCodec;

    protected JsonParserBase(IOContext ioCtxt, int features, ObjectCodec codec) {
        super(ioCtxt, features);
        this._objectCodec = codec;
    }

    @Override
    public ObjectCodec getCodec() {
        return this._objectCodec;
    }

    @Override
    public void setCodec(ObjectCodec c) {
        this._objectCodec = c;
    }

    @Override
    public final JacksonFeatureSet<StreamReadCapability> getReadCapabilities() {
        return JSON_READ_CAPABILITIES;
    }

    @Override
    public JsonParser.NumberTypeFP getNumberTypeFP() throws IOException {
        return JsonParser.NumberTypeFP.UNKNOWN;
    }

    @Override
    public abstract JsonLocation currentLocation();

    @Override
    public abstract JsonLocation currentTokenLocation();

    @Override
    protected abstract JsonLocation _currentLocationMinusOne();

    @Override
    @Deprecated
    public final JsonLocation getCurrentLocation() {
        return this.currentLocation();
    }

    @Override
    @Deprecated
    public final JsonLocation getTokenLocation() {
        return this.currentTokenLocation();
    }
}

