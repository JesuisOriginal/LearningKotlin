class Note (
        note: Notes,
        octave: Int,
) {
    var octave = octave
    var note = note
    var myPosition: Int
    var intervals: MutableList<Interval> = mutableListOf()

    init {

        myPosition = notesList.NoteList.indexOf(note) + 1
//        println("MyPos: " + (myPosition - 1) + "  Got: " + notesList.NoteList.get(myPosition - 1))
//        println("List Size: " + Notes.values().size)
//
//        println()
        var offset = 0
        var addingNote = note
        for (i in 0..13) {
            var lastIndex = intervals.toTypedArray().lastIndex
            if (intervals.size == 0) {
                addingNote = notesList.NoteList.get((i + myPosition) % 16)
//                println("adding note initing")
            }


            var addingInterval = Intervals.values().get(i)

//            println("i: " + i + " getting: " + ((i + myPosition)%14))


//            println("LastIndex: " + lastIndex)


            if (lastIndex > -1) {
//                println("Last Element: " + intervals.elementAt(lastIndex).note.toString() + "  equalNote: " + intervals.elementAt(lastIndex).note.equalNote + "  addinNote: " +  addingNote.toString() + "  addNote Cypher: " + addingNote.cipher + "  addNote Equal: " + addingNote.equalNote)
//                println("Equal? " + (intervals.elementAt(lastIndex).note.equalNote === addingNote.cipher || intervals.elementAt(lastIndex).note == addingNote) )

//                addingNote = notesList.NoteList.get(notesList.NoteList.indexOf(intervals.get(lastIndex).note) + 1)
//                println("indexOf(${intervals.get(lastIndex).note})")
//                println("This note ${addingNote.extenseName} ->  next note ${notesList.NoteList.get(notesList.NoteList.indexOf(addingNote) + 1)} ")

                if (intervals.elementAt(lastIndex).note.equalNote == addingNote.cipher || intervals.elementAt(lastIndex).note == addingNote) {
                    // Equivalent Note Already added
//                    println("Repeated Note Skipping with offset ${i + offset + myPosition}")

//                    addingNote = notesList.NoteList.get((i + 1 + myPosition ) % 16)
//                    addingInterval = Intervals.values().get(i)
//                    println("While Equal? " + (intervals.elementAt(lastIndex).note.equalNote === addingNote.cipher || intervals.elementAt(lastIndex).note == addingNote) )


                    while ((intervals.elementAt(lastIndex).note.equalNote === addingNote.cipher || intervals.elementAt(lastIndex).note == addingNote) ) {
                        offset += 1
//                        println("Repeated Note Skipping (AGAIN =_= < ...) ${offset + i + myPosition}")
                        addingNote = notesList.NoteList.get((i + offset + myPosition ) % 16)
//                        println("  addinNote: " +  addingNote.toString() + "  addNote Cypher: " + addingNote.cipher + "  addNote Equal: " + addingNote.equalNote)

                    }
                    if (addingInterval.tonalInterval == intervals.elementAt(lastIndex).interval.tonalInterval ) {
                        // if is equivalent intervals, pick last note and next interval
//                        println("Last Element: " + intervals.elementAt(lastIndex).note.toString() + "  equalNote: " + intervals.elementAt(lastIndex).note.equalNote + "  addinNote: " +  addingNote.toString() + "  addNote Cypher: " + addingNote.cipher + "  addNote Equal: " + addingNote.equalNote)

//                        println("Repeated Tonal Interval => Put same note")
                        intervals.add(Interval(intervals.elementAt(lastIndex).note, addingInterval))
//                        println(".add(Interval(${intervals.elementAt(lastIndex).note}, ${addingInterval}))")
//                        println("Jumpping to next note ===> ${addingNote.extenseName}".toUpperCase())

                        addingNote = notesList.NoteList.get(notesList.NoteList.indexOf(addingNote) + 1)
//                        println("\n Adding Note ${addingNote.extenseName}".toUpperCase())

                    } else {
//                        println("Last Element: " + intervals.elementAt(lastIndex).note.toString() + "  equalNote: " + intervals.elementAt(lastIndex).note.equalNote + "  addinNote: " +  addingNote.toString() + "  addNote Cypher: " + addingNote.cipher + "  addNote Equal: " + addingNote.equalNote)

                        intervals.add(Interval(addingNote, addingInterval))
//                        println(".add(Interval(" + addingNote.toString() + ", " + addingInterval.toString() + " ))")
                    }


                }
                else {
                    // Pick Next
//                    println("Picking Next => This note ${addingNote.extenseName} ->  next note ${notesList.NoteList.get((i + (offset + 1)+ myPosition ) % 16)} ")

                    intervals.add(Interval(addingNote, addingInterval))
//                    println(".add(Interval(${addingNote.extenseName} , ${Intervals.values().get(i).toString()} ))")

                }
            } else {
                // If List Empty, populate first
                intervals.add(Interval(addingNote, addingInterval))
//                println(".add(Interval(" + notesList.NoteList.get((i + myPosition ) % 16).toString() + ", " + Intervals.values().get(i).toString() + " ))")

            }
//            println()

        }
    }
}

class NotesList() {
    var NoteList : MutableList<Notes> = Notes.values().toMutableList()

}

var notesList = NotesList()

enum class Intervals(val intervalName: String, val tonalInterval: Double){
    SEGUNDA_MENOR("2-", 0.5),
    SEGUNDA_MAIOR("2", 1.0),
    TERCA_MENOR("3-", 1.5),
    TERCA_MAIOR("3", 2.0),
    QUARTA_JUSTA("4", 2.5),
    QUARTA_AUMENTADA("4+", 3.0),
    QUINNTA_DIMINUTA("5-", 3.0),
    QUINTA_JUSTA("5", 3.5),
    QUINTA_AUMENTADA("5+", 4.0),
    SEXTA_MENOR("6-",4.0),
    SEXTA_MAIOR("6", 4.5),
    SETIMA_MENOR("7", 5.0),
    SETIMA_MAIOR("7+", 5.5),
    OITAVA("8", 6.0)
}

data class Interval(var note: Notes, var interval: Intervals)

enum class Notes(val extenseName: String, val cipher: String, val toNextNote: Double,
                 val wholeNote: Boolean, val isAcidental: Boolean, val equalNote: String) {
    C("Dó", "C",1.0, true, false, "B#"),

    Cs("Dó Sustenido","C#", 0.5, false, true, "Db"),
    Db("Ré bemol","Db", 0.5, false, true, "C#"),

    D("Ré", "D", 1.0, true, false, "D"),

    Ds("Ré Sustenido","D#", 1.0, true, true, "Eb"),
    Eb("Mi bemol","Eb",   0.5, false, true, "D#"),

    E("Mi", "E", 0.5, false, false, "Fb"),
    F("Fá", "F",1.0, true, false, "E#"),

    Fs("Fá Sustenido", "F#", 0.5, true, true, "Gb"),
    Gb("Sol Bemol", "Gb", 0.5, true, true, "F#"),

    G("Sol", "G", 1.0, true, true, "G"),

    Gs("Sol Sustenido", "G#", 0.5, true, true, "Ab"),
    Ab("La Bemol", "Ab", 0.5, true, true, "G#"),


    A("Lá", "A",1.0, true, true, "A"),

    As("Lá  Sustenido", "A#", 0.5, true, true, "Bb"),
    Bb("Sí Bemol", "Bb", 0.5, false, true, "A#"),


    B("Si", "B",0.5, false, false, "Cb"),
}

//fun main() {
//    var myNote: Note = Note(Notes.C, 0)
//    println(myNote.note.extenseName)
//    for (interval in myNote.intervals) {
////         println("NoteName:" + interval.note.cipher.toString() + " Interval: " + interval.interval.toString() )
//
//        println(interval.note.extenseName + "  ${interval.interval}")
//    }
//}
