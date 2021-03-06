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
package abstractfactory;

import abstractfactory.App.FactoryMaker.KingdomType;

/**
 * 
 * The Abstract Factory pattern provides a way to encapsulate a group of individual factories that have a common theme
 * without specifying their concrete classes. In normal usage, the client software creates a concrete implementation of
 * the abstract factory and then uses the generic interface of the factory to create the concrete objects that are part
 * of the theme. The client does not know (or care) which concrete objects it gets from each of these internal
 * factories, since it uses only the generic interfaces of their products. This pattern separates the details of
 * implementation of a set of objects from their general usage and relies on object composition, as object creation is
 * implemented in methods exposed in the factory interface.
 * <p>
 * The essence of the Abstract Factory pattern is a factory interface ({@link KingdomFactory}) and its implementations (
 * {@link ElfKingdomFactory}, {@link OrcKingdomFactory}). The example uses both concrete implementations to create a
 * king, a castle and an army.
 * 
 */
public class App {

  private /*@ spec_public nullable @*/ King king;
  private /*@ spec_public nullable @*/ Castle castle;
  private /*@ spec_public nullable @*/ Army army;
  
  /**
   * Creates kingdom
   */
  public void createKingdom(final /*@ non_null @*/ KingdomFactory factory) {
    setKing(factory.createKing());
    setCastle(factory.createCastle());
    setArmy(factory.createArmy());
  }
  
  //@ ensures \result instanceof King;
  King getKing(final /*@ non_null @*/ KingdomFactory factory) {
    return factory.createKing();
  }
  

  public /*@ pure @*/ King getKing() {
    return king;
  }
  
  //@ assignable king;
  private void setKing(final /*@ non_null @*/ King king) {
    this.king = king;
  }
  
  //@ ensures \result instanceof Castle;
  Castle getCastle(final /*@ non_null @*/ KingdomFactory factory) {
    return factory.createCastle();
  }

  //@ ensures \result instanceof Castle;
  public /*@pure@*/ Castle getCastle() {
    return castle;
  }
  
  //@ assignable castle;
  private void setCastle(final /*@ non_null @*/ Castle castle) {
    this.castle = castle;
  }
  
  //@ ensures \result instanceof Army;
  Army getArmy(final /*@ non_null @*/ KingdomFactory factory) {
    return factory.createArmy();
  }

  //@ ensures \result instanceof Army;
  public /*@pure @*/ Army getArmy() {
    return army;
  }

  //@ assignable army;
  private void setArmy(final /*@ non_null @*/ Army army) {
    this.army = army;
  }

  /**
   * The factory of kingdom factories.
   */
  public static class FactoryMaker {

    /**
     * Enumeration for the different types of Kingdoms.
     */
    public enum KingdomType {
      ELF, ORC
    }

    /**
     * The factory method to create KingdomFactory concrete objects.
     */
    /*public normal_behavior
      		ensures \result instanceof KingdomFactory;
      	also
      public exceptional_behavior
      		
      		signals_only IllegalArgumentException;
     @*/
    public static KingdomFactory makeFactory(KingdomType type) {
      switch (type) {
        case ELF:
          return new ElfKingdomFactory();
        case ORC:
          return new OrcKingdomFactory();
        default:
          throw new IllegalArgumentException("KingdomType not supported.");
      }
    }
  }

  /**
   * Program entry point.
   * 
   * @param args
   *          command line args
   */
  public static void main(String[] args) {

    App app = new App();
    
    System.out.println("Elf Kingdom");
    app.createKingdom(FactoryMaker.makeFactory(KingdomType.ELF));
    System.out.println(app.getArmy().getDescription());
    System.out.println(app.getCastle().getDescription());
    System.out.println(app.getKing().getDescription());

    System.out.println("Orc Kingdom");
    app.createKingdom(FactoryMaker.makeFactory(KingdomType.ORC));
    System.out.println(app.getArmy().getDescription());
    System.out.println(app.getCastle().getDescription());
    System.out.println(app.getKing().getDescription());
  }
}