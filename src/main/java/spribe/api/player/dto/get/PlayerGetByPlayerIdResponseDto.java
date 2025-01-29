package spribe.api.player.dto.get;

import lombok.Data;

@Data
public class PlayerGetByPlayerIdResponseDto {
    private Integer age;
    private String gender;
    private Integer id;
    private String login;
    private String password;
    private String role;
    private String screenName;
}
