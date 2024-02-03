package com.example.wallproject.Model.Characters

import kotlin.random.Random

abstract class Character{

    abstract var name : String
    abstract var health : Double
    open var isDead : Boolean = false
    abstract var attack : Attack
    abstract var defense : Defense

    fun isDead (): Boolean {
        return (health <= 0)
    }

    open fun attack(character: Character){

        var trueAttack = calculateAttackTrue(character)

        if(isCritical(trueAttack.luck)){
            trueAttack.attackDmg = trueAttack.attackDmg * 1.5
            trueAttack.abilityPower = trueAttack.abilityPower * 1.5
        }

        character.health = character.health - (trueAttack.attackDmg + trueAttack.abilityPower)

    }

    private fun calculateDefensePercentage(character: Character) : Defense{

        var resultArmor = (100 * character.defense.armor / character.health) / 100

        var resultMagicResist = (100 * character.defense.magicResist / character.health) / 100

        if(resultArmor > 0.8)
            resultArmor = 0.8

        if (resultMagicResist > 0.8)
            resultMagicResist = 0.8

        return Defense(resultArmor, resultMagicResist)

    }

    private fun calculateLuckPercentage() : Double {
        return 100 * this.defense.magicResist / this.health
    }

    private fun calculateAttackTrue(character: Character) : Attack{

        var resultDefense = calculateDefensePercentage(character)

        var resultAttack : Attack = Attack(0.0, 0.0, 0.0)

        resultAttack.attackDmg = this.attack.attackDmg * resultDefense.armor

        resultAttack.abilityPower = this.attack.abilityPower * resultDefense.magicResist

        resultAttack.luck = calculateLuckPercentage()

        return resultAttack

    }

    private fun isCritical (luck : Double) : Boolean {

        var result = Random.nextDouble(101.0)

        return (result < luck)

    }

}
