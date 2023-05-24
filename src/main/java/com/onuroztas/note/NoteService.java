package com.onuroztas.note;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public List<Note> allNotes() {
        return noteRepository.findAll();
    }

    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }

    public Note getNoteById(ObjectId id) {
        return noteRepository.findById(id).orElse(null);
    }

    public boolean deleteNoteById(ObjectId id) {
        Optional<Note> note = noteRepository.findById(id);
        if (note.isPresent()) {
            noteRepository.delete(note.get());
            return true;
        } else {
            return false;
        }
    }
    public void deleteAllNotes() {
        noteRepository.deleteAll();
    }

    public Note updateNoteById(ObjectId id, Note updatedNote) {
        Note note = getNoteById(id);
        if (note != null) {
            note.setTitle(updatedNote.getTitle());
            note.setContent(updatedNote.getContent());
            return noteRepository.save(note);
        }
        return null;
    }

}
