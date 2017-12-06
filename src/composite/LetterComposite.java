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
package composite;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Composite interface.
 * 
 */
public abstract class LetterComposite {

  private /*@ spec_public @*/ List<LetterComposite> children = new ArrayList<>();

  /*@  requires letter != null; 
    @  ensures children.size() > \old(children.size());
    @*/
  public void add(LetterComposite letter) {
    children.add(letter);
  }

  
  public /*@ pure @*/ int count() {
    return children.size();
  }

  protected void printThisBefore() {}

  protected void printThisAfter() {}

  /**
   * Print
   */
  public /*@ pure @*/ void print() {
    printThisBefore();
    for (LetterComposite letter : children) {
       /*@ invariant (\forall int i;
    	 @				0 <= i && i < children.size(); 
    	 @				children.get(i).equals(\old(children).get(i)));
    	 @*/
    	letter.print();
    }
    printThisAfter();
  }
}
