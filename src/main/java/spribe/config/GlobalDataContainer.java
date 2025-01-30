package spribe.config;

import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class GlobalDataContainer {

    @Getter
    private static final GlobalDataContainer instance = new GlobalDataContainer();
    private final Set<Integer> affectedPlayerIdList = new CopyOnWriteArraySet<>();

    private GlobalDataContainer() {
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
