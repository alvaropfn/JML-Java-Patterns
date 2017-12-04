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
package builder;

/**
 * 
 * Hero, the class with many parameters.
 * 
 */
public final class Hero {

  private /*@ spec_public @*/ final Profession profession;
  private /*@ spec_public @*/ final String name;
  private /*@ spec_public @*/ final HairType hairType;
  private /*@ spec_public @*/ final HairColor hairColor;
  private /*@ spec_public @*/ final Armor armor;
  private /*@ spec_public @*/ final Weapon weapon;

  /*@ 	ensures profession != \old(profession);
   		ensures hairColor != \old(hairColor);
   		ensures hairType != \old(hairType);
    	ensures weapon != \old(weapon);
    	ensures armor != \old(armor);
    	ensures name != \old(name);
      	assignable profession, hairColor, hairType, weapon, armor, name;
   @*/
  private Hero(/*@ non_null @*/Builder builder) {
    this.profession = builder.profession;
    this.name = builder.name;
    this.hairColor = builder.hairColor;
    this.hairType = builder.hairType;
    this.weapon = builder.weapon;
    this.armor = builder.armor;
  }

  public Profession getProfession() {
    return profession;
  }

  public String getName() {
    return name;
  }

  public HairType getHairType() {
    return hairType;
  }

  public HairColor getHairColor() {
    return hairColor;
  }

  public Armor getArmor() {
    return armor;
  }
  
  
  public Weapon getWeapon() {
    return /*@ non_null @*/weapon;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append("This is a ")
            .append(profession)
            .append(" named ")
            .append(name);
    if (hairColor != null || hairType != null) {
      sb.append(" with ");
      if (hairColor != null) {
        sb.append(hairColor).append(' ');
      }
      if (hairType != null) {
        sb.append(hairType).append(' ');
      }
      sb.append(hairType != HairType.BALD ? "hair" : "head");
    }
    if (armor != null) {
      sb.append(" wearing ").append(armor);
    }
    if (weapon != null) {
      sb.append(" and wielding a ").append(weapon);
    }
    sb.append('.');
    return sb.toString();
  }

  /**
   * 
   * The builder class.
   * 
   */
  public static class Builder {

    private final /*@ spec_public @*/ Profession profession;
    private final /*@ spec_public @*/ String name;
    private /*@ spec_public @*/ HairType hairType;
    private /*@ spec_public @*/ HairColor hairColor;
    private /*@ spec_public @*/ Armor armor;
    private /*@ spec_public @*/ Weapon weapon;

    /**
     * Constructor
     */
    /*@public normal_behavior
      	requires profession != null;
      	requires name != null;
      	also
      	public exceptional_behavior
      	signals_only IllegalArgumentException;
     @*/
    //@ assignable profession;
    //@ assignable name;
    public Builder(Profession profession, String name) {
      if (profession == null || name == null) {
        throw new IllegalArgumentException("profession and name can not be null");
      }
      this.profession = profession;
      this.name = name;
    }
    
    /*@ assignable profession;
      	ensures hairType != \old(hairType);
     @*/
    public Builder withHairType(/*@ non_null @*/ HairType hairType) {
      this.hairType = hairType;
      return this;
    }

    /*@ assignable hairColor;
  		ensures hairColor != \old(hairColor);
 	@*/
    public Builder withHairColor(/*@ non_null @*/HairColor hairColor) {
      this.hairColor = hairColor;
      return this;
    }

    /*@ assignable armor;
		ensures armor != \old(armor);
	@*/
    public Builder withArmor(/*@ non_null @*/Armor armor) {
      this.armor = armor;
      return this;
    }

    /*@ assignable weapon;
		ensures weapon != \old(weapon);
	@*/
    public Builder withWeapon(/*@ non_null @*/Weapon weapon) {
      this.weapon = weapon;
      return this;
    }
    
    
    public /*@ pure @*/Hero build() {
      return new Hero(this);
    }
  }
}
