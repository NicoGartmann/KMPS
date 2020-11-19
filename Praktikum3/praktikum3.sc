import scala.::
import scala.io.Source

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

//Linux-Pfad (fuer Laptop)
/*
val file_content:List[Char] = Source.fromFile("IdeaProjects/test/src/alben.xml").toList
val jackson_content:List[Char] = Source.fromFile("IdeaProjects/test/src/jackson.xml").toList
 */

//Windows-Pfad (fuer PC)

val file_content:List[Char] = Source.fromFile("D:/Uni-Projekte/KMPS/Praktika/Praktikum3/alben.xml").toList
val jackson_content:List[Char] = Source.fromFile("D:/Uni-Projekte/KMPS/Praktika/Praktikum3/jackson.xml").toList

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

val token_alben = createTokenList(file_content, Nil, "")
val token_jackson = createTokenList(jackson_content, Nil, "")

val list_alben = parseFile(token_alben, Nil)
val list_jackson = parseFile(token_jackson, Nil)

/**
 * ------------------------------------- ab hier P3 ---------------------------------------------------
 */


/**
 * Nimmt eine Liste vom Typ A entgegen und gibt eine List vom Typ A zurück, auf
 * die die Funktion func angewendet wurde
 *
 * Aufgabe 1a
 *
 * @param input_list List[A]
 * @param func A => A
 * @tparam A
 * @return List[A]
 */
def map[A](input_list: List[A], func:A => A): List[A] = input_list match {
  case Nil => Nil
  case y :: ys => func(y) :: map(ys, func)
}

/**
 * Nimmt eine Liste von Alben entgeben und gibt eine Liste von Alben aus,
 * bei der alle Alben Titel gross geschrieben sind
 *
 * Aufgabe 1b
 *
 * @param alben
 * @return
 */
def albenToUpper(alben: List[Album]): List[Album] =
  map[Album](alben, alben => alben.copy(title = alben.title.toUpperCase()))

albenToUpper(list_alben)

/**
 * Nimmt eine Liste von Alben entgegen und gibt eine Liste von Alben
 * zurueck, bei denen jeder Track Titel und der Alben Titel gross
 * geschrieben ist
 *
 * Aufgabe 1c
 *
 * @param alben
 * @return List[Album]
 */
def listAlbumToUpper(alben: List[Album]): List[Album]
  = map[Album](alben, alben => alben.copy(title = alben.title.toUpperCase(),
  tracks = map[Track](alben.tracks, Track => Track.copy(Track.title.toUpperCase()))))

listAlbumToUpper(list_alben)

/**
 * Nimmt eine Liste vom Typ A entgeben und liefert eine Liste vom Typ B, auf der die
 * Funktion func angewendet wurde
 *
 * Aufgabe 1d
 *
 * @param input_list
 * @param func
 * @tparam A
 * @tparam B
 * @return List[B]
 */
def poly_map[A,B](input_list: List[A], func:A => B): List[B] = input_list match {
  case Nil => Nil
  case y :: ys => func(y) :: poly_map(ys, func)
}

/**
 * Nimmt eine input_liste von Alben entgeben und liefert eine Liste von Strings,
 * bei denen die Länge der Tracks aufgezaehlt wird
 *
 * Aufgabe 1e
 *
 * @param input_list
 * @return List[Album]
 */
def albTrackList(input_list: List[Album]): List[List[String]]
 = poly_map[Album, List[String]](input_list, Album
    => poly_map[Track,String](Album.tracks, Track => Track.length))

albTrackList(list_alben)

/**
 * Nimmt eine Liste vom Typ A entgeben und gibt eine Liste vom Typ A zurueck,
 * bei der die condition zutrifft
 *
 * Aufgabe 2a
 *
 * @param input_list List[A]
 * @param condition A => Boolean
 * @return List[A]
 */
def filter[A](input_list: List[A], condition:A => Boolean): List[A] = input_list match {
  case Nil => input_list
  case x :: xs => if(condition(x)) x :: filter(xs, condition) else filter(xs, condition)
}

/**
 * Nimmt eine liste von Tracks entgegen und gibt eine Liste von Tracks mit einer
 * Bewertung >= 4 zurueck
 *
 * Aufgabe 2b
 *
 * @param tracks
 * @return List[Track]
 */
def tracksHigherFour(tracks: List[Track]): List[Track]
  = filter[Track](tracks, Track => Track.rating >= 4)

/**
 * Nimmt ein Album entgegen und liefert eine Liste der dazugehoerigen Tracks
 *
 * Hilfsfunktion
 *
 * @param album
 * @return List[Track]
 */
def getTracksFromAlb(album: List[Album]): List[Track] = {
  def getTracks(album: Album): List[Track] = {
    album.tracks
  }
  album match {
    case Nil => Nil
    case x :: xs => getTracks(x) ::: getTracksFromAlb(xs)
  }
}

tracksHigherFour(getTracksFromAlb(list_jackson))

/**
 * Nimmt eine Liste von Alben entgegen und Liefert eine Liste von Strings, in der
 * alle Titel von Rod Temperton gelistet sind
 *
 * Aufgabe 2c
 *
 * @param album
 * @return List[String]
 */
def ListofRodyTitles(album : List[Album]) : List[String] = {
  def byRody(album : List[Album]) : List[Track] = {
    def sort(track : Track) : Boolean = {
      def check(writer : List[String]) : Boolean = writer match {
        case Nil => false
        case x::xs => if(x == "Rod Temperton") true else check(xs)
      }
      check(track.writers)
    }
    album match {
      case Nil => Nil
      case x::xs => filter[Track](x.tracks, sort)
    }
  }
  poly_map[Track,String](byRody(album), Track => Track.title)
}

ListofRodyTitles(list_jackson)

/**
 * Nimmt eine Liste entgegen und splittet diese in Teil-Listen, wenn ein Element erreicht ist, bei dem
 * condition false zurueckgibt
 *
 * Aufgabe 3a
 *
 * @param input_list
 * @param condition
 * @tparam A
 * @return List[List[A]]
 */
def partition[A](input_list: List[A], condition: A => Boolean): List[List[A]] = {
  def hilf[A](input_list: List[A], condition: A => Boolean, output_list: List[List[A]], inner_list: List[A]): List[List[A]] = input_list match {
    case Nil => output_list :+ inner_list
    case x :: xs => {
      if(!condition(x)) hilf(xs, condition, output_list, inner_list :+ x)
      else hilf(xs, condition, output_list :+ inner_list, Nil)
    }
  }
  hilf[A](input_list, condition, Nil, Nil)
}

partition[Char]('a'::'b'::'c'::'D'::'e'::'f'::'G'::'H'::'i'::'J'::Nil, x => x.isUpper)


/**
 * Nimmt eine Liste von Char entgegen und erzeugt eine Token-Liste
 *
 * Aufgabe 3c
 *
 * @param input_list
 * @return List[String]
 */
def createTokenListb(input_list : List[Char]) : List[String] = {
  def isBlank(s: String): Boolean = s.trim.isEmpty
  def isTag(c: Char): Boolean = { c == '>' || c == '<' }
    filter[String](poly_map[List[Char],String](partition[Char](input_list, isTag), x => x.mkString), y => !isBlank(y))
}

createTokenListb(jackson_content)

/**
 * Nimmt einen Startwert von und eine Endwert bis entgegen und wendet auf alle
 * Elemente in diesen Bereich die Funktion func an
 *
 * Aufgabe 4a
 *
 * @param func
 * @param operation
 * @param von
 * @param bis
 * @return Int
 */
def sumProd(func: Int => Int, operation:(Int, Int) => Int, von: Int, bis: Int): Int = {
  if(von > bis) 0 else operation(func(von), sumProd(func, operation, von + 1, bis))
}

sumProd(x => x, (x,y) => x + y, 1, 5)
sumProd(x => x * x, (x,y) => x + y, 1, 5)
sumProd(x => x, (x, y) => x - y, 1, 5)

/**
 * Verknuepft für alle nichtleeren Integer-Listen mittels Funktion func alle Listenelemente
 * von links nach rechts
 *
 * Hilfsfunktion
 *
 * @param f
 * @param start
 * @param xs
 * @return Int
 */
def foldL(f:(Int, Int) => Int, start: Int, xs: List[Int]): Int = xs match {
  case x :: Nil => f(start, x)
  case h :: hs => foldL(f, f(start,h), hs)
}


/**
 * Erzeugt eine Liste von Integern zwischen a und b
 *
 * Hilfsfunktion
 *
 * @param a
 * @param b
 * @return List[Int]
 */
def range(a: Int, b: Int): List[Int] = {
  if(a > b) Nil else a::range(a + 1, b)
}

/**
 * Nimmt einen Startwert von und eine Endwert bis entgegen und wendet auf alle
 * Elemente in diesen Bereich die Funktion func an
 *
 * Aufgabe 4d
 *
 * @param func
 * @param operation
 * @param von
 * @param bis
 * @return Int
 */
def sumProd3(func: Int => Int, operation: (Int, Int) => Int, von: Int, bis: Int): Int = {
  foldL(operation, 0,map[Int](range(von, bis), x => func(x)))
}

sumProd3(x => x, (x,y) => x + y, 1, 5)
sumProd3(x => x * x,(x,y) => x + y, 1, 5)
sumProd3(x => x, (x,y) => x - y, 1, 5)


