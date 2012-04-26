/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.log4j.Logger;

import org.testng.annotations.Test;

import java.io.FileNotFoundException;

/**
 * Test cases for the Parser class.
 */
@Test( groups = { "DEFAULT" } )
public class TestParser {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** log4j Logger. */
  private static final Logger LOG = Logger.getLogger( TestParser.class );

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Test the JavaCC generated parser.
   *
   * @throws  ParseException
   * @throws  TokenMgrError
   * @throws  FileNotFoundException
   */
  @Test public void testParser_test() throws ParseException, TokenMgrError, FileNotFoundException {
    LOG.info( "Parsing test.rad" );
    final SimpleNode rootNode = Parser.parseFile( TestParser.class.getResourceAsStream( "test.rad" ) );
    assertTrue( rootNode instanceof ASTrad );
    final ASTrad rad = ( ASTrad ) rootNode;
    assertEquals( ( ( ASTNamespace ) rad.jjtGetChild( 0 ) ).jjtGetValue(), "net.ozias.rad.lang" );
    assertEquals( ( ( ASTUse ) rad.jjtGetChild( 1 ) ).jjtGetValue(), "java.util.List" );
    assertEquals( ( ( ASTUse ) rad.jjtGetChild( 2 ) ).jjtGetValue(), "java.util.ArrayList" );
    final ASTBlock block = ( ASTBlock ) rad.jjtGetChild( 3 );
    assertEquals( ( ( SimpleNode ) block.jjtGetChild( 0 ) ).jjtGetValue(), "Radical" );
    assertEquals( ( ( SimpleNode ) block.jjtGetChild( 1 ) ).jjtGetValue(), "EightiesSlangTerm" );
    final ASTBlock block1 = ( ASTBlock ) rad.jjtGetChild( 4 );
    assertEquals( ( ( SimpleNode ) block1.jjtGetChild( 0 ) ).jjtGetValue(), "Righteous" );
    assertEquals( ( ( SimpleNode ) block1.jjtGetChild( 1 ) ).jjtGetValue(), "EightiesSlangTerm" );
    final ASTBlock block2 = ( ASTBlock ) rad.jjtGetChild( 5 );
    assertEquals( ( ( SimpleNode ) block2.jjtGetChild( 0 ) ).jjtGetValue(), "Whassup" );
    assertEquals( ( ( SimpleNode ) block2.jjtGetChild( 1 ) ).jjtGetValue(), "NinetiesSlangTerm" );
  }

  /**
   * Test the JavaCC generated parser.
   *
   * @throws  FileNotFoundException
   * @throws  ParseException
   * @throws  TokenMgrError
   */
  @Test( expectedExceptions = ParseException.class )
  public void testParser_test1() throws FileNotFoundException, ParseException, TokenMgrError {
    LOG.info( "Parsing test1.rad" );
    final SimpleNode rootNode = Parser.parseFile( TestParser.class.getResourceAsStream( "test1.rad" ) );
    assertTrue( rootNode instanceof ASTrad );
    final ASTrad rad = ( ASTrad ) rootNode;
    assertEquals( ( ( ASTNamespace ) rad.jjtGetChild( 0 ) ).jjtGetValue(), "net.ozias.rad.lang" );
  }
}
