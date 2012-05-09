/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.datatype;

/**
 * A R@d String.
 */
public class RadString {

  //~ Instance fields ------------------------------------------------------------------------------------------------------------------------------------------

  /** The capacity of the RadString. */
  private final transient int capacity;
  /** The wrapped StringBuilder. */
  private final transient StringBuilder sb;

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new RadString object with a 0 capacity StringBuilder.
   *
   * @param  initial  The initial value of the RadString.
   */
  public RadString( final String initial ) {
    this( 0, initial );
  }

  /**
   * Creates a new RadString object.
   *
   * @param  size     capacity The capacity of the internal StringBuilder.
   * @param  initial  The initial value of the RadString.
   */
  public RadString( final int size, final String initial ) {
    this.sb = new StringBuilder( size );

    if ( initial.length() < size ) {
      sb.append( initial );
    } else {
      throw new IllegalArgumentException( "Initial value will not fit in capacity." );
    }
    this.capacity = size;
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Append the given String to the current RadString given it will fit within the capacity.
   *
   * @param   string  The string to append.
   *
   * @return  The RadString object with the string append, for chaining.
   */
  public RadString append( final String string ) {
    final int size = sb.length();

    if ( ( size + string.length() ) > sb.capacity() ) {
      throw new IllegalArgumentException( "Unable to append string." );
    } else {
      sb.append( string );
    }

    return this;
  }

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals( final Object obj ) {
    boolean retbool = false;

    if ( this == obj ) {
      retbool = true;
    } else if ( ( obj != null ) && ( obj instanceof RadString ) ) {
      final RadString other = ( RadString ) obj;

      if ( ( sb == null ) || ( other.sb != null ) || !sb.equals( other.sb ) ) {
        retbool = false;
      }

      if ( capacity != other.capacity ) {
        retbool = false;
      }
    }

    return retbool;
  }

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = ( prime * result ) + ( ( sb == null ) ? 0 : sb.hashCode() );
    result = ( prime * result ) + capacity;

    return result;
  }

  /**
   * @see  java.lang.Object#toString()
   */
  @Override public String toString() {
    return sb.toString();
  }
}
