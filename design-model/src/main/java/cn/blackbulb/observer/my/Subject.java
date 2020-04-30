package cn.blackbulb.observer.my;

/**
 * @author wangcan
 */
public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}
