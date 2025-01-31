package spribe.data.entity;

import lombok.Builder;
import lombok.Data;
import spribe.helpers.models.PlayerRole;

@Data
@Builder
public class GrantedPlayer {
    private Integer id;
    private String login;
    private PlayerRole role;
}
