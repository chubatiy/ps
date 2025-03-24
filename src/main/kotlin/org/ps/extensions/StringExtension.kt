package org.ps.extensions

fun String.getMasked(lastDigits: Int = 4): String {
    return if (this.length >= lastDigits) {
        "${this.first()}***${this.takeLast(lastDigits)}"
    } else {
        this
    }
}