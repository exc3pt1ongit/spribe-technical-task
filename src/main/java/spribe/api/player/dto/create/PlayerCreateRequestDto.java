package spribe.api.player.dto.create;

import lombok.Builder;
import lombok.Data;
import spribe.api.RequestDto;

@Data
@Builder
public class PlayerCreateRequestDto implements RequestDto {
    private Integer age;
    private String gender;
    private String login;
    private String password; // optional
    private String role;
    private String screenName;
}
