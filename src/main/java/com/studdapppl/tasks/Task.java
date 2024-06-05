package com.studdapppl.tasks;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Document(collection = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Task {
    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;
    private String title;
    private String startDate;
    private String endDate;
    private String isCompleted;

    public Task(String title, String startDate, String endDate, String isCompleted) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isCompleted = isCompleted;
    }

}