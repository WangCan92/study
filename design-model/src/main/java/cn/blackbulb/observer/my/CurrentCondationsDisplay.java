package cn.blackbulb.observer.my;

import cn.blackbulb.observer.DisplayAble;

/**
 * @author wangcan
 */
public class CurrentCondationsDisplay implements Observer, DisplayAble {
    private Subject weatherData;

    public CurrentCondationsDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void disPlay() {
        if (weatherData instanceof WeatherData) {
            WeatherData wd = (WeatherData) weatherData;
            System.out.println("当前温度：" + wd.getTemperature());
            System.out.println("当前湿度：" + wd.getHumidity());
            System.out.println("当前气压：" + wd.getPressure());
        }

    }

    @Override
    public void update(WeatherData weatherData) {
        disPlay();
    }
}
