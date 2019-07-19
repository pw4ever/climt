package climt

import tool._

object Main extends App {
  ParseOpts(args.toSeq) match {
    case opts: password.GenerateStrongPassword.ParseOptsResult =>
      password.GenerateStrongPassword(opts)

    case opts: password.MapPasswordToMnemonics.ParseOptsResult =>
      password.MapPasswordToMnemonics(opts)

    case opts: password.Common.ParseOptsResult =>
      opts.parsedOpts.password.printHelp()

    case opts => // no tool specified
      opts.parsedOpts.printHelp()
  }
}