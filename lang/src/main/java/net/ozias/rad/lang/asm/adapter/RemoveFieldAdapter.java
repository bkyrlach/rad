/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.asm.adapter;

import net.ozias.rad.lang.asm.ASMConstants;
import net.ozias.rad.lang.asm.ASMField;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * An ASM adapter used to remove a field (plus getter/setter) to an object.
 */
public class RemoveFieldAdapter extends AbstractChainableAdapter implements Opcodes {

  //~ Instance fields ------------------------------------------------------------------------------------------------------------------------------------------

  /** The name of the field to be removed. */
  private final transient String fieldName;
  /** The getter name. */
  private final transient String getter;
  /** The getter signature. */
  private final transient String getterSignature;
  /** The setter name. */
  private final transient String setter;
  /** The setter signature. */
  private final transient String setterSignature;

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new RemoveFieldAdapter object.
   *
   * @param  fieldName  The name of the field to remove.
   * @param  type       The type of the field to remove.
   */
  public RemoveFieldAdapter( final String fieldName, final String type ) {
    this.fieldName = fieldName;
    this.getter = ASMField.getter( fieldName );
    this.getterSignature = ASMConstants.getGetterSignature( type.replace( '.', '/' ) );
    this.setter = ASMField.setter( fieldName );
    this.setterSignature = ASMConstants.getSetterSignature( type.replace( '.', '/' ) );
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.objectweb.asm.ClassVisitor#visitField(int, java.lang.String, java.lang.String, java.lang.String, java.lang.Object)
   */
  @Override public FieldVisitor visitField( final int access, final String name, final String desc, final String signature, final Object value ) {
    FieldVisitor retvisitor = null;

    if ( !this.fieldName.equals( name ) ) {
      retvisitor = super.visitField( access, name, desc, signature, value );
    }

    return retvisitor;
  }

  /**
   * @see  org.objectweb.asm.ClassVisitor#visitMethod(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String[])
   */
  @Override public MethodVisitor visitMethod( final int access, final String name, final String desc, final String signature, final String[] exceptions ) {
    MethodVisitor retvisitor = super.visitMethod( access, name, desc, signature, exceptions );

    if ( ( this.getter.equals( name ) && this.getterSignature.equals( desc ) ) || ( this.setter.equals( name ) && this.setterSignature.equals( desc ) ) ) {
      retvisitor = null;
    }

    return retvisitor;
  }

}
