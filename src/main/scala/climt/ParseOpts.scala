package climt

import climt.tool.password.Common

import scala.language.reflectiveCalls
import org.rogach.scallop._

class ParseOpts(args: Seq[String]) extends ScallopConf(args) {
  appendDefaultToDescription = true

  version(
    s"""|${Globals.appname} ${Globals.appver} ${Globals.maintainerName} <${Globals.maintainerEmail}>""".stripMargin
  )

  banner(
    s"""
       |Command-Line Interface Multi-Tool (CLIMT) is a collection of CLI utilities.
       |""".stripMargin
  )

  val verbosity = tally(
    name = "verbose", short = 'v', descr = "Increase verbosity.",
  )

  val password = new Subcommand(
    "p", "pass", "password",
  ) {
    appendDefaultToDescription = true

    val genStrongPassword = new Subcommand(
      "g", "gp", "genpass", "generateStrongPassword",
    ) {
      appendDefaultToDescription = true

      val length = opt[Int](name = "length", short = 'l', descr = s"Password length.",
        default = Some(Globals.Defaults.Password.GenStrongPassword.length),
      )
      val mnemonics = toggle(name = "mnemonics", short = 'n',
        descrYes = "Show mnemonics.", descrNo = "Do not show mnemonics.",
        default = Some(false),
      )
    }
    addSubcommand(genStrongPassword)

    val mapPasswordToMnemonics = new Subcommand(
      "m", "mp", "mappass", "mapPasswordToMnemonics",
    ) {
      appendDefaultToDescription = true

      val passwords = trailArg[List[String]](
        required = false,
        descr = "Passwords to be mapped to mnemonics.",
        default = Some(List()),
      )
    }
    addSubcommand(mapPasswordToMnemonics)
  }
  addSubcommand(password)

  verify()
}

class ParseOptsResult(
                       val verbosity: Int,
                       val parsedOpts: ParseOpts,
                     )

object ParseOpts {

  def apply(args: Seq[String]): ParseOptsResult = {
    val parsedOpts = new ParseOpts(args)
    val verbosity = parsedOpts.verbosity().max(0)

    import climt.tool._

    parsedOpts.subcommands match {
      case List(
      parsedOpts.password,
      parsedOpts.password.mapPasswordToMnemonics,
      ) =>
        password.MapPasswordToMnemonics.ParseOptsResult(
          passwords = parsedOpts.password.mapPasswordToMnemonics.passwords(),
          verbosity = verbosity,
          parsedOpts = parsedOpts,
        )

      case List(
      parsedOpts.password,
      parsedOpts.password.genStrongPassword,
      ) =>
        password.GenerateStrongPassword.ParseOptsResult(
          length = parsedOpts.password.genStrongPassword.length().max(1),
          mnemonics = parsedOpts.password.genStrongPassword.mnemonics(),
          verbosity = verbosity,
          parsedOpts = parsedOpts,
        )

      case List(
      parsedOpts.password,
      ) =>
        new password.Common.ParseOptsResult(
          verbosity = verbosity,
          parsedOpts = parsedOpts,
        )

      case _ =>
        new ParseOptsResult(
          verbosity = verbosity,
          parsedOpts = parsedOpts,
        )
    }

  }

}