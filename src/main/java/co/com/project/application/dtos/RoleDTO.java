package co.com.project.application.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class RoleDTO {
    private Long id;
    private String name;
}