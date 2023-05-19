package com.onuroztas.note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    @Id
    private ObjectId id;
    private String title;
    private String content;

    public Note(String content) {
        this.title = "Test title";
        this.content = content;
    }
}