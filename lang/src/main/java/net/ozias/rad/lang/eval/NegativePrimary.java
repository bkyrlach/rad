/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval;

import net.ozias.rad.lang.ASTNegativePrimary;
import net.ozias.rad.lang.SimpleNode;

/**
 * Evaluate an ASTNegativePrimary node.
 */
public final class NegativePrimary implements Evaluatable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Singleton Instance. */
  private static NegativePrimary instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new NegativePrimary object.
   */
  private NegativePrimary() {
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
  public static Number eval( final SimpleNode node ) {
    return getInstance().evaluate( node );
  }

  /**
   * Get the singleton instance.
   *
   * @return  The singleton instance.
   */
  public static NegativePrimary getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new NegativePrimary();
      }
    }

    return instance;
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public Number evaluate( final SimpleNode node ) {

    if ( node instanceof ASTNegativePrimary ) {
      Number retnum = Primary.eval( ( SimpleNode ) node.jjtGetChild( 0 ) );

      if ( retnum instanceof Double ) {
        retnum = ( Double ) retnum * -1.0d;
      } else if ( retnum instanceof Integer ) {
        retnum = ( Integer ) retnum * -1;
      }

      return retnum;
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTNegativePrimary node." );
    }
  }

}
