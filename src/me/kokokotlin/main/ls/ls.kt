package me.kokokotlin.main.ls

/**
 *  represents a rule of the lindenmayer system
 *
 *  the member rule is a lambda expression which should contain
 *  the logic the match a char and return a boolean
 *
 *  the string out is the the string that will be appended to the new
 *  state of the lindermayer system if a rule matches
 */
data class Rule(val rule : (c : Char) -> Boolean, val out : String)


/**
 *  represents a l-system
 */
class Lindenmayer {

    // current state of the system represented as a string
    private var V = ""

    // getter for the state
    val state
        get() = V

    // a set of rule which will be applied to all chars in V
    private val P : Array<Rule>

    constructor(omega : String, P : Array<Rule>) {
        V = omega
        this.P = P
    }

    /**
     *  makes one cycle of the l-system
     *
     */
    fun makeIteration() {

        // holds the "next" state of the system
        var V0 = ""

        // iterate over all chars in the state
        for(c in V) {

            var found = false

            // if a rule matches append the out string to the new state and go on
            P.forEach l1@ {
                if(it.rule(c)) {
                    V0 += it.out
                    found = true
                    return@l1
                }
            }

            // if no rule matches append the char itself and go on
            if(!found) {
                V0 += c
            }
        }

        // set the old to the new state
        V = V0
    }
}