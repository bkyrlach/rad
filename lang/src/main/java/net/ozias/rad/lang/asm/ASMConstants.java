/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.asm;

/**
 * Some common ASM constant value.
 */
public final class ASMConstants {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Object Identifier. */
  private static final String OBJ = "L";
  /** Semicolon. */
  private static final String SEMI = ";";
  /** Array Identifier. */
  private static final String ARR = "[";
  /** Default constructor. */
  public static final String INIT = "<init>";
  /** TODO: DOCUMENT ME! */
  public static final String JAVA_LANG_BYTE = "java/lang/Byte";
  /** TODO: DOCUMENT ME! */
  public static final String JAVA_LANG_DOUBLE = "java/lang/Double";
  /** TODO: DOCUMENT ME! */
  public static final String JAVA_LANG_FLOAT = "java/lang/Float";
  /** TODO: DOCUMENT ME! */
  public static final String JAVA_LANG_INTEGER = "java/lang/Integer";
  /** TODO: DOCUMENT ME! */
  public static final String JAVA_LANG_LONG = "java/lang/Long";
  /** TODO: DOCUMENT ME! */
  public static final String JAVA_LANG_NUMBER = "java/lang/Number";
  /** TODO: DOCUMENT ME! */
  public static final String JAVA_LANG_SHORT = "java/lang/Short";
  /** TODO: DOCUMENT ME! */
  public static final String JAVA_UTIL_ITERATOR = "java/util/Iterator";
  /** TODO: DOCUMENT ME! */
  public static final String JAVA_UTIL_LIST = "java/util/List";

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Private to ensure this can't be instantiated by normal means.
   */
  private ASMConstants() {
    // Private to ensure this can't be instantiated by normal means.
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Get the object representation of the given name.
   *
   * @param   name  The name to make into an Object representation.
   *
   * @return  The object representation of the given name.
   */
  public static String getObject( final String name ) {
    return new StringBuilder( OBJ ).append( name ).append( SEMI ).toString();
  }

  /**
   * Get the array of objects representation of the given name.
   *
   * @param   name  The name to make into an Object array representation.
   *
   * @return  The array of objects representation of the given name.
   */
  public static String getObjectArray( final String name ) {
    return new StringBuilder( ARR ).append( OBJ ).append( name ).append( SEMI ).toString();
  }
}
