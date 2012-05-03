/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval;

import net.ozias.rad.lang.ASTNamespace;
import net.ozias.rad.lang.BaseInvoker;
import net.ozias.rad.lang.SimpleNode;

/**
 * Evaluate an ASTNamespace node.
 */
public final class Namespace implements Evaluatable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Singleton Instance. */
  private static Namespace instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new Namespace object.
   */
  private Namespace() {
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
  public static Namespace getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new Namespace();
      }
    }

    return instance;
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public String evaluate( final SimpleNode node ) {
    String retstr = null;

    if ( node instanceof ASTNamespace ) {

      if ( node.jjtGetNumChildren() == 1 ) {
        retstr = Name.eval( ( SimpleNode ) node.jjtGetChild( 0 ) );
      } else {
        retstr = BaseInvoker.currentNamespace();
      }
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTNamespace node." );
    }

    return retstr;
  }

}
