/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.hypherionmc.sdlink.shaded.kotlin;

import com.hypherionmc.sdlink.shaded.kotlin.Metadata;
import com.hypherionmc.sdlink.shaded.kotlin.SinceKotlin;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.AnnotationRetention;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.AnnotationTarget;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.Retention;
import com.hypherionmc.sdlink.shaded.kotlin.annotation.Target;
import com.hypherionmc.sdlink.shaded.kotlin.enums.EnumEntries;
import com.hypherionmc.sdlink.shaded.kotlin.enums.EnumEntriesKt;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import org.jetbrains.annotations.NotNull;

@Target(allowedTargets={AnnotationTarget.ANNOTATION_CLASS})
@Retention(value=AnnotationRetention.BINARY)
@java.lang.annotation.Retention(value=RetentionPolicy.CLASS)
@java.lang.annotation.Target(value={ElementType.ANNOTATION_TYPE})
@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0087\u0002\u0018\u00002\u00020\u0001:\u0001\bB\u0014\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005R\u000f\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0006R\u000f\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\u0007\u00a8\u0006\t"}, d2={"Lcom/hypherionmc/sdlink/shaded/kotlin/RequiresOptIn;", "", "message", "", "level", "Lcom/hypherionmc/sdlink/shaded/kotlin/RequiresOptIn$Level;", "()Lkotlin/RequiresOptIn$Level;", "()Ljava/lang/String;", "Level", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"})
@SinceKotlin(version="1.3")
public @interface RequiresOptIn {
    public String message() default "";

    public Level level() default Level.ERROR;

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lcom/hypherionmc/sdlink/shaded/kotlin/RequiresOptIn$Level;", "", "(Ljava/lang/String;I)V", "WARNING", "ERROR", "com.hypherionmc.sdlink.shaded.kotlin-stdlib"})
    public static final class Level
    extends Enum<Level> {
        public static final /* enum */ Level WARNING = new Level();
        public static final /* enum */ Level ERROR = new Level();
        private static final /* synthetic */ Level[] $VALUES;
        private static final /* synthetic */ EnumEntries $ENTRIES;

        public static Level[] values() {
            return (Level[])$VALUES.clone();
        }

        public static Level valueOf(String value) {
            return Enum.valueOf(Level.class, value);
        }

        @NotNull
        public static EnumEntries<Level> getEntries() {
            return $ENTRIES;
        }

        static {
            $VALUES = levelArray = new Level[]{Level.WARNING, Level.ERROR};
            $ENTRIES = EnumEntriesKt.enumEntries((Enum[])$VALUES);
        }
    }
}

