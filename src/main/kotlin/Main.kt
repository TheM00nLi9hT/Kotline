import kotlin.random.Random

open class Creature(
    var attack: Int,
    var defense: Int,
    var health: Int,
    var damageRange: IntRange
) {
    fun isAlive(): Boolean {
        return health > 0
    }

    fun heal() {
        val maxHealAmount = (0.3 * health).toInt()
        val healAmount = Random.nextInt(1, maxHealAmount + 1)
        health = kotlin.math.min(health + healAmount, health)
    }

    fun calculateDamage(): Int {
        return Random.nextInt(damageRange.start, damageRange.endInclusive + 1)
    }

    fun takeDamage(damage: Int) {
        health -= damage
        if (health < 0) {
            health = 0
        }
    }

    fun attack(target: Creature) {
        val modifier = attack - target.defense + 1
        val attackRolls = kotlin.math.max(modifier, 1)
        val successfulHits = (1..attackRolls).count { roll -> roll == 5 || roll == 6 }

        if (successfulHits > 0) {
            val damage = calculateDamage()
            target.takeDamage(damage)
            println("Successful hit! $damage damage dealt to the target.")
        } else {
            println("Attack missed!")
        }
    }
}

class Player(
    attack: Int,
    defense: Int,
    health: Int,
    damageRange: IntRange
) : Creature(attack, defense, health, damageRange) {
    fun healSelf() {
        val maxHealAmount = (0.3 * health).toInt()
        val healAmount = Random.nextInt(1, maxHealAmount + 1)
        health = kotlin.math.min(health + healAmount, health)
        println("Player healed for $healAmount. Current health: $health")
    }
}

class Monster(
    attack: Int,
    defense: Int,
    health: Int,
    damageRange: IntRange
) : Creature(attack, defense, health, damageRange)

fun main() {
    val player = Player(20, 15, 100, 5..15)
    val monster = Monster(18, 10, 80, 8..20)

    println("Player's health: ${player.health}")
    println("Monster's health: ${monster.health}")

    // Player attacks the monster
    player.attack(monster)
    println("Monster's health after attack: ${monster.health}")

    // Monster attacks the player
    monster.attack(player)
    println("Player's health after attack: ${player.health}")

    // Player heals themselves
    player.healSelf()
    println("Player's health after healing: ${player.health}")
}
