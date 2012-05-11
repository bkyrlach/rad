/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval.action;

import net.ozias.rad.lang.ASTSubtractAction;
import net.ozias.rad.lang.SimpleNode;
import net.ozias.rad.lang.eval.Evaluatable;
import net.ozias.rad.lang.eval.expression.Expression;

/**
 * Evaluate an ASTSubtractAction node.
 */
public final class SubtractAction implements Evaluatable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Singleton Instance. */
  private static SubtractAction instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new SubtractAction object.
   */
  private SubtractAction() {
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
  public static Number eval( final SimpleNode node ) {
    return getInstance().evaluate( node );
  }

  /**
   * Get the singleton instance.
   *
   * @return  The singleton instance.
   */
  public static SubtractAction getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new SubtractAction();
      }
    }

    return instance;
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public Number evaluate( final SimpleNode node ) {
    Number retnum = 0;

    if ( node instanceof ASTSubtractAction ) {
      final int count = node.jjtGetNumChildren();

      for ( int i = 0; i < count; i++ ) {
        final Number currentValue = Expression.eval( ( SimpleNode ) node.jjtGetChild( i ) );

        if ( ( retnum instanceof Integer ) && ( currentValue instanceof Integer ) ) {
          retnum = retnum.intValue() - currentValue.intValue();
        } else {
          retnum = retnum.doubleValue() - currentValue.doubleValue();
        }
      }
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTSubtractAction node." );
    }

    return retnum;
  }

}
