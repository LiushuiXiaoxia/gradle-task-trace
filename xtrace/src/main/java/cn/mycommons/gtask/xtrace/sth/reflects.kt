package cn.mycommons.gtask.xtrace.sth

import java.lang.reflect.Field
import java.util.concurrent.ConcurrentHashMap


fun Any.refObj(): ReflectObj {
    return ReflectObj(this)
}

class ReflectObj(private val obj: Any) {

    fun dField(field: String): ReflectObj? {
        return ReflectKit.getDeclaredField(obj, field)
    }


    fun field(field: String): ReflectObj? {
        return ReflectKit.getField(obj, field)
    }

    fun dField(field: String, value: Any?) {
        ReflectKit.setDeclaredField(obj, field, value)
    }

    fun value(): Any {
        return obj
    }

    fun fieldsMap(): Map<String, Any?> {
        val fs = ReflectKit.fields(obj)
        fs.sortBy { it.name }
        val map = mutableMapOf<String, Any?>()
        fs.forEach {
            map[it.name] = ReflectKit.getDeclaredField(obj, it.name)?.obj
        }
        return map
    }

    fun dFieldsMap(): Map<String, Any?> {
        val fs = ReflectKit.declaredFields(obj)
        fs.sortBy { it.name }
        val map = mutableMapOf<String, Any?>()
        fs.forEach {
            map[it.name] = ReflectKit.getDeclaredField(obj, it.name)?.obj
        }
        return map
    }

    override fun toString(): String {
        return "ReflectObj(obj=$obj)"
    }
}

object ReflectKit {

    private val fieldMap = ConcurrentHashMap<String, Field>()

    fun getDeclaredField(self: Any?, name: String): ReflectObj? {
        self ?: return null
        val field = getDeclaredField(self, name) ?: return null
        val v = field.get(self) ?: return null
        return ReflectObj(v)
    }

    fun getField(self: Any?, name: String): ReflectObj? {
        self ?: return null
        val field = getField(self, name) ?: return null
        val v = field.get(self) ?: return null
        return ReflectObj(v)
    }

    fun setDeclaredField(self: Any, name: String, value: Any?) {
        val f = getDeclaredField(self, name)
        f?.set(self, value)
    }

    fun setField(self: Any, name: String, value: Any?) {
        val f = getField(self, name)
        f?.set(self, value)
    }

    fun declaredFields(self: Any): Array<out Field> {
        return self.javaClass.declaredFields
    }

    fun fields(self: Any): Array<out Field> {
        return self.javaClass.fields
    }

    private fun getDeclaredField(self: Any, name: String): Field? {
        val key = self.javaClass.name + "#d#" + name
        if (fieldMap.contains(key)) {
            // log.info("get field from cache $key")
            return fieldMap[key]!!
        }

        return kotlin.runCatching {
            self.javaClass.getDeclaredField(name).apply {
                isAccessible = true
                fieldMap[key] = this
            }
        }.getOrNull()
    }

    private fun getField(self: Any, name: String): Field? {
        val key = self.javaClass.name + "#" + name
        if (fieldMap.contains(key)) {
            // log.info("get field from cache $key")
            return fieldMap[key]!!
        }

        return kotlin.runCatching {
            self.javaClass.getField(name).apply {
                isAccessible = true
                fieldMap[key] = this
            }
        }.getOrNull()
    }
}

