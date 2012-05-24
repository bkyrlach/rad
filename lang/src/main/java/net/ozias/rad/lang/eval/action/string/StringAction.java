/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval.action.string;

import net.ozias.rad.lang.ASTEchoAction;
import net.ozias.rad.lang.ASTExitAction;
import net.ozias.rad.lang.ASTManualAction;
import net.ozias.rad.lang.ASTStringAction;
import net.ozias.rad.lang.ASTViewAction;
import net.ozias.rad.lang.SimpleNode;
import net.ozias.rad.lang.eval.Evaluatable;

/**
 * Evaluate an ASTStringAction node.
 */
public final class StringAction implements Evaluatable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Singleton Instance. */
  private static StringAction instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new StringAction object.
   */
  private StringAction() {
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
  public static StringAction getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new StringAction();
      }
    }

    return instance;
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public String evaluate( final SimpleNode node ) {
    String retstr = null;

    if ( node instanceof ASTStringAction ) {
      final SimpleNode child = ( SimpleNode ) node.jjtGetChild( 0 );

      if ( child instanceof ASTManualAction ) {
        retstr = ManAction.eval( child );
      } else if ( child instanceof ASTEchoAction ) {
        retstr = EchoAction.eval( child );
      } else if ( child instanceof ASTViewAction ) {
        retstr = ViewAction.eval( child );
      } else if ( child instanceof ASTExitAction ) {
        retstr = ExitAction.eval( child );
      }
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTStringAction node." );
    }

    return retstr;
  }

}
