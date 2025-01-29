package spribe.api.player.dto.get;

import lombok.Builder;
import lombok.Data;
import spribe.api.RequestDto;

@Data
@Builder
public class PlayerGetByPlayerIdRequestDto implements RequestDto {
    private Integer playerId;
}
