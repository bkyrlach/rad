/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval.statement;

import net.ozias.rad.lang.ASTDataDeclarationStatement;
import net.ozias.rad.lang.ASTDataType;
import net.ozias.rad.lang.ASTFilter;
import net.ozias.rad.lang.ASTIdentifier;
import net.ozias.rad.lang.Invoker;
import net.ozias.rad.lang.SimpleNode;
import net.ozias.rad.lang.eval.Evaluatable;
import net.ozias.rad.lang.eval.datatype.DataType;

/**
 * Evaluate an ASTDataDeclarationStatement node.
 */
public final class DataDeclarationStatement implements Evaluatable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Invoke parameter types. */
  private static final Class<?>[] PARAMETER_TYPES = new Class<?>[] { String.class, String.class, Class.class, Object.class };
  /** Singleton Instance. */
  private static DataDeclarationStatement instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new DataDeclarationStatement object.
   */
  private DataDeclarationStatement() {
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
  public static DataDeclarationStatement getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new DataDeclarationStatement();
      }
    }

    return instance;
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public String evaluate( final SimpleNode node ) {
    final String retstr = "data declaration evaluated.";
    final StringBuilder sb = new StringBuilder();

    if ( node instanceof ASTDataDeclarationStatement ) {
      final int count = node.jjtGetNumChildren();
      boolean filter = false;
      Object typeObject = null;
      String identifier = "";

      for ( int i = 0; i < count; i++ ) {
        final SimpleNode child = ( SimpleNode ) node.jjtGetChild( i );

        if ( child instanceof ASTFilter ) {
          filter = true;
        } else if ( child instanceof ASTDataType ) {
          typeObject = DataType.eval( child );
        } else if ( child instanceof ASTIdentifier ) {
          identifier = child.jjtGetValue().toString();
        }
      }

      if ( filter ) {
        sb.append( " is filterable" );
      }

      final Class<?> fieldType = ( Class<?> ) Invoker.invoke( Invoker.getCurrentBase(), "hasField", new Class<?>[] { String.class },
          new Object[] { identifier } );

      if ( fieldType != null ) {
        Invoker.invoke( Invoker.getCurrentBase(), "removeField", new Class<?>[] { String.class, String.class },
          new Object[] { identifier, fieldType.getName() } );
      }
      Invoker.invoke( Invoker.getCurrentBase(), "addField", PARAMETER_TYPES,
        new Object[] { Invoker.getCurrentBase().replace( '.', '/' ), identifier, typeObject.getClass(), null } );
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTDataDeclarationStatement node." );
    }

    return retstr;
  }

}
