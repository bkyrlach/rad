/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval;

import static net.ozias.rad.lang.asm.ASMConstants.BASE_ASM_CN;

import net.ozias.rad.lang.ASTNamespace;
import net.ozias.rad.lang.Invoker;
import net.ozias.rad.lang.RadInvoker;
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
        final String namespace = Name.eval( ( SimpleNode ) node.jjtGetChild( 0 ) );
        final String object = new StringBuilder( namespace ).append( ".Base" ).toString();
        Invoker.addInvoker( object, new RadInvoker( object ) );
        Invoker.setCurrentNamespace( namespace );
        retstr = namespace;
      } else {
        retstr = ( String ) Invoker.invoke( "addField", new Class<?>[] { String.class, String.class, Class.class, Object.class },
            new Object[] { BASE_ASM_CN, "currentNamespace", String.class, Invoker.getCurrentNamespace() } );
      }
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTNamespace node." );
    }

    return retstr;
  }

}
