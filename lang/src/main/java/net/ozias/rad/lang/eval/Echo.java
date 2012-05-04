/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval;

import net.ozias.rad.lang.ASTEcho;
import net.ozias.rad.lang.Invoker;
import net.ozias.rad.lang.SimpleNode;

/**
 * Evaluates an ASTEcho node.
 */
public final class Echo implements Evaluatable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Singleton Instance. */
  private static Echo instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new Echo object.
   */
  private Echo() {
    // Ensures this cannot be instantiated through normal means.
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Evaluate the given node.
   *
   * @param   node  The node to evaluate.
   *
   * @return  The Number result from evaluating the node.
   */
  public static String eval( final SimpleNode node ) {
    return getInstance().evaluate( node );
  }

  /**
   * Get the singleton instance.
   *
   * @return  The singleton instance.
   */
  public static Echo getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new Echo();
      }
    }

    return instance;
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public String evaluate( final SimpleNode node ) {

    if ( node instanceof ASTEcho ) {
      String retstr = "";
      final String identifier = ( String ) ( ( SimpleNode ) node.jjtGetChild( 0 ) ).jjtGetValue();

      retstr = ( String ) Invoker.invoke( Invoker.getCurrentBase(), "readField", new Class<?>[] { String.class }, new Object[] { identifier } );

      return retstr;
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTExpression node" );
    }
  }
}
