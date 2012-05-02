/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval;

import static net.ozias.rad.lang.BaseInvoker.invoke;

import net.ozias.rad.lang.ASTPrimary;
import net.ozias.rad.lang.ASTTerm;
import net.ozias.rad.lang.SimpleNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Evaluate an ASTTerm node.
 */
public final class Term implements Evaluatable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Singleton Instance. */
  private static Term instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new Term object.
   */
  private Term() {
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
  public static Term getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new Term();
      }
    }

    return instance;
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public Number evaluate( final SimpleNode node ) {

    if ( node instanceof ASTTerm ) {
      final int count = node.jjtGetNumChildren();
      final LinkedList<Object> expression = new LinkedList<Object>();

      for ( int i = 0; i < count; i++ ) {
        final SimpleNode childNode = ( SimpleNode ) node.jjtGetChild( i );

        if ( childNode instanceof ASTPrimary ) {
          Number currentTerm = Primary.eval( childNode );
          Object popped = expression.peek();

          if ( ( popped != null ) && ( popped instanceof String ) ) {
            popped = expression.pop();
            currentTerm = invoke( ( String ) popped, populateNumbers( ( Number ) expression.pop(), currentTerm ) );
          }
          expression.push( currentTerm );
        } else {
          expression.push( childNode.jjtGetValue() );
        }
      }

      return ( Number ) expression.pop();
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTTerm node." );
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
