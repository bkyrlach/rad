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
  /** Local Variable 0. */
  public static final int LOC0 = 0;
  /** Local Variable 1. */
  public static final int LOC1 = 1;
  /** Local Variable 2. */
  public static final int LOC2 = 2;
  /** Local Variable 3. */
  public static final int LOC3 = 3;
  /** Local Variable 4. */
  public static final int LOC4 = 4;
  /** Local Variable 5. */
  public static final int LOC5 = 5;
  /** Local Variable 6. */
  public static final int LOC6 = 6;
  /** Local Variable 7. */
  public static final int LOC7 = 7;
  /** Local Variable 8. */
  public static final int LOC8 = 8;
  /** Local Variable 9. */
  public static final int LOC9 = 9;
  /** Local Variable 10. */
  public static final int LOC10 = 10;
  /** Local Variable 11. */
  public static final int LOC11 = 11;
  /** Default constructor. */
  public static final String INIT = "<init>";
  /** Byte ASM constant. */
  public static final String JAVA_LANG_BYTE = "java/lang/Byte";
  /** Double ASM constant. */
  public static final String JAVA_LANG_DOUBLE = "java/lang/Double";
  /** Float ASM constant. */
  public static final String JAVA_LANG_FLOAT = "java/lang/Float";
  /** Integer ASM constant. */
  public static final String JAVA_LANG_INTEGER = "java/lang/Integer";
  /** Long ASM constant. */
  public static final String JAVA_LANG_LONG = "java/lang/Long";
  /** Number ASM constant. */
  public static final String JAVA_LANG_NUMBER = "java/lang/Number";
  /** Short ASM constant. */
  public static final String JAVA_LANG_SHORT = "java/lang/Short";
  /** Iterator ASM constant. */
  public static final String JAVA_UTIL_ITERATOR = "java/util/Iterator";
  /** List ASM constant. */
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
