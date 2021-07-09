package ru.burenkov.weatherBroker.req;

public class Weather {

    private String city;
    private Double temp;

    public Weather(String city, Double temp) {
        this.city = city;
        this.temp = temp;
    }

    public Weather() {}

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "Weather [city=" + city + ", temp=" + temp + "]";
    }
}
