package jp.html5api.tunag_app.model

data class ChangePasswordRequest (
    val user: String,
    val oldPassword: String,
    val newPassword: String

)