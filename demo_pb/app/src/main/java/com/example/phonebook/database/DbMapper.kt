package com.example.phonebook.database

import com.example.phonebook.domain.model.ColorModel
import com.example.phonebook.domain.model.NEW_NOTE_ID
import com.example.phonebook.domain.model.NoteModel
import com.example.phonebook.domain.model.TagModel

class DbMapper {
    // Create list of NoteModels by pairing each note with a color
    fun mapNotes(
        noteDbModels: List<NoteDbModel>,
        colorDbModels: Map<Long, ColorDbModel>,
        tagDbModels: Map<Long, TagDbModel>
    ): List<NoteModel> = noteDbModels.map {
        val colorDbModel = colorDbModels[it.colorId]
            ?: throw RuntimeException("Color for colorId: ${it.colorId} was not found. Make sure that all colors are passed to this method")
        val tagDbModel = tagDbModels[it.tagId]
            ?: throw RuntimeException("Color for colorId: ${it.tagId} was not found.")
        mapNote(it, colorDbModel, tagDbModel)
    }

    // convert NoteDbModel to NoteModel

    private fun mapNote(noteDbModel: NoteDbModel, colorDbModel: ColorDbModel, tagDbModel: TagDbModel): NoteModel {
        val color = mapColor(colorDbModel)
        val tag = mapTag(tagDbModel)
        return with(noteDbModel) { NoteModel(id, title, content, color, tag) }
    }

    // convert list of ColorDdModels to list of ColorModels
    fun mapColors(colorDbModels: List<ColorDbModel>): List<ColorModel> =
        colorDbModels.map { mapColor(it) }

    // convert ColorDbModel to ColorModel
    private fun mapColor(colorDbModel: ColorDbModel): ColorModel =
        with(colorDbModel) { ColorModel(id, name, hex) }

    fun mapTags(tagDbModels: List<TagDbModel>): List<TagModel> =
        tagDbModels.map { mapTag(it) }

    // convert ColorDbModel to ColorModel
    private fun mapTag(tagDbModel: TagDbModel): TagModel =
        with(tagDbModel) { TagModel(id, nameTag) }

    // convert NoteModel back to NoteDbModel
    fun mapDbNote(note: NoteModel): NoteDbModel =
        with(note) {
            if (id == NEW_NOTE_ID)
                NoteDbModel(
                    title = title,
                    content = content,
                    colorId = color.id,
                    tagId = tag.id,
                    isInTrash = false
                )
            else
                NoteDbModel(id, title, content, color.id, tag.id,false)
        }
}