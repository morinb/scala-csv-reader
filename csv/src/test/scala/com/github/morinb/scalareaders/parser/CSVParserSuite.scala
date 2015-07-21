/*
 *     Copyright (C) 2015 morinb
 *     https://github.com/morinb
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.morinb.scalareaders.parser

import java.io.{IOException, StringReader}

import com.github.morinb.scalareader.log.Log
import org.scalatest.FunSuite

/**
 * .
 * @author Baptiste Morin
 */
class CSVParserSuite extends FunSuite with Log {

  lazy val parser = new CSVParser

  def parse(s: String): List[List[String]] = {
    parser.reset()
    parser.parse(new StringReader(s))
  }

  test("closed source") {
    println()
    info("closed source")
    val source = new StringReader("")
    source.close()


    intercept[IOException] {
      val result = parser.parse(source)
    }

  }

  test("only blanks EOF") {
    println()
    info("only blanks EOF")
    val result = parse("   ")
    assert(List(List("   ")) === result)
  }

  test("1 field EOF") {
    println()
    info("1 field EOF")
    val result = parse("a b c")
    assert(result === List(List("a b c")))
  }

  test("1 field NL") {
    println()
    info("1 field NL")
    val result = parse("a b c\n")
    assert(result === List(List("a b c")))
  }

  test("2 fields EOF") {
    println()
    info("2 fields EOF")
    val result = parse("a b c;d e f")
    assert(result === List(List("a b c", "d e f")))
  }

  test("many fields in double quote NL many fields without double quote EOF") {
    println()
    info("many fields in double quote NL many fields without double quote EOF")
    val result = parse("\"aaa\";\"bbb\";\"ccc\"\nzzz;yyy;xxx")
    assert(result === List(List("aaa", "bbb", "ccc"), List("zzz", "yyy", "xxx")))
  }

  test("NL in fields") {
    println()
    info("NL in fields")
    val result = parse("\"aaa\";\"b\nbb\";\"ccc\"\nzzz;yyy;xxx")
    assert(result === List(List("aaa", "b\nbb", "ccc"), List("zzz", "yyy", "xxx")))
  }

  test("1 field in double quote") {
    println()
    info("1 field in double quote")
    val result = parse("\"ab c \nd\"")
    assert(result === List(List("ab c \nd")))
  }

  test("1 field with double double quote") {
    println()
    info("1 field with double double quote")
    val result = parse("\"ab c \"\"pouet\"\" d\"")
    assert(result === List(List("ab c \"pouet\" d")))
  }

  test("Empty field") {
    println()
    info("Empty field")
    val result = parse("\n")
    assert(result === List(List()))
  }
  test("2 blank fields") {
    println()
    info("2 blank fields")
    val result = parse("   \n   ")
    assert(result === List(List("   "), List("   ")))
  }
  test("2 blank with empty field fields") {
    println()
    info("2 blank fields")
    val result = parse(";\n")
    assert(result === List(List("", "")))
  }

  test("Quoted string") {
    println()
    info("Quoted String")
    val result = parse("\"a \n\"\"b\"\" c  \"\n")
    assert(result === List(List("a \n\"b\" c  ")))
  }

  test("4 fields") {
    println()
    info("4 fields")
    val result = parse("a b c;d e f\nghi;jkl")
    assert(result === List(List("a b c", "d e f"), List("ghi", "jkl")))
  }

  test("4 fields with spaces") {
    println()
    info("4 fields with spaces")
    val result = parse("   \"a b c \"   ;   \"d e f\"   \n" +
      "   \"ghi\"   ;   \"jkl\"   ")
    assert(result === List(List("   \"a b c \"   ", "   \"d e f\"   "),
      List("   \"ghi\"   ", "   \"jkl\"   ")))
  }

  test("4 fields with spaces and NL") {
    println()
    info("4 fields with spaces and NL")
    val result = parse("  \"a \nb c\"  ;  \"d e\n f\"  \n" +
      "  \" g\nhi \"  ;  \"j\n\"\"kl\"  ")

    assert(result === List(List("  \"a "),List("b c\"  ", "  \"d e"), List(" f\"  "), List("  \" g"), List("hi \"  ", "  \"j"), List("kl\"  ")))
  }

  ignore("char before/after double quote") {
    println()
    info("char before/after double quote")
    val result = parse("ignored\"a \nb c\"ignored,d e f\n" + "ignored\" g\nhi \"ignored,ignored\"j\n\"\"kl\"ignored")

    assert(result===List())
  }

  test("complex 1") {
    println()
    info("complex 1")

    val result = parse("a b \"\"c;\"d\"\"ef\";qsd\n\"g\nhi\";\"j\n\"\"\nkl\";zsx")
    assert(result ===
      List(List("a b \"\"c","d\"ef","qsd"), List("g\nhi", "j\n\"\nkl","zsx"))
    )

  }

  test("many fields with emtpy fields") {
    println()
    info("many fields with emtpy fields")

    val result = parse("a;b;;d;e\nA;;C;D;E")
    assert(result === List(List("a","b","","d","e"), List("A","","C","D","E")))
  }

  test("specifying separator") {
    println()
    info("specifying separator")
    val parser = new CSVParser(separator = ',')

    val result = parser.parse(new StringReader("a b c,d e f"))

    assert(result === List(List("a b c", "d e f")))
  }
}
