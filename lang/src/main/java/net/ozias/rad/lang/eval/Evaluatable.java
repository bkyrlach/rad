/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.eval;

import net.ozias.rad.lang.SimpleNode;

/**
 * Implementing this ensures you can evaluate a JavaCC SimpleNode.
 */
public interface Evaluatable {

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Evaluate the given node. The type of the node should be checked, and an IllegalArgumentException thrown if you don't suppor the given node type.
   *
   * @param   node  The node to evaluate.
   *
   * @return  The result of evaluating the node.
   */
  Object evaluate( final SimpleNode node );
}
