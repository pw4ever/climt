package climt

import scala.language.reflectiveCalls

import org.rogach.scallop._

class ParseOpts private[climt](args: Seq[String]) extends ScallopConf(args) {
  version(
    s"""|${Globals.appname} ${Globals.appver} ${Globals.maintainerName} <${Globals.maintainerEmail}>""".stripMargin
  )

  banner(
    s"""
       |Command-Line Interface Multi-Tool (CLIMT) is a collection of CLI utilities.
       |""".stripMargin
  )

  val verbosity = tally(name = "verbose", short = 'v', descr = "Increase verbosity.")

  val genStrongPassword = new Subcommand("gp", "genpass", "genStrongPassword") {
    val length = opt[Int](name = "length", short = 'l', descr = s"Password length (default: ${
      Globals.Defaults.GenStrongPassword.length
    }).",
      default = Some(Globals.Defaults.GenStrongPassword.length))
    val mnemonics = toggle(name = "mnemonics", short = 'n',
      descrYes = "Show mnemonics.", descrNo = "Do not show mnemonics.")
  }
  addSubcommand(genStrongPassword)

  val trailArgs = trailArg[List[String]](required = false, descr = "Additional arguments.")

  verify()

}

sealed class ParseOptsResult(
                              val parsedOpts: ScallopConf
                            )

final case class GenStrongPasswordParseOptsResult(
                                                   length: Int,
                                                   mnemonics: Boolean,

                                                   verbosity: Int,
                                                   override val parsedOpts: ScallopConf
                                                 ) extends ParseOptsResult(parsedOpts)

object ParseOpts {

  def apply(args: Seq[String]): ParseOptsResult = {
    val parsedOpts = new ParseOpts(args)

    parsedOpts.subcommand match {
      case Some(parsedOpts.genStrongPassword) =>
        GenStrongPasswordParseOptsResult(
          length = parsedOpts.genStrongPassword.length.getOrElse(Globals.Defaults.GenStrongPassword.length).max(1),
          mnemonics = parsedOpts.genStrongPassword.mnemonics.getOrElse(false),
          verbosity = parsedOpts.verbosity.getOrElse(0).max(0),
          parsedOpts = parsedOpts,
        )

      case _ =>
        new ParseOptsResult(
          parsedOpts = parsedOpts,
        )
    }
  }

}