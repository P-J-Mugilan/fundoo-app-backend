package com.bridgelabz.fundoo.messaging.event;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetEvent implements Serializable {

    private String email;

    private String token;
}
