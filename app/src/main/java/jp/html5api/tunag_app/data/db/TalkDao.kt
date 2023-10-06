package jp.html5api.tunag_app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import jp.html5api.tunag_app.data.TalkEntity

@Dao
interface TalkDao {

    @Query("SELECT * FROM t_talk order by id limit 100")
    fun getTalk(): MutableList<TalkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(talkEntity: TalkEntity)
}