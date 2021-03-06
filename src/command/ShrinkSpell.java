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
package command;

/**
 * 
 * ShrinkSpell is a concrete command
 *
 */
public class ShrinkSpell extends Command {

  private /*@ spec_public nullable@*/ Size oldSize;
  private /*@ spec_public nullable@*/ Target target;

  //@ assignable target;
  //@ ensures target.getSize() != null;
  public void execute(/*@non_null@*/Target target) {
    oldSize = target.getSize();
    target.setSize(Size.SMALL);
    this.target = target;
  }
  
  //@ ensures \old(target.getSize()) == oldSize;
  public void undo() {
    if (oldSize != null && target != null) {
      Size temp = target.getSize();
      target.setSize(oldSize);
      oldSize = temp;
    }
  }

  @Override
  public void redo() {
    undo();
  }

  @Override
  public /*@pure@*/ String toString() {
    return "Shrink spell";
  }
}
