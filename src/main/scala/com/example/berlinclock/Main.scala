package com.example.berlinclock


import scala.util.{Failure, Success}

trait ConsoleInterface {

  def doInputOutput() = {
    println("Please enter input in format 'hours(24-based):minutes:seconds' (example: '23:48:01') :")
    val inputs = scala.io.StdIn.readLine().split(":").map(_.toInt)
    val res = BerlinClockMapper.mapToClock(inputs(0), inputs(1), inputs(2))
    res match {
      case Success(str) => println(str)
      case Failure(e: Throwable) => {
        println("Please re-run application with valid input!")
        e.printStackTrace
      }
    }
  }

}

object Main extends ConsoleInterface {

  def main(args: Array[String]): Unit = doInputOutput()

}
