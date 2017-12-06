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

  private /*@ spec_public nullable@*/ final Profession profession;
  private /*@ spec_public nullable@*/ final String name;
  private /*@ spec_public nullable@*/ final HairType hairType;
  private /*@ spec_public nullable@*/ final HairColor hairColor;
  private /*@ spec_public nullable@*/ final Armor armor;
  private /*@ spec_public nullable@*/ final Weapon weapon;

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

  public /*@pure@*/ Profession getProfession() {
    return profession;
  }

  public /*@pure@*/ String getName() {
    return name;
  }

  public /*@pure@*/ HairType getHairType() {
    return hairType;
  }

  public /*@pure@*/ HairColor getHairColor() {
    return hairColor;
  }

  public /*@pure@*/ Armor getArmor() {
    return armor;
  }
  
  
  public /*@pure@*/ Weapon getWeapon() {
    return weapon;
  }

  @Override
  /*@ 	requires profession != null;
   		requires name != null;
   		also ensures \result != null;
  @*/
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

    private final /*@ spec_public nullable@*/ Profession profession;
    private final /*@ spec_public nullable@*/ String name;
    private /*@ spec_public nullable@*/ HairType hairType;
    private /*@ spec_public nullable@*/ HairColor hairColor;
    private /*@ spec_public nullable@*/ Armor armor;
    private /*@ spec_public nullable@*/ Weapon weapon;

    /**
     * Constructor
     */
    
    /*@		public normal_behavior
      			assignable profession;
    			assignable name;
				ensures profession != null;
				ensures name != null;
			also
			public exceptional_behavior
				requires profession == null || name == null;
				signals_only IllegalArgumentException;
	@*/
    public Builder(Profession profession, String name) {
      if (profession == null || name == null) {
        throw new IllegalArgumentException("profession and name can not be null");
      }
      this.profession = profession;
      this.name = name;
    }
    
    /*@ assignable hairType;@*/
    public Builder withHairType( HairType hairType) {
      this.hairType = hairType;
      return this;
    }

    /*@ assignable hairColor;@*/
    public Builder withHairColor(HairColor hairColor) {
      this.hairColor = hairColor;
      return this;
    }

    /*@ assignable armor;@*/
    public Builder withArmor(Armor armor) {
      this.armor = armor;
      return this;
    }

    /*@ assignable weapon;@*/
    public Builder withWeapon(Weapon weapon) {
      this.weapon = weapon;
      return this;
    }
    
    
    public Hero build() {
      return new Hero(this);
    }
  }
}
