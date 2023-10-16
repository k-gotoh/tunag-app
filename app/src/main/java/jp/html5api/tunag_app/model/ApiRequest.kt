package jp.html5api.tunag_app.model


data class ApiRequest (
    val user: String,
    val pass: String,
    var token: String,
    val name: String,
)
