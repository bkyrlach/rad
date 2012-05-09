/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval;

import net.ozias.rad.lang.ASTDataType;
import net.ozias.rad.lang.SimpleNode;

/**
 * Evaluate an ASTDataType node.
 */
public final class DataType implements Evaluatable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Singleton Instance. */
  private static DataType instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new DataType object.
   */
  private DataType() {
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
  public static Object eval( final SimpleNode node ) {
    return getInstance().evaluate( node );
  }

  /**
   * Get the singleton instance.
   *
   * @return  The singleton instance.
   */
  public static DataType getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new DataType();
      }
    }

    return instance;
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public Object evaluate( final SimpleNode node ) {
    Object retobj = null;

    if ( node instanceof ASTDataType ) {
      final String type = node.jjtGetValue().toString();
      final int count = node.jjtGetNumChildren();
      Number size = 1;

      if ( count == 1 ) {
        size = ( Number ) ( ( SimpleNode ) node.jjtGetChild( 0 ) ).jjtGetValue();
      }

      if ( "str".equals( type ) ) {
        retobj = new StringBuilder( size.intValue() );
      } else if ( "bit".equals( type ) ) {
        retobj = new byte[size.intValue()];
      } else if ( "int".equals( type ) ) {
        retobj = new Integer( 0 );
      } else if ( "dec".equals( type ) ) {
        retobj = new Float( 0 );
      } else if ( "float".equals( type ) ) {
        retobj = new Float( 0 );
      } else if ( "double".equals( type ) ) {
        retobj = new Double( 0 );
      }
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTDataType node." );
    }

    return retobj;
  }
}
