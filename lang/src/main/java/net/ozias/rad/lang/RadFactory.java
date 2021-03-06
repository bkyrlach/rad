/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang;

import net.ozias.rad.RadClassLoader;
import net.ozias.rad.lang.asm.ASMRadShell;
import net.ozias.rad.lang.asm.adapter.AbstractChainableAdapter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.List;

/**
 * The factory used to generate R@d objects.
 */
public class RadFactory {

  //~ Instance fields ------------------------------------------------------------------------------------------------------------------------------------------

  /** Adapter list. This is used to modify the base R@d objectName dynamically. */
  private final transient List<AbstractChainableAdapter> adapters = new ArrayList<AbstractChainableAdapter>();
  /** The objectName this factory works with. */
  private final transient String objectName;
  /** The current byte code generated by the factory. */
  private transient byte[] currentByteCode;

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new RadFactory objectName.
   *
   * @param  objectName  The objectName this factory works with.
   */
  public RadFactory( final String objectName ) {
    this.objectName = objectName;
    final ClassWriter cw = new ClassWriter( 0 );
    ASMRadShell.generateShell( cw, objectName );
    this.currentByteCode = cw.toByteArray();
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Add the given flag to the list of current flags.
   *
   * @param   adapter  flag The flag to add.
   *
   * @return  true if the flag was added, false otherwise.
   */
  public boolean addAdapter( final AbstractChainableAdapter adapter ) {
    boolean retbool = false;

    if ( !contains( adapter ) ) {
      adapters.add( adapter );
      retbool = true;
    }

    return retbool;
  }

  /**
   * Does this factory have the given adapter.
   *
   * @param   adapter  The adapter to check.
   *
   * @return  true if this factory contains the given adapter, false otherwise.
   */
  public boolean contains( final AbstractChainableAdapter adapter ) {
    return adapters.contains( adapter );
  }

  /**
   * Create a new instance of the R@d objectName.
   *
   * @return  A new instance of the R@d objectName if everything happened according to plan.
   *
   * @throws  ClassNotFoundException     Thrown if the base class cannot be found for some reason.
   * @throws  NoSuchMethodException      Thrown if the constructor cannot be found.
   * @throws  InstantiationException     Thrown if the base class cannot be instantiated.
   * @throws  IllegalAccessException     Thrown if the constructor cannot be accessed.
   * @throws  InvocationTargetException  Thrown for some damn reason.
   */
  public Object newInstance() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
    this.currentByteCode = generateObject();
    final RadClassLoader classLoader = new RadClassLoader( currentByteCode );
    final Class<?> baseClazz = classLoader.loadClass( this.objectName );
    final Constructor<?> constructor = baseClazz.getDeclaredConstructor( ( Class<?>[] ) null );

    return constructor.newInstance( ( Object[] ) null );
  }

  /**
   * Remove a flag from the current flags.
   *
   * @param   adapter  flag The flag to remove.
   *
   * @return  true if the flag was removed, false otherwise.
   */
  public boolean removeAdapter( final AbstractChainableAdapter adapter ) {
    boolean retbool = false;

    if ( contains( adapter ) ) {
      adapters.remove( adapter );
      retbool = true;
    }

    return retbool;
  }

  /**
   * This method generates the bytecode for the base R@d objectName.
   *
   * @return  The bytecode of the generated base R@d objectName.
   */
  private byte[] generateObject() {
    final ClassWriter cw = new ClassWriter( 0 );
    final ClassReader cr = new ClassReader( currentByteCode );
    ClassVisitor current = cw;

    for ( final AbstractChainableAdapter adapter : adapters ) {
      current = adapter.setClassVisitor( current );
    }

    cr.accept( current, 0 );

    return cw.toByteArray();
  }
}
