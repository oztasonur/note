package com.onuroztas.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        return new ResponseEntity<List<Note>>(noteService.allNotes(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Note> addNote(@RequestBody Note note) {
        Note savedNote = noteService.saveNote(note);
        return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
    }
}


