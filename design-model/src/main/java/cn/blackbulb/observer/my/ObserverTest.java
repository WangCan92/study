package cn.blackbulb.observer.my;

/**
 * @author wangcan
 */
public class ObserverTest {
    public static void main(String[] args) {
        WeatherData subject = new WeatherData();
        Observer observer1 = new CurrentCondationsDisplay(subject);
        Observer observer2 = new ForecastDisplay(subject);
        subject.setMeasurements(1111f,1111f,2222f);
        System.out.println("===================================");

        subject.setMeasurements(22f,33f,33f);
    }
}
