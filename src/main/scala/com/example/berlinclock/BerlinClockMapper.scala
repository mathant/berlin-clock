package com.example.berlinclock

import scala.util.Try

object BerlinClockMapper {


  private val fourLampsAllOff = List.fill(4)(O)

  private val hoursTopLineAllOn = List.fill(4)(R)

  private val hoursSecondLineAllOn = List.fill(4)(R)

  private val minutesSecondLineAllOn = List.fill(4)(Y)

  private val minutesTopLineAllOff = List.fill(11)(O)

  private val minutesTopLineAllOn = List.range(1, 12).map(x => if (x % 3 == 0) R else Y)

  val lineSeparator: String = sys.props("line.separator")


  def mapSeconds(seconds: Int): LampState = if (seconds % 2 == 0) Y else O

  private def mapToLitStates(allOnList: List[LampState], allOffList: List[LampState], numberOfLampsOn: Int) =
    allOnList.take(numberOfLampsOn) ::: allOffList.drop(numberOfLampsOn)

  def mapHoursTopLine(hours: Int): List[LampState] =
    mapToLitStates(hoursTopLineAllOn, fourLampsAllOff, hours / 5)

  def mapHoursSecondLine(hours: Int): List[LampState] =
    mapToLitStates(hoursTopLineAllOn, fourLampsAllOff, hours % 5)

  def mapMinutesSecondLine(minutes: Int): List[LampState] =
    mapToLitStates(minutesSecondLineAllOn, fourLampsAllOff, minutes % 5)

  def mapMinutesTopLine(minutes: Int): List[LampState] =
    mapToLitStates(minutesTopLineAllOn, minutesTopLineAllOff, minutes / 5)

  def mapToClock(hours: Int, minutes: Int, seconds: Int): Try[String] = Try {

    require(hours >= 0 && hours < 24, "Hours should be in range of 0 to 23 (inclusive)")
    require(minutes >= 0 && minutes < 60, "Minutes should be in range of 0 to 59 (inclusive)")
    require(seconds >= 0 && seconds < 60, "Seconds should be in range of 0 to 59 (inclusive)")

    val secondsState = mapSeconds(seconds).toString
    val hoursTopLineState = mapHoursTopLine(hours).mkString
    val hoursSecondLineState = mapHoursSecondLine(hours).mkString
    val minutesTopLineState = mapMinutesTopLine(minutes).mkString
    val minutesSecondLineState = mapMinutesSecondLine(minutes).mkString

    List(secondsState, hoursTopLineState, hoursSecondLineState, minutesTopLineState, minutesSecondLineState)
      .mkString(lineSeparator)
  }

}
