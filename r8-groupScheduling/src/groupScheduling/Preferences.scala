
package groupScheduling

/**
 * A convenience container for storing
 * groups, assistants' preferences and derived information.
 * The type A is the type for Assistants and G for Groups.
 * @param groups   the ordered set of all tutorial groups
 * @param prefs    associates all assistants to a set of groups that s/he can teach
 */
sealed class Preferences[A, G](val groups: IndexedSeq[G], val prefs: Map[A, Set[G]]) {
  require(prefs.values.forall(gs => gs.subsetOf(groups.toSet)), "All the groups mentioned in prefs must be contained in the argument 'groups'")
  /** The set of assistants */
  val assistants: Set[A] = prefs.keySet

  /** "Inverse" of prefs: mapping from groups to available assistants */
  val assistantsForGroup: Map[G, Set[A]] =
    groups.map(group => (group, prefs.filter(_._2.contains(group)).keySet)).toMap
}

object Preferences {
  /**
   * Read textual format preferences of form
   * val group = IndexedSeq("Mon 10-12", "Tue 12-14", "Wed 8-10", "Wed 14-16", "Thu 18-20", "Fri 8-10")
   * val pref = Map[String, String](
   * "a1" -> "   x  ",
   * "a2" -> " x x  ",
   * "a3" -> "xxx xx",
   * "a4" -> "   x x",
   * "a5" -> " x x x",
   * "a6" -> "xx xx ")
   * Here assistant a1 can take only the Wednesday 14-16 group, a2 can take Tue 12-14 and Wed 14-16 groups etc.
   */
  def apply[G](groups: IndexedSeq[G], prefs: Map[String, String]): Preferences[String, G] = {
    require(groups.nonEmpty, "There must be at least one group")
    require(prefs.keys.forall(_.nonEmpty), "The assistant names must be non-empty")
    require(prefs.forall(_._2.length == groups.length), "The lenght of the preferences strings must match with the number of groups")
    require(prefs.forall({ case (a, g) => g.forall(c => c == ' ' || c == 'x') }), "The preferences strings must contain only ' ' and 'x'")

    if (prefs.isEmpty) return new Preferences(groups, Map())
    val decodedPrefs: Map[String, Set[G]] = prefs.map({
      case (aname, groupsString) =>
        (aname, groupsString.zipWithIndex.filter(_._1 == 'x').map(p => groups(p._2)).toSet)
    })
    new Preferences(groups, decodedPrefs)
  }
}

