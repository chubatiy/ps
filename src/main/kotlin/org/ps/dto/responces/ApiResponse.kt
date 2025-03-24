package org.ps.dto.responces

class ApiResponse<T>(
    val data: T? = null,
    val success: Boolean,
    val error: String? = null
) {

    companion object {
        fun <T> success(data: T) = ApiResponse(
            data = data,
            success = true
        )

        fun error(message: String) = ApiResponse(
            data = null,
            error = message,
            success = false
        )
    }
}