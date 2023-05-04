package com.example.phonebook.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDao {
//SELECT * FROM TABLENAME order by COLUMNNAME asc
    @Query("SELECT * FROM NoteDbModel ORDER by title asc")
    fun getAllSync(): List<NoteDbModel>

    @Query("SELECT * FROM NoteDbModel WHERE id IN (:noteIds)")
    fun getNotesByIdsSync(noteIds: List<Long>): List<NoteDbModel>

    @Query("SELECT * FROM NoteDbModel WHERE id LIKE :id")
    fun findByIdSync(id: Long): NoteDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(noteDbModel: NoteDbModel)

    @Insert
    fun insertAll(vararg noteDbModel: NoteDbModel)

    @Query("DELETE FROM NoteDbModel WHERE id LIKE :id")
    fun delete(id: Long)

    @Query("DELETE FROM NoteDbModel WHERE id IN (:noteIds)")
    fun delete(noteIds: List<Long>)
}