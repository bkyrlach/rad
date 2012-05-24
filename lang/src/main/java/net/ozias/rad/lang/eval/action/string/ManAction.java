/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval.action.string;

import net.ozias.rad.lang.ASTManualAction;
import net.ozias.rad.lang.SimpleNode;
import net.ozias.rad.lang.eval.Evaluatable;
import net.ozias.rad.lang.man.ManualEntry;
import net.ozias.rad.lang.man.ManualManager;

/**
 * Evaluate an ASTManualAction node.
 */
public final class ManAction implements Evaluatable, ManualEntry {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Singleton Instance. */
  private static ManAction instance = null;
  /** Lock object. */
  private static final Object LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new ManAction object.
   */
  private ManAction() {
    // Ensures this cannot be instantiated through normal means.
    ManualManager.addManualContent( getManualKey(), getManualEntry() );
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
  public static ManAction getInstance() {

    synchronized ( LOCK ) {

      if ( instance == null ) {
        instance = new ManAction();
      }
    }

    return instance;
  }

  /**
   * @see  net.ozias.rad.lang.eval.Evaluatable#evaluate(net.ozias.rad.lang.SimpleNode)
   */
  @Override public String evaluate( final SimpleNode node ) {
    String retstr = null;

    if ( node instanceof ASTManualAction ) {
      final String manrequest = ( ( SimpleNode ) node.jjtGetChild( 0 ) ).jjtGetValue().toString();
      retstr = ManualManager.getManualContent( manrequest );
    } else {
      throw new IllegalArgumentException( "Supplied node is not an ASTHelpAction node." );
    }

    return retstr;
  }

  /**
   * @see  net.ozias.rad.lang.man.ManualEntry#getManualEntry()
   */
  @Override public String getManualEntry() {
    return "Interface into the R@d manual content.";
  }

  /**
   * @see  net.ozias.rad.lang.man.ManualEntry#getManualKey()
   */
  @Override public String getManualKey() {
    return "man";
  }

}
