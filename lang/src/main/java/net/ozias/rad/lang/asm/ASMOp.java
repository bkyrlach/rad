/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.asm;

import static net.ozias.rad.lang.asm.ASMConstants.LOC0;
import static net.ozias.rad.lang.asm.ASMConstants.LOC1;
import static net.ozias.rad.lang.asm.ASMConstants.LOC2;
import static net.ozias.rad.lang.asm.ASMConstants.LOC3;
import static net.ozias.rad.lang.asm.ASMConstants.LOC4;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * ASM for Op enum.
 */
public final class ASMOp implements Opcodes {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** The object name. */
  private static final String OP = "Op";
  /** The enum init method signature. */
  private static final String INIT_SIG = "(Ljava/lang/String;I)V";
  /** The init value. */
  private static final String INIT;
  /** The Op object. */
  private static final String OP_OBJ;
  /** The array of Op objects. */
  private static final String OP_OBJ_ARR;
  /** DIV enum constant. */
  private static final String DIV = "DIV";
  /** MUL enum constant. */
  private static final String MUL = "MUL";
  /** SUB enum constant. */
  private static final String SUB = "SUB";
  /** ADD enum constant. */
  private static final String ADD = "ADD";

  static {
    INIT = ASMConstants.INIT;
    OP_OBJ = ASMConstants.getObject( OP );
    OP_OBJ_ARR = ASMConstants.getObjectArray( OP );
  }

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Private to ensure this can't be instantiated by normal means.
   */
  private ASMOp() {
    // Private to ensure this can't be instantiated by normal means.
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Generate the ASMOp enum.
   *
   * @return  The ASMOp enum bytecode array.
   */
  public static byte[] generateOpEnum() {
    final ClassWriter cw = new ClassWriter( 0 );

    cw.visit( V1_7, ACC_PUBLIC + ACC_FINAL + ACC_SUPER + ACC_ENUM, OP, "Ljava/lang/Enum<LOp;>;", "java/lang/Enum", null );

    visitAddField( cw );
    visitSubField( cw );
    visitMulField( cw );
    visitDivField( cw );
    visitValuesField( cw );
    visitValuesMethod( cw );
    visitValueOfMethod( cw );
    visitInitMethod( cw );
    visitClinitMethod( cw );

    cw.visitEnd();

    return cw.toByteArray();
  }

  /**
   * Visit the ADD enum field.
   *
   * @param  cw  The ClassVisitor to visit.
   */
  private static void visitAddField( final ClassWriter cw ) {
    final FieldVisitor fv = cw.visitField( ACC_PUBLIC + ACC_FINAL + ACC_STATIC + ACC_ENUM, ADD, OP_OBJ, null, null );
    fv.visitEnd();
  }

  /**
   * Visit the clinit method.
   *
   * @param  cw  The ClassVisitor to visit.
   */
  private static void visitClinitMethod( final ClassWriter cw ) {
    final MethodVisitor mv = cw.visitMethod( ACC_STATIC, "<clinit>", "()V", null, null );
    mv.visitCode();
    mv.visitTypeInsn( NEW, OP );
    mv.visitInsn( DUP );
    mv.visitLdcInsn( ADD );
    mv.visitInsn( ICONST_0 );
    mv.visitMethodInsn( INVOKESPECIAL, OP, INIT, INIT_SIG );
    mv.visitFieldInsn( PUTSTATIC, OP, ADD, OP_OBJ );
    mv.visitTypeInsn( NEW, OP );
    mv.visitInsn( DUP );
    mv.visitLdcInsn( SUB );
    mv.visitInsn( ICONST_1 );
    mv.visitMethodInsn( INVOKESPECIAL, OP, INIT, INIT_SIG );
    mv.visitFieldInsn( PUTSTATIC, OP, SUB, OP_OBJ );
    mv.visitTypeInsn( NEW, OP );
    mv.visitInsn( DUP );
    mv.visitLdcInsn( MUL );
    mv.visitInsn( ICONST_2 );
    mv.visitMethodInsn( INVOKESPECIAL, OP, INIT, INIT_SIG );
    mv.visitFieldInsn( PUTSTATIC, OP, MUL, OP_OBJ );
    mv.visitTypeInsn( NEW, OP );
    mv.visitInsn( DUP );
    mv.visitLdcInsn( DIV );
    mv.visitInsn( ICONST_3 );
    mv.visitMethodInsn( INVOKESPECIAL, OP, INIT, INIT_SIG );
    mv.visitFieldInsn( PUTSTATIC, OP, DIV, OP_OBJ );
    mv.visitInsn( ICONST_4 );
    mv.visitTypeInsn( ANEWARRAY, OP );
    mv.visitInsn( DUP );
    mv.visitInsn( ICONST_0 );
    mv.visitFieldInsn( GETSTATIC, OP, ADD, OP_OBJ );
    mv.visitInsn( AASTORE );
    mv.visitInsn( DUP );
    mv.visitInsn( ICONST_1 );
    mv.visitFieldInsn( GETSTATIC, OP, SUB, OP_OBJ );
    mv.visitInsn( AASTORE );
    mv.visitInsn( DUP );
    mv.visitInsn( ICONST_2 );
    mv.visitFieldInsn( GETSTATIC, OP, MUL, OP_OBJ );
    mv.visitInsn( AASTORE );
    mv.visitInsn( DUP );
    mv.visitInsn( ICONST_3 );
    mv.visitFieldInsn( GETSTATIC, OP, DIV, OP_OBJ );
    mv.visitInsn( AASTORE );
    mv.visitFieldInsn( PUTSTATIC, OP, "$VALUES", OP_OBJ_ARR );
    mv.visitInsn( RETURN );
    mv.visitMaxs( LOC4, LOC0 );
    mv.visitEnd();
  }

  /**
   * Visit the DIV enum field.
   *
   * @param  cw  The ClassVisitor to visit.
   */
  private static void visitDivField( final ClassWriter cw ) {
    final FieldVisitor fv = cw.visitField( ACC_PUBLIC + ACC_FINAL + ACC_STATIC + ACC_ENUM, DIV, OP_OBJ, null, null );
    fv.visitEnd();
  }

  /**
   * Visit the init method.
   *
   * @param  cw  The ClassVisitor to visit.
   */
  private static void visitInitMethod( final ClassWriter cw ) {
    final MethodVisitor mv = cw.visitMethod( ACC_PRIVATE, INIT, INIT_SIG, "()V", null );
    mv.visitCode();
    mv.visitVarInsn( ALOAD, LOC0 );
    mv.visitVarInsn( ALOAD, LOC1 );
    mv.visitVarInsn( ILOAD, LOC2 );
    mv.visitMethodInsn( INVOKESPECIAL, "java/lang/Enum", INIT, INIT_SIG );
    mv.visitInsn( RETURN );
    mv.visitMaxs( LOC3, LOC3 );
    mv.visitEnd();
  }

  /**
   * Visit the MUL enum field.
   *
   * @param  cw  The ClassVisitor to visit.
   */
  private static void visitMulField( final ClassWriter cw ) {
    final FieldVisitor fv = cw.visitField( ACC_PUBLIC + ACC_FINAL + ACC_STATIC + ACC_ENUM, MUL, OP_OBJ, null, null );
    fv.visitEnd();
  }

  /**
   * Visit the SUM enum field.
   *
   * @param  cw  The ClassVisitor to visit.
   */
  private static void visitSubField( final ClassWriter cw ) {
    final FieldVisitor fv = cw.visitField( ACC_PUBLIC + ACC_FINAL + ACC_STATIC + ACC_ENUM, SUB, OP_OBJ, null, null );
    fv.visitEnd();
  }

  /**
   * Visit the valueOf method.
   *
   * @param  cw  The ClassVisitor to visit.
   */
  private static void visitValueOfMethod( final ClassWriter cw ) {
    final MethodVisitor mv = cw.visitMethod( ACC_PUBLIC + ACC_STATIC, "valueOf", "(Ljava/lang/String;)LOp;", null, null );
    mv.visitCode();
    mv.visitLdcInsn( Type.getType( OP_OBJ ) );
    mv.visitVarInsn( ALOAD, LOC0 );
    mv.visitMethodInsn( INVOKESTATIC, "java/lang/Enum", "valueOf", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;" );
    mv.visitTypeInsn( CHECKCAST, OP );
    mv.visitInsn( ARETURN );
    mv.visitMaxs( LOC2, LOC1 );
    mv.visitEnd();
  }

  /**
   * Visit the $VALUES synthetic enum field.
   *
   * @param  cw  The ClassVisitor to visit.
   */
  private static void visitValuesField( final ClassWriter cw ) {
    final FieldVisitor fv = cw.visitField( ACC_PRIVATE + ACC_FINAL + ACC_STATIC + ACC_SYNTHETIC, "$VALUES", OP_OBJ_ARR, null, null );
    fv.visitEnd();
  }

  /**
   * Visit the values method.
   *
   * @param  cw  The ClassVisitor to visit.
   */
  private static void visitValuesMethod( final ClassWriter cw ) {
    final MethodVisitor mv = cw.visitMethod( ACC_PUBLIC + ACC_STATIC, "values", "()[LOp;", null, null );
    mv.visitCode();
    mv.visitFieldInsn( GETSTATIC, OP, "$VALUES", OP_OBJ_ARR );
    mv.visitMethodInsn( INVOKEVIRTUAL, OP_OBJ_ARR, "clone", "()Ljava/lang/Object;" );
    mv.visitTypeInsn( CHECKCAST, OP_OBJ_ARR );
    mv.visitInsn( ARETURN );
    mv.visitMaxs( LOC1, LOC0 );
    mv.visitEnd();
  }
}
