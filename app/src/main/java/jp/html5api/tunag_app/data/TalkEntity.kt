package jp.html5api.tunag_app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "t_talk")
data class TalkEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "room")
    val room: String,

    @ColumnInfo(name = "person")
    val person: String,

    @ColumnInfo(name = "dt_talked")
    val dt_talk: String,

    @ColumnInfo(name = "message")
    val message: String

)
