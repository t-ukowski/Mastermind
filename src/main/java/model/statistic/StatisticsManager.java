package model.statistic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.statistic.data_objects.RankingItem;
import model.statistic.data_objects.StatisticsItem;

/**
 * Responsible for managing dynamic ranking and statistics content
 */
public class StatisticsManager {
    INotifier notifier;
    private final ObservableList<RankingItem> cachedRanking;
    private final ObservableList<StatisticsItem> cachedStatistics;

    public StatisticsManager() {
        this.cachedRanking = FXCollections.observableArrayList();
        this.cachedStatistics = FXCollections.observableArrayList();
    }

    public StatisticsManager(INotifier notifier) {
        this.notifier = notifier;
        this.cachedRanking = FXCollections.observableArrayList();
        this.cachedStatistics = FXCollections.observableArrayList();
    }

    public ObservableList<StatisticsItem> getStatistics() {
        return cachedStatistics;
    }

    public ObservableList<RankingItem> getRanking() {
        return cachedRanking;
    }

    /**
     * Adds an item to ranking cache
     *
     * @param rItem ranking item to add
     */
    public final void addRankingItem(RankingItem rItem) {
        this.cachedRanking.add(rItem);
    }

    /**
     * Adds all items from an iterable to ranking cache
     *
     * @param rItems iterable of items to add
     */
    public final void addRankingItem(Iterable<RankingItem> rItems) {
        for (RankingItem r : rItems) {
            this.addRankingItem(r);
        }
    }

    /**
     * Clears ranking cache and adds all items from given iterable
     *
     * @param rItems iterable of ranking items to add
     */
    public void replaceRanking(Iterable<RankingItem> rItems) {
        cachedRanking.clear();
        addRankingItem(rItems);
    }

    /**
     * Adds an item to statistics cache
     *
     * @param sItem statistics item to add
     */
    public final void addStatisticsItem(StatisticsItem sItem) {
        this.cachedStatistics.add(sItem);
    }

    /**
     * Adds all items from an iterable to statistics cache
     *
     * @param sItems iterable of statistics items to add
     */
    public final void addStatisticsItem(Iterable<StatisticsItem> sItems) {
        for (StatisticsItem s : sItems) {
            this.addStatisticsItem(s);
        }
    }

    /**
     * Clears statistics cache and adds all items from given iterable
     *
     * @param sItems iterable of statistics items to add
     */
    public void replaceStatistics(Iterable<StatisticsItem> sItems) {
        cachedStatistics.clear();
        addStatisticsItem(sItems);
    }

    public void leaderChanged() {

    }
}
