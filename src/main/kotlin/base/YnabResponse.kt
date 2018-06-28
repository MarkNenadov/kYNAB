package base

import khttp.responses.Response
import org.json.JSONObject

class YnabResponse(response: Response) {
    val errors: MutableList<JsonObject> = mutableListOf()
    var data: JsonObject? = null

    init {
        val jsonObject = JsonObject(response.jsonObject)

        if(jsonObject.hasKey("error")) {
            errors.add(jsonObject.getObject("error"))
        } else if(jsonObject.hasKey("data")) {
            data = jsonObject.getObject("data")
        } else {
            throw Exception("This strange. YnabResponse has neither an error nor data, I don't understand what is going on.")
        }

    }

    fun hasError(): Boolean {
        return this.errors.size > 0
    }
}
