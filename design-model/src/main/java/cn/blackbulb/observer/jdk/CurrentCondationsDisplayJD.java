package cn.blackbulb.observer.jdk;

import cn.blackbulb.observer.DisplayAble;

import java.util.Observable;
import java.util.Observer;

/**
 * @author wangcan
 */
public class CurrentCondationsDisplayJD implements Observer,DisplayAble {
    Observable observable;

    public CurrentCondationsDisplayJD(Observable observable) {
        this.observable = observable;
        observable.addObserver(this);
    }

    @Override
    public void disPlay() {
        if(observable instanceof WeatherDataJD){
            WeatherDataJD wd = (WeatherDataJD) observable;
            System.out.println("当前温度：" + wd.getTemperature());
            System.out.println("当前湿度：" + wd.getHumidity());
            System.out.println("当前气压：" + wd.getPressure());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        disPlay();
    }
}
