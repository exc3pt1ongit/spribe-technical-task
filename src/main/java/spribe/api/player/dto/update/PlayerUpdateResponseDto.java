package spribe.api.player.dto.update;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PlayerUpdateResponseDto {
    private Integer age;
    private String gender;
    private Integer id;
    private String login;
    private String role;
    private String screenName;
}
