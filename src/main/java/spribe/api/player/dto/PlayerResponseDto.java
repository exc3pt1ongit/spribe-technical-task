package spribe.api.player.dto;

import lombok.Data;

@Data
public class PlayerResponseDto {
    private Integer age;
    private String gender;
    private Integer id;
    private String login;
    private String password;
    private String role;
    private String screenName;
}
