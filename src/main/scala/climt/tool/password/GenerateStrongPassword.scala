package climt.tool.password

object GenerateStrongPassword {

  import Common.NATO_PHONETIC_ALPHABET

  final case class ParseOptsResult(
                                    length: Int,
                                    mnemonics: Boolean,

                                    override val verbosity: Int,
                                    override val parsedOpts: climt.ParseOpts,
                                  ) extends
    Common.ParseOptsResult(
      verbosity = verbosity,
      parsedOpts = parsedOpts,
    )

  final case class Result(password: String, mnemonics: List[String])

  def work(length: Int): Result = {
    val s = scala.util.Random.alphanumeric.filter(
      NATO_PHONETIC_ALPHABET.isDefinedAt(_)
    ).take(length)
    Result(
      password = s.mkString,
      mnemonics = s.map(NATO_PHONETIC_ALPHABET(_)).toList,
    )
  }

  def apply(opts: ParseOptsResult) = {
    val result = work(opts.length)
    print(result.password)
    if (opts.mnemonics) {
      print("\t")
      print(result.mnemonics.mkString(" "))
    }
    println()
  }
}