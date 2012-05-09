/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Locale;

/**
 * ASM for adding a Field to a R@d object.
 */
public final class ASMField implements Opcodes {

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Private to ensure this can't be instantiated by normal means.
   */
  private ASMField() {
    // Private to ensure this can't be instantiated by normal means.
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Generate the namespace support code.
   *
   * @param  cv      The ClassVisitor to visit.
   * @param  object  The name of the object to add the field to.
   * @param  name    The name of the field.
   * @param  type    The type of the field.
   */
  public static void generateFieldCode( final ClassVisitor cv, final String object, final String name, final String type ) {
    final String objType = ASMConstants.getObject( type );
    final String getterSignature = ASMConstants.getGetterSignature( type );
    final String setterSignature = ASMConstants.getSetterSignature( type );

    final FieldVisitor fv = cv.visitField( ACC_PRIVATE, name, objType, null, null );
    fv.visitEnd();
    MethodVisitor mv = cv.visitMethod( ACC_PUBLIC, getter( name ), getterSignature, null, null );
    mv.visitCode();
    mv.visitVarInsn( ALOAD, 0 );
    mv.visitFieldInsn( GETFIELD, object, name, objType );
    mv.visitInsn( ARETURN );
    mv.visitMaxs( 1, 1 );
    mv.visitEnd();
    mv = cv.visitMethod( ACC_PUBLIC, setter( name ), setterSignature, null, null );
    mv.visitCode();
    mv.visitVarInsn( ALOAD, 0 );
    mv.visitVarInsn( ALOAD, 1 );
    mv.visitFieldInsn( PUTFIELD, object, name, objType );
    mv.visitInsn( RETURN );
    mv.visitMaxs( 2, 2 );
    mv.visitEnd();
  }

  /**
   * Generate the getter name based off the identifier given.
   *
   * @param   identifier  The identifier used to generate the getter name.
   *
   * @return  The getter name.
   */
  public static String getter( final String identifier ) {
    final StringBuilder sb = new StringBuilder( "get" );
    sb.append( upperCaseFirst( identifier ) );

    return sb.toString();
  }

  /**
   * Generate the setter name based off the identifier given.
   *
   * @param   identifier  The identifier used to generate the setter name.
   *
   * @return  The setter name.
   */
  public static String setter( final String identifier ) {
    final StringBuilder sb = new StringBuilder( "set" );
    sb.append( upperCaseFirst( identifier ) );

    return sb.toString();
  }

  /**
   * Set the first letter of the given String to upper case.
   *
   * @param   string  The String to convert.
   *
   * @return  The String with the first character set to upper case.
   */
  private static String upperCaseFirst( final String string ) {
    final StringBuilder sb = new StringBuilder();
    sb.append( string.substring( 0, 1 ).toUpperCase( Locale.US ) );
    sb.append( string.substring( 1 ) );

    return sb.toString();
  }
}
