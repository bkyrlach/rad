/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval.expression;

import net.ozias.rad.lang.ASTDecrement;
import net.ozias.rad.lang.ASTIncrement;
import net.ozias.rad.lang.ASTPostfixExpression;
import net.ozias.rad.lang.SimpleNode;
import net.ozias.rad.lang.eval.Evaluatable;

/**
 * Evaluate an ASTPostfixExpression node.
 */
public final class PostfixExpression implements Evaluatable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Singleton Instance. */
  private static PostfixExpression instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new PostfixExpression object.
   */
  private PostfixExpression() {
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
  public static PostfixExpression getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new PostfixExpression();
      }
    }

    return instance;
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public Number evaluate( final SimpleNode node ) {

    if ( node instanceof ASTPostfixExpression ) {
      Number retnum = Primary.eval( ( SimpleNode ) node.jjtGetChild( 0 ) );

      if ( ( node.jjtGetNumChildren() == 2 ) && ( retnum instanceof Integer ) ) {
        final SimpleNode operator = ( SimpleNode ) node.jjtGetChild( 1 );

        if ( operator instanceof ASTIncrement ) {
          retnum = increment( retnum );
        } else if ( operator instanceof ASTDecrement ) {
          retnum = decrement( retnum );
        }
      }

      return retnum;
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTPostfixExpression node." );
    }
  }

  /**
   * Decrement the given number.
   *
   * @param   number  The number to decrement.
   *
   * @return  The number minus 1.
   */
  private Number decrement( final Number number ) {
    int value = number.intValue();

    return value--;
  }

  /**
   * Increment the given number.
   *
   * @param   number  The number to increment.
   *
   * @return  The number plus 1.
   */
  private Number increment( final Number number ) {
    int value = number.intValue();

    return value++;
  }

}
