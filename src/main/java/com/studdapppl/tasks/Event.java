package com.studdapppl.tasks;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;
    private String eventName;
    private String date;
    private String time;

    public Event(String eventName, String date, String time) {
        this.eventName = eventName;
        this.date = date;
        this.time = time;
    }
}
