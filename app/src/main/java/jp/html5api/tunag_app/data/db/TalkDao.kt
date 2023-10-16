package jp.html5api.tunag_app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import jp.html5api.tunag_app.data.FriendEntity
import jp.html5api.tunag_app.data.TalkEntity

@Dao
interface TalkDao {

    @Query("SELECT * FROM t_talk order by id limit 100")
    fun getTalk(): MutableList<TalkEntity>

    @Query("SELECT * FROM t_talk where room = :room order by id limit 100")
    fun getTalkByRoom(room: String): MutableList<TalkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(talkEntity: TalkEntity)

    @Query("SELECT * FROM m_friend order by id")
    fun getFriends(): MutableList<FriendEntity>

    @Query("SELECT * FROM m_friend where name = :name")
    fun getFriendsByName(name: String): MutableList<FriendEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(friendEntity: FriendEntity)

    @Query("SELECT * FROM m_friend where id = :friendId")
    fun getFriendById(friendId: Int): FriendEntity

    @Query("update m_friend set name = :name where user = :user")
    fun updateName(user: String, name: String): Void
}