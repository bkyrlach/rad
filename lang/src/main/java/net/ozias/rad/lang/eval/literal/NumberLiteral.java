/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval.literal;

import net.ozias.rad.lang.ASTFloatingPointLiteral;
import net.ozias.rad.lang.ASTIntegerLiteral;
import net.ozias.rad.lang.ASTNumberLiteral;
import net.ozias.rad.lang.SimpleNode;
import net.ozias.rad.lang.eval.Evaluatable;

/**
 * Evaluate an ASTNumberLiteral node.
 */
public final class NumberLiteral implements Evaluatable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Singleton Instance. */
  private static NumberLiteral instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new NumberLiteral object.
   */
  private NumberLiteral() {
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
  public static NumberLiteral getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new NumberLiteral();
      }
    }

    return instance;
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public Number evaluate( final SimpleNode node ) {
    Number retnum = -1;

    if ( node instanceof ASTNumberLiteral ) {
      final SimpleNode child = ( SimpleNode ) node.jjtGetChild( 0 );

      if ( child instanceof ASTIntegerLiteral ) {
        retnum = ( Integer ) child.jjtGetValue();
      } else if ( child instanceof ASTFloatingPointLiteral ) {
        retnum = ( Double ) child.jjtGetValue();
      }
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTNumberLiteral node." );
    }

    return retnum;
  }

}
