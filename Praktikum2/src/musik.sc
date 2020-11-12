import scala.io.Source

/*
case class Track(title: String, length: String, rating: Int, features: List[String], writers: List[String])
case class Album(title: String, date: String, artist: String, tracks: List[Track])
*/

case class Track(title: String, length: String, rating:Int, features: List[String], writers: List[String]) {
  override def toString() : String = {
    "\n\tTrack:\n\t\tTitle:" + title + "\n\t\tLength:" + length + "\n\t\tRating:" + rating + "\n\t\tFeatures:" + features.toString() + "\n\t\tWriters:" + writers.toString()
  }
}
case class Album(title: String, date: String, artist:String, tracks: List[Track]) {
  override def toString() : String = {
    "Album:\n\tTitle:" + title + "\n\tDate:" + date + "\n\tArtist:" + artist + "\n\tTracks:" + tracks.toString() + "\n\t"
  }
}

val file_content:List[Char] = Source.fromFile("D:/Uni-Projekte/KMPS/Praktika/Praktikum2/src/alben.xml").toList

def createTokenList(file_content: List[Char], tokenList: List[String], token:String) : List[String] = token match
{
  case "" => file_content match
  {
    case Nil => tokenList
    case '<'::xs => createTokenList(xs, tokenList, "")
    case '>'::xs => createTokenList(xs, tokenList, "")
    case '\n'::xs => createTokenList(xs, tokenList, "")
    case '\t'::xs => createTokenList(xs, tokenList, "")
    case '\r'::xs => createTokenList(xs, tokenList, "")
    case x::xs => createTokenList(xs, tokenList, token + x)
  }
  case _ => file_content match
  {
    case Nil => tokenList
    case '<'::xs => createTokenList(xs, tokenList :+ token, "")
    case '>'::xs => createTokenList(xs, tokenList :+ token, "")
    case '\n'::xs => createTokenList(xs, tokenList :+ token, "")
    case '\t'::xs => createTokenList(xs, tokenList :+ token, "")
    case '\r'::xs => createTokenList(xs, tokenList :+ token, "")
    case x::xs => createTokenList(xs, tokenList, token + x)
  }
}

print(createTokenList(file_content, Nil, ""))

def getPosAlb(endAlbum : List[String]) : List[String] = endAlbum match {
  case "/album" :: xs => xs
  case x :: xs => getPosAlb(xs)
}

def getPosTrack(endTrack : List[String]) : List[String] = endTrack match {
  case "/track" :: xs => xs
  case x :: xs => getPosTrack(xs)
}


def parseTrack(tokenList : List[String], lied : Track) : Track = tokenList match {
  case Nil => lied
  case "/track" :: xs => lied
  case "title" :: x :: xs => parseTrack(xs, lied.copy(title = x))
  case "length" :: x :: xs => parseTrack(xs, lied.copy(length = x))
  case "rating" :: x :: xs => parseTrack(xs, lied.copy(rating = x.toInt))
  case "feature" :: x :: xs => parseTrack(xs, lied.copy(features = lied.features :+ x))
  case "writing" :: x :: xs => parseTrack(xs, lied.copy(writers = lied.writers :+ x))
  case _ :: xs => parseTrack(xs, lied)
}

def parseAlbum(tokenList : List[String], albi : Album) : Album = tokenList match {
  case Nil => albi
  case "/album" :: xs => albi
  case "title" :: x :: xs => parseAlbum(xs, albi.copy(title = x))
  case "date" :: x :: xs => parseAlbum(xs, albi.copy(date = x))
  case "artist" :: x :: xs => parseAlbum(xs, albi.copy(artist = x))
  case "track" :: x :: xs => parseAlbum(getPosTrack(x :: xs), albi.copy(tracks = albi.tracks :+ parseTrack(x :: xs, Track("","",0,Nil,Nil))))
  case _ :: xs => parseAlbum(xs, albi)
}

def parseFile(tokenList : List[String], albumListe : List[Album]) : List[Album] = tokenList match {
  case Nil => albumListe
  case "album" :: x :: xs => parseFile(getPosAlb(x :: xs), albumListe :+ parseAlbum(x :: xs, Album("","","",Nil)))
  case "/album" :: xs => parseFile(xs, albumListe)
  case _::xs => parseFile(xs, albumListe)
}

println(parseFile(createTokenList(file_content, Nil, ""), Nil))




