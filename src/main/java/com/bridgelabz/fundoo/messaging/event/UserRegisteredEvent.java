package com.bridgelabz.fundoo.messaging.event;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisteredEvent implements Serializable {

    private Long userId;

    private String firstName;

    private String lastName;

    private String email;
}
