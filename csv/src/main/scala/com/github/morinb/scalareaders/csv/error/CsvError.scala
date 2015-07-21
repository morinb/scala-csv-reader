package com.github.morinb.scalareaders.csv.error

/**
 * .
 * @author Baptiste Morin
 */
sealed trait CsvError

object NoError extends CsvError

object ErrorOther extends CsvError

object ErrorUserAborted extends CsvError

object ErrorMalformedQuotedString extends CsvError

object ErrorUnterminatedQuotedString extends CsvError

object ErrorEmptyFile extends CsvError
