package com.example.kmmlibrarywithxcframework

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform