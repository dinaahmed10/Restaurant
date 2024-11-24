package com.Application.Restaurant.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class updatePersonDTO {
    private String username;
    private String email;
    private String bio;
}
