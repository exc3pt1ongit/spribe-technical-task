package spribe.api.player.dto.update;

import lombok.Builder;
import lombok.Data;
import spribe.api.RequestDto;

@Data
@Builder
public class PlayerUpdateRequestDto implements RequestDto {
    private Integer age;
    private String gender;
    private String login;
    private String password;
    private String role;
    private String screenName;
}
