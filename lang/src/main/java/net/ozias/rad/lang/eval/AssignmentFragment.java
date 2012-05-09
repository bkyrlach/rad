/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval;

import net.ozias.rad.lang.ASTAssignmentFragment;
import net.ozias.rad.lang.Invoker;
import net.ozias.rad.lang.SimpleNode;

/**
 * Evaluates an ASTAssignmentFragment node.
 */
public final class AssignmentFragment implements Evaluatable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Invoke parameter types. */
  private static final Class<?>[] PARAMETER_TYPES = new Class<?>[] { String.class, String.class, Class.class, Object.class };
  /** Singleton Instance. */
  private static AssignmentFragment instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new Expression object.
   */
  private AssignmentFragment() {
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
  public static AssignmentFragment getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new AssignmentFragment();
      }
    }

    return instance;
  }

  /**
   * Invoke addField on the current base with the given identifier and default value.
   *
   * @param  identifier  The identifier to add to the current base object.
   * @param  value       The default value of the identifier.
   */
  public static void invoke( final String identifier, final Object value ) {
    Invoker.invoke( Invoker.getCurrentBase(), "addField", PARAMETER_TYPES,
      new Object[] { Invoker.getCurrentBase().replace( '.', '/' ), identifier, String.class, value.toString() } );
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public Number evaluate( final SimpleNode node ) {

    if ( node instanceof ASTAssignmentFragment ) {
      return Expression.eval( ( SimpleNode ) node.jjtGetChild( 0 ) );
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTAssignment node" );
    }
  }
}
