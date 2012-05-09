/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval;

import net.ozias.rad.lang.ASTOpFunction;
import net.ozias.rad.lang.Invoker;
import net.ozias.rad.lang.SimpleNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Evaluate an ASTOpFunction node.
 */
public final class OpFunction implements Evaluatable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Singleton Instance. */
  private static OpFunction instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new OpFunction object.
   */
  private OpFunction() {
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
  public static OpFunction getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new OpFunction();
      }
    }

    return instance;
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public Number evaluate( final SimpleNode node ) {

    if ( node instanceof ASTOpFunction ) {
      Number retnum = -1;

      if ( node != null ) {
        final String op = ( String ) node.jjtGetValue();
        final List<Number> numbers = new ArrayList<Number>();

        for ( int i = 0; i < node.jjtGetNumChildren(); i++ ) {
          numbers.add( Primary.eval( ( SimpleNode ) node.jjtGetChild( i ) ) );
        }

        retnum = ( Number ) Invoker.invoke( "evalOp", new Class<?>[] { String.class, List.class }, new Object[] { op, numbers } );
      }

      return retnum;
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTOpFunction node." );
    }
  }

}
