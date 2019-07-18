package climt

object Main extends App {
  ParseOpts(args.toIndexedSeq) match {

    case opts: GenStrongPasswordParseOptsResult =>
      tool.GenerateStrongPassword(opts)

    case opts => // no tool specified
      opts.parsedOpts.printHelp()

  }
}