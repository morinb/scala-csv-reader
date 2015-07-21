package com.github.morinb.scalareaders.csv.state

/**
 * Possible States of the State Machine
 * @author Baptiste Morin
 */
sealed trait State {
  def name: String
}

object Initial extends State {
  def name = "Initial"
}

object Comment extends State{
  def name = "Comment"
}

object Record extends State{
  def name = "Record"
}

object QuotedRecord extends State{
  def name = "QuotedRecord"
}
