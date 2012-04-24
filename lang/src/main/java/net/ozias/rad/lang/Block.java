/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang;

/**
 * TODO: DOCUMENT ME!
 */
public class Block {

  //~ Instance fields ------------------------------------------------------------------------------------------------------------------------------------------

  /** TODO: DOCUMENT ME! */
  private Has has;
  /** TODO: DOCUMENT ME! */
  private String id;
  private String parent;
  private Actions actions;
  private Data data;

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * TODO: Creates a new Block object.
   *
   * @param  id   TODO: DOCUMENT ME!
   * @param  has  TODO: DOCUMENT ME!
   */
  public Block( final String id, final String parent, final Has has, final Actions actions, final Data data ) {
    this.id = id;
    this.parent = parent;
    this.has = has;
    this.actions = actions;
    this.data = data;
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#toString()
   */
  @Override public String toString() {
    final StringBuilder sb = new StringBuilder( "( " ).append( id );
    
    if( parent != null ) {
      sb.append( " is " ).append( parent );
    }
    
    sb.append( "\n" );

    if ( has != null ) {
      sb.append( "    " ).append( has ).append( "\n" );
    }
    
    if ( actions != null ) {
      sb.append( "    " ).append( actions ).append( "\n" );
    }
    
    if ( data != null ) {
      sb.append( "    " ).append( data ).append( "\n" );
    }

    sb.append( "  )" );

    return sb.toString();
  }

}
