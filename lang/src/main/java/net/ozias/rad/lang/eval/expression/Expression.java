/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval.expression;

import net.ozias.rad.lang.ASTExpression;
import net.ozias.rad.lang.ASTMultiplicativeExpression;
import net.ozias.rad.lang.Invoker;
import net.ozias.rad.lang.SimpleNode;
import net.ozias.rad.lang.eval.Evaluatable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Evaluates an ASTExpression node.
 */
public final class Expression implements Evaluatable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Singleton Instance. */
  private static Expression instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new Expression object.
   */
  private Expression() {
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
  public static Expression getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new Expression();
      }
    }

    return instance;
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public Number evaluate( final SimpleNode node ) {

    if ( node instanceof ASTExpression ) {
      Number retnum = -1;

      if ( node != null ) {
        final Class<?>[] parameterTypes = new Class<?>[] { String.class, List.class };
        final Object[] parameters = new Object[parameterTypes.length];
        final int count = node.jjtGetNumChildren();
        final LinkedList<Object> expression = new LinkedList<Object>();

        for ( int i = 0; i < count; i++ ) {
          final SimpleNode childNode = ( SimpleNode ) node.jjtGetChild( i );

          if ( childNode instanceof ASTMultiplicativeExpression ) {
            Number currentTerm = MultiplicativeExpression.eval( childNode );
            Object popped = expression.peek();

            if ( ( popped != null ) && ( popped instanceof String ) ) {
              popped = expression.pop();
              parameters[0] = ( String ) popped;
              parameters[1] = populateNumbers( ( Number ) expression.pop(), currentTerm );
              currentTerm = ( Number ) Invoker.invoke( "evalOp", parameterTypes, parameters );
            }
            expression.push( currentTerm );
          } else {
            expression.push( childNode.jjtGetValue() );
          }
        }

        retnum = ( Number ) expression.pop();
      }

      return retnum;
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTExpression node" );
    }
  }

  /**
   * Populate the numbers list.
   *
   * @param   previousTerm  The previous term.
   * @param   currentTerm   The current term.
   *
   * @return  The list of numbers.
   */
  private List<Number> populateNumbers( final Number previousTerm, final Number currentTerm ) {
    final List<Number> numbers = new ArrayList<Number>();
    numbers.add( previousTerm );
    numbers.add( currentTerm );

    return numbers;
  }
}
