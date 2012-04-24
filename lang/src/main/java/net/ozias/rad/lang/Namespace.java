/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang;

/**
 * TODO: DOCUMENT ME!
 */
public class Namespace {

  //~ Instance fields ------------------------------------------------------------------------------------------------------------------------------------------

  /** TODO: DOCUMENT ME! */
  public String namespace;

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new Namespace object.
   *
   * @param  namespace  TODO: DOCUMENT ME!
   */
  public Namespace( final String namespace ) {
    this.namespace = namespace;
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#toString()
   */
  @Override public String toString() {
    return new StringBuilder( "( ns " ).append( namespace ).append( " )" ).toString();
  }

}
