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
public class MediaModel implements Serializable {
    @Id
    private String id;
    private String title;
    private String data;

    public MediaModel(String title) {
        this.title = title;
    }

}
