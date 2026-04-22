package tamilcraft.discordcommands

import java.util.*
import java.lang.reflect.Method
import java.util.Locale

object ReflectionHelpers {
    fun readStaticField(clazz: Class<*>, fieldName: String): Any? {
        return try {
            val field = clazz.getField(fieldName)
            field.get(null)
        } catch (_: Exception) {
            try {
                val field = clazz.getDeclaredField(fieldName)
                field.isAccessible = true
                field.get(null)
            } catch (_: Exception) {
                null
            }
        }
    }

    fun readField(target: Any, fieldName: String): Any? {
        return try {
            val field = target.javaClass.getField(fieldName)
            field.get(target)
        } catch (_: Exception) {
            try {
                val field = target.javaClass.getDeclaredField(fieldName)
                field.isAccessible = true
                field.get(target)
            } catch (_: Exception) {
                null
            }
        }
    }

    fun callNoArg(target: Any, methodNames: List<String>): Any? {
        for (name in methodNames) {
            val value = call(target, name, emptyArray())
            if (value != null) {
                return value
            }
        }
        return null
    }

    fun callSingleArg(target: Any, methodNames: List<String>, arg: Any): Any? {
        for (name in methodNames) {
            val value = call(target, name, arrayOf(arg))
            if (value != null) {
                return value
            }
        }
        return null
    }

    fun callTwoArgs(target: Any, methodNames: List<String>, arg1: Any, arg2: Any): Any? {
        for (name in methodNames) {
            val value = call(target, name, arrayOf(arg1, arg2))
            if (value != null) {
                return value
            }
        }
        return null
    }

    private fun call(target: Any, methodName: String, args: Array<Any>): Any? {
        val isClassTarget = target is Class<*>
        val targetClass = if (isClassTarget) target else target.javaClass
        val methods = targetClass.methods.filter { method ->
            method.name == methodName && method.parameterCount == args.size
        }

        for (method in methods) {
            try {
                val invokeTarget = if (isClassTarget) null else target
                if (args.isEmpty()) {
                    return method.invoke(invokeTarget)
                }

                val compatible = method.parameterTypes.indices.all { index ->
                    isCompatible(method.parameterTypes[index], args[index].javaClass)
                }
                if (compatible) {
                    return method.invoke(invokeTarget, *args)
                }
            } catch (_: Exception) {
                continue
            }
        }

        return null
    }

    fun extractEntries(value: Any?): List<Any> {
        if (value == null) {
            return emptyList()
        }

        if (value is Iterable<*>) {
            return value.filterNotNull()
        }
        if (value is Array<*>) {
            return value.filterNotNull()
        }
        if (value is Iterator<*>) {
            return value.asSequence().filterNotNull().toList()
        }
        if (value is Map<*, *>) {
            return value.values.filterNotNull()
        }

        val fromMethod = callNoArg(value, listOf("toList", "toGappyList", "getSlots", "values", "all", "stream"))
        if (fromMethod != null && fromMethod !== value) {
            return extractEntries(fromMethod)
        }

        return emptyList()
    }

    fun extractNumber(value: Any?): Int? {
        return when (value) {
            is Number -> value.toInt()
            is String -> value.toIntOrNull()
            else -> null
        }
    }

    fun extractBoolean(value: Any?): Boolean? {
        return when (value) {
            is Boolean -> value
            is String -> value.toBooleanStrictOrNull()
            else -> null
        }
    }

    fun extractDisplayName(value: Any?): String? {
        if (value == null) {
            return null
        }

        if (value is CharSequence) {
            return value.toString().trim().takeIf { it.isNotBlank() }
        }

        val byMethod = callNoArg(value, listOf("getString", "getName", "name", "toString"))
        if (byMethod is CharSequence) {
            val text = byMethod.toString().trim()
            if (text.isNotBlank()) {
                return text
            }
        }

        return null
    }

    fun extractOptionalValue(value: Any?): Any? {
        if (value == null) {
            return null
        }

        return try {
            val optionalClass = Class.forName("java.util.Optional")
            if (!optionalClass.isInstance(value)) {
                return null
            }

            val isPresent = optionalClass.getMethod("isPresent").invoke(value) as? Boolean ?: false
            if (!isPresent) {
                null
            } else {
                optionalClass.getMethod("get").invoke(value)
            }
        } catch (_: Exception) {
            null
        }
    }

    fun asMap(value: Any?): Map<Any, Any> {
        if (value !is Map<*, *>) {
            return emptyMap()
        }
        return value.entries
            .filter { it.key != null && it.value != null }
            .associate { entry -> entry.key!! to entry.value!! }
    }

    private fun isCompatible(parameterType: Class<*>, argumentType: Class<*>): Boolean {
        if (parameterType.isAssignableFrom(argumentType)) {
            return true
        }

        if (!parameterType.isPrimitive) {
            return false
        }

        return when (parameterType) {
            java.lang.Boolean.TYPE -> argumentType == java.lang.Boolean::class.java
            java.lang.Integer.TYPE -> argumentType == java.lang.Integer::class.java
            java.lang.Long.TYPE -> argumentType == java.lang.Long::class.java
            java.lang.Double.TYPE -> argumentType == java.lang.Double::class.java
            java.lang.Float.TYPE -> argumentType == java.lang.Float::class.java
            java.lang.Short.TYPE -> argumentType == java.lang.Short::class.java
            java.lang.Byte.TYPE -> argumentType == java.lang.Byte::class.java
            java.lang.Character.TYPE -> argumentType == java.lang.Character::class.java
            else -> false
        }
    }
}
