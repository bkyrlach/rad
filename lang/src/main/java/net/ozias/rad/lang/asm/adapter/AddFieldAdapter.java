/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.asm.adapter;

import net.ozias.rad.lang.asm.ASMField;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * An ASM adapter used to add a field to an object.
 */
public class AddFieldAdapter extends AbstractChainableAdapter implements Opcodes {

  //~ Instance fields ------------------------------------------------------------------------------------------------------------------------------------------

  /** The field fieldName. */
  private final transient String fieldName;
  /** The object fieldName. */
  private final transient String object;
  /** The field type. */
  private final transient String type;

  /** Is the field already present. */
  private transient boolean isFieldPresent = false;
  /** Is the getter method already present. */
  private transient boolean isGetterPresent = false;
  /** Is the setter method already present. */
  private transient boolean isSetterPresent = false;

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new AddFieldAdapter object.
   *
   * @param  object     The name of the object you wish to add the field to.
   * @param  fieldName  The field name.
   * @param  type       The type of the field.
   */
  public AddFieldAdapter( final String object, final String fieldName, final String type ) {
    this.object = object;
    this.fieldName = fieldName;
    this.type = type;
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.objectweb.asm.ClassVisitor#visitEnd()
   */
  @Override public void visitEnd() {

    if ( !isFieldPresent && !isGetterPresent && !isSetterPresent ) {
      ASMField.generateFieldCode( cv, object, fieldName, type );
    }

    cv.visitEnd();
  }

  /**
   * @see  org.objectweb.asm.ClassVisitor#visitField(int, java.lang.String, java.lang.String, java.lang.String, java.lang.Object)
   */
  @Override public FieldVisitor visitField( final int access, final String name, final String desc, final String signature, final Object value ) {

    if ( this.fieldName.equals( name ) ) {
      isFieldPresent = true;
    }

    return super.visitField( access, name, desc, signature, value );
  }

  /**
   * @see  org.objectweb.asm.ClassVisitor#visitMethod(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String[])
   */
  @Override public MethodVisitor visitMethod( final int access, final String name, final String desc, final String signature, final String[] exceptions ) {

    if ( ASMField.getter( this.fieldName ).equals( name ) ) {
      isGetterPresent = true;
    }

    if ( ASMField.setter( this.fieldName ).equals( name ) ) {
      isSetterPresent = true;
    }

    return super.visitMethod( access, name, desc, signature, exceptions );
  }

}
