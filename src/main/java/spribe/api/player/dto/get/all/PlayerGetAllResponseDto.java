package spribe.api.player.dto.get.all;

import lombok.Data;

import java.util.List;

@Data
public class PlayerGetAllResponseDto {
    private List<PlayerItemResponseDto> players;
}
