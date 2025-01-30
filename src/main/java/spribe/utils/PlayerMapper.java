package spribe.utils;

import spribe.api.player.dto.PlayerResponseDto;
import spribe.api.player.dto.create.PlayerCreateResponseDto;
import spribe.api.player.dto.get.PlayerGetByPlayerIdResponseDto;
import spribe.api.player.dto.get.all.PlayerItemResponseDto;
import spribe.api.player.dto.update.PlayerUpdateResponseDto;

public class PlayerMapper {

    public static PlayerCreateResponseDto toCreateResponseDto(PlayerResponseDto playerResponseDto) {
        if (playerResponseDto == null) {
            return null;
        }
        return new PlayerCreateResponseDto()
                .setAge(playerResponseDto.getAge())
                .setGender(playerResponseDto.getGender())
                .setId(playerResponseDto.getId())
                .setLogin(playerResponseDto.getLogin())
                .setPassword(playerResponseDto.getPassword())
                .setRole(playerResponseDto.getRole())
                .setScreenName(playerResponseDto.getScreenName());
    }

    public static PlayerGetByPlayerIdResponseDto toGetByPlayerIdResponseDto(PlayerResponseDto playerResponseDto) {
        if (playerResponseDto == null) {
            return null;
        }
        return new PlayerGetByPlayerIdResponseDto()
                .setAge(playerResponseDto.getAge())
                .setGender(playerResponseDto.getGender())
                .setId(playerResponseDto.getId())
                .setLogin(playerResponseDto.getLogin())
                .setPassword(playerResponseDto.getPassword())
                .setRole(playerResponseDto.getRole())
                .setScreenName(playerResponseDto.getScreenName());
    }

    public static PlayerItemResponseDto toItemResponseDto(PlayerResponseDto playerResponseDto) {
        if (playerResponseDto == null) {
            return null;
        }
        return new PlayerItemResponseDto()
                .setAge(playerResponseDto.getAge())
                .setGender(playerResponseDto.getGender())
                .setId(playerResponseDto.getId())
                .setRole(playerResponseDto.getRole())
                .setScreenName(playerResponseDto.getScreenName());
    }

    public static PlayerUpdateResponseDto toUpdateResponseDto(PlayerResponseDto playerResponseDto) {
        if (playerResponseDto == null) {
            return null;
        }
        return new PlayerUpdateResponseDto()
                .setAge(playerResponseDto.getAge())
                .setGender(playerResponseDto.getGender())
                .setId(playerResponseDto.getId())
                .setLogin(playerResponseDto.getLogin())
                .setRole(playerResponseDto.getRole())
                .setScreenName(playerResponseDto.getScreenName());
    }

    public static PlayerResponseDto fromCreateResponseDto(PlayerCreateResponseDto createResponseDto) {
        if (createResponseDto == null) {
            return null;
        }
        return new PlayerResponseDto()
                .setAge(createResponseDto.getAge())
                .setGender(createResponseDto.getGender())
                .setId(createResponseDto.getId())
                .setLogin(createResponseDto.getLogin())
                .setPassword(createResponseDto.getPassword())
                .setRole(createResponseDto.getRole())
                .setScreenName(createResponseDto.getScreenName());
    }

    public static PlayerResponseDto fromGetByPlayerIdResponseDto(PlayerGetByPlayerIdResponseDto getByPlayerIdResponseDto) {
        if (getByPlayerIdResponseDto == null) {
            return null;
        }
        return new PlayerResponseDto()
                .setAge(getByPlayerIdResponseDto.getAge())
                .setGender(getByPlayerIdResponseDto.getGender())
                .setId(getByPlayerIdResponseDto.getId())
                .setLogin(getByPlayerIdResponseDto.getLogin())
                .setPassword(getByPlayerIdResponseDto.getPassword())
                .setRole(getByPlayerIdResponseDto.getRole())
                .setScreenName(getByPlayerIdResponseDto.getScreenName());
    }

    public static PlayerResponseDto fromItemResponseDto(PlayerItemResponseDto itemResponseDto) {
        if (itemResponseDto == null) {
            return null;
        }
        return new PlayerResponseDto()
                .setAge(itemResponseDto.getAge())
                .setGender(itemResponseDto.getGender())
                .setId(itemResponseDto.getId())
                .setRole(itemResponseDto.getRole())
                .setScreenName(itemResponseDto.getScreenName());
    }

    public static PlayerResponseDto fromUpdateResponseDto(PlayerUpdateResponseDto updateResponseDto) {
        if (updateResponseDto == null) {
            return null;
        }
        return new PlayerResponseDto()
                .setAge(updateResponseDto.getAge())
                .setGender(updateResponseDto.getGender())
                .setId(updateResponseDto.getId())
                .setLogin(updateResponseDto.getLogin())
                .setRole(updateResponseDto.getRole())
                .setScreenName(updateResponseDto.getScreenName());
    }
}
