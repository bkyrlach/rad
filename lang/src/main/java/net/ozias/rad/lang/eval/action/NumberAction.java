/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval.action;

import net.ozias.rad.lang.ASTAddAction;
import net.ozias.rad.lang.ASTNumberAction;
import net.ozias.rad.lang.ASTSubtractAction;
import net.ozias.rad.lang.SimpleNode;
import net.ozias.rad.lang.eval.Evaluatable;

/**
 * Evaluate an ASTNumberAction node.
 */
public final class NumberAction implements Evaluatable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Singleton Instance. */
  private static NumberAction instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new NumberAction object.
   */
  private NumberAction() {
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
  public static NumberAction getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new NumberAction();
      }
    }

    return instance;
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public Number evaluate( final SimpleNode node ) {
    Number retnum = -1;

    if ( node instanceof ASTNumberAction ) {
      final SimpleNode opAction = ( SimpleNode ) node.jjtGetChild( 0 );

      if ( opAction instanceof ASTAddAction ) {
        retnum = AddAction.eval( opAction );
      } else if ( opAction instanceof ASTSubtractAction ) {
        retnum = SubtractAction.eval( opAction );
      }
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTEchoAction node." );
    }

    return retnum;
  }

}
