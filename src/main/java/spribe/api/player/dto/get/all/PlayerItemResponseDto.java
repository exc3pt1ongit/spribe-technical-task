package spribe.api.player.dto.get.all;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PlayerItemResponseDto {
    private Integer age;
    private String gender;
    private Integer id;
    private String role;
    private String screenName;
}
