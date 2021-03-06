/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval.statement;

import net.ozias.rad.lang.ASTActionStatement;
import net.ozias.rad.lang.ASTDataDeclarationStatement;
import net.ozias.rad.lang.ASTExpression;
import net.ozias.rad.lang.ASTNamespaceStatement;
import net.ozias.rad.lang.ASTStatement;
import net.ozias.rad.lang.ASTUseStatement;
import net.ozias.rad.lang.SimpleNode;
import net.ozias.rad.lang.eval.Evaluatable;
import net.ozias.rad.lang.eval.expression.Expression;

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
    String retstr = "Unknown statement!!";

    if ( node == null ) {
      retstr = "Parse failed, check logs.";
    } else if ( node instanceof ASTStatement ) {
      retstr = evaluateASTStatementNode( node );
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTStatement node." );
    }

    return retstr;
  }

  /**
   * Evaluate the given ASTStatementNode.
   *
   * @param   node  The ASTStatementNode to evaluate.
   *
   * @return  The evaluated value.
   */
  private String evaluateASTStatementNode( final SimpleNode node ) {
    String retstr = "";
    final SimpleNode child = ( SimpleNode ) node.jjtGetChild( 0 );

    if ( child instanceof ASTNamespaceStatement ) {
      retstr = NamespaceStatement.eval( child );
    } else if ( child instanceof ASTUseStatement ) {
      retstr = UseStatement.eval( child );
    } else if ( child instanceof ASTDataDeclarationStatement ) {
      retstr = DataDeclarationStatement.eval( child );
    } else if ( child instanceof ASTActionStatement ) {
      retstr = ActionStatement.eval( child ).toString();
    } else if ( child instanceof ASTExpression ) {
      retstr = Expression.eval( child ).toString();
    }

    return retstr;
  }

}
