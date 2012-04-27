/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad;

import org.apache.log4j.Logger;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The R@d Bootstrap class.
 */
public class Bootstrap implements Runnable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** log4j logger. */
  private static final Logger LOG = Logger.getLogger( Bootstrap.class );
  /** Executor Service for threads. */
  private static final ExecutorService EXECSERVICE;
  /** Futures list. */
  private static final Collection<Future<?>> FUTURES = new LinkedList<Future<?>>();

  static {
    EXECSERVICE = new ThreadPoolExecutor( 10, 50, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>( 20 ) );
    ( ( ThreadPoolExecutor ) EXECSERVICE ).allowCoreThreadTimeOut( true );
  }

  //~ Instance fields ------------------------------------------------------------------------------------------------------------------------------------------

  /** TODO: DOCUMENT ME! */
  private RadClassLoader radClassLoader = new RadClassLoader();

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Add a runnable to the futures list and submit it for execution.
   *
   * @param  runnable  The runnable to add.
   */
  public static void addRunnable( final Runnable runnable ) {
    FUTURES.add( EXECSERVICE.submit( runnable ) );
  }

  /**
   * main method.
   *
   * @param  args  Command line arguments.
   */
  public static void main( final String... args ) {
    ( new Thread( new Bootstrap() ) ).start();
  }

  /**
   * @see  java.lang.Runnable#run()
   */
  @Override public void run() {
    generateBase();
    addRunnable( new RadIntepreter() );

    for ( final Future<?> future : FUTURES ) {

      try {
        future.get();
      } catch ( InterruptedException e ) {
        LOG.error( "InterruptedException", e );
      } catch ( ExecutionException e ) {
        LOG.error( "ExecutionException", e );
      }
    }
  }

  /**
   * TODO: DOCUMENT ME!
   */
  private void generateBase() {
    final ClassNode cn = new ClassNode();
    cn.version = Opcodes.V1_7;
    cn.name = "net/ozias/rad/lang/Base";
    cn.superName = "java/lang/Object";
    final ClassWriter cw = new ClassWriter( ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES );
    cn.accept( cw );
    final byte[] bytecode = cw.toByteArray();
    radClassLoader.loadClass( cn.name.replace('/', '.'), bytecode );
  }
}
