/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Generate the ASM for the R@d shell object.
 */
public final class ASMRadShell {

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Private to ensure this can't be instantiated by normal means.
   */
  private ASMRadShell() {
    // Private to ensure this can't be instantiated by normal means.
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Generate the base R@d object shell.
   *
   * @param  cv          The ClassVisitor to visit.
   * @param  objectName  The name of the object to generate a shell for.
   */
  public static void generateShell( final ClassVisitor cv, final String objectName ) {
    final String jvmName = objectName.replace( '.', '/' );
    // Setup the base class information.
    cv.visit( Opcodes.V1_7, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, jvmName, null, "java/lang/Object", null );

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
