/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval.statement;

import net.ozias.rad.lang.ASTActionStatement;
import net.ozias.rad.lang.ASTNumberAction;
import net.ozias.rad.lang.ASTStringAction;
import net.ozias.rad.lang.SimpleNode;
import net.ozias.rad.lang.eval.Evaluatable;
import net.ozias.rad.lang.eval.action.number.NumberAction;
import net.ozias.rad.lang.eval.action.string.StringAction;

/**
 * Evaluate an ASTActionStatement node.
 */
public final class ActionStatement implements Evaluatable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Singleton Instance. */
  private static ActionStatement instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new NamespaceStatement object.
   */
  private ActionStatement() {
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
  public static Object eval( final SimpleNode node ) {
    return getInstance().evaluate( node );
  }

  /**
   * Get the singleton instance.
   *
   * @return  The singleton instance.
   */
  public static ActionStatement getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new ActionStatement();
      }
    }

    return instance;
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public Object evaluate( final SimpleNode node ) {
    Object retobj = null;

    if ( node instanceof ASTActionStatement ) {
      retobj = evaluateActionStatement( ( SimpleNode ) node.jjtGetChild( 0 ) );
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTActionStatement node." );
    }

    return retobj;
  }

  /**
   * Evaluate the given ActionStatement node.
   *
   * @param   node  The node to evaluate.
   *
   * @return  The result of evaluating the node.
   */
  private Object evaluateActionStatement( final SimpleNode node ) {
    Object retobj = null;

    if ( node instanceof ASTStringAction ) {
      retobj = StringAction.eval( node );
    } else if ( node instanceof ASTNumberAction ) {
      retobj = NumberAction.eval( node );
    } else {
      retobj = "Unimplemented action encountered.";
    }

    return retobj;
  }

}
