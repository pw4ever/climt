package climt.tool.password

object MapPasswordToMnemonics {

  import Common.NATO_PHONETIC_ALPHABET

  final case class ParseOptsResult(
                                    passwords: List[String],

                                    override val verbosity: Int,
                                    override val parsedOpts: climt.ParseOpts,
                                  ) extends
    Common.ParseOptsResult(
      verbosity = verbosity,
      parsedOpts = parsedOpts,
    )

  final case class Result(password: String, mnemonics: List[String])

  def work(password: String): Result = {
    Result(
      password = password,
      mnemonics = password.toList.map(
        (c) => if (NATO_PHONETIC_ALPHABET.isDefinedAt(c)) NATO_PHONETIC_ALPHABET(c) else c.toString
      ),
    )
  }

  def apply(opts: ParseOptsResult) = {
    for (password <- opts.passwords) {
      println(s"${password}\t${work(password).mnemonics.mkString(" ")}")
    }
  }
}
