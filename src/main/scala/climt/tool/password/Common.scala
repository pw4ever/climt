package climt.tool.password

object Common {
  final val NATO_PHONETIC_ALPHABET: Map[Char, String] = Map[Char, String](
    'a' -> "Alfa", 'b' -> "Bravo", 'c' -> "Charlie", 'd' -> "Delta", 'e' -> "Echo", 'f' -> "Foxtrot", 'g' -> "Golf",
    'h' -> "Hotel", 'i' -> "India", 'j' -> "Juliet", 'k' -> "Kilo", 'l' -> "Lima", 'm' -> "Mike", 'n' -> "November",
    'o' -> "Oscar", 'p' -> "Papa", 'q' -> "Quebec", 'r' -> "Romeo", 's' -> "Sierra", 't' -> "Tango",
    'u' -> "Uniform", 'v' -> "Victor", 'w' -> "Whiskey", 'x' -> "Xray", 'y' -> "Yankee", 'z' -> "Zulu",
    '0' -> "0", '1' -> "1", '2' -> "2", '3' -> "3", '4' -> "4",
    '5' -> "5", '6' -> "6", '7' -> "7", '8' -> "8", '9' -> "9",
  ).flatMap((entry: (Char, String)) => {
    val k = entry._1
    val v = entry._2
    List(k.toLower -> v.toLowerCase, k.toUpper -> v.toUpperCase)
  })

  class ParseOptsResult(
                         override val verbosity: Int,
                         override val parsedOpts: climt.ParseOpts,
                       ) extends
    climt.ParseOptsResult(
      verbosity = verbosity,
      parsedOpts = parsedOpts,
    )
}
