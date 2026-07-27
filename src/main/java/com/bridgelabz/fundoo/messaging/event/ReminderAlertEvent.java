package com.bridgelabz.fundoo.messaging.event;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReminderAlertEvent implements Serializable {
    private Long reminderId;
    private Long noteId;
    private String title;
    private String ownerEmail;
    private String remindAt;
}
