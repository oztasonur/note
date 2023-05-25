package com.onuroztas.note;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(NoteController.class)
class NoteControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private NoteService noteService;


    @Test
    public void testGetAllNotes() throws Exception {
        // Set up mock behavior
        List<Note> sampleNotes = Arrays.asList(new Note(null, "Title 1", "Content 1"), new Note(null,"Title 2", "Content 2"));
        Mockito.when(noteService.allNotes()).thenReturn(sampleNotes);

        // Perform GET request and validate response
        mockMvc.perform(MockMvcRequestBuilders.get("/notes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{'title':'Title 1','content':'Content 1'}, {'title':'Title 2','content':'Content 2'}]"));
    }

    @Test
    public void testAddNote() throws Exception {
        // Set up mock behavior
        Note savedNote = new Note(null, "Test title", "Test content");
        Mockito.when(noteService.saveNote(Mockito.any(Note.class))).thenReturn(savedNote);

        // Create a new Note object
        Note newNote = new Note(null, "POST Title", "POST Content");

        // Convert the Note object to JSON
        String noteJson = "{\"title\":\"Test title\",\"content\":\"Test content\"}";

        // Perform POST request and validate response
        mockMvc.perform(MockMvcRequestBuilders.post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noteJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Test content"));
    }


    @Test
    public void testDeleteNoteById() throws Exception {
        ObjectId noteId = new ObjectId();
        Mockito.when(noteService.deleteNoteById(noteId)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/notes/{id}", noteId.toHexString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        Mockito.verify(noteService, Mockito.times(1)).deleteNoteById(noteId);
    }

    @Test
    public void testUpdateNoteById() throws Exception {
        // Set up mock behavior
        Note updatedNote = new Note(ObjectId.get().toString(), "Updated title", "Updated content");
        Mockito.when(noteService.updateNoteById(Mockito.any(ObjectId.class), Mockito.any(Note.class))).thenReturn(updatedNote);

        // Create a new Note object
        Note newNote = new Note(null, "Test title", "Test content");

        // Convert the Note object to JSON
        String noteJson = "{\"title\":\"Updated title\",\"content\":\"Updated content\"}";

        // Perform PUT request and validate response
        mockMvc.perform(MockMvcRequestBuilders.put("/notes/{id}", ObjectId.get())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noteJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Updated title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Updated content"));
    }



}