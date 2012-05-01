/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.asm;

import static net.ozias.rad.lang.BaseFactory.BASE_ASM_CN;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Generate the ASM for the base R@d object.
 */
public final class ASMBase {

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Private to ensure this can't be instantiated by normal means.
   */
  private ASMBase() {
    // Private to ensure this can't be instantiated by normal means.
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Generate the base R@d object shell.
   *
   * @param  cv  The ClassVisitor to visit.
   */
  public static void generateBaseShell( final ClassVisitor cv ) {
    // Setup the base class information.
    cv.visit( Opcodes.V1_7, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, BASE_ASM_CN, null, "java/lang/Object", null );

    // Setup the default constructor.
    final MethodVisitor mv = cv.visitMethod( Opcodes.ACC_PUBLIC, "<init>", "()V", null, null );
    mv.visitCode();
    mv.visitVarInsn( Opcodes.ALOAD, 0 );
    mv.visitMethodInsn( Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V" );
    mv.visitInsn( Opcodes.RETURN );
    mv.visitMaxs( 1, 1 );
    mv.visitEnd();
  }
}
