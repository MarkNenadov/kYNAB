package com.pythonbyte.kynab.base

import org.json.JSONObject

class JsonObject(val wrappedJSONObject: JSONObject) {
    fun getString(key: String): String {
        return if (isNull(key)) {
            ""
        } else {
            return wrappedJSONObject.getString(key)
        }
    }

    fun getInt(key: String) = wrappedJSONObject.getInt(key)

    fun getArray(key: String): List<JsonObject> {
        val jsonObjectArray: MutableList<JsonObject> = mutableListOf()

        if (hasKey(key)) {
            for (arrayItem in wrappedJSONObject.getJSONArray(key)) {
                jsonObjectArray.add(JsonObject(arrayItem as JSONObject))
            }
        }

        return jsonObjectArray
    }

    fun isNull(key: String) = wrappedJSONObject.isNull(key)

    fun getObject(key: String) = JsonObject(wrappedJSONObject.getJSONObject(key))

    fun hasKey(key: String) = wrappedJSONObject.has(key)

    override fun toString() = wrappedJSONObject.toString()

    fun getBoolean(key: String) = wrappedJSONObject.getBoolean(key)
}
