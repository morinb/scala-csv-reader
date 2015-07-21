package com.github.morinb.scalareaders.parser

import java.io.Reader

import com.github.morinb.scalareader.log.Log
import com.github.morinb.scalareaders.csv.state._

import scala.collection.mutable

/**
 * .
 * @author Baptiste Morin
 */
class CSVParser extends Log {

  private[this] var currentState: State = Initial
  val DOUBLE_QUOTE = '"'
  val START_COMMENT = '#'
  val LINE_END = '\n'
  val SEPARATOR = ';'
  private[this] var currentPosition = 0

  def reset(): Unit = {
    currentState = Initial
    currentPosition = 0
  }


  def parse(reader: Reader): List[List[String]] = {

    val results = mutable.MutableList[List[String]]()
    val currentLine = mutable.MutableList[String]()
    val currentItem = mutable.MutableList[Char]()

    def log(s: => String): Unit = {
      debug(s"[${currentState.name}] - $s")
    }

    def store(c: Char): Unit = {
      log(s"storing '$c'")
      currentItem += c
    }

    def endItem(): Unit = {
      if (currentItem.nonEmpty) {
        currentLine += currentItem.mkString
        currentItem.clear()
      } else {
        currentLine += ""
      }
    }

    def endLine(): Unit = {
      if (currentLine.nonEmpty) {
        results += currentLine.toList
        currentLine.clear()
      } else {
        results += List()
      }
    }

    def setState(state: State): Unit = {
      log(s"State change to ${state.name}")
      currentState = state
    }


    var read = -1
    while ( {
      read = reader.read()
      read != -1
    }) {

      val next: Char = read.toChar
      println(s"c='$next'")
      currentPosition += 1

      currentState match {
        case Initial =>
          next match {
            case START_COMMENT => log(s"$next is the start comment token")
              setState(Comment)

            case DOUBLE_QUOTE => log(s"Double quote encountered")
              setState(QuotedRecord)

            case SEPARATOR => log(s"separator encountered, starting next item")
              endItem()
              setState(Record)

            case LINE_END => log(s"end of line encountered, starting next line")
              endLine()

            case c => store(c)
              setState(Record)
          }
        case Comment => log(s"Still in a comment line, ignoring token")
          next match {
            case LINE_END => log(s"Line end reached. Comment ends here")

            case _ => /* ignoring char in comment */
          }
        case Record =>
          next match {
            case LINE_END => log(s"Line end reached. Starting a new line")
              setState(Initial)
              endItem()
              endLine()

            case DOUBLE_QUOTE =>
              if (currentItem.nonEmpty) {
                log(s"Double quote encountered but not at start/end of record")
                store(next)
              } else {
                setState(QuotedRecord)
              }

            case SEPARATOR => log(s"Separator encountered. Starting next item")
              endItem()

            case c => store(c)
          }
        case QuotedRecord =>
          next match {
            case DOUBLE_QUOTE => log(s"Double quote encountered, checking next char")
              reader.mark(currentPosition)
              val peek = reader.read().toChar
              peek match {
                case DOUBLE_QUOTE => log(s"2 double quotes encountered, transforming in double quote ")
                  store(peek)
                  currentPosition += 1 // increment counter
                case c => log(s"Ending quote")
                  setState(Record)
                  reader.reset() // resetting counter to handle this char normally
              }

            case c => store(c)
          }
      }
    }

    if (currentItem.nonEmpty) {
      currentLine += currentItem.mkString
    }
    if (currentLine.nonEmpty) {
      results += currentLine.toList
    }

    results.toList
  }
}
