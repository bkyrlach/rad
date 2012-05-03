/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.asm.adapter;

import net.ozias.rad.lang.asm.ASMNamespace;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * An ASM adapter used to add namespace support to an object.
 */
public class AddNamespaceAdapter extends ClassVisitor implements Opcodes {

  //~ Instance fields ------------------------------------------------------------------------------------------------------------------------------------------

  /** Is the add method already present. */
  private transient boolean isMethodPresent = false;

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new AddNamespaceAdapter object.
   *
   * @param  cv  The class visitor this adapter is intercepting visits from.
   */
  public AddNamespaceAdapter( final ClassVisitor cv ) {
    super( ASM4, cv );
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.objectweb.asm.ClassVisitor#visitEnd()
   */
  @Override public void visitEnd() {

    if ( !isMethodPresent ) {
      ASMNamespace.generateNamespaceCode( cv );
    }

    cv.visitEnd();
  }

  /**
   * @see  org.objectweb.asm.ClassVisitor#visitMethod(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String[])
   */
  @Override public MethodVisitor visitMethod( final int access, final String name, final String desc, final String signature, final String[] exceptions ) {

    if ( "getCurrentNamespace".equals( name ) ) {
      isMethodPresent = true;
    }

    return super.visitMethod( access, name, desc, signature, exceptions );
  }

}
