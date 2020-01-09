import com.buzzfactory.dag.{Position, Size, Room, Door}

val doors = (new Door(new Position(9, 5)) :: Nil)
val room = Room(new Position(0,0), new Size(10,10), doors)
