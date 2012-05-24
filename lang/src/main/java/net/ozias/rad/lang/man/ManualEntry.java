/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.man;

/**
 * Indicates that a class supports generating a R@d manual entry.
 */
public interface ManualEntry {

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Get the manual entry.
   *
   * @return  The manual entry.
   */
  String getManualEntry();

  /**
   * Get the manual key.
   *
   * @return  Return the manual entry.
   */
  String getManualKey();
}
