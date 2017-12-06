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
package observer.generic;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Generic observer inspired by Java Generics and Collection by Naftalin & Wadler
 *
 * @param <S> Subject
 * @param <O> Observer
 * @param <A> Argument type
 */
public abstract class Observable<S extends Observable<S, O, A>, O extends Observer<S, O, A>, A> {

  protected /*@spec_public nullable@*/ List<O> observers;

  //@ ensures observers != null;
  public Observable() {
    this.observers = new CopyOnWriteArrayList<>();
  }

  //@ requires observers != null;
  //@ ensures observers.get(observers.size()-1).equals(observer);
  //@ ensures (\forall int i; 0 <= i && i < observers.size()-1; observers.get(i).equals(observers.get(i)));
  public void addObserver(/*@ non_null @*/ O observer) {
    this.observers.add(observer);
  }

  //@ requires observer != null;
  /*@ requires observers != null;
  /* ensures (\forall int i, diff; diff >= 0 && diff <= 2 && 0 <= i && i < \old(observers).size();
 			(if(diff > 1) breaks;)
 			(if(!\old(observers).get(i).equals(observers.get(i - diff))) diff++;));
	@*/
  public void removeObserver(O observer) {
    this.observers.remove(observer);
  }

  /**
   * Notify observers
   */
  //@ ensures (\forall int i; 0 <= i && i < observers.size()-1; observers.get(i).equals(observers.get(i)));
  @SuppressWarnings("unchecked")
  public void notifyObservers(A argument) {
    for (O observer : observers) {
      observer.update((S) this, argument);
    }
  }
}
