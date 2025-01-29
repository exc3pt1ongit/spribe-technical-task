package spribe.api.player.dto.create;

import lombok.Data;

@Data
public class PlayerCreateResponseDto {
    private Integer age;
    private String gender;
    private Integer id;
    private String login;
    private String password;
    private String role;
    private String screenName;
}
