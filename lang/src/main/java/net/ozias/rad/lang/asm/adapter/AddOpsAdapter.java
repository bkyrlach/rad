/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.asm.adapter;

import net.ozias.rad.lang.asm.ASMOps;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * An ASM adapter used to add an add method to a class.
 */
public class AddOpsAdapter extends ClassVisitor implements Opcodes {

  //~ Instance fields ------------------------------------------------------------------------------------------------------------------------------------------

  /** Is the add method already present. */
  private transient boolean isMethodPresent = false;

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new AddOpsAdapter object.
   *
   * @param  cv  The class visitor this adapter is intercepting visits from.
   */
  public AddOpsAdapter( final ClassVisitor cv ) {
    super( ASM4, cv );
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.objectweb.asm.ClassVisitor#visitEnd()
   */
  @Override public void visitEnd() {

    if ( !isMethodPresent ) {
      ASMOps.generateOpsMethods( cv );
    }

    cv.visitEnd();
  }

  /**
   * @see  org.objectweb.asm.ClassVisitor#visitMethod(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String[])
   */
  @Override public MethodVisitor visitMethod( final int access, final String name, final String desc, final String signature, final String[] exceptions ) {

    if ( "evalOp".equals( name ) ) {
      isMethodPresent = true;
    }

    return super.visitMethod( access, name, desc, signature, exceptions );
  }

}
