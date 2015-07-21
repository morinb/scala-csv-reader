/*Copyright*/

package com.github.morinb.scalareader.log

import org.slf4j.{Logger, LoggerFactory}

/**
 *
 * @author morinb.
 */
trait Log {
  lazy val log: Logger = LoggerFactory.getLogger(getClass.getName)

  def error(s: => String): Unit = {
    if(log.isErrorEnabled) {
      log.error(s)
    }
  }
  def warn(s: => String): Unit = {
    if(log.isWarnEnabled) {
      log.warn(s)
    }
  }

  def info(s: => String): Unit = {
    if(log.isInfoEnabled) {
      log.info(s)
    }
  }

  def debug(s: => String): Unit = {
    if(log.isDebugEnabled) {
      log.debug(s)
    }
  }

  def trace(s: => String): Unit = {
    if(log.isTraceEnabled) {
      log.trace(s)
    }
  }



}
