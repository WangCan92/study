package cn.blackbulb.observer.my;

import cn.blackbulb.observer.DisplayAble;

/**
 * @author wangcan
 */
public class ForecastDisplay implements Observer, DisplayAble {
    private Subject weatherData;

    public ForecastDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void disPlay() {
        if (weatherData instanceof WeatherData) {
            WeatherData wd = (WeatherData) weatherData;
            if(wd.getPressure()>1000l&&wd.getHumidity()>10l){
                System.out.println("要下雨了，，，，");
            }else{
                System.out.println("又是一个大晴天！");
            }
        }

    }

    @Override
    public void update(WeatherData weatherData) {
        disPlay();
    }
}
