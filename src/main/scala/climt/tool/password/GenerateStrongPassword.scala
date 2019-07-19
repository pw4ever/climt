package climt.tool.password

object GenerateStrongPassword {

  import Common.NATO_PHONETIC_ALPHABET

  final case class ParseOptsResult(
                                    length: Int,
                                    mnemonics: Boolean,
                                    repeat: Int,
                                    lengths: List[Int],

                                    override val verbosity: Int,
                                    override val parsedOpts: climt.ParseOpts,
                                  ) extends
    Common.ParseOptsResult(
      verbosity = verbosity,
      parsedOpts = parsedOpts,
    )

  final case class Result(password: String, mnemonics: List[String]) {
    override def toString = password
    def toString(showMnemonics: Boolean) =
      if (showMnemonics) s"${password}\t${mnemonics.mkString(" ")}" else password
  }

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
    val items = if (opts.lengths.isEmpty) List.fill(opts.repeat)(opts.length) else opts.lengths
    for (item <- items) println(work(item).toString(opts.mnemonics))
  }
}