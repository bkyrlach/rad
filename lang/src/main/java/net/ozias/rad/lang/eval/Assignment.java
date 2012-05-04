/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval;

import net.ozias.rad.lang.ASTAssignment;
import net.ozias.rad.lang.Invoker;
import net.ozias.rad.lang.SimpleNode;

/**
 * Evaluates an ASTAssignment node.
 */
public final class Assignment implements Evaluatable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Invoke parameter types. */
  private static final Class<?>[] PARAMETER_TYPES = new Class<?>[] { String.class, String.class, Class.class, Object.class };
  /** Singleton Instance. */
  private static Assignment instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new Expression object.
   */
  private Assignment() {
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
  public static Assignment getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new Assignment();
      }
    }

    return instance;
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public Number evaluate( final SimpleNode node ) {

    if ( node instanceof ASTAssignment ) {
      final String identifier = ( String ) node.jjtGetValue();
      final Number value = Expression.eval( ( SimpleNode ) node.jjtGetChild( 0 ) );
      Invoker.invoke( Invoker.getCurrentBase(), "addField", PARAMETER_TYPES,
        new Object[] { Invoker.getCurrentBase().replace( '.', '/' ), identifier, String.class, value.toString() } );

      return value;
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTAssignment node" );
    }
  }
}
