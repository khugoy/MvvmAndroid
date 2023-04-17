package com.sapient.demoapp.core

import okio.buffer
import okio.source
import java.io.IOException
import java.nio.charset.StandardCharsets

class MockFileReader {

    @Throws(IOException::class)
    fun getResponseFromJson(fileName: String?): String? {
        val inputStream = javaClass.classLoader?.getResourceAsStream(
            "mockData/$fileName"
        )
        val source = inputStream?.source()?.buffer()
        return source?.readString(StandardCharsets.UTF_8)
    }

    companion object {
         val ID = 1
         val detail_fileName = "/characterDetail.json"
         val list_fileName = "/characterList.json"
         val ALIVE = "Alive"
         val ONE = 1
         val TWO = 2
         val NETWORK_ERROR = "Network Error"
    }
}

