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
