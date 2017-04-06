import sbt.Def.{task, taskDyn}
import sbt.{AutoPlugin, _}
import Show._
import sbt.plugins.JvmPlugin

import scala.util.Random

object Dino extends AutoPlugin {

  override def trigger = allRequirements
  override def requires = JvmPlugin


  object autoImport {

    val triceratops = settingKey[String]("triceratops")
    val stegosaurus = settingKey[String]("stegosaurus")
    val pterodactyl = settingKey[String]("pterodactyl")
    val velociraptor = settingKey[String]("velociraptor")
    val dimetrodon = settingKey[String]("dimetrodon")
    val trex = settingKey[String]("trex")

    val asciis = Vector(triceratops, stegosaurus, pterodactyl, velociraptor, trex, dimetrodon)

    val circus = settingKey[File]("circus")
    val cherry = settingKey[File]("cherry")
    val gun = settingKey[File]("gun")
    val bike = settingKey[File]("bike")
    val chicken = settingKey[File]("chicken")
    val roar = settingKey[File]("roar")
    val chase = settingKey[File]("chase")

    val dinoCircus = taskKey[String]("run circus task")
    val dinoCherry = taskKey[String]("run cherry task")
    val dinoGun = taskKey[String]("run gun task")
    val dinoBike = taskKey[String]("run bike task")
    val dinoChicken = taskKey[String]("run chicken task")
    val dinoRoar = taskKey[String]("run roar task")
    val dinoChase = taskKey[String]("run chase task")

    val loops = Seq(circus, cherry, gun, bike, chicken, roar, chase)

    val randomAscii = taskKey[String]("random ascii dinosaur")
    val randomLoop = taskKey[File]("random dinosaur loop")

    val dinoMatrix = taskKey[String]("matrix build of dinosaurs")
  }

  import autoImport._

  def dinoCircusTask = Def.task(loop(circus.value, 0))
  def dinoCherryTask = Def.task(loop(cherry.value, 3, "33%", "0%"))
  def dinoGunTask = Def.task(loop(gun.value, 12, "67%", "0%"))
  def dinoBikeTask = Def.task(loop(bike.value, 6, "0%", "33%"))
  def dinoChickenTask = Def.task(loop(chicken.value, 2, "80%","80%"))
  def dinoRoarTask = Def.task(loop(roar.value, 1, "0%", "67%"))
  def dinoChaseTask = Def.task(loop(chase.value, 2, "23%", "60%"))

  val asciiDinoSettings = Seq(
  triceratops :=
    """
      |                       _. - ~ ~ ~ - .
      |   ..       __.    .-~               ~-.
      |   ((\     /   `}.~                     `.
      |    \\\   {     }               /     \   \
      |(\   \\~~^      }              |       }   \
      | \`.-~ -@~      }  ,-.         |       )    \
      | (___     ) _}   (    :        |    / /      `.
      |  `----._-~.     _\ \ |_       \   / /- _      `.
      |         ~~----~~  \ \| ~~--~~~(  + /     ~-.     ~- _
      |                   /  /         \  \          ~- . _ _~_-_.
      |                __/  /          _\  )
      |              .<___.'         .<___/
      |""".stripMargin,
  stegosaurus :=
    """
      |                         .       .
      |                        / `.   .' \
      |                .---.  <    > <    >  .---.
      |                |    \  \ - ~ ~ - /  /    |
      |                 ~-..-~             ~-..-~
      |             \~~~\.'                    `./~~~/
      |   .-~~^-.    \__/                        \__/
      | .'  O    \     /               /       \  \
      |(_____,    `._.'               |         }  \/~~~/
      | `----.          /       }     |        /    \__/
      |       `-.      |       /      |       /      `. ,~~|
      |           ~-.__|      /_ - ~ ^|      /- _      `..-'   f: f:
      |                |     /        |     /     ~-.     `-. _||_||_
      |                |_____|        |_____|         ~ - . _ _ _ _ _>
    """.stripMargin,
  pterodactyl :=
    """
      |                           <\              _
      |                            \\          _/{
      |                     _       \\       _-   -_
      |                   /{        / `\   _-     - -_
      |                 _~  =      ( @  \ -        -  -_
      |               _- -   ~-_   \( =\ \           -  -_
      |             _~  -       ~_ | 1 :\ \      _-~-_ -  -_
      |           _-   -          ~  |V: \ \  _-~     ~-_-  -_
      |        _-~   -            /  | :  \ \            ~-_- -_
      |     _-~    -   _.._      {   | : _-``               ~- _-_
      |  _-~   -__..--~    ~-_  {   : \:}
      |=~__.--~~              ~-_\  :  /
      |                           \ : /__
      |                          //`Y'--\\
      |                         <+       \\
      |                          \\      WWW
      |                          MMM
    """.stripMargin,
  velociraptor :=
    """
      |                                                     ___._
      |                                                   .'  <0>'-.._
      |                                                  /  /.--.____")
      |                                                 |   \   __.-'~
      |                                                 |  :  -'/
      |                                                /:.  :.-'
      |__________                                     | : '. |
      |'--.____  '--------.______       _.----.-----./      :/
      |        '--.__            `'----/       '-.      __ :/
      |              '-.___           :           \   .'  )/
      |                    '---._           _.-'   ] /  _/
      |                         '-._      _/     _/ / _/
      |                             \_ .-'____.-'__< |  \___
      |                               <_______.\    \_\_---.7
      |                              |   /'=r_.-'     _\\ =/
      |                          .--'   /            ._/'>
      |                        .'   _.-'
      |                       / .--'
      |                      /,/
      |                      |/`)
      |                      'c=,
    """.stripMargin,
  trex :=
    """
      |                                              ____
      |  ___                                      .-~. /_"-._
      |`-._~-.                                  / /_ "~o\  :Y
      |      \  \                                / : \~x.  ` ')
      |      ]  Y                              /  |  Y< ~-.__j
      |     /   !                        _.--~T : l  l<  /.-~
      |    /   /                 ____.--~ .   ` l /~\ \<|Y
      |   /   /             .-~~"        /| .    ',-~\ \L|
      |  /   /             /     .^   \ Y~Y \.^>/l_   "--'
      | /   Y           .-"(  .  l__  j_j l_/ /~_.-~    .
      |Y    l          /    \  )    ~~~." / `/"~ / \.__/l_
      ||     \     _.-"      ~-{__     l  :  l._Z~-.___.--~
      ||      ~---~           /   ~~"---\_  ' __[>
      |l  .                _.^   ___     _>-y~
      | \  \     .      .-~   .-~   ~>--"  /
      |  \  ~---"            /     ./  _.-'
      |   "-.,_____.,_  _.--~\     _.-~
      |               ~~     (   _}
      |                      `. ~(
      |                        )  \
      |                  /,`--'~\--'~\
    """.stripMargin,
  dimetrodon :=
    """
      |                          _._
      |                        _/:|:
      |                       /||||||.
      |                       ||||||||.
      |                      /|||||||||:
      |                     /|||||||||||
      |                    .|||||||||||||
      |                    | ||||||||||||:
      |                  _/| |||||||||||||:_=---.._
      |                  | | |||||:'''':||  '~-._  '-.
      |                _/| | ||'         '-._   _:    ;
      |                | | | '               '~~     _;
      |                | '                _.=._    _-~
      |             _.~                  {     '-_'
      |     _.--=.-~       _.._          {_       }
      | _.-~   @-,        {    '-._     _. '~==+  |
      |('          }       \_      \_.=~       |  |
      |`======='  /_         ~-_    )         <_oo_>
      |  `-----~~/ /'===...===' +   /
      |         <_oo_>         /  //
      |                       /  //
      |                      <_oo_>
    """.stripMargin
  )


  override lazy val projectSettings = Seq(
    circus := vid("dino-0-circus.mp4").value,
    cherry := vid("dino-1-cherry.mp4").value,
    gun := vid("dino-2-gun.mp4").value,
    bike := vid("dino-3-bike.mp4").value,
    chicken := vid("dino-4-chicken.mp4").value,
    roar := vid("dino-5-roar.mp4").value,
    chase := vid("dino-6-chase.mp4").value,

    randomAscii := taskDyn {
      val selected = asciis(Random.nextInt(asciis.size))
      task(selected.value)
    }.value,
    randomLoop := taskDyn {
      val selected = loops(Random.nextInt(loops.size))
      task{
        loop(selected.value)
        selected.value
      }
    }.value,

    dinoCircus := dinoCircusTask.value,
    dinoCherry := dinoCherryTask.value,
    dinoGun := dinoGunTask.value,
    dinoBike:= dinoBikeTask.value,
    dinoChicken := dinoChickenTask.value,
    dinoRoar := dinoRoarTask.value,
    dinoChase := dinoChaseTask.value,

    dinoMatrix := {
      dinoCircusTask.value +
      dinoCherryTask.value +
      dinoGunTask.value +
      dinoBikeTask.value +
      dinoChickenTask.value +
      dinoRoarTask.value +
      dinoChaseTask.value
    }
  ) ++ asciiDinoSettings

}
