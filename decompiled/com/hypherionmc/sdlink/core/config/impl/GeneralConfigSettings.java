/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.libs.moonconfig.core.conversion.Path
 *  com.hypherionmc.craterlib.libs.moonconfig.core.conversion.SpecComment
 */
package com.hypherionmc.sdlink.core.config.impl;

import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.Path;
import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.SpecComment;
import com.hypherionmc.sdlink.core.config.SDLinkConfig;

public final class GeneralConfigSettings {
    @Path(value="enabled")
    @SpecComment(value="Should the mod be enabled or not")
    public boolean enabled = true;
    @Path(value="debugging")
    @SpecComment(value="Enable Additional Logging. Used for Fault Finding. WARNING: CAUSES LOG SPAM!")
    public boolean debugging = false;
    @Path(value="language")
    @SpecComment(value="The active language to use for built in messages. Defaults to en_us if a language is not found")
    public String language = "en_us";
    @Path(value="configVersion")
    @SpecComment(value="Internal version control. DO NOT TOUCH!")
    public int configVersion = SDLinkConfig.configVer;
}

