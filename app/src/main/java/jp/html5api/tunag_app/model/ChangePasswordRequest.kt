package jp.html5api.tunag_app.model

data class EditData (
    val procType:EditType,
    val oldPassword: String,
    val newPassword: String,
    val newName: String
)
enum class EditType {
    NONE,
    CHANGE_PASSWORD,
    CHANGE_NAME
}