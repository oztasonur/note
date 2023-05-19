package com.onuroztas.note;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
}