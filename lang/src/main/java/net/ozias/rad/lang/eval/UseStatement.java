/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval;

import net.ozias.rad.lang.ASTUseStatement;
import net.ozias.rad.lang.SimpleNode;

/**
 * Evaluate an ASTUseStatement node.
 */
public final class UseStatement implements Evaluatable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Singleton Instance. */
  private static UseStatement instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new UseStatement object.
   */
  private UseStatement() {
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
  public static UseStatement getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new UseStatement();
      }
    }

    return instance;
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public String evaluate( final SimpleNode node ) {
    String retstr = null;

    if ( node instanceof ASTUseStatement ) {
      retstr = Name.eval( ( SimpleNode ) node.jjtGetChild( 0 ) );
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTUse node." );
    }

    return retstr;
  }

}
