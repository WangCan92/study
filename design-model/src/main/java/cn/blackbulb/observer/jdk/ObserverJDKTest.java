package cn.blackbulb.observer.jdk;

/**
 * @author wangcan
 */
public class ObserverJDKTest {
    public static void main(String[] args) {
        WeatherDataJD wd = new WeatherDataJD();
        new CurrentCondationsDisplayJD(wd);
        wd.setMeasurements(123f,3333f,444f);
    }
}
