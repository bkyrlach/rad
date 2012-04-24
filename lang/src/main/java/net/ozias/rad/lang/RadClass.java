/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang;

import java.util.List;

/**
 * TODO: DOCUMENT ME!
 */
public class RadClass {

  //~ Instance fields ------------------------------------------------------------------------------------------------------------------------------------------

  /** The main Rad class block. */
  private final List<Block> blocks;
  /** TODO: DOCUMENT ME! */
  private final List<Use> uses;
  /** TODO: DOCUMENT ME! */
  private final Namespace namespace;

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new RadClass object.
   *
   * @param  namespace  TODO: DOCUMENT ME!
   * @param  uses       TODO: DOCUMENT ME!
   * @param  blocks     The main class block.
   */
  public RadClass( final Namespace namespace, final List<Use> uses, final List<Block> blocks ) {

    if ( namespace == null ) {
      this.namespace = new Namespace( "DEFAULT" );
    } else {
      this.namespace = namespace;
    }
    this.uses = uses;
    this.blocks = blocks;
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Get the main Rad class block.
   *
   * @return  The block.
   */
  public List<Block> getBlocks() {
    return this.blocks;
  }

  /**
   * TODO: DOCUMENT ME!
   *
   * @return  TODO: DOCUMENT ME!
   */
  public Namespace getNamespace() {
    return this.namespace;
  }

  /**
   * TODO: DOCUMENT ME!
   *
   * @return  TODO: DOCUMENT ME!
   */
  public List<Use> getUses() {
    return this.uses;
  }

  /**
   * @see  java.lang.Object#toString()
   */
  @Override public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append( "( RadClass\n" );
    sb.append( "  " ).append( namespace ).append( "\n" );

    for ( final Use use : uses ) {
      sb.append( "  " ).append( use ).append( "\n" );
    }

    for ( final Block block : blocks ) {
      sb.append( "  " ).append( block ).append( "\n" );
    }

    sb.append( ")" );

    return sb.toString();
  }

}
