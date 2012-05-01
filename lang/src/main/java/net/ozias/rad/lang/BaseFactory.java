/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang;

import static net.ozias.rad.lang.BaseMethodFlags.ADD_METHOD;
import static net.ozias.rad.lang.BaseMethodFlags.SUB_METHOD;

import net.ozias.rad.RadClassLoader;
import net.ozias.rad.lang.asm.ASMBase;
import net.ozias.rad.lang.asm.adapter.AddOpsAdapter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.EnumSet;
import java.util.Locale;

/**
 * Base Factory.
 *
 * <p>This class is used to regenerate the base R@d object when new functionality is used.</p>
 */
public final class BaseFactory {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Name of the base class for use with class loaders. */
  private static final String BASE_CN = "net.ozias.rad.lang.Base";
  /** Name of the base class for use with ASM. */
  public static final String BASE_ASM_CN = BASE_CN.replace( '.', '/' );
  /** The current byte code generated by the factory. */
  private static byte[] currentByteCode;
  /** An empty set of BaseMethodFlags. */
  private static EnumSet<BaseMethodFlags> currentFlags = EnumSet.noneOf( BaseMethodFlags.class );

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new BaseFactory object.
   */
  private BaseFactory() {
    // Ensures this cannot be instantiated through normal means.
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Add the given flag to the list of current flags.
   *
   * @param  flag  The flag to add.
   */
  public static void addFlag( final String flag ) {
    final StringBuilder sb = new StringBuilder( flag.toUpperCase( Locale.US ) ).append( "_METHOD" );

    if ( !currentFlags.contains( flag ) ) {
      currentFlags.add( BaseMethodFlags.valueOf( sb.toString() ) );
    }
  }

  /**
   * Create a new instance of the base object.
   *
   * @return  A new instance of the base object if everything happened according to plan.
   *
   * @throws  ClassNotFoundException     Thrown if the base class cannot be found for some reason.
   * @throws  NoSuchMethodException      Thrown if the constructor cannot be found.
   * @throws  InstantiationException     Thrown if the base class cannot be instantiated.
   * @throws  IllegalAccessException     Thrown if the constructor cannot be accessed.
   * @throws  InvocationTargetException  Thrown for some damn reason.
   */
  public static Object newInstance() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException,
    InvocationTargetException {
    currentByteCode = generateBaseObject();
    final RadClassLoader classLoader = new RadClassLoader( currentByteCode );
    final Class<?> baseClazz = classLoader.loadClass( BASE_CN );
    final Constructor<?> constructor = baseClazz.getDeclaredConstructor( ( Class<?>[] ) null );

    return constructor.newInstance( ( Object[] ) null );
  }

  /**
   * Remove a flag from the current flags.
   *
   * @param  flag  The flag to remove.
   */
  public static void removeFlag( final BaseMethodFlags flag ) {

    if ( currentFlags.contains( flag ) ) {
      currentFlags.remove( flag );
    }
  }

  /**
   * This method generates the bytecode for the base R@d object.
   *
   * @return  The bytecode of the generated base R@d object.
   */
  private static byte[] generateBaseObject() {
    final ClassWriter cw = new ClassWriter( 0 );

    if ( currentFlags.isEmpty() ) {
      ASMBase.generateBaseShell( cw );
    } else {
      ClassVisitor current = cw;
      final ClassReader cr = new ClassReader( currentByteCode );

      if ( currentFlags.contains( ADD_METHOD ) || currentFlags.contains( SUB_METHOD ) ) {
        current = new AddOpsAdapter( current );
      }

      cr.accept( current, 0 );

    }

    return cw.toByteArray();
  }
}
