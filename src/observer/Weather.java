/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Weather can be observed by implementing {@link WeatherObserver} interface and registering as
 * listener.
 * 
 */
public class Weather {
	
  private /*@ spec_public nullable @*/ WeatherType currentWeather;
  private /*@ spec_public nullable @*/ List<WeatherObserver> observers;

  /*@	ensures currentWeather != null;
    	ensures observers != null;
     	ensures observers.size() == 0;
   @*/
  public Weather() {
    observers = new ArrayList<>();
    currentWeather = WeatherType.SUNNY;
  }

  //@ requires observers != null;
  //@ ensures observers.get(observers.size()-1).equals(obs);
  //@ ensures (\forall int i; 0 <= i && i < observers.size()-1; observers.get(i).equals(observers.get(i)));
  public void addObserver(/*@ non_null @*/ WeatherObserver obs) {
    observers.add(obs);
  }
  
  //@ requires observers != null;
  /* ensures (\forall int i, diff; diff >= 0 && diff <= 2 && 0 <= i && i < \old(observers).size();
 			(if(diff > 1) breaks;)
 			(if(!\old(observers).get(i).equals(observers.get(i - diff))) diff++;));
	@*/
  public void removeObserver(WeatherObserver obs) {
    observers.remove(obs);
  }

  /**
   * Makes time pass for weather
   */
  public void timePasses() {
    WeatherType[] enumValues = WeatherType.values();
    currentWeather = enumValues[(currentWeather.ordinal() + 1) % enumValues.length];
    System.out.println("The weather changed to {}."+ currentWeather);
    notifyObservers();
  }

  private void notifyObservers() {
    for (WeatherObserver obs : observers) {
      obs.update(currentWeather);
    }
  }
}
