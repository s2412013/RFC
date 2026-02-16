package net_alberto.rfc

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform