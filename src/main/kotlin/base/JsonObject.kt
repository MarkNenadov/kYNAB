package base

import org.json.JSONObject

class JsonObject ( val wrappedJSONObject : JSONObject ) {
    fun getString( key: String ): String {
        return wrappedJSONObject.getString( key )
    }

    fun getInt( key: String ): Int {
        return wrappedJSONObject.getInt( key )
    }

    fun getArray( key: String ): List<JsonObject> {
        val jsonObjectArray : MutableList<JsonObject> = mutableListOf()
        for( arrayItem in wrappedJSONObject.getJSONArray( key ) ) {
            jsonObjectArray.add( JsonObject( arrayItem as JSONObject ) )
        }

        return jsonObjectArray
    }

    fun isNull( key: String ): Boolean {
        return wrappedJSONObject.isNull(key)
    }

    fun getObject( key: String ): JsonObject {
        return JsonObject( wrappedJSONObject.getJSONObject( key ) )
    }

    fun hasKey( key: String ): Boolean {
        return wrappedJSONObject.has( key )
    }

    override fun toString() : String {
        return wrappedJSONObject.toString()
    }
}