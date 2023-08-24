package com.delivery.trizi.trizi.domain.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
@Document(value = "MEDIA")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Media implements Serializable {
    @Id
    private String id;
    private String title;
    private Binary data;

    public Media(String title) {
        this.title = title;
    }
}
