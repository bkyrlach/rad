/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval;

import net.ozias.rad.lang.ASTAssignment;
import net.ozias.rad.lang.ASTExpression;
import net.ozias.rad.lang.ASTNamespace;
import net.ozias.rad.lang.ASTStatement;
import net.ozias.rad.lang.ASTUse;
import net.ozias.rad.lang.SimpleNode;

/**
 * Evaluate an ASTStatement node.
 */
public final class Statement implements Evaluatable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Singleton Instance. */
  private static Statement instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new Statement object.
   */
  private Statement() {
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
  public static Statement getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new Statement();
      }
    }

    return instance;
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public String evaluate( final SimpleNode node ) {
    String retstr = null;

    if ( node == null ) {
      retstr = "Parse failed, check logs.";
    } else {

      if ( node instanceof ASTStatement ) {
        final SimpleNode child = ( SimpleNode ) node.jjtGetChild( 0 );

        if ( child instanceof ASTExpression ) {
          retstr = Expression.eval( child ).toString();
        } else if ( child instanceof ASTAssignment ) {
          retstr = Assignment.eval( child ).toString();
        } else if ( child instanceof ASTNamespace ) {
          retstr = Namespace.eval( child );
        } else if ( child instanceof ASTUse ) {
          retstr = Use.eval( child );
        } else {
          retstr = "Unknown statement!!";
        }
      } else {
        throw new IllegalArgumentException( "Supplied node is not an ASTStatement node." );
      }
    }

    return retstr;
  }

}
