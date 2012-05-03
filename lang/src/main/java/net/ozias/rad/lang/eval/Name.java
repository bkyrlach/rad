/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval;

import net.ozias.rad.lang.ASTName;
import net.ozias.rad.lang.SimpleNode;

/**
 * Evaluate an ASTName node.
 */
public final class Name implements Evaluatable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Singleton Instance. */
  private static Name instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new Name object.
   */
  private Name() {
    // Ensures this cannot be instantiated through normal means.
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Evaluate the given node.
   *
   * @param   node  The node to evaluate.
   *
   * @return  The String result from evaluating the node.
   */
  public static String eval( final SimpleNode node ) {
    return getInstance().evaluate( node );
  }

  /**
   * Get the singleton instance.
   *
   * @return  The singleton instance.
   */
  public static Name getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new Name();
      }
    }

    return instance;
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public String evaluate( final SimpleNode node ) {

    if ( node instanceof ASTName ) {
      final StringBuilder sb = new StringBuilder();
      final int count = node.jjtGetNumChildren();

      for ( int i = 0; i < count; i++ ) {
        sb.append( ( ( SimpleNode ) node.jjtGetChild( i ) ).jjtGetValue() );

        if ( i < ( count - 1 ) ) {
          sb.append( "." );
        }
      }

      return sb.toString();
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTName node." );
    }
  }

}
