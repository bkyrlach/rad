/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad;

/**
 * TODO: DOCUMENT ME!
 */
public class RadClassLoader extends ClassLoader {

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * TODO: DOCUMENT ME!
   *
   * @param   name      TODO: DOCUMENT ME!
   * @param   bytecode  TODO: DOCUMENT ME!
   *
   * @return  TODO: DOCUMENT ME!
   */
  public Class<?> loadClass( final String name, final byte[] bytecode ) {
    return super.defineClass( name, bytecode, 0, bytecode.length );
  }
}
