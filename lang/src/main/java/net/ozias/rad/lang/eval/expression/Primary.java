/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval.expression;

import net.ozias.rad.lang.ASTExpression;
import net.ozias.rad.lang.ASTNumberAction;
import net.ozias.rad.lang.ASTNumberLiteral;
import net.ozias.rad.lang.ASTPrimary;
import net.ozias.rad.lang.SimpleNode;
import net.ozias.rad.lang.eval.Evaluatable;
import net.ozias.rad.lang.eval.action.number.NumberAction;
import net.ozias.rad.lang.eval.literal.NumberLiteral;

/**
 * Evaluate an ASTPrimary node.
 */
public final class Primary implements Evaluatable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Singleton Instance. */
  private static Primary instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new Primary object.
   */
  private Primary() {
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
  public static Primary getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new Primary();
      }
    }

    return instance;
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public Number evaluate( final SimpleNode node ) {

    if ( node instanceof ASTPrimary ) {
      Number retnum = Double.NaN;
      final SimpleNode childNode = ( SimpleNode ) node.jjtGetChild( 0 );

      if ( childNode instanceof ASTNumberLiteral ) {
        retnum = NumberLiteral.eval( childNode );
      } else if ( childNode instanceof ASTExpression ) {
        retnum = Expression.eval( childNode );
      } else if ( childNode instanceof ASTNumberAction ) {
        retnum = NumberAction.eval( childNode );
      }

      return retnum;
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTPrimary node." );
    }
  }

}
