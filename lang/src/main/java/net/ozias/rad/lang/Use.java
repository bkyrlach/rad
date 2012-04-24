/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang;

/**
 * TODO: DOCUMENT ME!
 */
public class Use {

  //~ Instance fields ------------------------------------------------------------------------------------------------------------------------------------------

  /** TODO: DOCUMENT ME! */
  private String use;

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * TODO: Creates a new Use object.
   *
   * @param  use  TODO: DOCUMENT ME!
   */
  public Use( final String use ) {
    this.use = use;
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#toString()
   */
  @Override public String toString() {
    return new StringBuilder( "( use " ).append( use ).append( " )" ).toString();
  }
}
