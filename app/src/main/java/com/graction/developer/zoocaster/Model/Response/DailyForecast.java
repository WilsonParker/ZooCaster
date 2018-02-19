package com.graction.developer.zoocaster.Model.Response;

import com.graction.developer.zoocaster.Model.Weather.Weather;
import com.graction.developer.zoocaster.Util.StringUtil;

import java.util.ArrayList;

/*
 * 16 day weather model
 */
public class DailyForecast {
    private int cod, cnt;
    private String message;
    private ArrayList<DailyForecastItem> list;

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<DailyForecastItem> getList() {
        return list;
    }

    public void setList(ArrayList<DailyForecastItem> list) {
        this.list = list;
    }


    @Override
    public String toString() {
        return "DailyForecastModel [cod=" + cod + ", cnt=" + cnt + ", message=" + message + ", " + StringUtil.createString("list", list) + "]";
    }

    public class DailyForecastItem {
        private long dt;
        private int humidity, deg;
        private double pressure, speed, clouds, rain, snow;
        private Temp temp;
        private ArrayList<Weather> weather;

        public class Temp {
            private double day, min, max, night, eve, morn;

            public double getDay() {
                return day;
            }

            public void setDay(double day) {
                this.day = day;
            }

            public double getMin() {
                return min;
            }

            public void setMin(double min) {
                this.min = min;
            }

            public double getMax() {
                return max;
            }

            public void setMax(double max) {
                this.max = max;
            }

            public double getNight() {
                return night;
            }

            public void setNight(double night) {
                this.night = night;
            }

            public double getEve() {
                return eve;
            }

            public void setEve(double eve) {
                this.eve = eve;
            }

            public double getMorn() {
                return morn;
            }

            public void setMorn(double morn) {
                this.morn = morn;
            }

            @Override
            public String toString() {
                return "Temp [day=" + day + ", min=" + min + ", max=" + max + ", night=" + night + ", eve=" + eve
                        + ", morn=" + morn + "]";
            }

        }

        public long getDt() {
            return dt;
        }

        public void setDt(long dt) {
            this.dt = dt;
        }

        public double getPressure() {
            return pressure;
        }

        public void setPressure(double pressure) {
            this.pressure = pressure;
        }

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public int getDeg() {
            return deg;
        }

        public void setDeg(int deg) {
            this.deg = deg;
        }

        public double getClouds() {
            return clouds;
        }

        public void setClouds(double clouds) {
            this.clouds = clouds;
        }

        public double getRain() {
            return rain;
        }

        public void setRain(double rain) {
            this.rain = rain;
        }

        public double getSnow() {
            return snow;
        }

        public void setSnow(double snow) {
            this.snow = snow;
        }

        public Temp getTemp() {
            return temp;
        }

        public void setTemp(Temp temp) {
            this.temp = temp;
        }

        public ArrayList<Weather> getWeather() {
            return weather;
        }

        public void setWeather(ArrayList<Weather> weather) {
            this.weather = weather;
        }

        @Override
        public String toString() {
            return "DailyForecastItem [dt=" + dt + ", pressure=" + pressure + ", speed=" + speed + ", humidity="
                    + humidity + ", deg=" + deg + ", clouds=" + clouds + ", rain=" + rain + ", snow=" + snow + ", temp="
                    + temp + ", " + StringUtil.createString("weather", weather) + "]";
        }

    }
}
