package com.opengl.learnkotling.ListContent

import Interval
import Note
import Notes
import android.icu.text.Transliterator
import android.util.Log
import android.util.Log.DEBUG
import java.util.HashMap

object NotesList {

    val ITEMS: MutableList<NoteItemElement> = ArrayList()
    val ITEM_MAP: MutableMap<String, NoteItemElement> = HashMap()

    init {
        var NOTE_LIST: MutableList<Note> = ArrayList()

        for (note in Notes.values()) {
            createListItem(Note(note, 0))
//            NOTE_LIST.add(Note(note, 0))
        }

    }

    private fun createListItem(item: Note) {
        var index = ITEMS.size.toString()

        val element = NoteItemElement(index, item)



        Log.d("ItemList", "Index of Creation: ${index.toString()} of ${element.toString()}\n")
        ITEM_MAP.put(element.id, element)
        ITEMS.add(element)
        ITEMS.get(ITEMS.lastIndex).content = buildDetails(index.toInt())
        ITEM_MAP.getValue(index).content = buildDetails(index.toInt())
    }

    private fun buildDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Note Intervals: ").append(position)
        for (interval in ITEM_MAP.getValue(position.toString()).details.intervals ) {
            builder.append("Note: ${interval.note.cipher}|${interval.note.extenseName} " +
                    "==> ${interval.interval} " +
                    "= Interval: ${interval.interval.tonalInterval}\n")
        }
        return builder.toString()
    }


    data class NoteItemElement(val id: String, val details: Note) {
        var content: String = ""

        override fun toString(): String = details.note.extenseName
    }
}