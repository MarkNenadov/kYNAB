package com.pythonbyte.kynab.base

import khttp.get
import khttp.post

class YnabHttp {
    companion object {
        fun get(urlBase: String, serverKnowledgeNumber: Int = 0): YnabResponse {
            val url = "$urlBase&last_knowledge_of_server=$serverKnowledgeNumber"
            val response = YnabResponse(get(url = url))

            return if (response.hasError()) {
                print(response.errors[0].toString())
                throw Exception("Error connecting to YNAB $urlBase ${response.errors[0]}]")
            } else {
                response
            }
        }

        fun post(baseUrl: String, postData: String): YnabResponse {
            print(baseUrl)
            val response = YnabResponse(post(url = baseUrl, params = mapOf("transaction" to postData)))

            return if (response.hasError()) {
                print(response.errors[0].toString())
                throw Exception("Error connecting to YNAB $baseUrl ${response.errors[0]}]")
            } else {
                response
            }
        }
    }
}
