package jp.html5api.tunag_app.model

data class PasswordChange (
    val isChange: Boolean,
    val oldPassword: String,
    val newPassword: String
)