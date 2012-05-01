/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.asm;

import static net.ozias.rad.lang.asm.ASMConstants.JAVA_LANG_BYTE;
import static net.ozias.rad.lang.asm.ASMConstants.JAVA_LANG_DOUBLE;
import static net.ozias.rad.lang.asm.ASMConstants.JAVA_LANG_FLOAT;
import static net.ozias.rad.lang.asm.ASMConstants.JAVA_LANG_INTEGER;
import static net.ozias.rad.lang.asm.ASMConstants.JAVA_LANG_LONG;
import static net.ozias.rad.lang.asm.ASMConstants.JAVA_LANG_SHORT;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Generate the add operation method.
 */
public final class ASMOpMethod implements Opcodes {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** TODO: DOCUMENT ME! */
  private static final String DOUBLE_VALUE = "doubleValue";
  /** TODO: DOCUMENT ME! */
  private static final String D_SIG_2 = "(D)Ljava/lang/Double;";
  /** TODO: DOCUMENT ME! */
  private static final String D_SIG = "()D";
  /** TODO: DOCUMENT ME! */
  private static final String VALUE_OF = "valueOf";

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Private to ensure this can't be instantiated by normal means.
   */
  private ASMOpMethod() {
    // Private to ensure this can't be instantiated by normal means.
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Generate the add op method.
   *
   * @param  cv  The ClassVisitor to visit.
   * @param  op  The op method to generate.
   */
  public static void generateOp( final ClassVisitor cv, final String op ) {
    final MethodVisitor mv = cv.visitMethod( ACC_PRIVATE + ACC_STATIC, op, "(Ljava/lang/Number;Ljava/lang/Number;)Ljava/lang/Number;", null, null );
    mv.visitCode();
    mv.visitInsn( ACONST_NULL );
    mv.visitVarInsn( ASTORE, 2 );
    mv.visitVarInsn( ALOAD, 0 );
    mv.visitTypeInsn( INSTANCEOF, JAVA_LANG_BYTE );
    final Label l0 = new Label();
    mv.visitJumpInsn( IFEQ, l0 );
    mv.visitVarInsn( ALOAD, 1 );
    final Label l1 = new Label();
    mv.visitJumpInsn( IFNONNULL, l1 );
    mv.visitVarInsn( ALOAD, 0 );
    mv.visitTypeInsn( CHECKCAST, JAVA_LANG_BYTE );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_BYTE, "byteValue", "()B" );
    mv.visitInsn( I2D );
    final Label l2 = new Label();
    mv.visitJumpInsn( GOTO, l2 );
    mv.visitLabel( l1 );
    mv.visitFrame( F_APPEND, 1, new Object[] { "java/lang/Number" }, 0, null );
    mv.visitVarInsn( ALOAD, 1 );
    mv.visitTypeInsn( CHECKCAST, JAVA_LANG_DOUBLE );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_DOUBLE, DOUBLE_VALUE, D_SIG );
    mv.visitVarInsn( ALOAD, 0 );
    mv.visitTypeInsn( CHECKCAST, JAVA_LANG_BYTE );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_BYTE, "byteValue", "()B" );
    mv.visitInsn( I2D );

    visitOp( op, mv );

    mv.visitLabel( l2 );
    mv.visitFrame( F_SAME1, 0, null, 1, new Object[] { DOUBLE } );
    mv.visitMethodInsn( INVOKESTATIC, JAVA_LANG_DOUBLE, VALUE_OF, D_SIG_2 );
    mv.visitVarInsn( ASTORE, 2 );
    final Label l3 = new Label();
    mv.visitJumpInsn( GOTO, l3 );
    mv.visitLabel( l0 );
    mv.visitFrame( F_SAME, 0, null, 0, null );
    mv.visitVarInsn( ALOAD, 0 );
    mv.visitTypeInsn( INSTANCEOF, JAVA_LANG_SHORT );
    final Label l4 = new Label();
    mv.visitJumpInsn( IFEQ, l4 );
    mv.visitVarInsn( ALOAD, 1 );
    final Label l5 = new Label();
    mv.visitJumpInsn( IFNONNULL, l5 );
    mv.visitVarInsn( ALOAD, 0 );
    mv.visitTypeInsn( CHECKCAST, JAVA_LANG_SHORT );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_SHORT, "shortValue", "()S" );
    mv.visitInsn( I2D );
    final Label l6 = new Label();
    mv.visitJumpInsn( GOTO, l6 );
    mv.visitLabel( l5 );
    mv.visitFrame( F_SAME, 0, null, 0, null );
    mv.visitVarInsn( ALOAD, 1 );
    mv.visitTypeInsn( CHECKCAST, JAVA_LANG_DOUBLE );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_DOUBLE, DOUBLE_VALUE, D_SIG );
    mv.visitVarInsn( ALOAD, 0 );
    mv.visitTypeInsn( CHECKCAST, JAVA_LANG_SHORT );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_SHORT, "shortValue", "()S" );
    mv.visitInsn( I2D );

    visitOp( op, mv );

    mv.visitLabel( l6 );
    mv.visitFrame( F_SAME1, 0, null, 1, new Object[] { DOUBLE } );
    mv.visitMethodInsn( INVOKESTATIC, JAVA_LANG_DOUBLE, VALUE_OF, D_SIG_2 );
    mv.visitVarInsn( ASTORE, 2 );
    mv.visitJumpInsn( GOTO, l3 );
    mv.visitLabel( l4 );
    mv.visitFrame( F_SAME, 0, null, 0, null );
    mv.visitVarInsn( ALOAD, 0 );
    mv.visitTypeInsn( INSTANCEOF, JAVA_LANG_INTEGER );
    final Label l7 = new Label();
    mv.visitJumpInsn( IFEQ, l7 );
    mv.visitVarInsn( ALOAD, 1 );
    final Label l8 = new Label();
    mv.visitJumpInsn( IFNONNULL, l8 );
    mv.visitVarInsn( ALOAD, 0 );
    mv.visitTypeInsn( CHECKCAST, JAVA_LANG_INTEGER );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_INTEGER, "intValue", "()I" );
    mv.visitInsn( I2D );
    final Label l9 = new Label();
    mv.visitJumpInsn( GOTO, l9 );
    mv.visitLabel( l8 );
    mv.visitFrame( F_SAME, 0, null, 0, null );
    mv.visitVarInsn( ALOAD, 1 );
    mv.visitTypeInsn( CHECKCAST, JAVA_LANG_DOUBLE );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_DOUBLE, DOUBLE_VALUE, D_SIG );
    mv.visitVarInsn( ALOAD, 0 );
    mv.visitTypeInsn( CHECKCAST, JAVA_LANG_INTEGER );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_INTEGER, "intValue", "()I" );
    mv.visitInsn( I2D );

    visitOp( op, mv );

    mv.visitLabel( l9 );
    mv.visitFrame( F_SAME1, 0, null, 1, new Object[] { DOUBLE } );
    mv.visitMethodInsn( INVOKESTATIC, JAVA_LANG_DOUBLE, VALUE_OF, D_SIG_2 );
    mv.visitVarInsn( ASTORE, 2 );
    mv.visitJumpInsn( GOTO, l3 );
    mv.visitLabel( l7 );
    mv.visitFrame( F_SAME, 0, null, 0, null );
    mv.visitVarInsn( ALOAD, 0 );
    mv.visitTypeInsn( INSTANCEOF, JAVA_LANG_FLOAT );
    final Label l10 = new Label();
    mv.visitJumpInsn( IFEQ, l10 );
    mv.visitVarInsn( ALOAD, 1 );
    final Label l11 = new Label();
    mv.visitJumpInsn( IFNONNULL, l11 );
    mv.visitVarInsn( ALOAD, 0 );
    mv.visitTypeInsn( CHECKCAST, JAVA_LANG_FLOAT );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_FLOAT, "floatValue", "()F" );
    mv.visitInsn( F2D );
    final Label l12 = new Label();
    mv.visitJumpInsn( GOTO, l12 );
    mv.visitLabel( l11 );
    mv.visitFrame( F_SAME, 0, null, 0, null );
    mv.visitVarInsn( ALOAD, 1 );
    mv.visitTypeInsn( CHECKCAST, JAVA_LANG_DOUBLE );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_DOUBLE, DOUBLE_VALUE, D_SIG );
    mv.visitVarInsn( ALOAD, 0 );
    mv.visitTypeInsn( CHECKCAST, JAVA_LANG_FLOAT );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_FLOAT, "floatValue", "()F" );
    mv.visitInsn( F2D );

    visitOp( op, mv );

    mv.visitLabel( l12 );
    mv.visitFrame( F_SAME1, 0, null, 1, new Object[] { DOUBLE } );
    mv.visitMethodInsn( INVOKESTATIC, JAVA_LANG_DOUBLE, VALUE_OF, D_SIG_2 );
    mv.visitVarInsn( ASTORE, 2 );
    mv.visitJumpInsn( GOTO, l3 );
    mv.visitLabel( l10 );
    mv.visitFrame( F_SAME, 0, null, 0, null );
    mv.visitVarInsn( ALOAD, 0 );
    mv.visitTypeInsn( INSTANCEOF, JAVA_LANG_LONG );
    final Label l13 = new Label();
    mv.visitJumpInsn( IFEQ, l13 );
    mv.visitVarInsn( ALOAD, 1 );
    final Label l14 = new Label();
    mv.visitJumpInsn( IFNONNULL, l14 );
    mv.visitVarInsn( ALOAD, 0 );
    mv.visitTypeInsn( CHECKCAST, JAVA_LANG_LONG );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_LONG, "longValue", "()J" );
    mv.visitInsn( L2D );
    final Label l15 = new Label();
    mv.visitJumpInsn( GOTO, l15 );
    mv.visitLabel( l14 );
    mv.visitFrame( F_SAME, 0, null, 0, null );
    mv.visitVarInsn( ALOAD, 1 );
    mv.visitTypeInsn( CHECKCAST, JAVA_LANG_DOUBLE );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_DOUBLE, DOUBLE_VALUE, D_SIG );
    mv.visitVarInsn( ALOAD, 0 );
    mv.visitTypeInsn( CHECKCAST, JAVA_LANG_LONG );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_LONG, "longValue", "()J" );
    mv.visitInsn( L2D );

    visitOp( op, mv );

    mv.visitLabel( l15 );
    mv.visitFrame( F_SAME1, 0, null, 1, new Object[] { DOUBLE } );
    mv.visitMethodInsn( INVOKESTATIC, JAVA_LANG_DOUBLE, VALUE_OF, D_SIG_2 );
    mv.visitVarInsn( ASTORE, 2 );
    mv.visitJumpInsn( GOTO, l3 );
    mv.visitLabel( l13 );
    mv.visitFrame( F_SAME, 0, null, 0, null );
    mv.visitVarInsn( ALOAD, 0 );
    mv.visitTypeInsn( INSTANCEOF, JAVA_LANG_DOUBLE );
    mv.visitJumpInsn( IFEQ, l3 );
    mv.visitVarInsn( ALOAD, 1 );
    final Label l16 = new Label();
    mv.visitJumpInsn( IFNONNULL, l16 );
    mv.visitVarInsn( ALOAD, 0 );
    mv.visitTypeInsn( CHECKCAST, JAVA_LANG_DOUBLE );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_DOUBLE, DOUBLE_VALUE, D_SIG );
    final Label l17 = new Label();
    mv.visitJumpInsn( GOTO, l17 );
    mv.visitLabel( l16 );
    mv.visitFrame( F_SAME, 0, null, 0, null );
    mv.visitVarInsn( ALOAD, 1 );
    mv.visitTypeInsn( CHECKCAST, JAVA_LANG_DOUBLE );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_DOUBLE, DOUBLE_VALUE, D_SIG );
    mv.visitVarInsn( ALOAD, 0 );
    mv.visitTypeInsn( CHECKCAST, JAVA_LANG_DOUBLE );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_DOUBLE, DOUBLE_VALUE, D_SIG );

    visitOp( op, mv );

    mv.visitLabel( l17 );
    mv.visitFrame( F_SAME1, 0, null, 1, new Object[] { DOUBLE } );
    mv.visitMethodInsn( INVOKESTATIC, JAVA_LANG_DOUBLE, VALUE_OF, D_SIG_2 );
    mv.visitVarInsn( ASTORE, 2 );
    mv.visitLabel( l3 );
    mv.visitFrame( F_SAME, 0, null, 0, null );
    mv.visitVarInsn( ALOAD, 2 );
    mv.visitInsn( ARETURN );
    mv.visitMaxs( 4, 3 );
    mv.visitEnd();
  }

  /**
   * Add the appropriate op instruction.
   *
   * @param  op  The op to add.
   * @param  mv  The MethodVisitor to visit.
   */
  private static void visitOp( final String op, final MethodVisitor mv ) {

    if ( "add".equals( op ) ) {
      mv.visitInsn( DADD );
    } else if ( "div".equals( op ) ) {
      mv.visitInsn( DDIV );
    } else if ( "mult".equals( op ) ) {
      mv.visitInsn( DMUL );
    } else if ( "sub".equals( op ) ) {
      mv.visitInsn( DSUB );
    }
  }

}
