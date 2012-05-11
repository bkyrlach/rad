/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval.action;

import net.ozias.rad.lang.ASTViewAction;
import net.ozias.rad.lang.Invoker;
import net.ozias.rad.lang.SimpleNode;
import net.ozias.rad.lang.eval.Evaluatable;

/**
 * Evaluate an ASTViewAction node.
 */
public final class ViewAction implements Evaluatable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Singleton Instance. */
  private static ViewAction instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new ViewAction object.
   */
  private ViewAction() {
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
  public static ViewAction getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new ViewAction();
      }
    }

    return instance;
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public String evaluate( final SimpleNode node ) {
    String retstr = null;

    if ( node instanceof ASTViewAction ) {
      final String identifier = ( ( SimpleNode ) node.jjtGetChild( 0 ) ).jjtGetValue().toString();
      retstr = ( String ) Invoker.invoke( Invoker.getCurrentBase(), "readField", new Class<?>[] { String.class }, new Object[] { identifier } );

      if ( retstr == null ) {
        retstr = new StringBuilder( "null" ).toString();
      }
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTViewAction node." );
    }

    return retstr;
  }

}
