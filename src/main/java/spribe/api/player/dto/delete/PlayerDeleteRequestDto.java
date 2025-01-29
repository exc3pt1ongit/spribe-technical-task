package spribe.api.player.dto.delete;

import lombok.Builder;
import lombok.Data;
import spribe.api.RequestDto;

@Data
@Builder
public class PlayerDeleteRequestDto implements RequestDto {
    private Integer playerId;
}
