package spribe.api.player.dto.update;

import lombok.Data;

@Data
public class PlayerUpdateResponseDto {
    private Integer age;
    private String gender;
    private Integer id;
    private String login;
    private String role;
    private String screenName;
}
