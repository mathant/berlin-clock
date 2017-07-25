package com.example.berlinclock

import org.scalatest.{FreeSpec, Matchers}

import scala.util.{Failure, Try}

class BerlinClockMapperSpec extends FreeSpec with Matchers {

  val underTest = BerlinClockMapper

  "seconds parameter" - {
    " passed as 1 should return unlit state " in {
      underTest.mapSeconds(1) shouldBe O
    }
    " passed as 0 should return lit state " in {
      underTest.mapSeconds(0) shouldBe Y
    }
  }

  "hours parameter" - {
    " to the top most line" - {
      "passed as 3 should return no lamp lit " in {
        underTest.mapHoursTopLine(3) shouldBe List(O, O, O, O)
      }
      "passed as 5 should return first lamp lit " in {
        underTest.mapHoursTopLine(5) shouldBe List(R, O, O, O)
      }
      "passed as 7 should return first lamp lit " in {
        underTest.mapHoursTopLine(7) shouldBe List(R, O, O, O)
      }
      "passed as 16 should return first three lamp lit " in {
        underTest.mapHoursTopLine(16) shouldBe List(R, R, R, O)
      }
      "passed as 23 should return all lamp lit" in {
        underTest.mapHoursTopLine(23) shouldBe List(R, R, R, R)
      }
    }

    " to the second  line" - {
      "passed as 3 should return three lamp lit " in {
        underTest.mapHoursSecondLine(3) shouldBe List(R, R, R, O)
      }
      "passed as 5 should return no lamp lit " in {
        underTest.mapHoursSecondLine(5) shouldBe List(O, O, O, O)
      }
      "passed as 7 should return two lamp lit " in {
        underTest.mapHoursSecondLine(7) shouldBe List(R, R, O, O)
      }
      "passed as 16 should return first one lamp lit " in {
        underTest.mapHoursSecondLine(16) shouldBe List(R, O, O, O)
      }
      "passed as 19 should return all lamp lit" in {
        underTest.mapHoursSecondLine(19) shouldBe List(R, R, R, R)
      }
    }
  }

  "minutes parameter" - {
    " to the second line " - {
      " passed 0 should return no lamps lit" in {
        underTest.mapMinutesSecondLine(0) shouldBe List(O, O, O, O)
      }
      "passed as 3 should return three lamp lit " in {
        underTest.mapMinutesSecondLine(3) shouldBe List(Y, Y, Y, O)
      }
      "passed as 5 should return no lamp lit " in {
        underTest.mapMinutesSecondLine(5) shouldBe List(O, O, O, O)
      }
      "passed as 7 should return two lamp lit " in {
        underTest.mapMinutesSecondLine(7) shouldBe List(Y, Y, O, O)
      }
      "passed as 16 should return first one lamp lit " in {
        underTest.mapMinutesSecondLine(16) shouldBe List(Y, O, O, O)
      }
      "passed as 19 should return all lamp lit" in {
        underTest.mapMinutesSecondLine(19) shouldBe List(Y, Y, Y, Y)
      }
    }
    " to the top line " - {
      " passed 0 should return no lamps lit" in {
        underTest.mapMinutesTopLine(0) shouldBe List.fill(11)(O)
      }
      "passed as 3 should return no lamp lit " in {
        underTest.mapMinutesTopLine(3) shouldBe List.fill(11)(O)
      }
      "passed as 5 should return first lamp lit " in {
        underTest.mapMinutesTopLine(5) shouldBe List(Y, O, O, O, O, O, O, O, O, O, O)
      }
      "passed as 7 should return two lamp lit " in {
        underTest.mapMinutesTopLine(7) shouldBe List(Y, O, O, O, O, O, O, O, O, O, O)
      }
      "passed as 16 should return first three lamp lit " in {
        underTest.mapMinutesTopLine(16) shouldBe List(Y, Y, R, O, O, O, O, O, O, O, O)
      }
      "passed as 59 should return all lamp lit" in {
        underTest.mapMinutesTopLine(59) shouldBe List(Y, Y, R, Y, Y, R, Y, Y, R, Y, Y)
      }
    }
  }

  "invalid input to clock" - {
    "with invalid hour should throw error" in {
      verifyException(underTest.mapToClock(234, 30, 2), "Hours")
      verifyException(underTest.mapToClock(-3, 30, 2), "Hours")
    }
    "with invalid minute should throw error" in {
      verifyException(underTest.mapToClock(15, 330, 2), "Minutes")
      verifyException(underTest.mapToClock(13, -3, 2), "Minutes")
    }
    "with invalid second should throw error" in {
      verifyException(underTest.mapToClock(2, 23, 222), "Seconds")
      verifyException(underTest.mapToClock(13, 3, -2), "Seconds")
    }

  }
  "valid input to clock" - {
    " 0 hours, 0 minutes, 0 seconds should return correct value" in {
      val expected = List("Y", "OOOO", "OOOO", "OOOOOOOOOOO", "OOOO").mkString(underTest.lineSeparator)
      underTest.mapToClock(0,0,0).get shouldBe expected
    }
    " 23 hours, 59 minutes, 59 seconds should return correct value" in {
      val expected = List("O", "RRRR", "RRRO", "YYRYYRYYRYY", "YYYY").mkString(underTest.lineSeparator)
      underTest.mapToClock(23,59,59).get shouldBe expected
    }
    " 17 hours, 48 minutes, 1 seconds should return correct value" in {
      val expected = List("O", "RRRO", "RROO", "YYRYYRYYROO", "YYYO").mkString(underTest.lineSeparator)
      underTest.mapToClock(17,48,1).get shouldBe expected
    }

  }

  private def verifyException(res: Try[String], matchString: String) = res match {
    case Failure(e: IllegalArgumentException) => assert(e.getMessage.contains(matchString))
    case _ => fail()
  }
}
