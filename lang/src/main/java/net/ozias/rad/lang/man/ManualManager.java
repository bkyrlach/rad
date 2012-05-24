/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.man;

import java.util.Map;
import java.util.TreeMap;

/**
 * The R@d Manual Content Manager.
 */
public final class ManualManager {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** The Help content map. */
  private static final Map<String, Object> MANUAL = new TreeMap<String, Object>();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Private to ensure this class cannot be instantiated through normal means.
   */
  private ManualManager() {
    // Private to ensure this class cannot be instantiated through normal means.
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Add man content to the manager.
   *
   * @param  key    The man keyword. This is used by the man command to lookup the manual content.
   * @param  value  The man content. This should be a string containing the man information, or another map in the case of complex man statements.
   */
  public static void addManualContent( final String key, final Object value ) {
    MANUAL.put( key, value );
  }

  /**
   * Get the manual content for the given key(s).
   *
   * @param   keys  The key(s) to use to lookup the manual content.
   *
   * @return  The manual content.
   */
  public static String getManualContent( final String... keys ) {
    String content = ( String ) MANUAL.get( keys[0] );

    if ( content == null ) {
      content = "No manual entry for " + keys;
    }

    return content;
  }
}
