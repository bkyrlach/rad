/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * ASM for Namespace methods.
 */
public final class ASMNamespace implements Opcodes {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Where these methods live. */
  private static final String OBJ_NAME = "net/ozias/rad/lang/Base";

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Private to ensure this can't be instantiated by normal means.
   */
  private ASMNamespace() {
    // Private to ensure this can't be instantiated by normal means.
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Generate the namespace support code.
   *
   * @param  cv  The ClassVisitor to visit.
   */
  public static void generateNamespaceCode( final ClassVisitor cv ) {
    final FieldVisitor fv = cv.visitField( ACC_PRIVATE, "currentNamespace", "Ljava/lang/String;", null, null );
    fv.visitEnd();
    MethodVisitor mv = cv.visitMethod( ACC_PUBLIC, "getCurrentNamespace", "()Ljava/lang/String;", null, null );
    mv.visitCode();
    mv.visitVarInsn( ALOAD, 0 );
    mv.visitFieldInsn( GETFIELD, OBJ_NAME, "currentNamespace", "Ljava/lang/String;" );
    mv.visitInsn( ARETURN );
    mv.visitMaxs( 1, 1 );
    mv.visitEnd();
    mv = cv.visitMethod( ACC_PUBLIC, "setCurrentNamespace", "(Ljava/lang/String;)V", null, null );
    mv.visitCode();
    mv.visitVarInsn( ALOAD, 0 );
    mv.visitVarInsn( ALOAD, 1 );
    mv.visitFieldInsn( PUTFIELD, OBJ_NAME, "currentNamespace", "Ljava/lang/String;" );
    mv.visitInsn( RETURN );
    mv.visitMaxs( 2, 2 );
    mv.visitEnd();
  }
}
