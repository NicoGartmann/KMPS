def foldL(f:(Int, Int) => Int, start: Int, xs: List[Int]): Int = xs match {
  case x :: Nil => f(start, x)
  case h :: hs => foldL(f, f(start,h), hs)
}
foldL((x,y) => x*y, 1, 1::2::3::4::Nil)

def getToFalse[A](list: List[A], condition: A => Boolean, out: List[A]): List[A] = list match {
  case Nil => out
  case x :: xs => if(condition(x)) getToFalse(xs, condition, out :+ x) else out
}

getToFalse[Int](2::4::6::8::9::10::Nil, x => x != 8 , Nil)

def poly_map[A,B](input_list: List[A], func:A => B): List[B] = input_list match {
  case Nil => Nil
  case y::ys => func(y) :: poly_map(ys, func)
}

def filter[A](input_list: List[A], condition:A => Boolean): List[A] = input_list match {
  case Nil => input_list
  case x::xs => if(condition(x)) x::filter(xs, condition) else filter(xs, condition)
}

def test[A](input_list: List[A], condition: A => Boolean, output_list: List[List[A]], tmp: List[A]): List[List[A]] = input_list match {
  case Nil => output_list
  case x :: xs => {
    if(condition(x)) test(xs, condition, output_list, tmp :+ x)
    else test(xs, condition, output_list :+ tmp, Nil)
  }
}

test[Int](1::2::3::4::5::6::7::Nil, x => x != 4, Nil, Nil)

test[Int](2::4::6::8::1::2::4::6::8::Nil, x => x % 2 == 0, Nil, Nil)

