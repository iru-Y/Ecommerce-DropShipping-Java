package com.delivery.trizi.trizi.infra.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("IMAGES")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StorageModel {
    @Id
    private String id;
}
