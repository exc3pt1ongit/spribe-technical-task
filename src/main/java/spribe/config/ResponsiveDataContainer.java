package spribe.config;

import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class ResponsiveDataContainer {

    @Getter
    private static final ResponsiveDataContainer instance = new ResponsiveDataContainer();
    private final Set<Integer> affectedPlayerIdList = new CopyOnWriteArraySet<>();

    private ResponsiveDataContainer() {
    }

    public synchronized void addAffectedPlayerId(Integer playerId) {
        affectedPlayerIdList.add(playerId);
    }

    public List<Integer> getAffectedPlayerIdList() {
        return new CopyOnWriteArrayList<>(affectedPlayerIdList);
    }

    public synchronized void removeAffectedPlayerId(Integer playerId) {
        affectedPlayerIdList.remove(playerId);
    }
}
