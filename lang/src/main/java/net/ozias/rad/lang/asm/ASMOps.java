/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.asm;

import static net.ozias.rad.lang.asm.ASMConstants.INIT;
import static net.ozias.rad.lang.asm.ASMConstants.JAVA_LANG_NUMBER;
import static net.ozias.rad.lang.asm.ASMConstants.JAVA_UTIL_ITERATOR;
import static net.ozias.rad.lang.asm.ASMConstants.JAVA_UTIL_LIST;
import static net.ozias.rad.lang.asm.ASMConstants.LOC0;
import static net.ozias.rad.lang.asm.ASMConstants.LOC1;
import static net.ozias.rad.lang.asm.ASMConstants.LOC10;
import static net.ozias.rad.lang.asm.ASMConstants.LOC11;
import static net.ozias.rad.lang.asm.ASMConstants.LOC2;
import static net.ozias.rad.lang.asm.ASMConstants.LOC3;
import static net.ozias.rad.lang.asm.ASMConstants.LOC4;
import static net.ozias.rad.lang.asm.ASMConstants.LOC5;
import static net.ozias.rad.lang.asm.ASMConstants.LOC6;
import static net.ozias.rad.lang.asm.ASMConstants.LOC7;
import static net.ozias.rad.lang.asm.ASMConstants.LOC8;
import static net.ozias.rad.lang.asm.ASMConstants.LOC9;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * ASM for Ops.
 */
public final class ASMOps implements Opcodes {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Op object. */
  private static final String OP = "Op";
  /** Number (Number Number) signature. */
  private static final String SIG_2 = "(Ljava/lang/Number;Ljava/lang/Number;)Ljava/lang/Number;";
  /** boolean (Object) signature. */
  private static final String SIG = "(Ljava/lang/Object;)Z";
  /** equals. */
  private static final String EQUALS = "equals";
  /** Op method. */
  private static final String OP_METHOD;
  /** Where these methods live. */
  private static final String OBJ_NAME = "net/ozias/rad/lang/Base";

  static {
    OP_METHOD = ASMConstants.getObject( OP );
  }

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Private to ensure this can't be instantiated by normal means.
   */
  private ASMOps() {
    // Private to ensure this can't be instantiated by normal means.
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Generate the Ops methods bytecode.
   *
   * @param  cv  The ClassVisitor to visit.
   */
  public static void generateOpsMethods( final ClassVisitor cv ) {
    final MethodVisitor mv;

    mv = cv.visitMethod( ACC_PUBLIC + ACC_STATIC, "evalOp", "(Ljava/lang/String;Ljava/util/List;)Ljava/lang/Number;",
        "(Ljava/lang/String;Ljava/util/List<Ljava/lang/Number;>;)Ljava/lang/Number;", null );
    mv.visitCode();
    mv.visitInsn( ACONST_NULL );
    mv.visitVarInsn( ASTORE, LOC2 );
    mv.visitVarInsn( ALOAD, LOC0 );
    mv.visitMethodInsn( INVOKESTATIC, OP, "valueOf", "(Ljava/lang/String;)LOp;" );
    mv.visitVarInsn( ASTORE, LOC3 );
    mv.visitVarInsn( ALOAD, LOC1 );
    mv.visitMethodInsn( INVOKEINTERFACE, JAVA_UTIL_LIST, "iterator", "()Ljava/util/Iterator;" );
    mv.visitVarInsn( ASTORE, LOC5 );
    final Label l0 = new Label();
    mv.visitJumpInsn( GOTO, l0 );
    final Label l1 = new Label();
    mv.visitLabel( l1 );
    mv.visitFrame( F_FULL, LOC6, new Object[] { "java/lang/String", JAVA_UTIL_LIST, JAVA_LANG_NUMBER, OP, TOP, JAVA_UTIL_ITERATOR }, 0, new Object[] {} );
    mv.visitVarInsn( ALOAD, LOC5 );
    mv.visitMethodInsn( INVOKEINTERFACE, JAVA_UTIL_ITERATOR, "next", "()Ljava/lang/Object;" );
    mv.visitTypeInsn( CHECKCAST, JAVA_LANG_NUMBER );
    mv.visitVarInsn( ASTORE, LOC4 );
    mv.visitVarInsn( ALOAD, LOC3 );
    mv.visitFieldInsn( GETSTATIC, OP, "ADD", OP_METHOD );
    mv.visitMethodInsn( INVOKEVIRTUAL, OP, EQUALS, SIG );
    final Label l2 = new Label();
    mv.visitJumpInsn( IFEQ, l2 );
    mv.visitVarInsn( ALOAD, LOC4 );
    mv.visitVarInsn( ALOAD, LOC2 );
    mv.visitMethodInsn( INVOKESTATIC, OBJ_NAME, "add", SIG_2 );
    mv.visitVarInsn( ASTORE, LOC2 );
    mv.visitJumpInsn( GOTO, l0 );
    mv.visitLabel( l2 );
    mv.visitFrame( F_FULL, LOC6, new Object[] { "java/lang/String", JAVA_UTIL_LIST, JAVA_LANG_NUMBER, OP, JAVA_LANG_NUMBER, JAVA_UTIL_ITERATOR }, 0,
      new Object[] {} );
    mv.visitVarInsn( ALOAD, LOC3 );
    mv.visitFieldInsn( GETSTATIC, OP, "SUB", OP_METHOD );
    mv.visitMethodInsn( INVOKEVIRTUAL, OP, EQUALS, SIG );
    final Label l3 = new Label();
    mv.visitJumpInsn( IFEQ, l3 );
    mv.visitVarInsn( ALOAD, LOC4 );
    mv.visitVarInsn( ALOAD, LOC2 );
    mv.visitMethodInsn( INVOKESTATIC, OBJ_NAME, "sub", SIG_2 );
    mv.visitVarInsn( ASTORE, LOC2 );
    mv.visitJumpInsn( GOTO, l0 );
    mv.visitLabel( l3 );
    mv.visitFrame( F_SAME, LOC0, null, LOC0, null );
    mv.visitVarInsn( ALOAD, LOC3 );
    mv.visitFieldInsn( GETSTATIC, OP, "MUL", OP_METHOD );
    mv.visitMethodInsn( INVOKEVIRTUAL, OP, EQUALS, SIG );
    final Label l4 = new Label();
    mv.visitJumpInsn( IFEQ, l4 );
    mv.visitVarInsn( ALOAD, LOC4 );
    mv.visitVarInsn( ALOAD, LOC2 );
    mv.visitMethodInsn( INVOKESTATIC, OBJ_NAME, "mult", SIG_2 );
    mv.visitVarInsn( ASTORE, LOC2 );
    mv.visitJumpInsn( GOTO, l0 );
    mv.visitLabel( l4 );
    mv.visitFrame( F_SAME, LOC0, null, LOC0, null );
    mv.visitVarInsn( ALOAD, LOC3 );
    mv.visitFieldInsn( GETSTATIC, OP, "DIV", OP_METHOD );
    mv.visitMethodInsn( INVOKEVIRTUAL, OP, EQUALS, SIG );
    mv.visitJumpInsn( IFEQ, l0 );
    mv.visitVarInsn( ALOAD, LOC4 );
    mv.visitVarInsn( ALOAD, LOC2 );
    mv.visitMethodInsn( INVOKESTATIC, OBJ_NAME, "div", SIG_2 );
    mv.visitVarInsn( ASTORE, LOC2 );
    mv.visitLabel( l0 );
    mv.visitFrame( F_FULL, LOC6, new Object[] { "java/lang/String", JAVA_UTIL_LIST, JAVA_LANG_NUMBER, OP, TOP, JAVA_UTIL_ITERATOR }, LOC0, new Object[] {} );
    mv.visitVarInsn( ALOAD, LOC5 );
    mv.visitMethodInsn( INVOKEINTERFACE, JAVA_UTIL_ITERATOR, "hasNext", "()Z" );
    mv.visitJumpInsn( IFNE, l1 );
    mv.visitVarInsn( ALOAD, LOC2 );
    mv.visitVarInsn( ALOAD, LOC1 );
    mv.visitMethodInsn( INVOKESTATIC, OBJ_NAME, "convertTotal", "(Ljava/lang/Number;Ljava/util/List;)Ljava/lang/Number;" );
    mv.visitInsn( ARETURN );
    mv.visitMaxs( LOC2, LOC6 );
    mv.visitEnd();

    ASMOpMethod.generateOp( cv, "add" );
    ASMOpMethod.generateOp( cv, "sub" );
    ASMOpMethod.generateOp( cv, "mult" );
    ASMOpMethod.generateOp( cv, "div" );
    visitConvertTotalMethod( cv );
  }

  /**
   * Visit the convertTotal method.
   *
   * @param  cv  The ClassVisitor to visit.
   */
  private static void visitConvertTotalMethod( final ClassVisitor cv ) {
    final MethodVisitor mv;
    mv = cv.visitMethod( ACC_PUBLIC + ACC_STATIC, "convertTotal", "(Ljava/lang/Number;Ljava/util/List;)Ljava/lang/Number;",
        "(Ljava/lang/Number;Ljava/util/List<Ljava/lang/Number;>;)Ljava/lang/Number;", null );
    mv.visitCode();
    mv.visitInsn( ACONST_NULL );
    mv.visitVarInsn( ASTORE, LOC2 );
    mv.visitInsn( ICONST_0 );
    mv.visitVarInsn( ISTORE, LOC3 );
    mv.visitInsn( ICONST_0 );
    mv.visitVarInsn( ISTORE, LOC4 );
    mv.visitInsn( ICONST_0 );
    mv.visitVarInsn( ISTORE, LOC5 );
    mv.visitInsn( ICONST_0 );
    mv.visitVarInsn( ISTORE, LOC6 );
    mv.visitInsn( ICONST_0 );
    mv.visitVarInsn( ISTORE, LOC7 );
    mv.visitInsn( ICONST_0 );
    mv.visitVarInsn( ISTORE, LOC8 );
    mv.visitVarInsn( ALOAD, LOC1 );
    mv.visitMethodInsn( INVOKEINTERFACE, JAVA_UTIL_LIST, "iterator", "()Ljava/util/Iterator;" );
    mv.visitVarInsn( ASTORE, LOC10 );
    final Label l0 = new Label();
    mv.visitJumpInsn( GOTO, l0 );
    final Label l1 = new Label();
    mv.visitLabel( l1 );
    mv.visitFrame( F_FULL, LOC11,
      new Object[] { JAVA_LANG_NUMBER, JAVA_UTIL_LIST, JAVA_LANG_NUMBER, INTEGER, INTEGER, INTEGER, INTEGER, INTEGER, INTEGER, TOP, JAVA_UTIL_ITERATOR }, LOC0,
      new Object[] {} );
    mv.visitVarInsn( ALOAD, LOC10 );
    mv.visitMethodInsn( INVOKEINTERFACE, JAVA_UTIL_ITERATOR, "next", "()Ljava/lang/Object;" );
    mv.visitTypeInsn( CHECKCAST, JAVA_LANG_NUMBER );
    mv.visitVarInsn( ASTORE, LOC9 );
    mv.visitVarInsn( ALOAD, LOC9 );
    mv.visitTypeInsn( INSTANCEOF, "java/lang/Byte" );
    final Label l2 = new Label();
    mv.visitJumpInsn( IFEQ, l2 );
    mv.visitInsn( ICONST_1 );
    mv.visitVarInsn( ISTORE, LOC3 );
    mv.visitJumpInsn( GOTO, l0 );
    mv.visitLabel( l2 );
    mv.visitFrame( F_FULL, LOC11,
      new Object[] {
        JAVA_LANG_NUMBER, JAVA_UTIL_LIST, JAVA_LANG_NUMBER, INTEGER, INTEGER, INTEGER, INTEGER, INTEGER, INTEGER, JAVA_LANG_NUMBER, JAVA_UTIL_ITERATOR
      }, LOC0, new Object[] {} );
    mv.visitVarInsn( ALOAD, LOC9 );
    mv.visitTypeInsn( INSTANCEOF, "java/lang/Short" );
    final Label l3 = new Label();
    mv.visitJumpInsn( IFEQ, l3 );
    mv.visitInsn( ICONST_1 );
    mv.visitVarInsn( ISTORE, LOC4 );
    mv.visitJumpInsn( GOTO, l0 );
    mv.visitLabel( l3 );
    mv.visitFrame( F_SAME, LOC0, null, LOC0, null );
    mv.visitVarInsn( ALOAD, LOC9 );
    mv.visitTypeInsn( INSTANCEOF, "java/lang/Integer" );
    final Label l4 = new Label();
    mv.visitJumpInsn( IFEQ, l4 );
    mv.visitInsn( ICONST_1 );
    mv.visitVarInsn( ISTORE, LOC5 );
    mv.visitJumpInsn( GOTO, l0 );
    mv.visitLabel( l4 );
    mv.visitFrame( F_SAME, LOC0, null, LOC0, null );
    mv.visitVarInsn( ALOAD, LOC9 );
    mv.visitTypeInsn( INSTANCEOF, "java/lang/Float" );
    final Label l5 = new Label();
    mv.visitJumpInsn( IFEQ, l5 );
    mv.visitInsn( ICONST_1 );
    mv.visitVarInsn( ISTORE, LOC6 );
    mv.visitJumpInsn( GOTO, l0 );
    mv.visitLabel( l5 );
    mv.visitFrame( F_SAME, LOC0, null, LOC0, null );
    mv.visitVarInsn( ALOAD, LOC9 );
    mv.visitTypeInsn( INSTANCEOF, "java/lang/Long" );
    final Label l6 = new Label();
    mv.visitJumpInsn( IFEQ, l6 );
    mv.visitInsn( ICONST_1 );
    mv.visitVarInsn( ISTORE, LOC7 );
    mv.visitJumpInsn( GOTO, l0 );
    mv.visitLabel( l6 );
    mv.visitFrame( F_SAME, LOC0, null, LOC0, null );
    mv.visitVarInsn( ALOAD, LOC9 );
    mv.visitTypeInsn( INSTANCEOF, "java/lang/Double" );
    mv.visitJumpInsn( IFEQ, l0 );
    mv.visitInsn( ICONST_1 );
    mv.visitVarInsn( ISTORE, LOC8 );
    mv.visitLabel( l0 );
    mv.visitFrame( F_FULL, LOC11,
      new Object[] { JAVA_LANG_NUMBER, JAVA_UTIL_LIST, JAVA_LANG_NUMBER, INTEGER, INTEGER, INTEGER, INTEGER, INTEGER, INTEGER, TOP, JAVA_UTIL_ITERATOR }, LOC0,
      new Object[] {} );
    mv.visitVarInsn( ALOAD, LOC10 );
    mv.visitMethodInsn( INVOKEINTERFACE, JAVA_UTIL_ITERATOR, "hasNext", "()Z" );
    mv.visitJumpInsn( IFNE, l1 );
    mv.visitVarInsn( ILOAD, LOC8 );
    final Label l7 = new Label();
    mv.visitJumpInsn( IFEQ, l7 );
    mv.visitVarInsn( ALOAD, LOC0 );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_NUMBER, "doubleValue", "()D" );
    mv.visitMethodInsn( INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;" );
    mv.visitVarInsn( ASTORE, LOC2 );
    final Label l8 = new Label();
    mv.visitJumpInsn( GOTO, l8 );
    mv.visitLabel( l7 );
    mv.visitFrame( F_FULL, LOC9, new Object[] { JAVA_LANG_NUMBER, JAVA_UTIL_LIST, JAVA_LANG_NUMBER, INTEGER, INTEGER, INTEGER, INTEGER, INTEGER, INTEGER },
      LOC0, new Object[] {} );
    mv.visitVarInsn( ILOAD, LOC7 );
    final Label l9 = new Label();
    mv.visitJumpInsn( IFEQ, l9 );
    mv.visitTypeInsn( NEW, "java/lang/Long" );
    mv.visitInsn( DUP );
    mv.visitVarInsn( ALOAD, LOC0 );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_NUMBER, "longValue", "()J" );
    mv.visitMethodInsn( INVOKESPECIAL, "java/lang/Long", INIT, "(J)V" );
    mv.visitVarInsn( ASTORE, LOC2 );
    mv.visitJumpInsn( GOTO, l8 );
    mv.visitLabel( l9 );
    mv.visitFrame( F_SAME, LOC0, null, LOC0, null );
    mv.visitVarInsn( ILOAD, LOC6 );
    final Label l10 = new Label();
    mv.visitJumpInsn( IFEQ, l10 );
    mv.visitTypeInsn( NEW, "java/lang/Float" );
    mv.visitInsn( DUP );
    mv.visitVarInsn( ALOAD, LOC0 );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_NUMBER, "floatValue", "()F" );
    mv.visitMethodInsn( INVOKESPECIAL, "java/lang/Float", INIT, "(F)V" );
    mv.visitVarInsn( ASTORE, LOC2 );
    mv.visitJumpInsn( GOTO, l8 );
    mv.visitLabel( l10 );
    mv.visitFrame( F_SAME, LOC0, null, LOC0, null );
    mv.visitVarInsn( ILOAD, LOC5 );
    final Label l11 = new Label();
    mv.visitJumpInsn( IFEQ, l11 );
    mv.visitTypeInsn( NEW, "java/lang/Integer" );
    mv.visitInsn( DUP );
    mv.visitVarInsn( ALOAD, LOC0 );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_NUMBER, "intValue", "()I" );
    mv.visitMethodInsn( INVOKESPECIAL, "java/lang/Integer", INIT, "(I)V" );
    mv.visitVarInsn( ASTORE, LOC2 );
    mv.visitJumpInsn( GOTO, l8 );
    mv.visitLabel( l11 );
    mv.visitFrame( F_SAME, LOC0, null, LOC0, null );
    mv.visitVarInsn( ILOAD, LOC4 );
    final Label l12 = new Label();
    mv.visitJumpInsn( IFEQ, l12 );
    mv.visitTypeInsn( NEW, "java/lang/Short" );
    mv.visitInsn( DUP );
    mv.visitVarInsn( ALOAD, LOC0 );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_NUMBER, "shortValue", "()S" );
    mv.visitMethodInsn( INVOKESPECIAL, "java/lang/Short", INIT, "(S)V" );
    mv.visitVarInsn( ASTORE, LOC2 );
    mv.visitJumpInsn( GOTO, l8 );
    mv.visitLabel( l12 );
    mv.visitFrame( F_SAME, LOC0, null, LOC0, null );
    mv.visitVarInsn( ILOAD, LOC3 );
    mv.visitJumpInsn( IFEQ, l8 );
    mv.visitTypeInsn( NEW, "java/lang/Byte" );
    mv.visitInsn( DUP );
    mv.visitVarInsn( ALOAD, LOC0 );
    mv.visitMethodInsn( INVOKEVIRTUAL, JAVA_LANG_NUMBER, "byteValue", "()B" );
    mv.visitMethodInsn( INVOKESPECIAL, "java/lang/Byte", INIT, "(B)V" );
    mv.visitVarInsn( ASTORE, LOC2 );
    mv.visitLabel( l8 );
    mv.visitFrame( F_SAME, LOC0, null, LOC0, null );
    mv.visitVarInsn( ALOAD, LOC2 );
    mv.visitInsn( ARETURN );
    mv.visitMaxs( LOC4, LOC11 );
    mv.visitEnd();
  }
}
