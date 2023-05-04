package com.example.phonebook.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.phonebook.domain.model.ColorModel
import com.example.phonebook.domain.model.NoteModel
import com.example.phonebook.domain.model.TagModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Repository(
    private val noteDao: NoteDao,
    private val colorDao: ColorDao,
    private val tagDao: TagDao,
    private val dbMapper: DbMapper
) {

    private val notesNotInTrashLiveData: MutableLiveData<List<NoteModel>> by lazy {
        MutableLiveData<List<NoteModel>>()
    }

    fun getAllNotesNotInTrash(): LiveData<List<NoteModel>> = notesNotInTrashLiveData

    private val notesInTrashLiveData: MutableLiveData<List<NoteModel>> by lazy {
        MutableLiveData<List<NoteModel>>()
    }

    init {
        initDatabase(this::updateNotesLiveData)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun initDatabase(postInitAction: () -> Unit) {
        GlobalScope.launch {
            // Prepopulate colors
            val colors = ColorDbModel.DEFAULT_COLORS.toTypedArray()
            val dbColors = colorDao.getAllSync()
            if (dbColors.isEmpty()) {
                colorDao.insertAll(*colors)

            }

            val tags = TagDbModel.DEFAULT_TAGS.toTypedArray()
            val dbTags = tagDao.getAllSync()
            if (dbTags.isEmpty()) {
                tagDao.insertAll(*tags)

            }

            val notes = NoteDbModel.DEFAULT_NOTES.toTypedArray()
            val dbNotes = noteDao.getAllSync()
            if (dbNotes.isEmpty()) {
                noteDao.insertAll(*notes)
            }

            postInitAction.invoke()
        }
    }

    // get list of working notes or deleted notes
    private fun getAllNotesDependingOnTrashStateSync(inTrash: Boolean): List<NoteModel> {
        val colorDbModels: Map<Long, ColorDbModel> = colorDao.getAllSync().associateBy { it.id }
        val tagDbModels: Map<Long, TagDbModel> = tagDao.getAllSync().associateBy { it.id }
        val dbNotes: List<NoteDbModel> =
            noteDao.getAllSync().filter { it.isInTrash == inTrash }
        return dbMapper.mapNotes(dbNotes, colorDbModels, tagDbModels)
    }

    fun insertNote(note: NoteModel) {
        noteDao.insert(dbMapper.mapDbNote(note))
        updateNotesLiveData()
    }

    fun moveNoteToTrash(noteId: Long) {
        val dbNote = noteDao.findByIdSync(noteId)
        val newDbNote = dbNote.copy(isInTrash = true)
        noteDao.insert(newDbNote)
        updateNotesLiveData()
    }

    fun getAllColors(): LiveData<List<ColorModel>> =
        Transformations.map(colorDao.getAll()) { dbMapper.mapColors(it) }

    fun getAllTags(): LiveData<List<TagModel>> =
        Transformations.map(tagDao.getAll()) { dbMapper.mapTags(it) }

    private fun updateNotesLiveData() {
        notesNotInTrashLiveData.postValue(getAllNotesDependingOnTrashStateSync(false))
        notesInTrashLiveData.postValue(getAllNotesDependingOnTrashStateSync(true))
    }
}