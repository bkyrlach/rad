/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.asm.adapter;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Implementing this interface indicates you can be placed into a ClassVisitor adapter chain.
 */
public abstract class AbstractChainableAdapter extends ClassVisitor implements Opcodes {

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new AbstractChainableAdapter object.
   */
  public AbstractChainableAdapter() {
    super( ASM4 );
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Set the ClassVisitor to visit and return yourself to be chained.
   *
   * @param   cv  The ClassVisitor to visit
   *
   * @return  Yourself, for chaining.
   */
  public ClassVisitor setClassVisitor( final ClassVisitor cv ) {
    super.cv = cv;

    return this;
  }
}
