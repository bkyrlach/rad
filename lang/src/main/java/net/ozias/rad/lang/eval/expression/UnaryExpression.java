/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval.expression;

import net.ozias.rad.lang.ASTNegateExpression;
import net.ozias.rad.lang.ASTPostfixExpression;
import net.ozias.rad.lang.ASTUnaryExpression;
import net.ozias.rad.lang.SimpleNode;
import net.ozias.rad.lang.eval.Evaluatable;

/**
 * Evaluate an ASTUnaryExpression node.
 */
public final class UnaryExpression implements Evaluatable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Singleton Instance. */
  private static UnaryExpression instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new UnaryExpression object.
   */
  private UnaryExpression() {
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
  public static UnaryExpression getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new UnaryExpression();
      }
    }

    return instance;
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public Number evaluate( final SimpleNode node ) {
    Number retnum = -1;

    if ( node instanceof ASTUnaryExpression ) {
      final SimpleNode child = ( SimpleNode ) node.jjtGetChild( 0 );

      if ( child instanceof ASTNegateExpression ) {
        retnum = NegateExpression.eval( child );
      } else if ( child instanceof ASTPostfixExpression ) {
        retnum = PostfixExpression.eval( child );
      }

      return retnum;
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTUnaryExpression node." );
    }
  }
}
