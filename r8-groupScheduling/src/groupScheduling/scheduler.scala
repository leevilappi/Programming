
package groupScheduling

/**
 * A simple scheduler that assigns assistants to tutorial groups.
 */
object scheduler {
  /**
   * Schedule assistants to a set of tutorial groups.
   * Each tutorial group should have exactly one assistant and
   * each assistant can only teach at most one group.
   * In addition, assistants' preferences should be respected.
   * @param preferences  the container object containing all the groups and assistants' preferences
   * @return An assignment from groups to assistants that fulfill the above conditions, or
   *         None if it is not possible to assign an assistant to each group.
   */
  def schedule[A, G](preferences: Preferences[A, G]): Option[Map[G, A]] = {
    /* The inner function that does all the actual work.
     * @param freeGroups       the set of groups that do not yet have an assistant
     * @param freeAssistants   the set of assistants that have not been assigned to any group
     */
    def inner(freeGroups: Set[G], freeAssistants: Set[A]): Option[Map[G, A]] = {
      /* No free groups that lack an assistant? Return an empty group assignment. */
      if (freeGroups.isEmpty) return Some(Map[G, A]())

      /* Are there more free groups than available assistants? */
      if (freeAssistants.size < freeGroups.size) return None

      /* Find a group that has a smallest number of assistants who can teach the group */
      val chosenGroup = freeGroups.minBy(group => preferences.assistantsForGroup(group).intersect(freeAssistants).size)
      /* .. and all the assistants that can teach the chosen group */
      val potentialAssistants = preferences.assistantsForGroup(chosenGroup).intersect(freeAssistants)

      if (potentialAssistants.isEmpty) {
        /* No-one can take the group, therefore no schedule is possible.
         * Return None to indicate this. */
        return None
      } else {
        /* Iterate over all potential assistants.
	 		 	 * For each such assistant, (recursively) test if a solution can be found
     	 	 * in the case the assistant is assigned to chosenGroup; that is, test if there is a solution
     	   * when chosenGroup is removed from the free groups set and the assistant is removed from
     	   * the free assistants set.
		 	   * If finding a solution to this reduced instance is possible,
		 	   * return that solution extended with the information that chosenGroup is being teached by that assistant.
     	   */
        
        for(i <- potentialAssistants){
           if(inner(freeGroups - chosenGroup,freeAssistants-i).isDefined){
             return Some(inner(freeGroups - chosenGroup,freeAssistants-i).get + (chosenGroup -> i))
           }
         }
        
        /* No solution found, return None to indicate this. */
        return None
      }
    }
    inner(preferences.groups.toSet, preferences.assistants)
  }

  /**
   * Test if sol is a valid solution to the scheduling problem,
   * meaning that all the groups are assigned an assistant and
   * each assistant teaches at most one group.
   */
  def isValidSolution[A, G](sol: Map[G, A], prefs: Preferences[A, G]): (Boolean, String) = {
    if (sol.keySet != prefs.groups.toSet) return (false, "The set of groups does not match")
    for ((group, assistant) <- sol if !prefs.assistants.contains(assistant))
      return (false, s"Assistant '$assistant' is not mentioned in the original preferences")
    for ((assistant, assignments) <- sol.groupBy(_._2) if (assignments.size > 1))
      return (false, s"Assistant '$assistant' is assigned to more than one group")
    (true, "")
  }
}

