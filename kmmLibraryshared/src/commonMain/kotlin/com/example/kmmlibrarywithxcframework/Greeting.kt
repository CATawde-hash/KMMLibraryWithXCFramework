package com.example.kmmlibrarywithxcframework

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello I am Library with XCFramwork, ${platform.name}!"
    }

}