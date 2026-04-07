/*
 * Decompiled with CFR 0.152.
 */
package com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.annotation;

import com.hypherionmc.sdlink.shaded.dv8tion.jda.api.Permission;
import com.hypherionmc.sdlink.shaded.jagrosh.jdautilities.command.CooldownScope;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface JDACommand {
    public String[] name() default {"null"};

    public String help() default "no help available";

    public boolean guildOnly() default true;

    public String requiredRole() default "";

    public boolean ownerCommand() default false;

    public String arguments() default "";

    public Cooldown cooldown() default @Cooldown(value=0);

    public Permission[] botPermissions() default {};

    public Permission[] userPermissions() default {};

    public boolean useTopicTags() default true;

    public String[] children() default {};

    public boolean isHidden() default false;

    public Category category() default @Category(name="null", location=Category.class);

    @Target(value={ElementType.TYPE})
    @Retention(value=RetentionPolicy.RUNTIME)
    public static @interface Category {
        public String name();

        public Class<?> location();
    }

    @Target(value={ElementType.TYPE})
    @Retention(value=RetentionPolicy.RUNTIME)
    public static @interface Cooldown {
        public int value();

        public CooldownScope scope() default CooldownScope.USER;
    }

    @Target(value={ElementType.TYPE})
    @Retention(value=RetentionPolicy.RUNTIME)
    public static @interface Module {
        public String[] value();
    }
}

