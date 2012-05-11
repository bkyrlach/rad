/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval.action;

import net.ozias.rad.lang.ASTEchoAction;
import net.ozias.rad.lang.SimpleNode;
import net.ozias.rad.lang.eval.Evaluatable;

/**
 * Evaluate an ASTEchoAction node.
 */
public final class EchoAction implements Evaluatable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Singleton Instance. */
  private static EchoAction instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new EchoAction object.
   */
  private EchoAction() {
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
  public static EchoAction getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new EchoAction();
      }
    }

    return instance;
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public String evaluate( final SimpleNode node ) {
    String retstr = null;

    if ( node instanceof ASTEchoAction ) {
      retstr = ( ( SimpleNode ) node.jjtGetChild( 0 ) ).jjtGetValue().toString();
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTEchoAction node." );
    }

    return retstr;
  }

}
